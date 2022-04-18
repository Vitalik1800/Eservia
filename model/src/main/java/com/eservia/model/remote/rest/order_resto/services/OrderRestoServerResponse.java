package com.eservia.model.remote.rest.order_resto.services;

import com.eservia.model.remote.rest.response.ServerResponse;
import com.google.gson.annotations.SerializedName;

public abstract class OrderRestoServerResponse extends ServerResponse {

    @SerializedName("isSuccess")
    private Boolean isSuccess;

    @SerializedName("error")
    private ServerError error;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public ServerError getError() {
        return error;
    }

    public void setError(ServerError error) {
        this.error = error;
    }
}
