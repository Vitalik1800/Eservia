package com.eservia.model.remote.rest.customer.services;

import com.google.gson.annotations.SerializedName;

public class ServerError {

    @SerializedName("errorType")
    private Integer errorType;
    @SerializedName("errorDescription")
    private String errorDescription;
    @SerializedName("errorSource")
    private String errorSource;
    @SerializedName("errorName")
    private String errorName;

    public Integer getErrorType() {
        return errorType;
    }

    public void setErrorType(Integer errorType) {
        this.errorType = errorType;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorSource() {
        return errorSource;
    }

    public void setErrorSource(String errorSource) {
        this.errorSource = errorSource;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }
}
