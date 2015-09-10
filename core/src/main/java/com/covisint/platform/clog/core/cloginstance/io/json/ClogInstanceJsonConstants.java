/* Copyright (C) 2014 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.core.cloginstance.io.json;

import com.covisint.platform.clog.core.cloginstance.ClogInstance;

/**
 * Contains all JSON Contants associated with the {@link ClogInstance} resource.
 * 
 * @since Jul 8, 2015
 *
 */
final class ClogInstanceJsonConstants {

	/** name field in JSON payload */
	public static final String JSON_CLOG_INSTANCE_NAME = "name";

	/** platform solution id field in JSON payload */
	public static final String JSON_PLATFORM_SOLUTION_ID = "platformSolutionId";

	/** platform instance id field in JSON payload */
	public static final String JSON_PLATFORM_INSTANCE_ID = "platformInstanceId";

	/** platform group id in JSON payload */
	public static final String JSON_PLATFORM_GROUP_ID = "platformGroupId";

	/** status field in JSON payload */
	public static final String JSON_STATUS = "status";

	/** alias field in JSON payload */
	public static final String JSON_ALIAS = "alias";

	/** NOT TO BE INSTANTIATED */
	private ClogInstanceJsonConstants() {
	}
}
