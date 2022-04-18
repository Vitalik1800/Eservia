package com.eservia.booking.util;


import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.eservia.booking.R;
import com.eservia.booking.util.parsers.ErrorUsersParser;
import com.eservia.model.remote.error.NetworkErrorCode;
import com.eservia.model.remote.rest.retrofit.RetrofitException;
import com.eservia.model.remote.rest.retrofit.ServerResponseError;
import com.eservia.model.remote.rest.users.services.UsersServerResponse;

import java.util.concurrent.TimeoutException;

public class ServerErrorUtil {

    public static int getErrorCode(@NonNull Throwable throwable) {
        int errorCode = 0;
        if (throwable instanceof RetrofitException) {
            RetrofitException e = (RetrofitException) throwable;
            errorCode = e.getCode();
        }
        return errorCode;
    }

    public static boolean isConnectionError(@NonNull Throwable throwable) {
        if (throwable instanceof RetrofitException) {
            RetrofitException e = (RetrofitException) throwable;
            return e.getCode() == NetworkErrorCode.NO_INTERNET ||
                    e.getCode() == NetworkErrorCode.TIMEOUT;
        } else return throwable instanceof TimeoutException;
    }

    @StringRes
    public static int getErrorStringRes(@NonNull Throwable throwable) {
        if (throwable instanceof RetrofitException) {
            RetrofitException e = (RetrofitException) throwable;
            if (e.getCause() instanceof ServerResponseError) {
                ServerResponseError err = ((ServerResponseError) e.getCause());
                if (err.getResponse() instanceof UsersServerResponse){
                    return parseUsersErrorCode(e.getCode());
                }
            }
            return parseErrorCode(e.getCode());
        } else if (throwable instanceof TimeoutException) {
            return R.string.error_no_internet;
        } else {
            return R.string.error_unknown;
        }
    }

    public static String getServerFirstErrorString() {
        return "";
    }

    @StringRes
    private static int parseErrorCode(int errorCode) {
        switch (errorCode) {
            case NetworkErrorCode.TIMEOUT:
                return R.string.timeout;
            case NetworkErrorCode.NO_INTERNET:
                return R.string.error_no_internet;
            default:
                return R.string.unknown_error;
        }
    }

    @StringRes
    private static int parseUsersErrorCode(int errorCode) {
        return ErrorUsersParser.parseErrorCode(errorCode);
    }
}
