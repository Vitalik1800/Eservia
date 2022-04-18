package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestoOrderResponseData {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("addressId")
    @Expose
    private Long addressId;

    @SerializedName("departmentId")
    @Expose
    private Long departmentId;

    @SerializedName("tableId")
    @Expose
    private Long tableId;

    @SerializedName("menuVersion")
    @Expose
    private Long menuVersion;

    @SerializedName("totalPrice")
    @Expose
    private Double totalPrice;

    @SerializedName("totalDiscountPrice")
    @Expose
    private Double totalDiscountPrice;

    @SerializedName("orderStatusId")
    @Expose
    private String orderStatusId;

    @SerializedName("orderTypeId")
    @Expose
    private String orderTypeId;

    @SerializedName("orderTime")
    @Expose
    private String orderTime;

    @SerializedName("checkOutTime")
    @Expose
    private String checkOutTime;

    @SerializedName("closeTime")
    @Expose
    private String closeTime;

    @SerializedName("toBePreparedAtTime")
    @Expose
    private String toBePreparedAtTime;

    @SerializedName("customerId")
    @Expose
    private String customerId;

    @SerializedName("waiterId")
    @Expose
    private String waiterId;

    @SerializedName("cashierId")
    @Expose
    private String cashierId;

    @SerializedName("orderReference")
    @Expose
    private String orderReference;

    @SerializedName("initializationId")
    @Expose
    private String initializationId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("businessId")
    @Expose
    private Long businessId;

    @SerializedName("guestOrders")
    @Expose
    private List<RestoGuestOrder> guestOrders = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getMenuVersion() {
        return menuVersion;
    }

    public void setMenuVersion(Long menuVersion) {
        this.menuVersion = menuVersion;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    public void setTotalDiscountPrice(Double totalDiscountPrice) {
        this.totalDiscountPrice = totalDiscountPrice;
    }

    public String getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(String orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(String orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getToBePreparedAtTime() {
        return toBePreparedAtTime;
    }

    public void setToBePreparedAtTime(String toBePreparedAtTime) {
        this.toBePreparedAtTime = toBePreparedAtTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(String waiterId) {
        this.waiterId = waiterId;
    }

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public List<RestoGuestOrder> getGuestOrders() {
        return guestOrders;
    }

    public void setGuestOrders(List<RestoGuestOrder> guestOrders) {
        this.guestOrders = guestOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestoOrderResponseData that = (RestoOrderResponseData) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(addressId, that.addressId))
            return false;
        if (!Objects.equals(departmentId, that.departmentId))
            return false;
        if (!Objects.equals(tableId, that.tableId)) return false;
        if (!Objects.equals(menuVersion, that.menuVersion))
            return false;
        if (!Objects.equals(totalPrice, that.totalPrice))
            return false;
        if (!Objects.equals(totalDiscountPrice, that.totalDiscountPrice))
            return false;
        if (!Objects.equals(orderStatusId, that.orderStatusId))
            return false;
        if (!Objects.equals(orderTypeId, that.orderTypeId))
            return false;
        if (!Objects.equals(orderTime, that.orderTime))
            return false;
        if (!Objects.equals(checkOutTime, that.checkOutTime))
            return false;
        if (!Objects.equals(closeTime, that.closeTime))
            return false;
        if (!Objects.equals(toBePreparedAtTime, that.toBePreparedAtTime))
            return false;
        if (!Objects.equals(customerId, that.customerId))
            return false;
        if (!Objects.equals(waiterId, that.waiterId))
            return false;
        if (!Objects.equals(cashierId, that.cashierId))
            return false;
        if (!Objects.equals(orderReference, that.orderReference))
            return false;
        if (!Objects.equals(initializationId, that.initializationId))
            return false;
        if (!Objects.equals(description, that.description))
            return false;
        if (!Objects.equals(currency, that.currency))
            return false;
        if (!Objects.equals(businessId, that.businessId))
            return false;
        return Objects.equals(guestOrders, that.guestOrders);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (addressId != null ? addressId.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (tableId != null ? tableId.hashCode() : 0);
        result = 31 * result + (menuVersion != null ? menuVersion.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (totalDiscountPrice != null ? totalDiscountPrice.hashCode() : 0);
        result = 31 * result + (orderStatusId != null ? orderStatusId.hashCode() : 0);
        result = 31 * result + (orderTypeId != null ? orderTypeId.hashCode() : 0);
        result = 31 * result + (orderTime != null ? orderTime.hashCode() : 0);
        result = 31 * result + (checkOutTime != null ? checkOutTime.hashCode() : 0);
        result = 31 * result + (closeTime != null ? closeTime.hashCode() : 0);
        result = 31 * result + (toBePreparedAtTime != null ? toBePreparedAtTime.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (waiterId != null ? waiterId.hashCode() : 0);
        result = 31 * result + (cashierId != null ? cashierId.hashCode() : 0);
        result = 31 * result + (orderReference != null ? orderReference.hashCode() : 0);
        result = 31 * result + (initializationId != null ? initializationId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (businessId != null ? businessId.hashCode() : 0);
        result = 31 * result + (guestOrders != null ? guestOrders.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "RestoOrderResponseData{" +
                "id=" + id +
                ", addressId=" + addressId +
                ", departmentId=" + departmentId +
                ", tableId=" + tableId +
                ", menuVersion=" + menuVersion +
                ", totalPrice=" + totalPrice +
                ", totalDiscountPrice=" + totalDiscountPrice +
                ", orderStatusId='" + orderStatusId + '\'' +
                ", orderTypeId='" + orderTypeId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", checkOutTime='" + checkOutTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", toBePreparedAtTime='" + toBePreparedAtTime + '\'' +
                ", customerId='" + customerId + '\'' +
                ", waiterId='" + waiterId + '\'' +
                ", cashierId='" + cashierId + '\'' +
                ", orderReference='" + orderReference + '\'' +
                ", initializationId='" + initializationId + '\'' +
                ", description='" + description + '\'' +
                ", currency='" + currency + '\'' +
                ", businessId=" + businessId +
                ", guestOrders=" + guestOrders +
                '}';
    }
}
