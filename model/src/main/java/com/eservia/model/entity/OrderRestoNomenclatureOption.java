package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

@Entity
public class OrderRestoNomenclatureOption {

    @Id
    private long dbId;

    @Index
    private long businessId;

    @Index
    private long addressId;

    @Index
    @SerializedName("nomenclatureId")
    @Expose
    private Long nomenclatureId;

    @Index
    @SerializedName("optionId")
    @Expose
    private Long optionId;

    @SerializedName("minQuantity")
    @Expose
    private Long minQuantity;

    @SerializedName("maxQuantity")
    @Expose
    private Long maxQuantity;

    @SerializedName("defaultQuantity")
    @Expose
    private Long defaultQuantity;

    @SerializedName("belongsToOption")
    @Expose
    private Boolean belongsToOption;

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

    public Long getNomenclatureId() {
        return nomenclatureId;
    }

    public void setNomenclatureId(Long nomenclatureId) {
        this.nomenclatureId = nomenclatureId;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
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

    public Long getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(Long defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public Boolean getBelongsToOption() {
        return belongsToOption;
    }

    public void setBelongsToOption(Boolean belongsToOption) {
        this.belongsToOption = belongsToOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderRestoNomenclatureOption that = (OrderRestoNomenclatureOption) o;

        if (dbId != that.dbId) return false;
        if (businessId != that.businessId) return false;
        if (addressId != that.addressId) return false;
        if (!Objects.equals(nomenclatureId, that.nomenclatureId))
            return false;
        if (!Objects.equals(optionId, that.optionId))
            return false;
        if (!Objects.equals(minQuantity, that.minQuantity))
            return false;
        if (!Objects.equals(maxQuantity, that.maxQuantity))
            return false;
        if (!Objects.equals(defaultQuantity, that.defaultQuantity))
            return false;
        return Objects.equals(belongsToOption, that.belongsToOption);
    }

    @Override
    public int hashCode() {
        int result = (int) (dbId ^ (dbId >>> 32));
        result = 31 * result + (int) (businessId ^ (businessId >>> 32));
        result = 31 * result + (int) (addressId ^ (addressId >>> 32));
        result = 31 * result + (nomenclatureId != null ? nomenclatureId.hashCode() : 0);
        result = 31 * result + (optionId != null ? optionId.hashCode() : 0);
        result = 31 * result + (minQuantity != null ? minQuantity.hashCode() : 0);
        result = 31 * result + (maxQuantity != null ? maxQuantity.hashCode() : 0);
        result = 31 * result + (defaultQuantity != null ? defaultQuantity.hashCode() : 0);
        result = 31 * result + (belongsToOption != null ? belongsToOption.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrderRestoNomenclatureOption{" +
                "dbId=" + dbId +
                ", businessId=" + businessId +
                ", addressId=" + addressId +
                ", nomenclatureId=" + nomenclatureId +
                ", optionId=" + optionId +
                ", minQuantity=" + minQuantity +
                ", maxQuantity=" + maxQuantity +
                ", defaultQuantity=" + defaultQuantity +
                ", belongsToOption=" + belongsToOption +
                '}';
    }
}
