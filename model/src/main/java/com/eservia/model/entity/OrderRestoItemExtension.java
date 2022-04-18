package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

@Entity
public class OrderRestoItemExtension {

    @Id
    private long dbId;

    @Index
    private long businessId;

    @SerializedName("extensionId")
    @Expose
    private Long extensionId;

    @SerializedName("optionId")
    @Expose
    private Long optionId;

    @SerializedName("amount")
    @Expose
    private Long amount;

    public long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public Long getExtensionId() {
        return extensionId;
    }

    public void setExtensionId(Long extensionId) {
        this.extensionId = extensionId;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderRestoItemExtension that = (OrderRestoItemExtension) o;

        if (dbId != that.dbId) return false;
        if (businessId != that.businessId) return false;
        if (!Objects.equals(extensionId, that.extensionId))
            return false;
        if (!Objects.equals(optionId, that.optionId))
            return false;
        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        int result = (int) (dbId ^ (dbId >>> 32));
        result = 31 * result + (int) (businessId ^ (businessId >>> 32));
        result = 31 * result + (extensionId != null ? extensionId.hashCode() : 0);
        result = 31 * result + (optionId != null ? optionId.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrderRestoItemExtension{" +
                "dbId=" + dbId +
                ", businessId=" + businessId +
                ", extensionId=" + extensionId +
                ", optionId=" + optionId +
                ", amount=" + amount +
                '}';
    }
}
