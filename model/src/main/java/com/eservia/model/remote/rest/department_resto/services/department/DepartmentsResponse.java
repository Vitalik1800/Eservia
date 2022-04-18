package com.eservia.model.remote.rest.department_resto.services.department;

import com.eservia.model.entity.RestoDepartment;
import com.eservia.model.remote.rest.department_resto.services.DepartmentRestoServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DepartmentsResponse extends DepartmentRestoServerResponse {

    @SerializedName("data")
    private List<RestoDepartment> data;

    @Override
    public boolean isItemValid() {
        return data != null;
    }

    public List<RestoDepartment> getData() {
        return data;
    }

    public void setData(List<RestoDepartment> data) {
        this.data = data;

    }
}
