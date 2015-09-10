/* Copyright (C) 2014 Covisint. All Rights Reserved. */

package com.covisint.platform.clog.server.elasticsearch;

import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicStatusLine;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import com.covisint.platform.clog.core.ClogInstanceStatusE;
import com.covisint.platform.clog.core.cloginstance.ClogInstance;

/**
 * Wrapper Test.
 * 
 */
public class HttpClientServiceTest {
	
    /** wrapper. */
	@InjectMocks
    private HttpClientService httpClientService;
	
	//@Mock
	//HttpResponse httpResponse;
	
	@Mock
	HttpPost post;
	
	@Mock
	HttpDelete delete;
	
	@Mock
	StringEntity entity;

	@Mock
	CloseableHttpClient httpClient;
	
	@Mock
	CloseableHttpResponse httpResponse;
	
    /**
     * setup.
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
    	
    	httpClient = Mockito.mock(CloseableHttpClient.class);
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
     * testPost.
     * 
     * @throws Exception 
     */
    @Ignore
    public void testPost() throws Exception {
    	
    	PowerMockito.mockStatic(HttpClients.class);
    	//PowerMockito.doReturn(httpClient).when(HttpClients.createDefault());
    	PowerMockito.whenNew(HttpPost.class).withArguments(Mockito.anyString()).thenReturn(post);
    	PowerMockito.whenNew(StringEntity.class).withArguments(Mockito.anyString()).thenReturn(entity);
    	Mockito.when(httpClient.execute(post)).thenReturn(httpResponse);
    	Mockito.when(httpResponse.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, ""));
    	
    	httpClientService.post("http://localhost:9200", "payload");
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
    
    private HttpResponse createHttpResponse() {
    	HttpResponseFactory factory = new DefaultHttpResponseFactory();
    	HttpResponse response = factory.newHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null), null);
    	return response;
    	
    }

}
