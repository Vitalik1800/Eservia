package com.eservia.model.remote.rest.delivery.services.deliveries;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PostRestoDeliveryRequest {

    @SerializedName("addressId")
    @Expose
    private Long addressId;

    @SerializedName("expectedDeliveryDate")
    @Expose
    private String expectedDeliveryDate;

    @SerializedName("streetId")
    @Expose
    private Long streetId;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("orderId")
    @Expose
    private Long orderId;

    @SerializedName("clientPhone")
    @Expose
    private String clientPhone;

    @SerializedName("clientName")
    @Expose
    private String clientName;

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostRestoDeliveryRequest that = (PostRestoDeliveryRequest) o;

        if (!Objects.equals(addressId, that.addressId))
            return false;
        if (!Objects.equals(expectedDeliveryDate, that.expectedDeliveryDate))
            return false;
        if (!Objects.equals(streetId, that.streetId))
            return false;
        if (!Objects.equals(location, that.location))
            return false;
        if (!Objects.equals(description, that.description))
            return false;
        if (!Objects.equals(price, that.price)) return false;
        if (!Objects.equals(orderId, that.orderId)) return false;
        if (!Objects.equals(clientPhone, that.clientPhone))
            return false;
        return Objects.equals(clientName, that.clientName);
    }

    @Override
    public int hashCode() {
        int result = addressId != null ? addressId.hashCode() : 0;
        result = 31 * result + (expectedDeliveryDate != null ? expectedDeliveryDate.hashCode() : 0);
        result = 31 * result + (streetId != null ? streetId.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (clientPhone != null ? clientPhone.hashCode() : 0);
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "PostRestoDeliveryRequest{" +
                "addressId=" + addressId +
                ", expectedDeliveryDate='" + expectedDeliveryDate + '\'' +
                ", streetId=" + streetId +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", orderId=" + orderId +
                ", clientPhone='" + clientPhone + '\'' +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
