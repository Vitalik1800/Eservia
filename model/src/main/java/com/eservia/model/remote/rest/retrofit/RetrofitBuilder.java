package com.eservia.model.remote.rest.retrofit;


import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private static final int CONNECT_TIMEOUT_S = 10;
    private static final int READ_TIMEOUT_S = 15;

    public static Retrofit createRetrofit(String baseUrl,
                                          OkHttpClient client,
                                          CallAdapter.Factory factory) {
        return new Retrofit.Builder()
                .baseUrl(fillUrl(baseUrl))
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(factory)
                .client(client)
                .build();
    }

    public static OkHttpClient getClient(Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    private static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(GeneralBookingsResponseData.class, new GeneralBookingsResponseDataDeserializer())
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .create();
    }

    private static String fillUrl(String baseUrl) {
        if (!baseUrl.endsWith("/")) baseUrl = baseUrl + "/";
        return baseUrl;
    }
}
