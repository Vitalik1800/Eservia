package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

@Entity
public class OrderRestoOption {

    @Id
    private long dbId;

    @Index
    private long businessId;

    @Index
    private long addressId;

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("isFree")
    @Expose
    private Boolean isFree;

    @SerializedName("minQuantity")
    @Expose
    private Long minQuantity;

    @SerializedName("maxQuantity")
    @Expose
    private Long maxQuantity;

    @SerializedName("name")
    @Expose
    private String name;

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

    public Boolean isFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public Long getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Long minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Long getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderRestoOption that = (OrderRestoOption) o;

        if (dbId != that.dbId) return false;
        if (businessId != that.businessId) return false;
        if (addressId != that.addressId) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(isFree, that.isFree)) return false;
        if (!Objects.equals(minQuantity, that.minQuantity))
            return false;
        if (!Objects.equals(maxQuantity, that.maxQuantity))
            return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (dbId ^ (dbId >>> 32));
        result = 31 * result + (int) (businessId ^ (businessId >>> 32));
        result = 31 * result + (int) (addressId ^ (addressId >>> 32));
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (isFree != null ? isFree.hashCode() : 0);
        result = 31 * result + (minQuantity != null ? minQuantity.hashCode() : 0);
        result = 31 * result + (maxQuantity != null ? maxQuantity.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrderRestoOption{" +
                "dbId=" + dbId +
                ", businessId=" + businessId +
                ", addressId=" + addressId +
                ", id=" + id +
                ", isFree=" + isFree +
                ", minQuantity=" + minQuantity +
                ", maxQuantity=" + maxQuantity +
                ", name='" + name + '\'' +
                '}';
    }
}
