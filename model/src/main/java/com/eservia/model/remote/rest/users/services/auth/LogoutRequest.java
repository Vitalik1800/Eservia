package com.eservia.model.remote.rest.users.services.auth;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexander on 28.12.2017.
 */

public class LogoutRequest {

    @SerializedName("sessionId")
    private String sessionId;

    public LogoutRequest(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
