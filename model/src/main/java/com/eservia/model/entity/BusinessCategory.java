package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

public class BusinessCategory implements Validator {

    public static final String CATEGORY_BEAUTY_BOOKING = "beauty";
    public static final String CATEGORY_RESTO_DELIVERY = "resto";

    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_INACTIVE = 0;

    @SerializedName("id")
    private Integer id;
    @SerializedName("category")
    private String category;
    @SerializedName("sector_id")
    private Integer sectorId;
    @SerializedName("strategy_id")
    private Integer strategyId;
    @SerializedName("name_en")
    private String nameEn;
    @SerializedName("name_ru")
    private String nameRu;
    @SerializedName("name_uk")
    private String nameUk;
    @SerializedName("status")
    private Integer status;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("deleted_at")
    private String deletedAt;

    @Override
    public boolean isItemValid() {
        return id != null && category != null
                && sectorId != null
                && strategyId != null
                && nameEn != null
                && nameRu != null
                && nameUk != null
                && status != null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getSectorId() {
        return sectorId;
    }

    public void setSectorId(Integer sectorId) {
        this.sectorId = sectorId;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameUk() {
        return nameUk;
    }

    public void setNameUk(String nameUk) {
        this.nameUk = nameUk;
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
