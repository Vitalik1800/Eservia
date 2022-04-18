package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

@Entity
public class OrderRestoPortionGradation {

    @Id
    private long dbId;

    @Index
    private long businessId;

    @Index
    private long addressId;

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("nomenclatureId")
    @Expose
    private Long nomenclatureId;

    @SerializedName("minimum")
    @Expose
    private Double minimum;

    @SerializedName("maximum")
    @Expose
    private Double maximum;

    @SerializedName("minimumPrice")
    @Expose
    private Double minimumPrice;

    @SerializedName("step")
    @Expose
    private Double step;

    @SerializedName("stepPrice")
    @Expose
    private Double stepPrice;

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

    public Long getNomenclatureId() {
        return nomenclatureId;
    }

    public void setNomenclatureId(Long nomenclatureId) {
        this.nomenclatureId = nomenclatureId;
    }

    public Double getMinimum() {
        return minimum;
    }

    public void setMinimum(Double minimum) {
        this.minimum = minimum;
    }

    public Double getMaximum() {
        return maximum;
    }

    public void setMaximum(Double maximum) {
        this.maximum = maximum;
    }

    public Double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(Double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public Double getStepPrice() {
        return stepPrice;
    }

    public void setStepPrice(Double stepPrice) {
        this.stepPrice = stepPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderRestoPortionGradation gradation = (OrderRestoPortionGradation) o;

        if (dbId != gradation.dbId) return false;
        if (businessId != gradation.businessId) return false;
        if (addressId != gradation.addressId) return false;
        if (!Objects.equals(id, gradation.id)) return false;
        if (!Objects.equals(nomenclatureId, gradation.nomenclatureId))
            return false;
        if (!Objects.equals(minimum, gradation.minimum))
            return false;
        if (!Objects.equals(maximum, gradation.maximum))
            return false;
        if (!Objects.equals(minimumPrice, gradation.minimumPrice))
            return false;
        if (!Objects.equals(step, gradation.step)) return false;
        return Objects.equals(stepPrice, gradation.stepPrice);
    }

    @Override
    public int hashCode() {
        int result = (int) (dbId ^ (dbId >>> 32));
        result = 31 * result + (int) (businessId ^ (businessId >>> 32));
        result = 31 * result + (int) (addressId ^ (addressId >>> 32));
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (nomenclatureId != null ? nomenclatureId.hashCode() : 0);
        result = 31 * result + (minimum != null ? minimum.hashCode() : 0);
        result = 31 * result + (maximum != null ? maximum.hashCode() : 0);
        result = 31 * result + (minimumPrice != null ? minimumPrice.hashCode() : 0);
        result = 31 * result + (step != null ? step.hashCode() : 0);
        result = 31 * result + (stepPrice != null ? stepPrice.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrderRestoPortionGradation{" +
                "dbId=" + dbId +
                ", businessId=" + businessId +
                ", addressId=" + addressId +
                ", id=" + id +
                ", nomenclatureId=" + nomenclatureId +
                ", minimum=" + minimum +
                ", maximum=" + maximum +
                ", minimumPrice=" + minimumPrice +
                ", step=" + step +
                ", stepPrice=" + stepPrice +
                '}';
    }
}
