/* Copyright (C) 2014 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.elasticsearch;

public class ElasticSearchException extends Exception {

	/** Serial Version ID */
	private static final long serialVersionUID = 7230958395632218927L;

	/**
	 * Constructor
	 */
	public ElasticSearchException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            Exception Message
	 */
	public ElasticSearchException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param cause
	 *            Cause of the exception
	 */
	public ElasticSearchException(Throwable cause) {
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
	public ElasticSearchException(String message, Throwable cause) {
		super(message, cause);
	}
}
