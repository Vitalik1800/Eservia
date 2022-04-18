package com.eservia.model.remote.rest.business.services.business;


import com.eservia.model.entity.Business;
import com.eservia.model.remote.rest.business.services.BusinessServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessesResponse extends BusinessServerResponse {

    @SerializedName("data")
    private List<Business> data;

    public List<Business> getData() {
        return data;
    }

    public void setData(List<Business> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
