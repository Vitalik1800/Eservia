package com.eservia.model.remote.rest.users.services;

import com.eservia.model.remote.rest.response.ServerResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexander on 26.12.2017.
 */

public abstract class UsersServerResponse extends ServerResponse {

    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private int type;
    @SerializedName("dateTime")
    private String dateTime;
    @SerializedName("description")
    private String description;
    @SerializedName("error")
    private ServerError error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
