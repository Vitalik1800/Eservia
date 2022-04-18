package com.eservia.model.remote.rest.file.services;

import com.eservia.model.remote.rest.response.ServerResponse;
import com.eservia.model.remote.rest.users.services.ServerError;
import com.google.gson.annotations.SerializedName;

public abstract class FileServerResponse extends ServerResponse {

    @SerializedName("id")
    private String id;
    @SerializedName("dateTime")
    private String dateTime;
    @SerializedName("description")
    private String description;
    @SerializedName("error")
    private ServerError error;
    @SerializedName("isSuccess")
    private Boolean isSuccess = false;
    @SerializedName("isDataFull")
    private Boolean isDataFull = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Boolean getDataFull() {
        return isDataFull;
    }

    public void setDataFull(Boolean dataFull) {
        isDataFull = dataFull;
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
