/* Copyright (C) 2014 Covisint. All Rights Reserved. */

package com.covisint.platform.clog.cloginstance.client;

import com.covisint.core.http.service.client.BaseResourceClientFactory;
import com.covisint.core.http.service.core.HttpServiceError;
import com.covisint.core.http.service.core.io.jsonp.HttpServiceErrorReader;
import com.covisint.core.http.service.core.io.jsonp.HttpServiceErrorWriter;
import com.covisint.core.support.constraint.Nonnull;
import com.covisint.core.support.constraint.NotEmpty;
import com.covisint.platform.clog.core.cloginstance.io.json.ClogInstanceReader;
import com.covisint.platform.clog.core.cloginstance.io.json.ClogInstanceWriter;

/** Factory for {@link ClogClientImpl} objects. */
public class ClogClientFactory extends BaseResourceClientFactory<ClogClientBuilder, ClogClientImpl> {

    /**
     * Constructor.
     *
     * @param serviceUrl the base url.
     */
    public ClogClientFactory(@Nonnull @NotEmpty String serviceUrl) {
        super(serviceUrl);
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected final ClogClientBuilder newBuilder() {
        return new ClogClientBuilder();
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected final ClogClientImpl buildResourceClient(@Nonnull ClogClientBuilder builder) {
        builder.addEntityReader(new HttpServiceErrorReader<HttpServiceError>()).addEntityReader(new ClogInstanceReader());
        builder.addEntityWriter(new HttpServiceErrorWriter<HttpServiceError>()).addEntityWriter(new ClogInstanceWriter());
        return builder.build();
    }

}
