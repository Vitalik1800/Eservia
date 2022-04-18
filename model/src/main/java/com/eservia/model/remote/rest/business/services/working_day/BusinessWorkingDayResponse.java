package com.eservia.model.remote.rest.business.services.working_day;

import com.eservia.model.entity.WorkingDay;
import com.eservia.model.remote.rest.business.services.BusinessServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessWorkingDayResponse extends BusinessServerResponse {

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
