package com.eservia.model.remote.rest.business.services.comment;

import com.eservia.model.entity.BusinessComment;
import com.eservia.model.remote.rest.business.services.BusinessServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessCommentsResponse extends BusinessServerResponse {

    @SerializedName("data")
    private List<BusinessComment> data;

    public List<BusinessComment> getData() {
        return data;
    }

    public void setData(List<BusinessComment> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
