package com.eservia.model.remote.rest.business_beauty.services.working_day;

import com.eservia.model.entity.WorkingDay;
import com.eservia.model.remote.rest.business_beauty.services.BusinessBeautyServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeautyWorkingDayResponse extends BusinessBeautyServerResponse {

    @SerializedName("data")
    private List<WorkingDay> data;

    public List<WorkingDay> getData() {
        return data;
    }

    public void setData(List<WorkingDay> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
