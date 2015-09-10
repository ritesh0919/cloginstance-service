/* Copyright (C) 2014 Covisint. All Rights Reserved. */

package com.covisint.platform.clog.core.cloginstance;

import static com.covisint.platform.clog.core.ClogInstancePersistenceConstants.CLOG_INSTANCE_TABLE_NAME;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.covisint.core.http.service.core.AbstractRealmScopedResource;
import com.covisint.core.support.constraint.Searchable;
import com.covisint.platform.clog.core.ClogInstancePersistenceConstants.Columns;
import com.covisint.platform.clog.core.ClogInstanceStatusE;
import com.google.common.base.Objects;
//import com.google.common.base.MoreObjects;

/**
 * Resource representing an Instance of CLOG
 * 
 * @author Owais.Mohamed@covisint.com
 *
 */
//@Audited
@Entity
@Table(name = CLOG_INSTANCE_TABLE_NAME)
public final class ClogInstance extends AbstractRealmScopedResource<ClogInstance> {

    /** Serial Version of this Serializable Object */
	private static final long serialVersionUID = 5706962646536410143L;

	/**
     * Name of the CLOG Instance. This is only Covisint Internal hence Internationalization no required.
     */
    @Column(name = Columns.NAME)
    @Searchable
    private String name;

    /** ID of the platform Solution to which this CLOG Instance associated with. */
    @Column(name = Columns.PLATFORM_SOLUTION_ID)
    @Searchable
    private String platformSolutionId;

    /** ID of the platform Instance to which this CLOG Instance is associated with. */
    @Column(name = Columns.PLATFORM_INSTANCE_ID)
    @Searchable
    private String platformInstanceId;

    /** Covisint IDM Group to which this CLOG instance is associated with. */
    @Column(name = Columns.PLATFORM_GROUP_ID)
    @Searchable
    private String platformGroupId;

    /** Status of the Clog Instance */
    @Enumerated(EnumType.STRING)
    @Column(name = Columns.STATUS)
    @Searchable
    private ClogInstanceStatusE status;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public ClogInstance setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return the platformSolutionId
     */
    public String getPlatformSolutionId() {
        return platformSolutionId;
    }

    /**
     * @param platformSolutionId the platformSolutionId to set
     */
    public ClogInstance setPlatformSolutionId(String solutionId) {
        this.platformSolutionId = solutionId;
        return this;
    }

    /**
     * @return the platformInstanceId
     */
    public String getPlatformInstanceId() {
        return platformInstanceId;
    }

    /**
     * @param platformInstanceId the platformInstanceId to set
     */
    public ClogInstance setPlatformInstanceId(String instanceId) {
        this.platformInstanceId = instanceId;
        return this;
    }

    /**
     * @return the platformGroupId
     */
    public String getPlatformGroupId() {
        return platformGroupId;
    }

    /**
     * @param platformGroupId the platformGroupId to set
     */
    public ClogInstance setPlatformGroupId(String groupId) {
        this.platformGroupId = groupId;
        return this;
    }

    /**
     * @return the status
     */
    public ClogInstanceStatusE getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public ClogInstance setStatus(ClogInstanceStatusE status) {
        this.status = status;
        return this;
    }

    /**
     * Hash code of the resource (ClogInstance)
     * 
     * @return hash code
     */
    @Override
    public int hashCode() {
        return getResourceIdentityHashCode();
    }

    /**
     * method to check if the passed in object is equals to this
     * 
     * @param obj New objec to compare with this object
     * @return true if same else false
     */
    @Override
    public boolean equals(Object obj) {
        return isResourceIdentityEqual(ClogInstance.class, obj);
    }

    /**
     * String conversion of this object
     * 
     *  @return String representation of this object
     */
    @SuppressWarnings("deprecation")
	@Override
    public String toString() {
        // @formatter:off
    	String strStatus = null;
    	ClogInstanceStatusE status = getStatus();
    	if (status != null) {
    		strStatus = status.name();
    	}
    	
        return populateToStringHelper((Objects.toStringHelper(this))
        		.add("name", getName())
                .add("platformSolutionId", getPlatformSolutionId())
                .add("platformInstanceId", getPlatformInstanceId())
                .add("platformGroupId", getPlatformGroupId())
                .add("status", strStatus))
                .toString();
        // @formatter:off
    }

}
