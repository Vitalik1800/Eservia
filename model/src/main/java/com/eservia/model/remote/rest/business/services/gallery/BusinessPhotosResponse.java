package com.eservia.model.remote.rest.business.services.gallery;

import com.eservia.model.entity.BusinessPhoto;
import com.eservia.model.remote.rest.business.services.BusinessServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessPhotosResponse extends BusinessServerResponse {

    @SerializedName("data")
    private List<BusinessPhoto> data;

    public List<BusinessPhoto> getData() {
        return data;
    }

    public void setData(List<BusinessPhoto> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
