/* Copyright (C) 2014 Covisint. All Rights Reserved. */

package com.covisint.platform.clog.server.groupservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.covisint.core.http.service.core.InvocationContext;
import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.ResourceReference;
import com.covisint.core.http.service.core.ServiceException;
import com.covisint.core.http.service.core.SortCriteria;
import com.covisint.core.support.constraint.Nonnull;
import com.covisint.platform.clog.core.cloginstance.ClogInstance;
import static com.covisint.platform.clog.server.util.ClogInstancesConstants.*;
import com.covisint.platform.group.client.group.GroupClient;
import com.covisint.platform.group.client.group.entitlement.GroupEntitlementClient;
import com.covisint.platform.group.core.group.Group;
import com.covisint.platform.group.core.group.GroupEntitlement;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * GroupService class exposing method to perform operation on GroupService
 * 
 * @author Lingesh.M
 *
 *
 */
public class GroupService {

	/** Logger of GroupService class */
	private static final Logger LOG = LoggerFactory.getLogger(GroupService.class);
	
	/** GroupService client object */
	private GroupClient groupClient;
	
	/** GroupEntitlementClient object */
	private GroupEntitlementClient groupEntitlementClient;

	/**
	 * Constructor.
	 *
	 * @param newGroupClient
	 * @param newGroupEntitlementClient
	 */
	public GroupService(@Nonnull GroupClient newGroupClient,
			@Nonnull GroupEntitlementClient newGroupEntitlementClient) {
		groupClient = newGroupClient;
		groupEntitlementClient = newGroupEntitlementClient;

	}

	/**
	 * Creates Group and Group Entitlement entry in Group Service
	 * 
	 * @param clogInstance
	 *            ClogInstance object
	 * @throws InterruptedException 
	 * @throws Exception
	 *             GroupServiceException
	 * 
	 */
	public void add(ClogInstance clogInstance) /*throws GroupServiceException*/ {
		
		if (GroupService.LOG.isDebugEnabled()) {
			GroupService.LOG.debug("Creating group in Group Service for {}", clogInstance.toString());
		}
		
		final Map<String, String> names = new HashMap<String, String>();
		names.put(GroupServiceConstants.EN, clogInstance.getName());
		
		final Map<String, String> descriptions = new HashMap<String, String>();
		descriptions.put(GroupServiceConstants.EN, clogInstance.getName());

		final Group group = new Group();
		group.setName(names)
			 .setDescription(descriptions)
			 .setCreator(InvocationContext.getRequestor())
			 .setCreatorApplicationId(InvocationContext.getRequestorApplicationId())
			 .setOwner(new ResourceReference(clogInstance.getPlatformInstanceId(), 
					 GroupServiceConstants.CLOG_INSTANCE, InvocationContext.getRealmId()))
			 .setRealm(InvocationContext.getRealmId());
		
		final HttpContext context = buildHttpContext();
		Group addedGroup = null;
		
		addedGroup = groupClient.add(group, context).checkedGet();
		
		clogInstance.setPlatformGroupId(addedGroup.getId());
		
		if (GroupService.LOG.isDebugEnabled()) {
			GroupService.LOG.debug("Group created in Group Service for {}", clogInstance.toString());
		}
		
		final GroupEntitlement groupEntitlement = new GroupEntitlement()
				.setGroup(addedGroup)
				.setName(Entitlement.VIEW_LOGS)
				.setCreator(InvocationContext.getRequestor())
				.setCreatorApplicationId(InvocationContext.getRequestorApplicationId());
		groupEntitlementClient.add(addedGroup.getId(), groupEntitlement, context).checkedGet();
		
		if (GroupService.LOG.isDebugEnabled()) {
			GroupService.LOG.debug("Group entitlement created in Group Service for {}", clogInstance.toString());
		}		
			
		if (GroupService.LOG.isDebugEnabled()) {
			GroupService.LOG.debug("Group creation completed for {}" + clogInstance.toString());
		}
	}

	/**
	 * delete.
	 * 
	 * @param clogInstance
	 * @throws ServiceException
	 */
	public void delete(ClogInstance clogInstance) {
		
		if (GroupService.LOG.isDebugEnabled()) {
			GroupService.LOG.debug("Deleting Group Service for {}", clogInstance.toString());
		}		

		
		final HttpContext context = buildHttpContext();
		
		final Group group = groupClient.get(clogInstance.getPlatformGroupId(), context).checkedGet();
		final String groupId = group.getId();
		final Multimap<String, String> searchCriteria = HashMultimap.create();
		
		/*
		 * Retrieve all the entitlements pertaining the group id stored in Group Service. Once we
		 * the list of entitlements, we shall loop for each and delete them individually. There is 
		 * no to delete all entitlements in one shot
		 */
		final List<GroupEntitlement> groupEntitlements = groupEntitlementClient.search(searchCriteria, 
				SortCriteria.NONE, Page.ALL, groupId, context).checkedGet();

		if (groupEntitlements != null && !groupEntitlements.isEmpty()) {
			if (GroupService.LOG.isDebugEnabled()) {
				GroupService.LOG.debug("Group entitlements to be deleted: {}",  groupEntitlements);
			}		
	
			for (final GroupEntitlement groupEntitlement : groupEntitlements) {
				groupEntitlementClient.delete(groupId, groupEntitlement.getId(), context);
			}
			
			if (GroupService.LOG.isDebugEnabled()) {
				GroupService.LOG.debug("Group entitlements deleted for {}", groupId);
			}		
		} else {
			if (GroupService.LOG.isDebugEnabled()) {
				GroupService.LOG.debug("No entitlements available for {}", groupId);
			}		
		}
		
		/*
		 * Delete the actual group entry from the Group Service 
		 */
		groupClient.delete(groupId, context);
		
		if (GroupService.LOG.isDebugEnabled()) {
			GroupService.LOG.debug("Group deleted in Group Service for {}", clogInstance.toString());
		}		
		
		if (GroupService.LOG.isDebugEnabled()) {
			GroupService.LOG.debug("Group deletion completed for {}", clogInstance.toString());
		}		
	}

	/**
	 * Build HTTP context needed to make GroupService calls 
	 * 
	 * @return HttpContext.
	 */
	@Nonnull
	private HttpContext buildHttpContext() {
		final HttpContext context = new BasicHttpContext();
		context.setAttribute(HttpContextConstants.REALM_ID, InvocationContext.getRealmId());
		context.setAttribute(HttpContextConstants.REQUESTOR_APP, InvocationContext.getRequestorApplicationId());
		context.setAttribute(HttpContextConstants.REQUESTOR, InvocationContext.getRequestor());
		return context;
	}

}
