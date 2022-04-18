package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

@Entity
public class OrderRestoCategory {

    public static final String ROOT_NAME = "root";

    @Id
    private long dbId;

    @Index
    private long businessId;

    @Index
    private long addressId;

    @Index
    @SerializedName("id")
    @Expose
    private Long id;

    @Index
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("iconPath")
    @Expose
    private String iconPath;

    @Index
    @SerializedName("parentId")
    @Expose
    private Long parentId;

    @Index
    @SerializedName("categoryTypeId")
    @Expose
    private Long categoryTypeId;

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(Long categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderRestoCategory category = (OrderRestoCategory) o;

        if (dbId != category.dbId) return false;
        if (businessId != category.businessId) return false;
        if (addressId != category.addressId) return false;
        if (!Objects.equals(id, category.id)) return false;
        if (!Objects.equals(name, category.name)) return false;
        if (!Objects.equals(iconPath, category.iconPath))
            return false;
        if (!Objects.equals(parentId, category.parentId))
            return false;
        return Objects.equals(categoryTypeId, category.categoryTypeId);
    }

    @Override
    public int hashCode() {
        int result = (int) (dbId ^ (dbId >>> 32));
        result = 31 * result + (int) (businessId ^ (businessId >>> 32));
        result = 31 * result + (int) (addressId ^ (addressId >>> 32));
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (iconPath != null ? iconPath.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (categoryTypeId != null ? categoryTypeId.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrderRestoCategory{" +
                "dbId=" + dbId +
                ", businessId=" + businessId +
                ", addressId=" + addressId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", iconPath='" + iconPath + '\'' +
                ", parentId=" + parentId +
                ", categoryTypeId=" + categoryTypeId +
                '}';
    }
}
