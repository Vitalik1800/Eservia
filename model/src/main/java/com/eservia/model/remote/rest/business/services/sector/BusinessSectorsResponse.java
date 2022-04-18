package com.eservia.model.remote.rest.business.services.sector;


import com.eservia.model.entity.BusinessSector;
import com.eservia.model.remote.rest.business.services.BusinessServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessSectorsResponse extends BusinessServerResponse {

    @SerializedName("data")
    private List<BusinessSector> data;

    public List<BusinessSector> getData() {
        return data;
    }

    public void setData(List<BusinessSector> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
