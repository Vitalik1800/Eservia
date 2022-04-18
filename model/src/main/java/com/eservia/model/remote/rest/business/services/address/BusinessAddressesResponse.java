package com.eservia.model.remote.rest.business.services.address;

import com.eservia.model.entity.Address;
import com.eservia.model.remote.rest.business.services.BusinessServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessAddressesResponse extends BusinessServerResponse {

    @SerializedName("data")
    private List<Address> data;

    public List<Address> getData() {
        return data;
    }

    public void setData(List<Address> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
