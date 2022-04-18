package com.eservia.model.remote.rest.business_beauty.services.service_group;

import com.eservia.model.entity.BeautyServiceGroup;
import com.eservia.model.remote.rest.business_beauty.services.BusinessBeautyServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeautyServiceGroupResponse extends BusinessBeautyServerResponse {

    @SerializedName("data")
    private List<BeautyServiceGroup> data;

    public List<BeautyServiceGroup> getData() {
        return data;
    }

    public void setData(List<BeautyServiceGroup> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
