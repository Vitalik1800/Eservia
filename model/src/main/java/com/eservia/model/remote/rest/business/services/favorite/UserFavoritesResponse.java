package com.eservia.model.remote.rest.business.services.favorite;

import com.eservia.model.remote.rest.business.services.BusinessServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserFavoritesResponse extends BusinessServerResponse {

    @SerializedName("data")
    private List<UserFavoritesResponseData> data;

    public List<UserFavoritesResponseData> getData() {
        return data;
    }

    public void setData(List<UserFavoritesResponseData> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
