/* Copyright (C) 2014 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.cloginstance.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.covisint.core.http.service.client.UnsupportedResponseContentTypeException;
import com.covisint.core.support.constraint.Nonnull;
import com.covisint.core.support.constraint.NotEmpty;
import com.covisint.core.support.httpclient.HttpClientBuilder;
import com.covisint.platform.clog.core.ClogInstanceStatusE;
import com.covisint.platform.clog.core.SupportedMediaTypesE;
import com.covisint.platform.clog.core.cloginstance.ClogInstance;
import com.covisint.platform.clog.core.cloginstance.io.json.ClogInstanceReader;
import com.covisint.platform.clog.core.cloginstance.io.json.ClogInstanceWriter;
import com.covisint.platform.oauth.core.SupportedMediaType;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/** Resource ClogClientTest Test class that operates on {@link ClogClient}. */
public class ClogClientTest {

	/** Supported media type for CLOG. */
	private static final String CLOG_MEDIA_TYPE = SupportedMediaTypesE.CLOG_INSTANCE_V1_MEDIA_TYPE
			.string();
	
	private static final String UNSUPPORTED_MEDIA_TYPE = SupportedMediaType.CLIENT_SECRET_GRANT_MT.value();

	/** Mock enabled set to true. */
	private static final Boolean MOCK_ENABLED = Boolean.TRUE;

	/** The wire mock server. */
	private static WireMockServer wireMockServer;

	/** The ClogClient client. */
	private static ClogClient client;

	/** The http context. */
	private static HttpContext httpContext;

	/** The http client. */
	private static HttpClient httpClient;

	/** The executor service. */
	private static ListeningExecutorService executor;

	/** The parser to parse JSON strings. */
	private JSONParser parser;

	/**
	 * Reads the JSON object from the file.
	 * 
	 * @param resourcePath
	 *            The resource path.
	 * @return {@link JSONObject}
	 * @throws ParseException
	 *             The exception this method may throw.
	 * @throws IOException
	 *             The exception this method may throw.
	 * @throws org.json.simple.parser.ParseException
	 */
	@Nonnull
	private JSONObject getJsonObject(@Nonnull @NotEmpty String resourcePath) throws IOException, ParseException {

		final InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(resourcePath);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));

		final JSONObject jsonObject = (JSONObject) parser.parse(reader);

		return jsonObject;
	}

	/**
	 * Read the JSON Array from the file.
	 * 
	 * @param resourcePath
	 *            The resource location
	 * @return {@link JSONArray}
	 * @throws IOException
	 *             {@link IOException} The exception this method may throw..
	 * @throws ParseException
	 *             {@link ParseException} The exception this method may throw..
	 */
	@Nonnull
	private JSONArray getJsonArray(@Nonnull @NotEmpty String resourcePath)
			throws IOException, ParseException {

		final InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(resourcePath);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));

		parser = new JSONParser();
		final JSONArray jsonArray = (JSONArray) parser.parse(reader);
		return jsonArray;
	}

	/**
	 * Assert ClogInstance objects with names and descriptions.
	 * 
	 * @param expectedClog
	 *            the actual ClogInstance object.
	 * @param actualClog
	 *            The expected ClogInstance object.
	 */
	private void assertEqualsFullObject(@Nonnull ClogInstance expectedClog,
			@Nonnull ClogInstance actualClog) {
		Assert.assertNotNull(expectedClog);
		Assert.assertEquals(expectedClog.getId(), actualClog.getId());
		Assert.assertEquals(expectedClog.getCreator(), actualClog.getCreator());
		Assert.assertEquals(expectedClog.getCreatorApplicationId(), actualClog.getCreatorApplicationId());
		Assert.assertEquals(expectedClog.getRealm(), actualClog.getRealm());
		Assert.assertEquals(expectedClog.getVersion(), actualClog.getVersion());
		Assert.assertEquals(expectedClog.getName().toString(), actualClog.getName().toString());
		Assert.assertEquals(expectedClog.getPlatformGroupId(), actualClog.getPlatformGroupId());
		Assert.assertEquals(expectedClog.getPlatformInstanceId(), actualClog.getPlatformInstanceId());
		Assert.assertEquals(expectedClog.getPlatformSolutionId(), actualClog.getPlatformSolutionId());
	}

	/**
	 * Initializes required fields.
	 * 
	 * @throws ParseException
	 *             The exception this method might throw.
	 * @throws IOException
	 *             The exception this method might throw.
	 */
	@BeforeClass
	private void init() throws IOException, ParseException {

		httpClient = new HttpClientBuilder().setMaxConnections(1024).setConnectionClosedAfterResponse(true)
				.addXRequestorInterceptor().build();

		executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1024));

		/** Look for a free port. */
		final int port = findFreePort();
		
		client = new ClogClientBuilder().addEntityWriter(new ClogInstanceWriter())
				.addEntityReader(new ClogInstanceReader())
				.setExecutorService(executor).setHttpClient(httpClient)
				.setServiceBaseUrl("http://localhost:" + port).build();

		httpContext = new BasicHttpContext();

		parser = new JSONParser();

		if (MOCK_ENABLED) {
			wireMockServer = new WireMockServer(port);
			wireMockServer.start();
			WireMock.configureFor("localhost", port);
			initializeMockServer();
		}

	}
	
    public static int findFreePort() {
        try {
            final ServerSocket socket = new ServerSocket(0);
            final int result = socket.getLocalPort();
            socket.close();

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * Initializes mock server.
	 * 
	 * @throws ParseException
	 *             The exception this method might throw.
	 * @throws IOException
	 *             The exception this method might throw.
	 */
	private void initializeMockServer() throws IOException, ParseException {
		
		
		// Stub data for scenario which returns incorrect media type.
		stubFor(get(urlMatching("/cloginstance/12345UNSUPPORTED")).willReturn(aResponse().withHeader("Content-Type", UNSUPPORTED_MEDIA_TYPE)
				.withBody(getJsonObject("json/cloginstance-post-response.json").toString())));
		
		// Stub data for delete
		stubFor(delete(urlMatching("/cloginstance/DELETE_CLOG")).willReturn(aResponse().withHeader("Content-Type", CLOG_MEDIA_TYPE)
				.withStatus(HttpStatus.SC_NO_CONTENT)));		

		// Stub data for scenario which returns clog instance.
		stubFor(get(urlMatching("/cloginstance/f3d82ba7-c276-4c6c-bb67-8c624555263f")).willReturn(aResponse().withHeader("Content-Type", CLOG_MEDIA_TYPE)
				.withBody(getJsonObject("json/cloginstance-post-response.json").toString())));

		// Stub data for scenario which adds a cloginstance.
		stubFor(post(urlMatching("/cloginstance")).willReturn(aResponse().withHeader("Content-Type", CLOG_MEDIA_TYPE)
				.withBody(getJsonObject("json/cloginstance-post-response.json").toString())));

		// Stub data for scenario which updating a cloginstance returning in exception.
		stubFor(put(urlMatching("/cloginstance/f3d82ba7-c276-4c6c-bb67-8c624555263f")).willReturn(aResponse().withHeader("Content-Type", CLOG_MEDIA_TYPE)
				.withBody(getJsonObject("json/cloginstance-put.json").toString())));

	}

	/** Stops the wire mock server after running test cases. */
	@AfterClass
	private final void destroy() {
		if (MOCK_ENABLED) {
			wireMockServer.stop();
		}
		executor.shutdown();
	}
	
    /** Test to get a ClogInstance with unsupported media type. */
    @Test(expectedExceptions = {UnsupportedResponseContentTypeException.class })
    public final void testGetClogInstanceWithUnsupportedMediaType() {
        client.get("12345UNSUPPORTED", httpContext).checkedGet();
    }

    @Test
    public final void testAddClogInstance() {
   	
    	ClogInstance request = new ClogInstance().setName("hello").setPlatformInstanceId("sdfa").setPlatformSolutionId("hello");
    	
    	final ClogInstance expected = new ClogInstance();
    	expected.setId("f3d82ba7-c276-4c6c-bb67-8c624555263f").setName("hello").setPlatformInstanceId("sdfa")
    		.setPlatformSolutionId("hello").setCreator("SOLUTION_CENTER").setCreatorApplicationId("PLATFORM_SOLUTION_CENTER")
    		.setRealm("REALM_ID").setVersion(0L).setCreationInstant(1438247896175L)
    		.setPlatformGroupId("ad534449-d345-4525-94ed-d29e5aa1608f").setStatus(ClogInstanceStatusE.ACTIVE);
    	
    	ClogInstance actual = client.add(request, httpContext).checkedGet();
    	
    	assertEqualsFullObject(expected, actual);
    	Assert.assertEquals(expected.getStatus().toString(), actual.getStatus().toString());
    }
    
    
    @Test
    public final void testDeleteClog() {
    	Assert.assertNull(client.delete("DELETE_CLOG", httpContext).checkedGet());
    }
    
    @Test
    public final void testGet() {
        ClogInstance actual = client.get("f3d82ba7-c276-4c6c-bb67-8c624555263f", httpContext).checkedGet();
        
    	final ClogInstance expected = new ClogInstance();
    	expected.setId("f3d82ba7-c276-4c6c-bb67-8c624555263f").setName("hello").setPlatformInstanceId("sdfa")
    		.setPlatformSolutionId("hello").setCreator("SOLUTION_CENTER").setCreatorApplicationId("PLATFORM_SOLUTION_CENTER")
    		.setRealm("REALM_ID").setVersion(0L).setCreationInstant(1438247896175L)
    		.setPlatformGroupId("ad534449-d345-4525-94ed-d29e5aa1608f").setStatus(ClogInstanceStatusE.ACTIVE);
     
    	assertEqualsFullObject(expected, actual);
    	Assert.assertEquals(expected.getStatus().toString(), actual.getStatus().toString());        
    }
    
    @Test
    public final void testGetWithClogInstance() {
    	
    	final ClogInstance request = new ClogInstance();
    	request.setId("f3d82ba7-c276-4c6c-bb67-8c624555263f").setName("hello").setPlatformInstanceId("sdfa")
    		.setPlatformSolutionId("hello").setCreator("SOLUTION_CENTER").setCreatorApplicationId("PLATFORM_SOLUTION_CENTER")
    		.setRealm("REALM_ID").setVersion(0L).setCreationInstant(1438247896175L)
    		.setPlatformGroupId("ad534449-d345-4525-94ed-d29e5aa1608f").setStatus(ClogInstanceStatusE.ACTIVE);

    	ClogInstance actual = client.get(request, httpContext).checkedGet();
        
    	assertEqualsFullObject(actual, request);
    	Assert.assertEquals(request.getStatus().toString(), actual.getStatus().toString());        
    }
    
    @Test(expectedExceptions = {UnsupportedOperationException.class})
    public final void testPut() {
    	
    	final ClogInstance request = new ClogInstance();
    	request.setId("f3d82ba7-c276-4c6c-bb67-8c624555263f").setName("hello").setPlatformInstanceId("sdfa")
			.setPlatformSolutionId("hello").setCreator("SOLUTION_CENTER").setCreatorApplicationId("PLATFORM_SOLUTION_CENTER")
			.setRealm("REALM_ID").setVersion(0L).setCreationInstant(1438247896175L)
			.setPlatformGroupId("ad534449-d345-4525-94ed-d29e5aa1608f").setStatus(ClogInstanceStatusE.DELETED);
    	
    	client.persist(request, httpContext).checkedGet();
    	
    }
    

}
