package com.eservia.model.remote.rest.users.services.auth;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexander on 27.12.2017.
 */

public class DeviceTokenRequest {
    @SerializedName("platform")
    private String platform;
    @SerializedName("sessionId")
    private String sessionId;
    @SerializedName("deviceToken")
    private String deviceToken;

    public DeviceTokenRequest(String platform, String sessionId, String deviceToken) {
        this.platform = platform;
        this.sessionId = sessionId;
        this.deviceToken = deviceToken;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
