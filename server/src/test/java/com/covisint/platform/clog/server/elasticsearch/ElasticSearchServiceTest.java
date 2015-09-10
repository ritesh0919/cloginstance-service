/* Copyright (C) 2014 Covisint. All Rights Reserved. */

package com.covisint.platform.clog.server.elasticsearch;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import com.covisint.platform.clog.core.ClogInstanceStatusE;
import com.covisint.platform.clog.core.cloginstance.ClogInstance;
import com.covisint.platform.clog.server.util.ClogInstanceJsonHelper;

/**
 * ElasticSearchServiceTest.
 * 
 */
public class ElasticSearchServiceTest {
	
	/** ElasticSearchService class to be tested */
	private ElasticSearchService elasticSearchService;

	/** Mocked HttpClient */
	@Mock 
	private HttpClientService httpClient;
	
	/** Mocked ClogInstanceJsonHelper class */
	@Mock
	ClogInstanceJsonHelper jsonHelper;
	
	private String elasticSearchUrl = "http://lcoalhsot:9200";
	private String indexName = "INDEXNAME";

	/**
	 * setup.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		httpClient = mock(HttpClientService.class);
		jsonHelper = mock(ClogInstanceJsonHelper.class);
		elasticSearchService = new ElasticSearchService(httpClient, elasticSearchUrl, indexName);
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
	 * testSuit.
	 * 
	 */
	@Test
	public void testCreateAlias() throws ElasticSearchException, HttpException, IOException {
		doNothing().when(httpClient).post(anyString(), anyString());
		elasticSearchService.createAlias(this.createClogInstance());
	}

	@Test(expected = ElasticSearchException.class)
	public void testCreateAliasThrowException() throws HttpException, ElasticSearchException {
		doThrow(HttpException.class).when(httpClient).post(anyString(), anyString());
		elasticSearchService.createAlias(this.createClogInstance());
	}
	
	@Test
	public void testDeleteAlias() throws ElasticSearchException, HttpException {
		doNothing().when(httpClient).delete(anyString());
		elasticSearchService.deleteAlias(this.createClogInstance());
	}

	@Test(expected = ElasticSearchException.class)
	public void testDeleteAliasThrowException() throws HttpException, ElasticSearchException {
		doThrow(HttpException.class).when(httpClient).delete(anyString());
		elasticSearchService.deleteAlias(this.createClogInstance());
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

}
