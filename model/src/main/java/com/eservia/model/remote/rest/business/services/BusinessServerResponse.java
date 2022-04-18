package com.eservia.model.remote.rest.business.services;

import com.eservia.model.remote.rest.response.ServerResponse;
import com.google.gson.annotations.SerializedName;

public abstract class BusinessServerResponse extends ServerResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("status_code")
    private Integer statusCode;
    @SerializedName("links")
    private Links links;
    @SerializedName("meta")
    private Meta meta;

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
