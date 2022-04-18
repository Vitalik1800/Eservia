package com.eservia.model.remote.rest.users.services.auth;

import com.eservia.model.entity.ProfileUserData;
import com.eservia.model.remote.rest.users.services.UsersServerResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexander on 26.12.2017.
 */

public class SignUpResponse extends UsersServerResponse {

    @SerializedName("data")
    private ProfileUserData data;

    public ProfileUserData getData() {
        return data;
    }

    public void setData(ProfileUserData data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data == null || data.isItemValid();
    }
}
