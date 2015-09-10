/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.elasticsearch;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.covisint.core.http.service.core.InvocationContext;
import com.covisint.core.support.constraint.Nonnull;
import com.covisint.platform.clog.core.cloginstance.ClogInstance;
import com.covisint.platform.clog.server.util.ClogInstanceJsonHelper;

/**
 * ElasticSearchService class exposing methods related to Elastic Search. This
 * class currently supports only creation and deletion of an alias
 * 
 * @since 25th August 2015
 *
 */
public class ElasticSearchService {

	/** Logger for ElasticSearchService class */
    private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchService.class);
    
    /** Elastic Search server URL */
    private String elasticSearchUrl;
    
    /** Name of the index on which alias needs to be created */
    private String indexName;
    
    /** Instance of HttpClient class, used to make calls to Elastic Search server */
    private HttpClientService httpClientService;

	/**
	 * Constructor
	 * 
	 * @param httpClient
	 *            HttpClient object
	 * @param elasticSearchUrl
	 *            URL of Elastic Search
	 * @param indexName
	 *            Name of the index on which alias needs to be created
	 */
    public ElasticSearchService(@Nonnull HttpClientService httpClientService, 
    								   @Nonnull String elasticSearchUrl, 
    								   @Nonnull String indexName) {
        this.elasticSearchUrl = elasticSearchUrl;
        this.indexName = indexName;
        this.httpClientService = httpClientService;
    }

	/**
	 * Method that creates an alias for the given index
	 * 
	 * @param clogInstance
	 *            ClogInstance object contains details of the platform solution
	 * @throws ElasticSearchException
	 */
    public void createAlias(ClogInstance clogInstance) throws ElasticSearchException {
    	
    	if (ElasticSearchService.LOG.isDebugEnabled()) {
    		ElasticSearchService.LOG.debug("Creating elastic search alias for {}", clogInstance.toString());
    	}
    	
		try {
			httpClientService.post(getPostURL(), createPayload(clogInstance));
		} catch (HttpException e) {
			ElasticSearchService.LOG.error("Creation of alias failed. Exception: {}", e);
			throw new ElasticSearchException(e);
		}
		
    	if (ElasticSearchService.LOG.isDebugEnabled()) {
    		ElasticSearchService.LOG.debug("Elastic search alias created succcessfully for {}", clogInstance.toString());
    	}
    }

	/**
	 * Method to delete an alias
	 * 
	 * @param clogInstance
	 *            Clog Instance for which alias needs to be deleted
	 * 
	 * @throws ElasticSearchException
	 */
    public void deleteAlias(ClogInstance clogInstance) throws ElasticSearchException {
    	
    	if (ElasticSearchService.LOG.isDebugEnabled()) {
    		ElasticSearchService.LOG.debug("Deleting elastic search alias for {}", clogInstance.toString());
    	}
    	
        try {
			httpClientService.delete(getDeleteURL(clogInstance.getPlatformInstanceId()));
		} catch (HttpException e) {
			ElasticSearchService.LOG.error("Deletion of alias failed. Exception: {}", e);
			throw new ElasticSearchException(e);
		}
        
    	if (ElasticSearchService.LOG.isDebugEnabled()) {
    		ElasticSearchService.LOG.debug("Elastic search alias deleted successfully for {}", clogInstance);
    	}

    }

	/**
	 * Method returning Elastic Search alias creation POST URL. It just adds
	 * "_aliases" to the Elastic Search server URL configured in environment
	 * variables
	 * 
	 * @return Fully qualified URL for creation of alias
	 */
    private String getPostURL() {
    	return new StringBuilder(this.elasticSearchUrl).append("/_aliases").toString();
    }
    
	/**
	 * Method returning Elastic Search alias creation DELETE URL. Given the base
	 * URL of elastic search server, it adds "[INDEX_NAME]/alias/[ALIAS_NAME]"
	 * 
	 * @param aliasName
	 *            Name of the alias to be deleted
	 * @return Fully qualified DELETE URL
	 */
    private String getDeleteURL(String aliasName) {
    	return new StringBuilder(this.elasticSearchUrl).append("/")
    			.append(this.indexName).append("/_alias/")
    			.append(aliasName).toString();
    }

	/**
	 * Method to generate the alias creation payload data
	 * 
	 * @param clogInstance
	 *            ClogInstance object containing the metadata of Clog
	 * @return JSON format of payload
	 * @throws ElasticSearchException
	 */
    private String createPayload(ClogInstance clogInstance) throws ElasticSearchException {
    	String payload = null;
    	try {
    		payload = new ClogInstanceJsonHelper().createJsonForClogInstance(clogInstance.getPlatformInstanceId(), 
        		this.indexName, clogInstance.getPlatformInstanceId(), InvocationContext.getRealmId());
    		
    		if (ElasticSearchService.LOG.isDebugEnabled()) {
    			ElasticSearchService.LOG.debug("Payload for creating alias: {}", payload);
    		}
    		
    	} catch (IOException e) {
    		String msg = "Payload creation failed while creating alias for " + clogInstance.toString();
    		ElasticSearchService.LOG.error(msg);
    		throw new ElasticSearchException(msg);
    	}
    	
    	return payload;
    }

}
