package com.eservia.model.remote.rest.business.services.cities;

import com.eservia.model.entity.BusinessCity;
import com.eservia.model.remote.rest.business.services.BusinessServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessCitiesResponse extends BusinessServerResponse {

    @SerializedName("data")
    private List<BusinessCity> data;

    public List<BusinessCity> getData() {
        return data;
    }

    public void setData(List<BusinessCity> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
