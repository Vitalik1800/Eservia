package com.eservia.model.remote.socket.request;

import com.google.gson.annotations.SerializedName;

public class LeaveRequestData {

    @SerializedName("token")
    private String token;

    @SerializedName("guid")
    private String guid;

    @SerializedName("resource_id")
    private Long businessId;

    public LeaveRequestData() {}

    public LeaveRequestData(String token, String guid, Long businessId) {
        this.token = token;
        this.guid = guid;
        this.businessId = businessId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }
}
