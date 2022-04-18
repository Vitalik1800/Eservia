package com.eservia.model.remote.rest.customer.services.promoter_customer;

import com.eservia.model.entity.PromoterCustomerBusiness;
import com.eservia.model.remote.rest.customer.services.CustomerServerReponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PromoterCustomerBusinessesResponse extends CustomerServerReponse {

    @SerializedName("data")
    private List<PromoterCustomerBusiness> data;

    @Override
    public boolean isItemValid() {
        return data != null;
    }

    public List<PromoterCustomerBusiness> getData() {
        return data;
    }

    public void setData(List<PromoterCustomerBusiness> data) {
        this.data = data;
    }
}
