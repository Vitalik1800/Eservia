package com.eservia.model.remote.rest.file;


import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class FileRequestInterceptor implements Interceptor {

    @NonNull
    @Override
    public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
