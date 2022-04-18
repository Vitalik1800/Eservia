package com.eservia.model.remote.rest.business.services.comment;

import com.eservia.model.entity.RatingPayload;
import com.google.gson.annotations.SerializedName;

public class CreateBusinessCommentRequest {

    @SerializedName("business_id")
    private Integer businessId;
    @SerializedName("rating_payload")
    private RatingPayload ratingPayload;
    @SerializedName("comment")
    private String comment;

    public CreateBusinessCommentRequest(Integer businessId, RatingPayload ratingPayload,
                                        String comment) {
        this.businessId = businessId;
        this.ratingPayload = ratingPayload;
        this.comment = comment;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public RatingPayload getRatingPayload() {
        return ratingPayload;
    }

    public void setRatingPayload(RatingPayload ratingPayload) {
        this.ratingPayload = ratingPayload;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
