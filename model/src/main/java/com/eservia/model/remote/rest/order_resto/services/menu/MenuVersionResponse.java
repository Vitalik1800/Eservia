package com.eservia.model.remote.rest.order_resto.services.menu;

import com.eservia.model.remote.rest.order_resto.services.OrderRestoServerResponse;
import com.google.gson.annotations.SerializedName;

public class MenuVersionResponse extends OrderRestoServerResponse {

    @SerializedName("data")
    private Long data;

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
