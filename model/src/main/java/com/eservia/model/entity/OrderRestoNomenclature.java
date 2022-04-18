package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class OrderRestoNomenclature {

    public static final long TYPE_FOOD = 0;
    public static final long TYPE_DRINK = 1;

    @Id
    private long dbId;

    @Index
    private long businessId;

    @Index
    private long addressId;

    private ToOne<OrderRestoCategory> category;

    private ToOne<OrderRestoDimension> dimension;

    private ToOne<OrderRestoPortionGradation> portionGradation;

    private ToMany<OrderRestoSizePrice> sizePrices;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("photoPath")
    @Expose
    private String photoPath;

    @SerializedName("description")
    @Expose
    private String description;

    @Index
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("maxExtensionsQuantity")
    @Expose
    private Long maxExtensionsQuantity;

    @SerializedName("cookingTime")
    @Expose
    private Long cookingTime;

    @SerializedName("cookingPriorityId")
    @Expose
    private Long cookingPriorityId;

    @SerializedName("supportedOrderTypeId")
    @Expose
    private Long supportedOrderTypeId;

    @SerializedName("dishTypeId")
    @Expose
    private Long dishTypeId;

    @SerializedName("saleMethodId")
    @Expose
    private Long saleMethodId;

    @SerializedName("tasteGroupId")
    @Expose
    private Long tasteGroupId;

    @SerializedName("specialGroupId")
    @Expose
    private Long specialGroupId;

    @SerializedName("portionGradationId")
    @Expose
    private Long portionGradationId;

    @Index
    @SerializedName("parentId")
    @Expose
    private Long parentId;

    @SerializedName("dimensionId")
    @Expose
    private Long aDimensionId;

    public Long getDishTypeId() {
        return dishTypeId;
    }

    public void setDishTypeId(Long dishTypeId) {
        this.dishTypeId = dishTypeId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public ToMany<OrderRestoSizePrice> getSizePrices() {
        return sizePrices;
    }

    public void setSizePrices(ToMany<OrderRestoSizePrice> sizePrices) {
        this.sizePrices = sizePrices;
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

    public ToOne<OrderRestoCategory> getCategory() {
        return category;
    }

    public void setCategory(ToOne<OrderRestoCategory> category) {
        this.category = category;
    }

    public ToOne<OrderRestoDimension> getDimension() {
        return dimension;
    }

    public void setDimension(ToOne<OrderRestoDimension> dimension) {
        this.dimension = dimension;
    }

    public ToOne<OrderRestoPortionGradation> getPortionGradation() {
        return portionGradation;
    }

    public void setPortionGradation(ToOne<OrderRestoPortionGradation> portionGradation) {
        this.portionGradation = portionGradation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaxExtensionsQuantity() {
        return maxExtensionsQuantity;
    }

    public void setMaxExtensionsQuantity(Long maxExtensionsQuantity) {
        this.maxExtensionsQuantity = maxExtensionsQuantity;
    }

    public Long getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Long cookingTime) {
        this.cookingTime = cookingTime;
    }

    public Long getCookingPriorityId() {
        return cookingPriorityId;
    }

    public void setCookingPriorityId(Long cookingPriorityId) {
        this.cookingPriorityId = cookingPriorityId;
    }

    public Long getSupportedOrderTypeId() {
        return supportedOrderTypeId;
    }

    public void setSupportedOrderTypeId(Long supportedOrderTypeId) {
        this.supportedOrderTypeId = supportedOrderTypeId;
    }

    public Long getSaleMethodId() {
        return saleMethodId;
    }

    public void setSaleMethodId(Long saleMethodId) {
        this.saleMethodId = saleMethodId;
    }

    public Long getTasteGroupId() {
        return tasteGroupId;
    }

    public void setTasteGroupId(Long tasteGroupId) {
        this.tasteGroupId = tasteGroupId;
    }

    public Long getSpecialGroupId() {
        return specialGroupId;
    }

    public void setSpecialGroupId(Long specialGroupId) {
        this.specialGroupId = specialGroupId;
    }

    public Long getPortionGradationId() {
        return portionGradationId;
    }

    public void setPortionGradationId(Long portionGradationId) {
        this.portionGradationId = portionGradationId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }


    public Long getADimensionId() {
        return aDimensionId;
    }

    public void setADimensionId(Long aDimensionId) {
        this.aDimensionId = aDimensionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderRestoNomenclature that = (OrderRestoNomenclature) o;

        if (dbId != that.dbId) return false;
        if (businessId != that.businessId) return false;
        if (addressId != that.addressId) return false;
        if (!Objects.equals(category, that.category))
            return false;
        if (!Objects.equals(dimension, that.dimension))
            return false;
        if (!Objects.equals(portionGradation, that.portionGradation))
            return false;
        if (!Objects.equals(sizePrices, that.sizePrices))
            return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(photoPath, that.photoPath))
            return false;
        if (!Objects.equals(description, that.description))
            return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(maxExtensionsQuantity, that.maxExtensionsQuantity))
            return false;
        if (!Objects.equals(cookingTime, that.cookingTime))
            return false;
        if (!Objects.equals(cookingPriorityId, that.cookingPriorityId))
            return false;
        if (!Objects.equals(supportedOrderTypeId, that.supportedOrderTypeId))
            return false;
        if (!Objects.equals(dishTypeId, that.dishTypeId))
            return false;
        if (!Objects.equals(saleMethodId, that.saleMethodId))
            return false;
        if (!Objects.equals(tasteGroupId, that.tasteGroupId))
            return false;
        if (!Objects.equals(specialGroupId, that.specialGroupId))
            return false;
        if (!Objects.equals(portionGradationId, that.portionGradationId))
            return false;
        if (!Objects.equals(parentId, that.parentId))
            return false;
        return Objects.equals(aDimensionId, that.aDimensionId);
    }

    @Override
    public int hashCode() {
        int result = (int) (dbId ^ (dbId >>> 32));
        result = 31 * result + (int) (businessId ^ (businessId >>> 32));
        result = 31 * result + (int) (addressId ^ (addressId >>> 32));
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (dimension != null ? dimension.hashCode() : 0);
        result = 31 * result + (portionGradation != null ? portionGradation.hashCode() : 0);
        result = 31 * result + (sizePrices != null ? sizePrices.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (photoPath != null ? photoPath.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (maxExtensionsQuantity != null ? maxExtensionsQuantity.hashCode() : 0);
        result = 31 * result + (cookingTime != null ? cookingTime.hashCode() : 0);
        result = 31 * result + (cookingPriorityId != null ? cookingPriorityId.hashCode() : 0);
        result = 31 * result + (supportedOrderTypeId != null ? supportedOrderTypeId.hashCode() : 0);
        result = 31 * result + (dishTypeId != null ? dishTypeId.hashCode() : 0);
        result = 31 * result + (saleMethodId != null ? saleMethodId.hashCode() : 0);
        result = 31 * result + (tasteGroupId != null ? tasteGroupId.hashCode() : 0);
        result = 31 * result + (specialGroupId != null ? specialGroupId.hashCode() : 0);
        result = 31 * result + (portionGradationId != null ? portionGradationId.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (aDimensionId != null ? aDimensionId.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrderRestoNomenclature{" +
                "dbId=" + dbId +
                ", businessId=" + businessId +
                ", addressId=" + addressId +
                ", category=" + category +
                ", dimension=" + dimension +
                ", portionGradation=" + portionGradation +
                ", sizePrices=" + sizePrices +
                ", name='" + name + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", maxExtensionsQuantity=" + maxExtensionsQuantity +
                ", cookingTime=" + cookingTime +
                ", cookingPriorityId=" + cookingPriorityId +
                ", supportedOrderTypeId=" + supportedOrderTypeId +
                ", dishTypeId=" + dishTypeId +
                ", saleMethodId=" + saleMethodId +
                ", tasteGroupId=" + tasteGroupId +
                ", specialGroupId=" + specialGroupId +
                ", portionGradationId=" + portionGradationId +
                ", parentId=" + parentId +
                ", aDimensionId=" + aDimensionId +
                '}';
    }
}
