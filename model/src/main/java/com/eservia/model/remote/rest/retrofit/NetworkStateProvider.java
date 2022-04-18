package com.eservia.model.remote.rest.retrofit;

import android.content.Context;

public class NetworkStateProvider {

    private final Context context;

    public NetworkStateProvider(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        return NetworkUtil.isNetworkAvailable(context);
    }
}
