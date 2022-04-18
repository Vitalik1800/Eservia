package com.eservia.model.remote.rest.retrofit;

import com.eservia.model.remote.rest.booking_beauty.services.BookingBeautyServerResponse;
import com.eservia.model.remote.rest.business.services.BusinessServerResponse;
import com.eservia.model.remote.rest.business_beauty.services.BusinessBeautyServerResponse;
import com.eservia.model.remote.rest.customer.services.CustomerServerReponse;
import com.eservia.model.remote.rest.file.services.FileServerResponse;
import com.eservia.model.remote.rest.marketing.services.MarketingServerReponse;
import com.eservia.model.remote.rest.order_resto.services.OrderRestoServerResponse;
import com.eservia.model.remote.rest.users.services.UsersServerResponse;

import io.reactivex.functions.Function;


class ErrorResponseHandler<T> implements Function<T, T> {

    //private final int HTTP_OK = 200;

    @Override
    public T apply(T t) throws Exception {

        if (t instanceof UsersServerResponse) {
            if (((UsersServerResponse) t).getError() != null) {
                throw new RetrofitException(((UsersServerResponse) t).getError().getErrorType(),
                        new ServerResponseError((UsersServerResponse) t));
            }
        } else if (t instanceof FileServerResponse) {
            if (((FileServerResponse) t).getError() != null) {
                throw new RetrofitException(((FileServerResponse) t).getError().getErrorType(),
                        new ServerResponseError((FileServerResponse) t));
            }
        } else if (t instanceof BusinessServerResponse) {
            if (((BusinessServerResponse) t).getStatusCode() != null) {
                throw new RetrofitException(((BusinessServerResponse) t).getStatusCode(),
                        new ServerResponseError((BusinessServerResponse) t));
            }
        } else if (t instanceof BusinessBeautyServerResponse) {
            if (((BusinessBeautyServerResponse) t).getStatusCode() != null) {
                throw new RetrofitException(((BusinessBeautyServerResponse) t).getStatusCode(),
                        new ServerResponseError((BusinessBeautyServerResponse) t));
            }
        } else if (t instanceof BookingBeautyServerResponse) {
            if (((BookingBeautyServerResponse) t).getStatusCode() != null) {
                throw new RetrofitException(((BookingBeautyServerResponse) t).getStatusCode(),
                        new ServerResponseError((BookingBeautyServerResponse) t));
            }
        } else if (t instanceof MarketingServerReponse) {
            if (((MarketingServerReponse) t).getError() != null) {
                throw new RetrofitException(((MarketingServerReponse) t).getError().getErrorType(),
                        new ServerResponseError((MarketingServerReponse) t));
            }
        } else if (t instanceof CustomerServerReponse) {
            if (((CustomerServerReponse) t).getError() != null) {
                throw new RetrofitException(((CustomerServerReponse) t).getError().getErrorType(),
                        new ServerResponseError((CustomerServerReponse) t));
            }
        } else if (t instanceof OrderRestoServerResponse) {
            if (((OrderRestoServerResponse) t).getError() != null) {
                throw new RetrofitException(((OrderRestoServerResponse) t).getError().getErrorType(),
                        new ServerResponseError((OrderRestoServerResponse) t));
            }
        }
        return t;
    }
}
