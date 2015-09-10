/* Copyright (C) 2014 Covisint. All Rights Reserved. */

package com.covisint.platform.clog.server.groupservice;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.protocol.HttpContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.covisint.core.http.service.core.InvocationContext;
import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.ServiceException;
import com.covisint.core.http.service.core.SortCriteria;
import com.covisint.platform.clog.core.ClogInstanceStatusE;
import com.covisint.platform.clog.core.cloginstance.ClogInstance;
import com.covisint.platform.group.client.group.GroupClient;
import com.covisint.platform.group.client.group.entitlement.GroupEntitlementClient;
import com.covisint.platform.group.core.group.Group;
import com.covisint.platform.group.core.group.GroupEntitlement;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.CheckedFuture;


/**
 * GroupServiceWrapperTest.
 * 
 */
public class GroupServiceTest {

    @InjectMocks
    private GroupService groupService;

    @Mock
    private GroupClient groupClient;

    @Mock
    private GroupEntitlementClient groupEntitlementClient;

    /**
     * setup.
     * 
     * @throws Exception 
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    	InvocationContext.setRequestor("TEST-REQUESTOR");
    	InvocationContext.setRequestorApplicationId("TEST-APPID");
    	InvocationContext.setRealmId("TEST-REALM");
    }
    

    /**
     * teardown.
     * 
     * @throws Exception 
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * testGroupServiceWrapper.
     * 
     */
    @Test
    public void testGroupServiceAdd() {
    	
        final ClogInstance c = this.createClogInstance();
        
        when(groupEntitlementClient.add(any(String.class), any(GroupEntitlement.class), any(HttpContext.class)))
        		.thenAnswer(new Answer<CheckedFuture<GroupEntitlement, ServiceException>>() {

			@Override
			public CheckedFuture<GroupEntitlement, ServiceException> answer(
					InvocationOnMock invocation) throws Throwable {
				
				Object[] obj = invocation.getArguments();
				GroupEntitlement g = (GroupEntitlement) obj[1];
				g.setId("1234");
				
				
				CheckedFuture<GroupEntitlement, ServiceException> cf = new CheckedFutureImpl<GroupEntitlement, ServiceException>(g, new ServiceException("x", "y"));
				return cf;
			}
			
		});
        
        when(groupClient.add(any(Group.class), any(HttpContext.class)))
        		.thenAnswer(new Answer<CheckedFuture<Group, ServiceException>>() {

			@Override
			public CheckedFuture<Group, ServiceException> answer(InvocationOnMock invocation) throws Throwable {
				Object[] obj = invocation.getArguments();
				Group g = (Group) obj[0];
				g.setId("1234");
				
				CheckedFuture<Group, ServiceException> cf = new CheckedFutureImpl<Group, ServiceException>(g, new ServiceException("x", "y"));
				return cf;
			}
        });
        		
		groupService.add(c);

        verify(groupClient, times(1)).add(any(Group.class), any(HttpContext.class));
        verify(groupEntitlementClient, times(1)).add(any(String.class), any(GroupEntitlement.class), 
        		any(HttpContext.class));
    }
    
    
    @Test(expected = ServiceException.class)
    public void testGroupServiceAddGroupThrowException()  {
    	
        final ClogInstance c = this.createClogInstance();
        
        when(groupClient.add(any(Group.class), any(HttpContext.class))).thenThrow(this.createException());        		
		groupService.add(c);

        verify(groupClient, times(1)).add(any(Group.class), any(HttpContext.class));
    }
    
    @Test(expected = ServiceException.class)
    public void testGroupServiceAddEntitlementThrowException() {
    	
        final ClogInstance c = this.createClogInstance();
        
        when(groupEntitlementClient.add(any(String.class), any(GroupEntitlement.class), 
        		any(HttpContext.class))).thenThrow(this.createException());
        when(groupClient.add(any(Group.class), any(HttpContext.class)))
        		.thenAnswer(new Answer<CheckedFuture<Group, ServiceException>>() {

			@Override
			public CheckedFuture<Group, ServiceException> answer(InvocationOnMock invocation) throws Throwable {
				Object[] obj = invocation.getArguments();
				Group g = (Group) obj[0];
				g.setId("1234");
				
				CheckedFuture<Group, ServiceException> cf = new CheckedFutureImpl<Group, ServiceException>(g, new ServiceException("x", "y"));
				return cf;
			}
        });
        		
		groupService.add(c);

        verify(groupClient, times(1)).add(any(Group.class), any(HttpContext.class));
        verify(groupEntitlementClient, times(1)).add(any(String.class), any(GroupEntitlement.class), any(HttpContext.class));
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void testGroupDelete() {
        when(groupClient.get(any(String.class), any(HttpContext.class))).thenAnswer(new Answer<CheckedFuture<Group, ServiceException>>() {

			@Override
			public CheckedFuture<Group, ServiceException> answer (InvocationOnMock invocation) throws Throwable {
				
				Group gr = new Group();
				CheckedFuture<Group, ServiceException> cf = new CheckedFutureImpl<Group, ServiceException>(gr, new ServiceException("x", "y"));
				return cf;
			}

        });
        
        when(groupEntitlementClient.search(any(Multimap.class), any(SortCriteria.class), any(Page.class), 
        		any(String.class), any(HttpContext.class))).thenAnswer(new Answer<CheckedFuture<List<GroupEntitlement>, 
        				ServiceException>>() {

			@Override
			public CheckedFuture<List<GroupEntitlement>, ServiceException> answer (InvocationOnMock invocation) throws Throwable {
				
				List<GroupEntitlement> entitlements = new ArrayList<GroupEntitlement>();
				GroupEntitlement entitlement = new GroupEntitlement();
				entitlement.setId("ENTITLEMENTID");
				entitlements.add(entitlement);
				
				CheckedFuture<List<GroupEntitlement>, ServiceException> cf = new CheckedFutureImpl<List<GroupEntitlement>, 
						ServiceException>(entitlements, new ServiceException("x", "y"));
				return cf;
			}

        });
        
        when(groupEntitlementClient.delete(any(String.class), any(String.class), any(HttpContext.class)))
        		.thenAnswer(new Answer<CheckedFuture<Void, ServiceException>>() {

			@Override
			public CheckedFuture<Void, ServiceException> answer (InvocationOnMock invocation) throws Throwable {
				
				CheckedFuture<Void, ServiceException> cf = new CheckedFutureImpl<Void, ServiceException>(null, new ServiceException("x", "y"));
				return cf;
			}

        });
        
        groupService.delete(this.createClogInstance());
        
        verify(groupClient, times(1)).get(any(String.class), any(HttpContext.class));
        verify(groupEntitlementClient, times(1)).search(any(Multimap.class), any(SortCriteria.class), any(Page.class), 
        		any(String.class), any(HttpContext.class));
        verify(groupEntitlementClient, times(1)).delete(any(String.class), any(String.class), any(HttpContext.class));
    	
    }
    
    private ClogInstance createClogInstance() {
    	ClogInstance clog = new ClogInstance();
    	
    	clog.setName("TESTCLIENT")
    		.setPlatformInstanceId("COKE_SOLUTION_CENTER")
    		.setPlatformSolutionId("COKE_SOLUTION")
    		.setStatus(ClogInstanceStatusE.ACTIVE)
    		.setCreator("RITESH")
    		.setPlatformGroupId("GROUPID");
    	
    	return clog;
    }
    
    private Exception createException() {
    	return new ServiceException("TESTCODE", "TESTMESSAGE");
    }

}
