package com.eservia.booking.di.modules;


import android.content.Context;

import com.eservia.model.remote.rest.retrofit.NetworkStateProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkStateProviderModule {

    @Provides
    @Singleton
    public NetworkStateProvider provideNetworkStateProvider(Context context) {
        return new NetworkStateProvider(context);
    }
}
