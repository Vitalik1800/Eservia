package com.eservia.model.remote.rest.order_resto.services.orders;

import androidx.annotation.NonNull;

import com.eservia.model.entity.RestoGuestOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostRestoOrderRequest {

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

    @SerializedName("toBePreparedAtTime")
    @Expose
    private String toBePreparedAtTime;

    @SerializedName("guestOrders")
    @Expose
    private List<RestoGuestOrder> guestOrders = new ArrayList<>();

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

    public String getToBePreparedAtTime() {
        return toBePreparedAtTime;
    }

    public void setToBePreparedAtTime(String toBePreparedAtTime) {
        this.toBePreparedAtTime = toBePreparedAtTime;
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

        PostRestoOrderRequest that = (PostRestoOrderRequest) o;

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
        if (!Objects.equals(toBePreparedAtTime, that.toBePreparedAtTime))
            return false;
        return Objects.equals(guestOrders, that.guestOrders);
    }

    @Override
    public int hashCode() {
        int result = waiterId != null ? waiterId.hashCode() : 0;
        result = 31 * result + (tableId != null ? tableId.hashCode() : 0);
        result = 31 * result + (addressId != null ? addressId.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (menuVersion != null ? menuVersion.hashCode() : 0);
        result = 31 * result + (initializationId != null ? initializationId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (orderTypeId != null ? orderTypeId.hashCode() : 0);
        result = 31 * result + (toBePreparedAtTime != null ? toBePreparedAtTime.hashCode() : 0);
        result = 31 * result + (guestOrders != null ? guestOrders.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "PostRestoOrderRequest{" +
                "waiterId='" + waiterId + '\'' +
                ", tableId=" + tableId +
                ", addressId=" + addressId +
                ", departmentId=" + departmentId +
                ", menuVersion=" + menuVersion +
                ", initializationId='" + initializationId + '\'' +
                ", description='" + description + '\'' +
                ", orderTypeId=" + orderTypeId +
                ", toBePreparedAtTime='" + toBePreparedAtTime + '\'' +
                ", guestOrders=" + guestOrders +
                '}';
    }
}
