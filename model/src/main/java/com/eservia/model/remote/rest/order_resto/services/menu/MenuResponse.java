package com.eservia.model.remote.rest.order_resto.services.menu;

import com.eservia.model.entity.OrderRestoMenuData;
import com.eservia.model.remote.rest.order_resto.services.OrderRestoServerResponse;
import com.google.gson.annotations.SerializedName;

public class MenuResponse extends OrderRestoServerResponse {

    @SerializedName("data")
    private OrderRestoMenuData data;

    @Override
    public boolean isItemValid() {
        return data != null && data.isItemValid();
    }

    public OrderRestoMenuData getData() {
        return data;
    }

    public void setData(OrderRestoMenuData data) {
        this.data = data;
    }
}
