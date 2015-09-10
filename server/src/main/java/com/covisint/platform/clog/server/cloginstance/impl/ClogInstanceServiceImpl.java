/* Copyright (C) 2014 Covisint. All Rights Reserved. */

package com.covisint.platform.clog.server.cloginstance.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.covisint.core.http.service.client.InvalidRequestException;
import com.covisint.core.http.service.core.ServiceException;
import com.covisint.core.http.service.server.ResourceNotFoundException;
import com.covisint.core.http.service.server.service.BaseResourceService;
import com.covisint.core.support.constraint.Nonnull;
import com.covisint.core.support.constraint.NotEmpty;
import com.covisint.platform.clog.core.ClogInstanceStatusE;
import com.covisint.platform.clog.core.cloginstance.ClogInstance;
import com.covisint.platform.clog.server.cloginstance.ClogInstanceDAO;
import com.covisint.platform.clog.server.cloginstance.ClogInstanceService;
import com.covisint.platform.clog.server.elasticsearch.ElasticSearchException;
import com.covisint.platform.clog.server.elasticsearch.ElasticSearchService;
import com.covisint.platform.clog.server.exception.handler.ClogServerException;
import com.covisint.platform.clog.server.exception.handler.UpstreamServerException;
import com.covisint.platform.clog.server.groupservice.GroupService;
import com.google.common.base.Strings;


/**
 * ClogInstanceServiceImpl class implementing the REST calls to create and delete a
 * CLOG instance. 
 * 
 * @since August 25, 2015
 *
 */
public final class ClogInstanceServiceImpl extends BaseResourceService<ClogInstance, ClogInstanceDAO> 
			implements ClogInstanceService {
	
	/** Logger for this class */
    private static final Logger LOG = LoggerFactory.getLogger(ClogInstanceServiceImpl.class);
    
    /** Group Service instance used to make Group Service calls. This is a wrapper class around Group Service */
    private GroupService groupService;
    
    /** ElasticSearchService wrapper class to make Elastic Search specific calls */
    private ElasticSearchService elasticSearchService;
    

	/**
	 * Constructor Constructs the Service Impl using the passed in
	 * {@link ClogInstanceDAO}
	 * 
	 * @param dao
	 *            - Data Access Object for the {@link ClogInstance}
	 * @param groupService
	 *            Group client instance.
	 * @param elasticSearchService
	 *            ElasticSearchService object instance
	 */
    public ClogInstanceServiceImpl(@Nonnull final ClogInstanceDAO dao,
            					   @Nonnull GroupService groupService, 
            					   @Nonnull @NotEmpty ElasticSearchService elasticSearchService) {
    	
        super(dao);
        this.groupService = groupService;
        this.elasticSearchService = elasticSearchService;
    }
   
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.covisint.core.http.service.server.service.BaseResourceService#update(com.covisint.core.http.service.core.Resource)
     */
    @Nonnull
    @Transactional
    @Override
    public ClogInstance add(@Nonnull ClogInstance clogInstance) {

    	/** Validate the incoming resource object for the mandatory fields */
    	validateAdd(clogInstance);
    	
    	/** Add the status of the newly created CLOG Instance */
    	clogInstance.setStatus(ClogInstanceStatusE.ACTIVE);
    	
    	/*
    	 * Create Group Service first
    	 */
		try {
			groupService.add(clogInstance);
		} catch (ServiceException e) {
			ClogInstanceServiceImpl.LOG.error("Group Service creation failed. Exception: {}", e);
			throw new UpstreamServerException("CLOG-01", "Clog instance creation failed due to GroupService failure. Exception: " + e);
		}
        		
		/*
		 * New create alias in elastic search using the platform instance id as the
		 * name of the alias
		 */
        try {
			elasticSearchService.createAlias(clogInstance);
            LOG.info("ClogInstance service creation !!!");
		} catch (ElasticSearchException e) {
			/*
			 * In case elastic search alias creation fails, we need to roll back the transaction
			 * which means, since Group Service is already created, we need to explicitly delete
			 * it
			 */
			ClogInstanceServiceImpl.LOG.error("Elastic Search alias creation failed. Exception: {}", e);
			ClogInstanceServiceImpl.LOG.error("Deleting group created...");
			groupService.delete(clogInstance);
			ClogInstanceServiceImpl.LOG.error("Group deleted...");
			throw new UpstreamServerException("CLOG-02", "Clog instance creation failed due to upstream elastic search server. Exception: " + e);
		}
        
        /*
         * Make an entry in the CLOG_INSTANCE DB
         */
        final ClogInstance newClogInstance = getResourceDao().add(clogInstance);
        
		/*
		 * If entry in the DB failed, rollback the transaction, which would mean
		 * that we need to delete the newly created Group and alias in elastic
		 * search. In case the rollback fails, we shall log the information and
		 * exist with appropriate message and there could be some residual data
		 * 
		 */
        if (null == newClogInstance) {
        	
        	ClogInstanceServiceImpl.LOG.error("DB entry failed for Clog Instance. Rolling back the transaction...");
        	
            if (ClogInstanceServiceImpl.LOG.isDebugEnabled()) {
            	ClogInstanceServiceImpl.LOG.debug("Deleting group created...");
            }
            
            try { 
            	groupService.delete(clogInstance);
            } catch (ServiceException e) {
            	ClogInstanceServiceImpl.LOG.warn("Error while deleting group service entry while rolling back the transaction. Rollback reason, entry in DB failed");
            }
            
            if (ClogInstanceServiceImpl.LOG.isDebugEnabled()) {
            	ClogInstanceServiceImpl.LOG.debug("Group Service deleted during rollback of transaction...");
            }
            
            try {
            	elasticSearchService.deleteAlias(clogInstance);
                if (ClogInstanceServiceImpl.LOG.isDebugEnabled()) {
                	ClogInstanceServiceImpl.LOG.debug("Elastic Search alias deleted during rollback of transaction...");
                }
            } catch (ElasticSearchException e) {
            	ClogInstanceServiceImpl.LOG.warn("Deleting of alias failed during rollback of transaction. Exception: {}" + e);
            }
            
            throw new ClogServerException("CLOG-03", "Creating of CLOG failed. Transaction rollback attempted");
        } 
        
        if (ClogInstanceServiceImpl.LOG.isDebugEnabled()) {
        	ClogInstanceServiceImpl.LOG.debug("Clog [{}] instance created successfully", clogInstance.toString());
        }
        
        return clogInstance;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.covisint.core.http.service.server.service.BaseResourceService#validateAdd(com.covisint.core.http.service.core.Resource)
     */
    @Override
    protected void validateAdd(ClogInstance resource) {
    
    	/** Check if the name attribute is present in the resource or not */
    	if (Strings.isNullOrEmpty(resource.getName())) {
    		throw new InvalidRequestException("CLOG-05", "Name attribute missing in the payload");
    	}
    	
    	/** Check if the platform instance id attribute is present in the resource or not */
    	if (Strings.isNullOrEmpty(resource.getPlatformInstanceId())) {
    		throw new InvalidRequestException("CLOG-06", "Plaform Instance ID attribute missing in the payload");
    	}
    	
    	/** Check if the platform solution id attribute is present in the resource or not */
    	if (Strings.isNullOrEmpty(resource.getPlatformSolutionId())) {
    		throw new InvalidRequestException("CLOG-07", "Solution Instance ID attribute missing in the payload");
    	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.covisint.core.http.service.server.service.BaseResourceService#update(com.covisint.core.http.service.core.Resource)
     */
    @Nonnull
    @Transactional
    @Override
    public ClogInstance update(ClogInstance resource) {
    	throw this.unsupportedUpdateException();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.covisint.core.http.service.server.service.BaseResourceService#update(com.covisint.core.http.service.core.Resource)
     */
    @Nonnull
    @Transactional
    @Override
    public ClogInstance delete(@Nonnull final String clogInstanceId) {
    	
    	if (ClogInstanceServiceImpl.LOG.isDebugEnabled()) {
    		ClogInstanceServiceImpl.LOG.debug("Deleting Clog Instance [{}]", clogInstanceId);
    	}
    	
        final ClogInstance clogInstance = getResourceDao().getById(clogInstanceId);
        
        if (clogInstance.getStatus().equals(ClogInstanceStatusE.DELETED)) {
        	throw new ResourceNotFoundException(clogInstance.getId());
        }
        
        try {
        	groupService.delete(clogInstance);
        } catch (ServiceException e) {
        	ClogInstanceServiceImpl.LOG.warn("Error while deleting group service entry while deleting cloginstance entry");
        }
        
        try {
			elasticSearchService.deleteAlias(clogInstance);
		} catch (ElasticSearchException e) {
			ClogInstanceServiceImpl.LOG.error("Deletion of elastic search alias failed. Exception: {}", e);
		}
        
        clogInstance.setStatus(ClogInstanceStatusE.DELETED);
        getResourceDao().update(clogInstance);
        
        return clogInstance;  
    }

    /**
     * Creates an unsupported update exception for update methods.
     * 
     * @return
     */
    private UnsupportedOperationException unsupportedUpdateException() {
        return new UnsupportedOperationException("Update operation not allowed. Use Delete to set the status flag of clogisntance to DELETED");
    }

}
