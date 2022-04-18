package com.eservia.booking.di.modules;

import com.eservia.model.remote.rest.retrofit.ClientRestClientDelegate;
import com.eservia.model.remote.rest.retrofit.RestClientDelegate;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class RestClientDelegateModule {

    @Provides
    @Singleton
    public RestClientDelegate provideRestClientDelegate() {
        return new ClientRestClientDelegate();
    }
}
