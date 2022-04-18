package com.eservia.model.remote.rest.marketing.services.Marketing;

import com.eservia.model.entity.Marketing;
import com.eservia.model.remote.rest.marketing.services.MarketingServerReponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MarketingResponse extends MarketingServerReponse {

    @SerializedName("data")
    private List<Marketing> data = new ArrayList<>();

    public List<Marketing> getData() {
        return data;
    }

    public void setData(List<Marketing> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
