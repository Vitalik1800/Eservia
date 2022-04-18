package com.eservia.model.remote.rest.business_beauty;

import androidx.annotation.NonNull;

import com.eservia.model.prefs.Profile;
import com.eservia.model.remote.rest.users.AuthRequestInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class BusinessBeautyRequestInterceptor implements Interceptor {

    @NonNull
    @Override
    public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request.Builder b = chain.request().newBuilder();

        String accessToken = AuthRequestInterceptor.provideAccessToken();
        if (accessToken != null) {

            b.addHeader("Authorization", "Bearer " + accessToken);

            if (Profile.isUserLocationKnown()) {
                float lat = Profile.getUserLastLocationLat();
                float lng = Profile.getUserLastLocationLng();
                b.addHeader("X-Coordinate-Lat", String.valueOf(lat));
                b.addHeader("X-Coordinate-Lng", String.valueOf(lng));
            }

        }
        Request request = b.build();
        return chain.proceed(request);
    }
}
