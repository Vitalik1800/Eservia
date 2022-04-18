package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

public class BeautyService {

    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_INACTIVE = 0;

    @SerializedName("id")
    private Integer id;
    @SerializedName("business_id")
    private Integer businessId;
    @SerializedName("service_group_id")
    private Integer serviceGroupId;
    @SerializedName("name")
    private String name;
    @SerializedName("duration")
    private Integer duration;
    @SerializedName("price")
    private Float price;
    @SerializedName("currency")
    private String currency;
    @SerializedName("status")
    private Integer status;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("deleted_at")
    private String deletedAt;
    @SerializedName("is_fixed_price")
    private Boolean isFixedPrice;

    private BeautyServiceGroup serviceGroup;

    public BeautyServiceGroup getServiceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(BeautyServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    public Boolean isFixedPrice() {
        return isFixedPrice;
    }

    public void setFixedPrice(Boolean fixedPrice) {
        isFixedPrice = fixedPrice;
    }

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

    public Integer getServiceGroupId() {
        return serviceGroupId;
    }

    public void setServiceGroupId(Integer serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
}
