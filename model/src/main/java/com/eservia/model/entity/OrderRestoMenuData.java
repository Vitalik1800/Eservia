package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

@Entity
public class OrderRestoMenuData implements Validator {

    @Id
    private long dbId;

    @Index
    private long businessId;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("version")
    @Expose
    private Long version;

    @SerializedName("addressId")
    @Expose
    private Long addressId;

    @SerializedName("promoterId")
    @Expose
    private Long promoterId;

    @SerializedName("departmentId")
    @Expose
    private Long departmentId;

    @SerializedName("categories")
    @Expose
    private List<OrderRestoCategory> categories;

    @SerializedName("dimensions")
    @Expose
    private List<OrderRestoDimension> dimensions;

    @SerializedName("options")
    @Expose
    private List<OrderRestoOption> options;

    @SerializedName("nomenclatureOptions")
    @Expose
    private List<OrderRestoNomenclatureOption> nomenclatureOptions;

    @SerializedName("nomenclatures")
    @Expose
    private List<OrderRestoNomenclature> nomenclatures;

    @SerializedName("sizePrices")
    @Expose
    private List<OrderRestoSizePrice> sizePrices;

    @SerializedName("portionGradations")
    @Expose
    private List<OrderRestoPortionGradation> portionGradations;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(Long promoterId) {
        this.promoterId = promoterId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public List<OrderRestoCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<OrderRestoCategory> categories) {
        this.categories = categories;
    }

    public List<OrderRestoDimension> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<OrderRestoDimension> dimensions) {
        this.dimensions = dimensions;
    }

    public List<OrderRestoOption> getOptions() {
        return options;
    }

    public void setOptions(List<OrderRestoOption> options) {
        this.options = options;
    }

    public List<OrderRestoNomenclatureOption> getNomenclatureOptions() {
        return nomenclatureOptions;
    }

    public void setNomenclatureOptions(List<OrderRestoNomenclatureOption> nomenclatureOptions) {
        this.nomenclatureOptions = nomenclatureOptions;
    }

    public List<OrderRestoNomenclature> getNomenclatures() {
        return nomenclatures;
    }

    public void setNomenclatures(List<OrderRestoNomenclature> nomenclatures) {
        this.nomenclatures = nomenclatures;
    }

    public List<OrderRestoSizePrice> getSizePrices() {
        return sizePrices;
    }

    public void setSizePrices(List<OrderRestoSizePrice> sizePrices) {
        this.sizePrices = sizePrices;
    }

    public List<OrderRestoPortionGradation> getPortionGradations() {
        return portionGradations;
    }

    public void setPortionGradations(List<OrderRestoPortionGradation> portionGradations) {
        this.portionGradations = portionGradations;
    }

    @Override
    public boolean isItemValid() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderRestoMenuData that = (OrderRestoMenuData) o;

        if (dbId != that.dbId) return false;
        if (businessId != that.businessId) return false;
        if (!Objects.equals(currency, that.currency))
            return false;
        if (!Objects.equals(version, that.version)) return false;
        if (!Objects.equals(addressId, that.addressId))
            return false;
        if (!Objects.equals(promoterId, that.promoterId))
            return false;
        if (!Objects.equals(departmentId, that.departmentId))
            return false;
        if (!Objects.equals(categories, that.categories))
            return false;
        if (!Objects.equals(dimensions, that.dimensions))
            return false;
        if (!Objects.equals(options, that.options)) return false;
        if (!Objects.equals(nomenclatureOptions, that.nomenclatureOptions))
            return false;
        if (!Objects.equals(nomenclatures, that.nomenclatures))
            return false;
        if (!Objects.equals(sizePrices, that.sizePrices))
            return false;
        return Objects.equals(portionGradations, that.portionGradations);
    }

    @Override
    public int hashCode() {
        int result = (int) (dbId ^ (dbId >>> 32));
        result = 31 * result + (int) (businessId ^ (businessId >>> 32));
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (addressId != null ? addressId.hashCode() : 0);
        result = 31 * result + (promoterId != null ? promoterId.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        result = 31 * result + (dimensions != null ? dimensions.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        result = 31 * result + (nomenclatureOptions != null ? nomenclatureOptions.hashCode() : 0);
        result = 31 * result + (nomenclatures != null ? nomenclatures.hashCode() : 0);
        result = 31 * result + (sizePrices != null ? sizePrices.hashCode() : 0);
        result = 31 * result + (portionGradations != null ? portionGradations.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrderRestoMenuData{" +
                "dbId=" + dbId +
                ", businessId=" + businessId +
                ", currency='" + currency + '\'' +
                ", version=" + version +
                ", addressId=" + addressId +
                ", promoterId=" + promoterId +
                ", departmentId=" + departmentId +
                ", categories=" + categories +
                ", dimensions=" + dimensions +
                ", options=" + options +
                ", nomenclatureOptions=" + nomenclatureOptions +
                ", nomenclatures=" + nomenclatures +
                ", sizePrices=" + sizePrices +
                ", portionGradations=" + portionGradations +
                '}';
    }
}
