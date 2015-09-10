/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.exception.handler;

import com.covisint.core.http.service.core.ServiceException;

/**
 * ClogServerException class. This class takes care of HTTP status code 500,
 * which means any internal server error
 * 
 * @author ritesh.gupta
 *
 */
public class ClogServerException extends ServiceException {

	/** Generate serial version ID */
	private static final long serialVersionUID = -2526960612679132587L;

	/**
	 * Constructor
	 * 
	 * @param statusCode
	 *            Status Code of the exception
	 * @param message
	 *            Message for the error condition
	 */
	public ClogServerException(String statusCode, String message) {
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
	public ClogServerException(String statusCode, String message,
			Throwable cause) {
		super(statusCode, message, cause);
	}

}
