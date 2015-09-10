/* Copyright (C) 2014 Covisint. All Rights Reserved. */

package com.covisint.platform.clog.cloginstance.client;

import com.covisint.core.http.service.client.BaseResourceClientBuilder;
import com.covisint.core.support.constraint.Nonnull;
import com.covisint.core.support.constraint.NotEmpty;
import com.covisint.platform.clog.core.SupportedMediaTypesE;
import com.google.common.net.MediaType;

/** Default implementation of a {@link ClogClient} builder. */
public class ClogClientBuilder extends BaseResourceClientBuilder<ClogClientBuilder, ClogClientImpl> {

    /** Base path to the resource collection endpoint. */
    private static final String RESOURCE_COLLECTION_PATH = "/cloginstance";

    /** {@inheritDoc} */
    @Override
    @Nonnull
    @NotEmpty
    protected final String getResourceCollectionPath() {
        return RESOURCE_COLLECTION_PATH;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected final MediaType getResourceRepresentation() {
        return MediaType.parse(SupportedMediaTypesE.CLOG_INSTANCE_V1_MEDIA_TYPE.string());

    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public final ClogClientImpl build() {
        return populateBaseBuilder(new ClogClientImpl());
    }

}