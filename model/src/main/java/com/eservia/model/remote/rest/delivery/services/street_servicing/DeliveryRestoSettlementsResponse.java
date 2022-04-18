package com.eservia.model.remote.rest.delivery.services.street_servicing;

import com.eservia.model.entity.RestoDeliverySettlement;
import com.eservia.model.remote.rest.response.ServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryRestoSettlementsResponse extends ServerResponse {

    @SerializedName("data")
    private List<RestoDeliverySettlement> data;

    @Override
    public boolean isItemValid() {
        return data != null;
    }

    public List<RestoDeliverySettlement> getData() {
        return data;
    }

    public void setData(List<RestoDeliverySettlement> data) {
        this.data = data;

    }
}
