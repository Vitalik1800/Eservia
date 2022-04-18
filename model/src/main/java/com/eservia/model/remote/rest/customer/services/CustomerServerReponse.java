package com.eservia.model.remote.rest.customer.services;

import com.eservia.model.remote.rest.response.ServerResponse;
import com.google.gson.annotations.SerializedName;

public abstract class CustomerServerReponse extends ServerResponse {

    @SerializedName("id")
    private String id;
    @SerializedName("dateTime")
    private String dateTime;
    @SerializedName("type")
    private int type;
    @SerializedName("description")
    private String description;
    @SerializedName("isSuccess")
    private Boolean isSuccess;
    @SerializedName("error")
    private ServerError error;
    @SerializedName("paging")
    private Paging paging;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServerError getError() {
        return error;
    }

    public void setError(ServerError error) {
        this.error = error;
    }
}
