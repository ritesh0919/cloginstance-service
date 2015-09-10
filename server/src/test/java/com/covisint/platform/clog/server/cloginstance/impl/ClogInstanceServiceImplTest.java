/* Copyright (C) 2014 Covisint. All Rights Reserved. */

package com.covisint.platform.clog.server.cloginstance.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;

import com.covisint.core.http.service.client.InvalidRequestException;
import com.covisint.core.http.service.core.ServiceException;
import com.covisint.platform.clog.core.ClogInstanceStatusE;
import com.covisint.platform.clog.core.cloginstance.ClogInstance;
import com.covisint.platform.clog.server.cloginstance.ClogInstanceDAO;
import com.covisint.platform.clog.server.elasticsearch.ElasticSearchException;
import com.covisint.platform.clog.server.elasticsearch.ElasticSearchService;
import com.covisint.platform.clog.server.exception.handler.ClogServerException;
import com.covisint.platform.clog.server.exception.handler.UpstreamServerException;
import com.covisint.platform.clog.server.groupservice.GroupService;

/**
 * Tests. Tests for {@link ClogInstanceServiceImpl}
 * 
 * @since Jul 13, 2015
 *
 */
public class ClogInstanceServiceImplTest {
    /** thrown. */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private ClogInstanceServiceImpl subject;
    
    @Mock
    private ElasticSearchService esService;
    
    @Mock
    private GroupService groupService;
    
    @Mock
    ClogInstanceDAO dao;

    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testAdd() throws ElasticSearchException {
    	doNothing().when(groupService).add(any(ClogInstance.class));
    	doNothing().when(esService).createAlias(any(ClogInstance.class));
    	when(dao.add(any(ClogInstance.class))).thenAnswer(new Answer<ClogInstance>() {

			@Override
			public ClogInstance answer(InvocationOnMock invocation)
					throws Throwable {
				ClogInstance clog = (ClogInstance) invocation.getArguments()[0];
				return clog;
			}
    		
    	});
    	
    	subject.add(this.createClogInstance());
    }
    
    @Test(expected = InvalidRequestException.class)
    public void testAdd_InvalidName() throws ElasticSearchException {
    	doNothing().when(groupService).add(this.createClogInstance());
    	doNothing().when(esService).createAlias(this.createClogInstance());
    	when(dao.add(any(ClogInstance.class))).thenAnswer(new Answer<ClogInstance>() {

			@Override
			public ClogInstance answer(InvocationOnMock invocation)
					throws Throwable {
				ClogInstance clog = (ClogInstance) invocation.getArguments()[0];
				return clog;
			}
    		
    	});
    	
    	ClogInstance clog = this.createClogInstance();
    	clog.setName(null);
    	subject.add(clog);
    }
    
    @Test(expected = InvalidRequestException.class)
    public void testAdd_InvalidInstanceId() throws ServiceException, ElasticSearchException {
    	doNothing().when(groupService).add(this.createClogInstance());
    	doNothing().when(esService).createAlias(this.createClogInstance());
    	when(dao.add(any(ClogInstance.class))).thenAnswer(new Answer<ClogInstance>() {

			@Override
			public ClogInstance answer(InvocationOnMock invocation)
					throws Throwable {
				ClogInstance clog = (ClogInstance) invocation.getArguments()[0];
				return clog;
			}
    		
    	});
    	
    	ClogInstance clog = this.createClogInstance();
    	clog.setPlatformInstanceId(null);
    	subject.add(clog);
    }
    
    @Test(expected = InvalidRequestException.class)
    public void testAdd_InvalidSolutuionId() throws ElasticSearchException {
    	doNothing().when(groupService).add(this.createClogInstance());
    	doNothing().when(esService).createAlias(this.createClogInstance());
    	when(dao.add(any(ClogInstance.class))).thenAnswer(new Answer<ClogInstance>() {

			@Override
			public ClogInstance answer(InvocationOnMock invocation)
					throws Throwable {
				ClogInstance clog = (ClogInstance) invocation.getArguments()[0];
				return clog;
			}
    		
    	});
    	
    	ClogInstance clog = this.createClogInstance();
    	clog.setPlatformSolutionId(null);
    	subject.add(clog);
    }
    
    @Test(expected = UpstreamServerException.class)
    public void testAddGroupServiceFail() {
    	PowerMockito.doThrow(new ServiceException("AA", "VV")).when(groupService).add(any(ClogInstance.class));
    	subject.add(this.createClogInstance());
    }
    
    @Test(expected = UpstreamServerException.class)
    public void testAddElasticSearchServiceFail() throws ElasticSearchException {
    	doNothing().when(groupService).add(any(ClogInstance.class));
    	doNothing().when(groupService).delete(any(ClogInstance.class));
    	PowerMockito.doThrow(new ElasticSearchException()).when(esService).createAlias(any(ClogInstance.class));    
    	
    	subject.add(this.createClogInstance());
    }
    
    @Test(expected = ClogServerException.class)
    public void testAddDaoFail() throws ElasticSearchException {
    	doNothing().when(groupService).add(any(ClogInstance.class));
    	doNothing().when(groupService).delete(any(ClogInstance.class));
    	doNothing().when(esService).createAlias(any(ClogInstance.class));
    	when(dao.add(any(ClogInstance.class))).thenAnswer(new Answer<ClogInstance>() {

			@Override
			public ClogInstance answer(InvocationOnMock invocation)
					throws Throwable {
				return null;
			}
    		
    	});  
    	
    	subject.add(this.createClogInstance());
    }
    
    @Test
    public void testDelete() throws ElasticSearchException {
    	doNothing().when(groupService).delete(any(ClogInstance.class));
    	doNothing().when(esService).deleteAlias(any(ClogInstance.class));
    	when(dao.update(any(ClogInstance.class))).thenReturn(null);
    	Mockito.when(dao.getById("12345")).thenAnswer(new Answer<ClogInstance>() {

			@Override
			public ClogInstance answer(InvocationOnMock invocation)
					throws Throwable {
				return createClogInstance();
			}
    		
    	});  
    	
    	subject.delete("12345");
    	
    }

    @Test
    public void testDeleteElasticSearchServiceFail() throws ElasticSearchException {
    	doNothing().when(groupService).delete(any(ClogInstance.class));
    	PowerMockito.doThrow(new ElasticSearchException()).when(esService).deleteAlias(any(ClogInstance.class));
    	when(dao.update(any(ClogInstance.class))).thenReturn(null);
    	Mockito.when(dao.getById("12345")).thenAnswer(new Answer<ClogInstance>() {

			@Override
			public ClogInstance answer(InvocationOnMock invocation)
					throws Throwable {
				return createClogInstance();
			}
    		
    	});  
    	
    	subject.delete("12345");
    	
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testUpdate() {
    	subject.update(this.createClogInstance());
    }
    
    private ClogInstance createClogInstance() {
    	ClogInstance clog = new ClogInstance();
    	clog.setName("ritesh");
    	clog.setStatus(ClogInstanceStatusE.ACTIVE);
    	clog.setPlatformInstanceId("PLATFORM_INSTANCE");
    	clog.setPlatformSolutionId("SOLUTION_ID");
    	return clog;
    }
    
 

}
