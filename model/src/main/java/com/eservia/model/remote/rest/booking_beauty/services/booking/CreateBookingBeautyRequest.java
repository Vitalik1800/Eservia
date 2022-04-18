package com.eservia.model.remote.rest.booking_beauty.services.booking;

import com.google.gson.annotations.SerializedName;

public class CreateBookingBeautyRequest {

    @SerializedName("business_id")
    private Integer businessId;

    @SerializedName("address_id")
    private Integer addressId;

    @SerializedName("staff_id")
    private Integer staffId;

    @SerializedName("service_id")
    private Integer serviceId;

    @SerializedName("date")
    private String date;

    @SerializedName("comment")
    private String comment;

    @SerializedName("promotion_id")
    private Long promotionId;

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
