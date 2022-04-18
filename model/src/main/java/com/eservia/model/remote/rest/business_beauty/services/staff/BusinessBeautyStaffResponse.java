package com.eservia.model.remote.rest.business_beauty.services.staff;

import com.eservia.model.entity.BeautyStaff;
import com.eservia.model.remote.rest.business_beauty.services.BusinessBeautyServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessBeautyStaffResponse extends BusinessBeautyServerResponse {

    @SerializedName("data")
    private List<BeautyStaff> data;

    public List<BeautyStaff> getData() {
        return data;
    }

    public void setData(List<BeautyStaff> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
