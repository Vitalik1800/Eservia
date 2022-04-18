package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Business {

    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_INACTIVE = 0;

    public static final int WITH_TRASHED = 1;
    public static final int WITHOUT_TRASHED = 0;

    public static final int SEARCHABLE_TRUE = 1;
    public static final int SEARCHABLE_FALSE = 0;

    private Integer id;
    @SerializedName("promoter_id")
    private Integer promoterId;
    @SerializedName("sector_id")
    private Integer sectorId;
    @SerializedName("strategy_id")
    private Integer strategyId;
    @SerializedName("name")
    private String name;
    @SerializedName("short_description")
    private String shortDescription;
    @SerializedName("description")
    private String description;
    @SerializedName("alias")
    private String alias;
    @SerializedName("url")
    private String url;
    @SerializedName("rating")
    private Float rating;
    @SerializedName("rating_payload")
    private RatingPayload ratingPayload;
    @SerializedName("comments")
    private Integer comments;
    @SerializedName("plan_id")
    private Integer planId;
    @SerializedName("plan_prepaid")
    private Integer planPrepaid;
    @SerializedName("status")
    private Integer status;
    @SerializedName("is_verified")
    private Boolean isVerified;
    @SerializedName("is_searchable")
    private Boolean isSearchable;
    @SerializedName("background")
    private String background;
    @SerializedName("logo")
    private String logo;
    @SerializedName("link_instagram")
    private String linkInstagram;
    @SerializedName("link_facebook")
    private String linkFacebook;
    @SerializedName("plan_expire_in")
    private String planExpireIn;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("deleted_at")
    private String deletedAt;
    @SerializedName("is")
    private Is is;
    @SerializedName("can")
    private Can can;

    private final List<Address> addresses = new ArrayList<>();

    private final List<BusinessPhoto> photos = new ArrayList<>();

    private final List<BeautyStaff> staffs = new ArrayList<>();

    private final List<Marketing> marketings = new ArrayList<>();

    private final List<BusinessComment> commentsItems = new ArrayList<>();

    private BusinessSector sector;

    public BusinessSector getSector() {
        return sector;
    }

    public void setSector(BusinessSector sector) {
        this.sector = sector;
    }

    public List<BusinessComment> getCommentsItems() {
        return commentsItems;
    }

    public List<BusinessPhoto> getPhotos() {
        return photos;
    }

    public List<BeautyStaff> getStaffs() {
        return staffs;
    }

    public List<Marketing> getMarketings() {
        return marketings;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(Integer promoterId) {
        this.promoterId = promoterId;
    }

    public Integer getSectorId() {
        return sectorId;
    }

    public void setSectorId(Integer sectorId) {
        this.sectorId = sectorId;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Boolean getSearchable() {
        return isSearchable;
    }

    public void setSearchable(Boolean searchable) {
        isSearchable = searchable;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getPlanPrepaid() {
        return planPrepaid;
    }

    public void setPlanPrepaid(Integer planPrepaid) {
        this.planPrepaid = planPrepaid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLinkInstagram() {
        return linkInstagram;
    }

    public void setLinkInstagram(String linkInstagram) {
        this.linkInstagram = linkInstagram;
    }

    public String getLinkFacebook() {
        return linkFacebook;
    }

    public void setLinkFacebook(String linkFacebook) {
        this.linkFacebook = linkFacebook;
    }

    public String getPlanExpireIn() {
        return planExpireIn;
    }

    public void setPlanExpireIn(String planExpireIn) {
        this.planExpireIn = planExpireIn;
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

    public Is getIs() {
        return is;
    }

    public void setIs(Is is) {
        this.is = is;
    }

    public Can getCan() {
        return can;
    }

    public void setCan(Can can) {
        this.can = can;
    }
}
