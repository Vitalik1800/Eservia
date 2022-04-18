package com.eservia.booking.di.modules;


import com.eservia.model.remote.rest.retrofit.NetworkStateProvider;
import com.eservia.model.remote.rest.retrofit.RestClientDelegate;
import com.eservia.model.remote.rest.retrofit.RetrofitRestClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RetrofitRestClientModule {

    @Provides
    @Singleton
    public RetrofitRestClient provideRetrofitRestClient(NetworkStateProvider stateProvider,
                                                        RestClientDelegate clientDelegate) {
        return new RetrofitRestClient(stateProvider, clientDelegate);
    }
}
