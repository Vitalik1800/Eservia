package com.eservia.model.remote.rest.retrofit;


import com.eservia.model.local.ContentChangesObservable;
import com.eservia.model.local.SyncEvent;
import com.eservia.model.prefs.AccessToken;
import com.eservia.model.remote.rest.booking_beauty.services.BookingBeautyServerResponse;
import com.eservia.model.remote.rest.business.services.BusinessServerResponse;
import com.eservia.model.remote.rest.business_beauty.services.BusinessBeautyServerResponse;
import com.eservia.model.remote.rest.response.ServerResponse;
import com.eservia.model.remote.rest.users.services.UsersServerResponse;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


public class WrongTokenHandler<T> implements Function<Observable<? extends Throwable>, Observable<T>> {

    private static final int MAX_RETRY = 2;
    private int retryCount = 0;

    @Override
    public Observable<T> apply(Observable<? extends Throwable> errors) throws Exception {
        return errors.flatMap(throwable -> {
            if (retryCount == MAX_RETRY) {
                return Observable.error(throwable);
            }

            if (throwable instanceof RetrofitException) {
                RetrofitException exc = (RetrofitException) throwable;
                if (exc.getCause() instanceof ServerResponseError) {

                    ServerResponse response = ((ServerResponseError) exc.getCause()).getResponse();

                    if (response instanceof BusinessServerResponse) {
                        int statusCode = ((BusinessServerResponse) response).getStatusCode();

                        if (isAuthorized(statusCode)) {
                            ContentChangesObservable.send(SyncEvent.NOT_AUTHORIZED);
                        } else {
                            retryCount++;
                            //return Observable.just(null);
                        }
                        return Observable.error(throwable);

                    } else if (response instanceof BusinessBeautyServerResponse) {
                        int statusCode = ((BusinessBeautyServerResponse) response).getStatusCode();

                        if (isAuthorized(statusCode)) {
                            ContentChangesObservable.send(SyncEvent.NOT_AUTHORIZED);
                        } else {
                            retryCount++;
                            //return Observable.just(null);
                        }
                        return Observable.error(throwable);

                    } else if (response instanceof BookingBeautyServerResponse) {
                        int statusCode = ((BookingBeautyServerResponse) response).getStatusCode();

                        if (isAuthorized(statusCode)) {
                            ContentChangesObservable.send(SyncEvent.NOT_AUTHORIZED);
                        } else {
                            retryCount++;
                            //return Observable.just(null);
                        }
                        return Observable.error(throwable);

                    } else if (response instanceof UsersServerResponse) {
                        int type = ((UsersServerResponse) response).getType();

                        if (isAuthorized(type)) {
                            ContentChangesObservable.send(SyncEvent.NOT_AUTHORIZED);
                        } else {
                            retryCount++;
                            //return Observable.just(null);
                        }
                        return Observable.error(throwable);
                    }
                }

                if (isAuthorized(exc.getCode())) {
                    ContentChangesObservable.send(SyncEvent.NOT_AUTHORIZED);
                }
            }

            // don't retry
            return Observable.error(throwable);
        });
    }

    private static boolean isAuthorized(Integer errCode) {
        return !AccessToken.isValidRefreshToken() || errCode == 401;
    }
}
