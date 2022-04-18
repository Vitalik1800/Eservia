package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.relation.ToMany;

@Entity
public class OrderResto {

    @Id
    private long dbId;

    @Index
    private long businessId;

    @Backlink(to = "orderResto")
    @SerializedName("orderItems")
    @Expose
    private ToMany<OrderRestoItem> orderItems;

    @SerializedName("waiterId")
    @Expose
    private String waiterId;

    @SerializedName("tableId")
    @Expose
    private Long tableId;

    @SerializedName("addressId")
    @Expose
    private Long addressId;

    @SerializedName("departmentId")
    @Expose
    private Long departmentId;

    @SerializedName("menuVersion")
    @Expose
    private Long menuVersion;

    @SerializedName("initializationId")
    @Expose
    private String initializationId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("orderTypeId")
    @Expose
    private Long orderTypeId;

    @SerializedName("totalPrice")
    @Expose
    private Long totalPrice;

    @SerializedName("toBePreparedAtTime")
    @Expose
    private String toBePreparedAtTime;

    @SerializedName("clientName")
    @Expose
    private String clientName;

    @SerializedName("clientPhoneNumber")
    @Expose
    private String clientPhoneNumber;

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

    public String getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(String waiterId) {
        this.waiterId = waiterId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
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

    public Long getMenuVersion() {
        return menuVersion;
    }

    public void setMenuVersion(Long menuVersion) {
        this.menuVersion = menuVersion;
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

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getToBePreparedAtTime() {
        return toBePreparedAtTime;
    }

    public void setToBePreparedAtTime(String toBePreparedAtTime) {
        this.toBePreparedAtTime = toBePreparedAtTime;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public ToMany<OrderRestoItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ToMany<OrderRestoItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderResto that = (OrderResto) o;

        if (dbId != that.dbId) return false;
        if (businessId != that.businessId) return false;
        if (!Objects.equals(orderItems, that.orderItems))
            return false;
        if (!Objects.equals(waiterId, that.waiterId))
            return false;
        if (!Objects.equals(tableId, that.tableId)) return false;
        if (!Objects.equals(addressId, that.addressId))
            return false;
        if (!Objects.equals(departmentId, that.departmentId))
            return false;
        if (!Objects.equals(menuVersion, that.menuVersion))
            return false;
        if (!Objects.equals(initializationId, that.initializationId))
            return false;
        if (!Objects.equals(description, that.description))
            return false;
        if (!Objects.equals(orderTypeId, that.orderTypeId))
            return false;
        if (!Objects.equals(totalPrice, that.totalPrice))
            return false;
        if (!Objects.equals(toBePreparedAtTime, that.toBePreparedAtTime))
            return false;
        if (!Objects.equals(clientName, that.clientName))
            return false;
        return Objects.equals(clientPhoneNumber, that.clientPhoneNumber);
    }

    @Override
    public int hashCode() {
        int result = (int) (dbId ^ (dbId >>> 32));
        result = 31 * result + (int) (businessId ^ (businessId >>> 32));
        result = 31 * result + (orderItems != null ? orderItems.hashCode() : 0);
        result = 31 * result + (waiterId != null ? waiterId.hashCode() : 0);
        result = 31 * result + (tableId != null ? tableId.hashCode() : 0);
        result = 31 * result + (addressId != null ? addressId.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (menuVersion != null ? menuVersion.hashCode() : 0);
        result = 31 * result + (initializationId != null ? initializationId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (orderTypeId != null ? orderTypeId.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (toBePreparedAtTime != null ? toBePreparedAtTime.hashCode() : 0);
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        result = 31 * result + (clientPhoneNumber != null ? clientPhoneNumber.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrderResto{" +
                "dbId=" + dbId +
                ", businessId=" + businessId +
                ", orderItems=" + orderItems +
                ", waiterId='" + waiterId + '\'' +
                ", tableId=" + tableId +
                ", addressId=" + addressId +
                ", departmentId=" + departmentId +
                ", menuVersion=" + menuVersion +
                ", initializationId='" + initializationId + '\'' +
                ", description='" + description + '\'' +
                ", orderTypeId=" + orderTypeId +
                ", totalPrice=" + totalPrice +
                ", toBePreparedAtTime='" + toBePreparedAtTime + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientPhoneNumber='" + clientPhoneNumber + '\'' +
                '}';
    }
}
