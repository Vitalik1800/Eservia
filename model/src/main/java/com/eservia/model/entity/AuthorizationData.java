package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexander on 27.12.2017.
 */

public class AuthorizationData implements Validator {

    @SerializedName("token")
    private String token;
    @SerializedName("expirationTime")
    private long expiration;
    @SerializedName("id")
    private String id;

    public String getToken() {
        return token;
    }

    public long getExpiration() {
        return expiration;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    @Override
    public boolean isItemValid() {
        return token != null && !token.isEmpty();
    }
}
