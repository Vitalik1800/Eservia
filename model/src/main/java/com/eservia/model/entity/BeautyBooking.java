package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

public class BeautyBooking extends GeneralBooking {

    public static final int STATUS_ARCHIVE = 0;
    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_PENDING = 2;

    public static final int DECISION_CANCELLED = 0;
    public static final int DECISION_APPROVED = 1;
    public static final int DECISION_PENDING = 2;

    public static final int TYPE_BY_USER = 1;
    public static final int TYPE_BY_BUSINESS = 2;

    @SerializedName("id")
    private Integer id;
    @SerializedName("business_id")
    private Integer businessId;
    @SerializedName("address_id")
    private Integer addressId;
    @SerializedName("staff_id")
    private Integer staffId;
    @SerializedName("service_id")
    private Integer serviceId;
    @SerializedName("duration")
    private Integer duration;
    @SerializedName("amount")
    private Float amount;
    @SerializedName("currency")
    private String currency;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_phone")
    private String userPhone;
    @SerializedName("date")
    private String date;
    @SerializedName("comment")
    private String comment;
    @SerializedName("status")
    private Integer status;
    @SerializedName("decision")
    private Integer decision;
    @SerializedName("type")
    private Integer type;
    @SerializedName("is_appeared")
    private Boolean isAppeared;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("deleted_at")
    private String deletedAt;

    private Business business;

    private Address address;

    private BeautyStaff staff;

    private BeautyService service;

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BeautyStaff getStaff() {
        return staff;
    }

    public void setStaff(BeautyStaff staff) {
        this.staff = staff;
    }

    public BeautyService getService() {
        return service;
    }

    public void setService(BeautyService service) {
        this.service = service;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDecision() {
        return decision;
    }

    public void setDecision(Integer decision) {
        this.decision = decision;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getIsAppeared() {
        return isAppeared;
    }

    public void setIsAppeared(Boolean isAppeared) {
        this.isAppeared = isAppeared;
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
