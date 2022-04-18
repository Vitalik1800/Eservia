package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Marketing {

    public static final int TYPE_NEWS = 0;
    public static final int TYPE_EVENT = 1;
    public static final int TYPE_PROMOTION = 2;

    public static final int DISCOUNT_PERCENT = 0;
    public static final int DISCOUNT_FIXED = 1;

    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_NON_ACTIVE = "nonactive";
    public static final String STATUS_ALL = "all";

    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("pathToPhoto")
    private String pathToPhoto;

    @SerializedName("beginTime")
    private String beginTime;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("publishDate")
    private String publishDate;

    @SerializedName("dateOfEditing")
    private String dateOfEditing;

    @SerializedName("marketingTypeId")
    private Integer marketingTypeId;

    @SerializedName("discountTypeId")
    private Integer discountTypeId;

    @SerializedName("address")
    private String address;

    @SerializedName("longitude")
    private Float longitude;

    @SerializedName("latitude")
    private Float latitude;

    @SerializedName("isActive")
    private Boolean isActive;

    @SerializedName("discount")
    private Float discount;

    @SerializedName("businessId")
    private Integer businessId;

    @SerializedName("links")
    private List<MarketingLink> links = null;

    @SerializedName("workSchedule")
    private List<MarketingWorkSchedule> workSchedule = null;

    private Business business;

    public Integer getDiscountTypeId() {
        return discountTypeId;
    }

    public void setDiscountTypeId(Integer discountTypeId) {
        this.discountTypeId = discountTypeId;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getDateOfEditing() {
        return dateOfEditing;
    }

    public void setDateOfEditing(String dateOfEditing) {
        this.dateOfEditing = dateOfEditing;
    }

    public Integer getMarketingTypeId() {
        return marketingTypeId;
    }

    public void setMarketingTypeId(Integer marketingTypeId) {
        this.marketingTypeId = marketingTypeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public List<MarketingLink> getLinks() {
        return links;
    }

    public void setLinks(List<MarketingLink> links) {
        this.links = links;
    }

    public List<MarketingWorkSchedule> getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(List<MarketingWorkSchedule> workSchedule) {
        this.workSchedule = workSchedule;
    }
}
