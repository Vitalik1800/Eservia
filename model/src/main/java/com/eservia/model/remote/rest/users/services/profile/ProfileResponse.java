package com.eservia.model.remote.rest.users.services.profile;


import com.eservia.model.entity.ProfileUserData;
import com.eservia.model.remote.rest.users.services.UsersServerResponse;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse extends UsersServerResponse {

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
        return data.isItemValid();
    }
}
