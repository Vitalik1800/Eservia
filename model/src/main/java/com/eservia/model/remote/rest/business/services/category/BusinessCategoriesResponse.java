package com.eservia.model.remote.rest.business.services.category;

import com.eservia.model.entity.BusinessCategory;
import com.eservia.model.remote.rest.business.services.BusinessServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessCategoriesResponse extends BusinessServerResponse {

    @SerializedName("data")
    private List<BusinessCategory> data;

    public List<BusinessCategory> getData() {
        return data;
    }

    public void setData(List<BusinessCategory> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
