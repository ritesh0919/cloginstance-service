/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.covisint.core.http.service.server.AbstractExceptionHandler;

/**
 * Clog Instance Exception Handler class. This class will implement the method to 
 * handle exceptions that are not handled by the http framework
 * 
 * @author ritesh.gupta
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ClogExceptionHandler extends AbstractExceptionHandler {

	/**
	 * Handle {@link UpStreamServerException}.
	 * 
	 * @param ex
	 *            UpstreamServerException to be handled
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String,Object> map with error message object
	 */
	@ExceptionHandler
	public ModelAndView handleUpStreamServerException(final UpstreamServerException ex, 
			HttpServletRequest request) {

		return super.buildErrorModel(HttpStatus.SC_BAD_GATEWAY, "clog:server", ex.getMessage());
	}
	
	/**
	 * Handle {@link ClogServerException}.
	 * 
	 * @param ex
	 *            ClogServerException to be handled
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String,Object> map with error message object
	 */
	@ExceptionHandler
	public ModelAndView handleClogServerException(final ClogServerException ex, 
			HttpServletRequest request) {

		return super.buildErrorModel(HttpStatus.SC_INTERNAL_SERVER_ERROR, "clog:server", ex.getMessage());
	}
}
