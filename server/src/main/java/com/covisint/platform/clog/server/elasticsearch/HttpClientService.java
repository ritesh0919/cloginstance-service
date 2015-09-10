/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.elasticsearch;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * HttpClient wrapper class. 
 * 
 * @since 25th August 2015
 */
public class HttpClientService {

	/** Logger for HttpClient class */
	private static final Logger LOG = LoggerFactory.getLogger(HttpClientService.class);

	/**
	 * Default constructor
	 */
	public HttpClientService() {
	}

	/**
	 * Method to make a POST call to the provided URL with the payload
	 * 
	 * @param url
	 *            URL of the server to post the request
	 * @param payload
	 *            Payload to be sent
	 * @throws HttpException
	 */
	public void post(String url, String payload) throws HttpException {
		
		if (HttpClientService.LOG.isDebugEnabled()) {
			HttpClientService.LOG.debug("Post URL: {}", url);
			HttpClientService.LOG.debug("Payload Data : {}", payload);
		}
		
		final CloseableHttpClient httpClient = HttpClients.createDefault();

		try {
			final HttpPost post = new HttpPost(url);

			final StringEntity params = new StringEntity(payload);
			post.setEntity(params);

			final HttpResponse response = httpClient.execute(post);
			
			if (HttpClientService.LOG.isDebugEnabled()) {
				HttpClientService.LOG.debug("Response received for post: {}", response);
			}
			
			this.is200Response(response);
			
			if (HttpClientService.LOG.isDebugEnabled()) {
				HttpClientService.LOG.debug("Post executed successfully...");
			}
		} catch (IOException e) {
			HttpClientService.LOG.error("Exception while post execution. Exception: {}", e);
			throw new HttpException(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				HttpClientService.LOG.warn("Error while closing closable http client. Exception: {}", e);
			}
		}
	}

	/**
	 * Method to make a delete call a resource from the remote server
	 * 
	 * @param url
	 *            URL to delete a resource
	 * @throws HttpException
	 */
	public void delete(String url) throws HttpException {

		if (HttpClientService.LOG.isDebugEnabled()) {
			HttpClientService.LOG.debug("Delete URL: {}", url);
		}

		final CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			final HttpDelete delete = new HttpDelete(url);
			final HttpResponse response = httpClient.execute(delete);
			
			if (HttpClientService.LOG.isDebugEnabled()) {
				HttpClientService.LOG.debug("Response received for delete: {}", response);
			}
			
			this.is200Response(response);
			
			if (HttpClientService.LOG.isDebugEnabled()) {
				HttpClientService.LOG.debug("Delete executed successfully...");
			}
			
		} catch (IOException e) {
			HttpClientService.LOG.error("Exception while executing delete operation. Exception: {}", e);
			throw new HttpException(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				HttpClientService.LOG.warn("Error while closing closable http client. Exception: {}", e);
			}
		}
	}
	
	/**
	 * Method to check the HttpResponse status code. It checks if the status
	 * code is between 200 (inclusive) and 300 (exclusive)
	 * 
	 * @param response
	 *            HttpResponse to be checked
	 * @throws HttpException
	 */
	private void is200Response(HttpResponse response) throws HttpException {
		
		//TODO: Need to check the response text as well
		
		if (response != null && null != response.getStatusLine()) {
			final int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				
				if (HttpClientService.LOG.isDebugEnabled()) {
					HttpClientService.LOG.debug("Status successful. Code: {}", status);
				}
			} else {
				
				HttpEntity entity = response.getEntity();
				String error = null;
				try {
					String responseString = EntityUtils.toString(entity, "UTF-8");
					if (!Strings.isNullOrEmpty(responseString)) {
						error = this.getError(responseString);
					}
				} catch (ParseException | IOException e) {
					HttpClientService.LOG.warn("Error while getting the error response string from elastic search response. Excepiton: {}", e);
				}
				
				final String msg = new StringBuilder("Response failed. ")
					.append(error)
					.append(" Response code: ")
					.append(response.getStatusLine().getStatusCode())
					.toString();
				
				HttpClientService.LOG.error(msg);
				throw new HttpException(msg);
			}
		}
	}
	
	/**
	 * 
	 * @param responseString
	 * @return
	 */
	private String getError(String responseString) {
		try {
			JsonReader reader = Json.createReader(new ByteArrayInputStream(responseString.getBytes()));
			JsonObject object = reader.readObject();
			return object.getString("error");
		} catch (Exception e) {
			HttpClientService.LOG.info("Parsing reponse string failed. Nothing to do...");
		}
		
		return null;
	}
}
