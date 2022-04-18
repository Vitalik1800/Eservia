package com.eservia.model.remote.rest.department_resto;


import androidx.annotation.NonNull;

import com.eservia.model.remote.rest.users.AuthRequestInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class DepartmentRestoRequestInterceptor implements Interceptor {

    @NonNull
    @Override
    public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();

        String accessToken = AuthRequestInterceptor.provideAccessToken();
        if (accessToken != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + accessToken);
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
