package com.eservia.model.remote.rest.business.services.comment;

import com.eservia.model.remote.rest.business.services.BusinessServerResponse;
import com.google.gson.annotations.SerializedName;

public class CreateBusinessCommentResponse extends BusinessServerResponse {

    @SerializedName("data")
    private CreateBusinessCommentResponseData data;

    public CreateBusinessCommentResponseData getData() {
        return data;
    }

    public void setData(CreateBusinessCommentResponseData data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
