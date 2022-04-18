package com.eservia.model.entity;


import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class WorkingDay {

    @SerializedName("id")
    private Integer workingDayId;
    @SerializedName("business_id")
    private Integer businessId;
    @SerializedName("object_type")
    private String object;
    @SerializedName("object_id")
    private Integer objectId;
    @SerializedName("rule")
    private String rule;
    @SerializedName("is_exclusion")
    private Boolean isExclusion = false;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    @NonNull
    @Override
    public String toString() {
        if (rule == null || isExclusion == null) {
            return super.toString();
        }
        if (isExclusion) {
            return "EX " + rule;
        } else {
            return "   " + rule;
        }
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getWorkingDayId() {
        return workingDayId;
    }

    public void setWorkingDayId(Integer workingDayId) {
        this.workingDayId = workingDayId;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Boolean isExclusion() {
        return isExclusion;
    }

    public void setExclusion(Boolean exclusion) {
        isExclusion = exclusion;
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
