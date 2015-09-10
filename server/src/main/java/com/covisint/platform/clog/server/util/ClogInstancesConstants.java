/* Copyright (C) 2014 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.util;

/**
 * ClogInstancesConstants.
 * 
 * @author Lingesh.M
 *
 */
public final class ClogInstancesConstants {

	/**
	 * Private constructor
	 */
	private ClogInstancesConstants() {
		
	}
	
	/**
	 * Constants for creating HttpContext for group service
	 * operations
	 *
	 */
	public static class HttpContextConstants {
		
		/** realmId. */
		public static final String REALM_ID = "realmId";
	
		/** requestor-app. */
		public static final String REQUESTOR_APP = "requestor-app";
	
		/** requestor. */
		public static final String REQUESTOR = "requestor";
		
		/**
		 * Private constructor
		 */
		private HttpContextConstants() {
			
		}		
	}
	
	/**
	 * Constants to create Group Service
	 *
	 */
	public static class GroupServiceConstants {
		/** CLOG INSTANCE. */
		public static final String CLOG_INSTANCE = "CLOG_INSTANCE";
	
		/** locale. */
		public static final String EN = "en";
		
		/**
		 * Private constructor
		 */
		private GroupServiceConstants() {
			
		}		
	}
	
	/**
	 * Entitlement constants used to create Group Service
	 * for CLOG INSTANCE
	 * 
	 */
	public static class Entitlement {
		
		/** Constant for entitlement */
		public static final String VIEW_LOGS = "view_logs";
		
		/**
		 * Private constructor
		 */
		private Entitlement() {
			
		}	
	}

}
