package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

@Entity
public class OrderRestoSizePrice {

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

    @SerializedName("presentationName")
    @Expose
    private String presentationName;

    @SerializedName("sizeTypeId")
    @Expose
    private Long sizeTypeId;

    @SerializedName("size")
    @Expose
    private Double size;

    @SerializedName("price")
    @Expose
    private Double price;

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

    public String getPresentationName() {
        return presentationName;
    }

    public void setPresentationName(String presentationName) {
        this.presentationName = presentationName;
    }

    public Long getSizeTypeId() {
        return sizeTypeId;
    }

    public void setSizeTypeId(Long sizeTypeId) {
        this.sizeTypeId = sizeTypeId;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderRestoSizePrice sizePrice = (OrderRestoSizePrice) o;

        if (dbId != sizePrice.dbId) return false;
        if (businessId != sizePrice.businessId) return false;
        if (addressId != sizePrice.addressId) return false;
        if (!Objects.equals(id, sizePrice.id)) return false;
        if (!Objects.equals(nomenclatureId, sizePrice.nomenclatureId))
            return false;
        if (!Objects.equals(presentationName, sizePrice.presentationName))
            return false;
        if (!Objects.equals(sizeTypeId, sizePrice.sizeTypeId))
            return false;
        if (!Objects.equals(size, sizePrice.size)) return false;
        return Objects.equals(price, sizePrice.price);
    }

    @Override
    public int hashCode() {
        int result = (int) (dbId ^ (dbId >>> 32));
        result = 31 * result + (int) (businessId ^ (businessId >>> 32));
        result = 31 * result + (int) (addressId ^ (addressId >>> 32));
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (nomenclatureId != null ? nomenclatureId.hashCode() : 0);
        result = 31 * result + (presentationName != null ? presentationName.hashCode() : 0);
        result = 31 * result + (sizeTypeId != null ? sizeTypeId.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrderRestoSizePrice{" +
                "dbId=" + dbId +
                ", businessId=" + businessId +
                ", addressId=" + addressId +
                ", id=" + id +
                ", nomenclatureId=" + nomenclatureId +
                ", presentationName='" + presentationName + '\'' +
                ", sizeTypeId=" + sizeTypeId +
                ", size=" + size +
                ", price=" + price +
                '}';
    }
}
