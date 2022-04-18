package com.eservia.model.remote.rest.users;

import androidx.annotation.NonNull;

import com.eservia.model.prefs.AccessToken;
import com.eservia.model.entity.AuthData;
import com.eservia.model.local.ContentChangesObservable;
import com.eservia.model.local.SyncEvent;
import com.eservia.model.remote.UrlList;
import com.eservia.model.remote.rest.retrofit.RetrofitBuilder;
import com.eservia.model.remote.rest.users.services.auth.AuthResponse;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by Alexander on 26.12.2017.
 */

public class AuthRequestInterceptor implements Interceptor {

    @NonNull
    @Override
    public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();

        String accessToken = provideAccessToken();
        if (accessToken != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + accessToken);
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

    public static synchronized String provideAccessToken() {
        if (!AccessToken.tokenIsExpired()) {
            return AccessToken.getAccessToken();
        }

        if (AccessToken.isValidRefreshToken()) {
            try {
                AuthData data = new UsersServer(UrlList.provideUsersApiUrlPlusVer(), RetrofitBuilder.getClient(
                        new HttpLoggingInterceptor().setLevel(
                                HttpLoggingInterceptor.Level.BODY)))

                        .provideAuthService()
                        .flatMap(service -> service.refreshToken(AccessToken.getRefreshToken()))
                        .map(AuthResponse::getData)
                        .blockingFirst();

                AccessToken.setToken(data);
                return AccessToken.getAccessToken();

            } catch (Exception e) {
                AccessToken.resetToken();
                ContentChangesObservable.send(SyncEvent.NOT_AUTHORIZED);
                return null;
            }
        }
        return null;
    }
}
