/* Copyright (C) 2014 Covisint. All Rights Reserved. */

package com.covisint.platform.clog.cloginstance.client;

import org.apache.http.protocol.HttpContext;

import com.covisint.core.http.service.client.BaseResourceClient;
import com.covisint.core.http.service.core.ServiceException;
import com.covisint.core.support.constraint.Nonnull;
import com.covisint.platform.clog.core.cloginstance.ClogInstance;
import com.google.common.util.concurrent.CheckedFuture;

/** Resource client implementation that operates on {@link clogInstance}. */
public class ClogClientImpl extends BaseResourceClient<ClogInstance> implements
		ClogClient {

	/** {@inheritDoc} */
	@Override
	@Nonnull
	public CheckedFuture<ClogInstance, ServiceException> persist(
			@Nonnull ClogInstance clogInstance, @Nonnull HttpContext httpContext) {
		throw new UnsupportedOperationException();
	}
}