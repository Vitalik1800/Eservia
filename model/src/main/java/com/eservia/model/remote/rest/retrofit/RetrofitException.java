package com.eservia.model.remote.rest.retrofit;


import androidx.annotation.Nullable;

import com.eservia.model.remote.error.NetworkErrorCode;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitException extends RuntimeException {

    private final String url;
    private final Response response;
    private final Kind kind;
    private final int code;
    private final Retrofit retrofit;

    public RetrofitException(int code) {
        this(null, null, null, null, null, code, null);
    }

    public RetrofitException(int code, Throwable throwable) {
        this(null, throwable, null, null, null, code, null);
    }

    public RetrofitException(String message, int code) {
        this(message, null, null, null, null, code, null);
    }

    public RetrofitException(String message, Throwable throwable, String url, Response response,
                             Kind kind, int code, Retrofit retrofit) {
        super(message, throwable);
        this.url = url;
        this.response = response;
        this.kind = kind;
        this.code = code;
        this.retrofit = retrofit;
    }

    public String getUrl() {
        return url;
    }

    public Response getResponse() {
        return response;
    }

    public Kind getKind() {
        return kind;
    }

    public int getCode() {
        return code;
    }

    public static RetrofitException httpError(String url, Response response, Retrofit retrofit) {
        String message = response.code() + " " + response.message();
        return new RetrofitException(message, null, url, response, Kind.HTTP, response.code(), retrofit);
    }

    public static RetrofitException networkError(IOException exception, Retrofit retrofit) {
        return networkError(exception, retrofit, NetworkErrorCode.UNKNOWN);
    }

    public static RetrofitException networkError(IOException exception, Retrofit retrofit, int errorCode) {
        return new RetrofitException(exception.getMessage(), exception, null, null,
                Kind.NETWORK, errorCode, retrofit);
    }

    public static RetrofitException unexpectedError(Throwable exception, Retrofit retrofit) {
        return new RetrofitException(exception.getMessage(), exception, null, null,
                Kind.UNEXPECTED, NetworkErrorCode.UNKNOWN, retrofit);
    }

    @Nullable
    public <T> T getErrorBodyAs(Class<T> type) throws IOException {
        if (response == null || response.errorBody() == null) {
            return null;
        }
        Converter<ResponseBody, T> converter = retrofit.responseBodyConverter(type, new Annotation[0]);
        return converter.convert(response.errorBody());
    }

    public enum Kind {
        /**
         * An {@link java.io.IOException} occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}
