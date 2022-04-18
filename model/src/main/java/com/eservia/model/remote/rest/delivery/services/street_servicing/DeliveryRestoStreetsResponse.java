package com.eservia.model.remote.rest.delivery.services.street_servicing;

import com.eservia.model.entity.RestoDeliveryStreet;
import com.eservia.model.remote.rest.response.ServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryRestoStreetsResponse extends ServerResponse {

    @SerializedName("data")
    private List<RestoDeliveryStreet> data;

    @Override
    public boolean isItemValid() {
        return data != null;
    }

    public List<RestoDeliveryStreet> getData() {
        return data;
    }

    public void setData(List<RestoDeliveryStreet> data) {
        this.data = data;

    }
}
