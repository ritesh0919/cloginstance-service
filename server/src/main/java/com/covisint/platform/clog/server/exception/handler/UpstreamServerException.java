/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.exception.handler;

import com.covisint.core.http.service.core.ServiceException;

/**
 * UpstreamServerException taking care of HTTP status code 502
 * 
 * @author ritesh.gupta
 *
 */
public class UpstreamServerException extends ServiceException {

	/** Generated Serial Version ID */
	private static final long serialVersionUID = -2284745054167482517L;

	/**
	 * Constructor
	 * 
	 * @param statusCode
	 *            Status Code of the exception
	 * @param message
	 *            Message for the error condition
	 */
	public UpstreamServerException(String statusCode, String message) {
		super(statusCode, message);
	}

	/**
	 * Constructor
	 * 
	 * @param statusCode
	 *            Status Code of the exception
	 * @param message
	 *            Message for the error condition
	 * @param cause
	 *            Cause of the exception
	 */
	public UpstreamServerException(String statusCode, String message,
			Throwable cause) {
		super(statusCode, message, cause);
	}


}
