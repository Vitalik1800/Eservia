package com.eservia.model.remote.rest.business_beauty.services.promotion;

import com.eservia.model.entity.BeautyPromotion;
import com.eservia.model.remote.rest.business_beauty.services.BusinessBeautyServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeautyPromotionsResponse extends BusinessBeautyServerResponse {

    @SerializedName("data")
    private List<BeautyPromotion> data;

    public List<BeautyPromotion> getData() {
        return data;
    }

    public void setData(List<BeautyPromotion> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
