package com.eservia.model.remote.rest.users.services.users;

import com.eservia.model.entity.UserInfoShort;
import com.eservia.model.remote.rest.users.services.UsersServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersResponse extends UsersServerResponse {

    @SerializedName("data")
    private List<UserInfoShort> data;

    public List<UserInfoShort> getData() {
        return data;
    }

    public void setData(List<UserInfoShort> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
