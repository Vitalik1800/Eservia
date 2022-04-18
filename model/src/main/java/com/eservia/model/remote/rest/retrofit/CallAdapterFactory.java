package com.eservia.model.remote.rest.retrofit;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eservia.model.remote.error.NetworkErrorCode;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.HttpException;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class CallAdapterFactory extends CallAdapter.Factory {

    private final RxJava2CallAdapterFactory origin;
    private final ValidResponseHandler validResponseHandler = new ValidResponseHandler();

    public CallAdapterFactory() {
        origin = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new CallAdapterFactory();
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        return new CallAdapterWrapper(origin.get(returnType, annotations, retrofit), retrofit);
    }

    private class CallAdapterWrapper<R> implements CallAdapter<R, Observable> {
        private final Retrofit retrofit;
        private final CallAdapter<R, Observable<R>> callAdapter;

        public CallAdapterWrapper(CallAdapter<R, Observable<R>> callAdapter, Retrofit retrofit) {
            this.callAdapter = callAdapter;
            this.retrofit = retrofit;
        }

        @NonNull
        @Override
        public Type responseType() {
            return callAdapter.responseType();
        }

        @NonNull
        @Override
        public Observable adapt(Call<R> call) {
            return ((Observable) callAdapter.adapt(call))
                    .onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                        @Override
                        public ObservableSource apply(Throwable throwable) throws Exception {
                            return Observable.error(asRetrofitException(throwable));
                        }
                    })
                    .map(new ErrorResponseHandler<>())
                    .map(validResponseHandler);
        }

        private RetrofitException asRetrofitException(Throwable throwable) {
            // We had non-200 http error
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                return RetrofitException.httpError(
                        response.raw().request().url().toString(), response, retrofit);
            }
            // A network error happened
            if (throwable instanceof IOException) {
                if (throwable instanceof SocketTimeoutException) {
                    return RetrofitException.networkError(
                            (IOException) throwable, retrofit, NetworkErrorCode.TIMEOUT);
                }
                return RetrofitException.networkError((IOException) throwable, retrofit);
            }

            // We don't know what happened. We need to simply convert to an unknown error
            return RetrofitException.unexpectedError(throwable, retrofit);
        }
    }
}
