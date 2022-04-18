package com.eservia.model.remote.rest.retrofit;


import com.eservia.model.entity.Validator;
import com.eservia.model.remote.error.NetworkErrorCode;

import io.reactivex.functions.Function;

public class ValidResponseHandler<T> implements Function<T, T> {
    @Override
    public T apply(T t) throws Exception {
        if (t instanceof Validator && !((Validator) t).isItemValid()) {
            throw new RetrofitException(
                    t.getClass().getSimpleName() + " model is not valid ",
                    NetworkErrorCode.INVALID_RESPONSE);
        }
        return t;
    }
}
