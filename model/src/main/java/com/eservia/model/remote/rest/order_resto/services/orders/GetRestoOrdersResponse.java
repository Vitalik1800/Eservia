package com.eservia.model.remote.rest.order_resto.services.orders;

import com.eservia.model.entity.RestoOrderResponseData;
import com.eservia.model.remote.rest.order_resto.services.OrderRestoServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRestoOrdersResponse extends OrderRestoServerResponse {

    @SerializedName("data")
    private List<RestoOrderResponseData> data;

    public List<RestoOrderResponseData> getData() {
        return data;
    }

    public void setData(List<RestoOrderResponseData> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
