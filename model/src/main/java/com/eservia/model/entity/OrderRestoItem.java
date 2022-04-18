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
public class OrderRestoItem {

    @Id
    private long dbId;

    @Index
    private long businessId;

    private ToOne<OrderResto> orderResto;

    private Double priceByPortion;

    private ToMany<OrderRestoItemExtension> dbExtensions;

    @SerializedName("nomenclatureId")
    @Expose
    private Long nomenclatureId;

    @SerializedName("amount")
    @Expose
    private Long amount;

    @SerializedName("size")
    @Expose
    private Double size;

    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("initializationId")
    @Expose
    private String initializationId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("dimension")
    @Expose
    private String dimension;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("orderTime")
    @Expose
    private String orderTime;

    public ToOne<OrderResto> getOrderResto() {
        return orderResto;
    }

    public void setOrderResto(ToOne<OrderResto> orderResto) {
        this.orderResto = orderResto;
    }

    public Double getPriceByPortion() {
        return priceByPortion;
    }

    public void setPriceByPortion(Double priceByPortion) {
        this.priceByPortion = priceByPortion;
    }

    public long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    public long getDbId() {
        return dbId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public Long getNomenclatureId() {
        return nomenclatureId;
    }

    public void setNomenclatureId(Long nomenclatureId) {
        this.nomenclatureId = nomenclatureId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getInitializationId() {
        return initializationId;
    }

    public void setInitializationId(String initializationId) {
        this.initializationId = initializationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ToMany<OrderRestoItemExtension> getDbExtensions() {
        return dbExtensions;
    }

    public void setDbExtensions(ToMany<OrderRestoItemExtension> dbExtensions) {
        this.dbExtensions = dbExtensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderRestoItem that = (OrderRestoItem) o;

        if (dbId != that.dbId) return false;
        if (businessId != that.businessId) return false;
        if (!Objects.equals(orderResto, that.orderResto))
            return false;
        if (!Objects.equals(priceByPortion, that.priceByPortion))
            return false;
        if (!Objects.equals(dbExtensions, that.dbExtensions))
            return false;
        if (!Objects.equals(nomenclatureId, that.nomenclatureId))
            return false;
        if (!Objects.equals(amount, that.amount)) return false;
        if (!Objects.equals(size, that.size)) return false;
        if (!Objects.equals(price, that.price)) return false;
        if (!Objects.equals(initializationId, that.initializationId))
            return false;
        if (!Objects.equals(description, that.description))
            return false;
        if (!Objects.equals(dimension, that.dimension))
            return false;
        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(orderTime, that.orderTime);
    }

    @Override
    public int hashCode() {
        int result = (int) (dbId ^ (dbId >>> 32));
        result = 31 * result + (int) (businessId ^ (businessId >>> 32));
        result = 31 * result + (orderResto != null ? orderResto.hashCode() : 0);
        result = 31 * result + (priceByPortion != null ? priceByPortion.hashCode() : 0);
        result = 31 * result + (dbExtensions != null ? dbExtensions.hashCode() : 0);
        result = 31 * result + (nomenclatureId != null ? nomenclatureId.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (initializationId != null ? initializationId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dimension != null ? dimension.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (orderTime != null ? orderTime.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrderRestoItem{" +
                "dbId=" + dbId +
                ", businessId=" + businessId +
                ", orderResto=" + orderResto +
                ", priceByPortion=" + priceByPortion +
                ", dbExtensions=" + dbExtensions +
                ", nomenclatureId=" + nomenclatureId +
                ", amount=" + amount +
                ", size=" + size +
                ", price=" + price +
                ", initializationId='" + initializationId + '\'' +
                ", description='" + description + '\'' +
                ", dimension='" + dimension + '\'' +
                ", name='" + name + '\'' +
                ", orderTime='" + orderTime + '\'' +
                '}';
    }
}
