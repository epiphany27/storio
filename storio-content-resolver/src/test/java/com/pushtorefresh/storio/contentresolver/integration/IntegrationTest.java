package com.pushtorefresh.storio.contentresolver.integration;

import android.content.ContentResolver;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.contentresolver.ContentResolverTypeMapping;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.impl.DefaultStorIOContentResolver;

import org.junit.Before;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowContentResolver;

public abstract class IntegrationTest {

    @NonNull // Initialized in @Before
    protected ContentResolver contentResolver;

    @NonNull // Initialized in @Before
    protected StorIOContentResolver storIOContentResolver;

    @Before
    public void setUp() {
        contentResolver = RuntimeEnvironment.application.getContentResolver();

        storIOContentResolver = DefaultStorIOContentResolver.builder()
                .contentResolver(contentResolver)
                .addTypeMapping(TestItem.class, ContentResolverTypeMapping.<TestItem>builder()
                        .putResolver(TestItem.PUT_RESOLVER)
                        .getResolver(TestItem.GET_RESOLVER)
                        .deleteResolver(TestItem.DELETE_RESOLVER)
                        .build())
                .build();

        IntegrationContentProvider contentProvider = new IntegrationContentProvider();
        contentProvider.onCreate();

        ShadowContentResolver.registerProvider(IntegrationContentProvider.AUTHORITY, contentProvider);
    }
}
