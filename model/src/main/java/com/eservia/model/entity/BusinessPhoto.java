package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

public class BusinessPhoto implements Validator {

    @SerializedName("id")
    private Integer id;
    @SerializedName("object_type")
    private String objectType;
    @SerializedName("object_id")
    private Integer objectId;
    @SerializedName("path")
    private String path;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    @Override
    public boolean isItemValid() {
        return id != null
                && path != null
                && objectId != null
                && objectType != null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
