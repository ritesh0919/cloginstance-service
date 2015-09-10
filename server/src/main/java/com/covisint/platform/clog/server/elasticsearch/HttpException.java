/* Copyright (C) 2014 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.elasticsearch;

/**
 * HttpException wrapper
 * 
 */
public class HttpException extends Exception {

	/** Serial Version ID */
	private static final long serialVersionUID = -4948955594347997792L;

	/**
	 * Constructor
	 */
	public HttpException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            Exception Message
	 */
	public HttpException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param cause
	 *            Cause of the exception
	 */
	public HttpException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            Exception message
	 * @param cause
	 *            Cause of the exception
	 */
	public HttpException(String message, Throwable cause) {
		super(message, cause);
	}
}
