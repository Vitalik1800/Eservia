package com.eservia.model.remote.rest.business.services.comment;

import com.eservia.model.entity.RatingPayload;
import com.google.gson.annotations.SerializedName;

public class CreateBusinessCommentResponseData {

    @SerializedName("id")
    private Integer id;
    @SerializedName("business_id")
    private Integer businessId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("rating")
    private Float rating;
    @SerializedName("rating_payload")
    private RatingPayload ratingPayload;
    @SerializedName("comment")
    private String comment;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
