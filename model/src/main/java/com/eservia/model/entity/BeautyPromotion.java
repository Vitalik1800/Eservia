package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BeautyPromotion {

    public static final int WITH_TRASHED = 1;
    public static final int WITHOUT_TRASHED = 0;

    @SerializedName("id")
    private Long id;

    @SerializedName("business_id")
    private Long businessId;

    @SerializedName("marketing_id")
    private Long marketingId;

    @SerializedName("max_count")
    private Integer maxCount;

    @SerializedName("count")
    private Integer count;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("deleted_at")
    private String deletedAt;

    private List<BeautyService> services = new ArrayList<>();

    public List<BeautyService> getServices() {
        return services;
    }

    public void setServices(List<BeautyService> services) {
        this.services = services;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(Long marketingId) {
        this.marketingId = marketingId;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeautyPromotion that = (BeautyPromotion) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(businessId, that.businessId))
            return false;
        if (!Objects.equals(marketingId, that.marketingId))
            return false;
        if (!Objects.equals(maxCount, that.maxCount))
            return false;
        if (!Objects.equals(count, that.count)) return false;
        if (!Objects.equals(createdAt, that.createdAt))
            return false;
        if (!Objects.equals(updatedAt, that.updatedAt))
            return false;
        return Objects.equals(deletedAt, that.deletedAt);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (businessId != null ? businessId.hashCode() : 0);
        result = 31 * result + (marketingId != null ? marketingId.hashCode() : 0);
        result = 31 * result + (maxCount != null ? maxCount.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "BeautyPromotion{" +
                "id=" + id +
                ", businessId=" + businessId +
                ", marketingId=" + marketingId +
                ", maxCount=" + maxCount +
                ", count=" + count +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}
