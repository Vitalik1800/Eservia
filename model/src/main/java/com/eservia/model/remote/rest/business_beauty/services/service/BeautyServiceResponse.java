package com.eservia.model.remote.rest.business_beauty.services.service;


import com.eservia.model.entity.BeautyService;
import com.eservia.model.remote.rest.business_beauty.services.BusinessBeautyServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeautyServiceResponse extends BusinessBeautyServerResponse {

    @SerializedName("data")
    private List<BeautyService> data;

    public List<BeautyService> getData() {
        return data;
    }

    public void setData(List<BeautyService> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
