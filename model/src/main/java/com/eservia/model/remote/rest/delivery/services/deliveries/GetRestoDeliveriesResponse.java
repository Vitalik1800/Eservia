package com.eservia.model.remote.rest.delivery.services.deliveries;

import com.eservia.model.entity.RestoDelivery;
import com.eservia.model.remote.rest.response.ServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRestoDeliveriesResponse extends ServerResponse {

    @SerializedName("data")
    private List<RestoDelivery> data;

    @Override
    public boolean isItemValid() {
        return data != null;
    }

    public List<RestoDelivery> getData() {
        return data;
    }

    public void setData(List<RestoDelivery> data) {
        this.data = data;
    }
}
