package com.eservia.model.remote.rest.order_resto.services.orders;

import com.eservia.model.entity.RestoOrderResponseData;
import com.eservia.model.remote.rest.order_resto.services.OrderRestoServerResponse;
import com.google.gson.annotations.SerializedName;

public class PostRestoOrderResponse extends OrderRestoServerResponse {

    @SerializedName("data")
    private RestoOrderResponseData data;

    public RestoOrderResponseData getData() {
        return data;
    }

    public void setData(RestoOrderResponseData data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
