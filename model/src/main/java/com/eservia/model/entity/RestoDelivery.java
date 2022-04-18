package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class RestoDelivery extends GeneralBooking {

    public static final String STATUS_CREATED = "Created";
    public static final String STATUS_NOT_AGREED = "NotAgreed";
    public static final String STATUS_WAITING = "Waiting";
    public static final String STATUS_ACCEPTED = "Accepted";
    public static final String STATUS_IN_PROGRESS = "InProgress";
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_DONE = "Done";
    public static final String STATUS_CANCELLED = "Canceled";
    public static final String STATUS_OVERDUE = "Overdue";

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("from")
    @Expose
    private RestoDeliveryFrom from;

    @SerializedName("company")
    @Expose
    private RestoDeliveryCompany company;

    @SerializedName("status")
    @Expose
    private Long status;

    @SerializedName("creationTime")
    @Expose
    private String creationTime;

    @SerializedName("orderReadyDate")
    @Expose
    private String orderReadyDate;

    @SerializedName("deliveryDate")
    @Expose
    private String deliveryDate;

    @SerializedName("expectedDeliveryDate")
    @Expose
    private String expectedDeliveryDate;

    @SerializedName("assignTime")
    @Expose
    private String assignTime;

    @SerializedName("arrivalDate")
    @Expose
    private String arrivalDate;

    @SerializedName("realDeliveryDate")
    @Expose
    private String realDeliveryDate;

    @SerializedName("wastedDate")
    @Expose
    private String wastedDate;

    @SerializedName("orderId")
    @Expose
    private Long orderId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("clientName")
    @Expose
    private String clientName;

    @SerializedName("clientPhone")
    @Expose
    private String clientPhone;

    @SerializedName("isCanceledByClient")
    @Expose
    private Boolean isCanceledByClient;

    private RestoOrderResponseData order;

    private Business business;

    private Address address;

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public RestoOrderResponseData getOrder() {
        return order;
    }

    public void setOrder(RestoOrderResponseData order) {
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public RestoDeliveryFrom getFrom() {
        return from;
    }

    public void setFrom(RestoDeliveryFrom from) {
        this.from = from;
    }

    public RestoDeliveryCompany getCompany() {
        return company;
    }

    public void setCompany(RestoDeliveryCompany company) {
        this.company = company;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getOrderReadyDate() {
        return orderReadyDate;
    }

    public void setOrderReadyDate(String orderReadyDate) {
        this.orderReadyDate = orderReadyDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getRealDeliveryDate() {
        return realDeliveryDate;
    }

    public void setRealDeliveryDate(String realDeliveryDate) {
        this.realDeliveryDate = realDeliveryDate;
    }

    public String getWastedDate() {
        return wastedDate;
    }

    public void setWastedDate(String wastedDate) {
        this.wastedDate = wastedDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Boolean getCanceledByClient() {
        return isCanceledByClient;
    }

    public void setCanceledByClient(Boolean canceledByClient) {
        isCanceledByClient = canceledByClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestoDelivery that = (RestoDelivery) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(location, that.location))
            return false;
        if (!Objects.equals(from, that.from)) return false;
        if (!Objects.equals(company, that.company)) return false;
        if (!Objects.equals(status, that.status)) return false;
        if (!Objects.equals(creationTime, that.creationTime))
            return false;
        if (!Objects.equals(orderReadyDate, that.orderReadyDate))
            return false;
        if (!Objects.equals(deliveryDate, that.deliveryDate))
            return false;
        if (!Objects.equals(expectedDeliveryDate, that.expectedDeliveryDate))
            return false;
        if (!Objects.equals(assignTime, that.assignTime))
            return false;
        if (!Objects.equals(arrivalDate, that.arrivalDate))
            return false;
        if (!Objects.equals(realDeliveryDate, that.realDeliveryDate))
            return false;
        if (!Objects.equals(wastedDate, that.wastedDate))
            return false;
        if (!Objects.equals(orderId, that.orderId)) return false;
        if (!Objects.equals(description, that.description))
            return false;
        if (!Objects.equals(price, that.price)) return false;
        if (!Objects.equals(clientName, that.clientName))
            return false;
        if (!Objects.equals(clientPhone, that.clientPhone))
            return false;
        return Objects.equals(isCanceledByClient, that.isCanceledByClient);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        result = 31 * result + (orderReadyDate != null ? orderReadyDate.hashCode() : 0);
        result = 31 * result + (deliveryDate != null ? deliveryDate.hashCode() : 0);
        result = 31 * result + (expectedDeliveryDate != null ? expectedDeliveryDate.hashCode() : 0);
        result = 31 * result + (assignTime != null ? assignTime.hashCode() : 0);
        result = 31 * result + (arrivalDate != null ? arrivalDate.hashCode() : 0);
        result = 31 * result + (realDeliveryDate != null ? realDeliveryDate.hashCode() : 0);
        result = 31 * result + (wastedDate != null ? wastedDate.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        result = 31 * result + (clientPhone != null ? clientPhone.hashCode() : 0);
        result = 31 * result + (isCanceledByClient != null ? isCanceledByClient.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "RestoDelivery{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", from=" + from +
                ", company=" + company +
                ", status=" + status +
                ", creationTime='" + creationTime + '\'' +
                ", orderReadyDate='" + orderReadyDate + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", expectedDeliveryDate='" + expectedDeliveryDate + '\'' +
                ", assignTime='" + assignTime + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", realDeliveryDate='" + realDeliveryDate + '\'' +
                ", wastedDate='" + wastedDate + '\'' +
                ", orderId=" + orderId +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", isCanceledByClient=" + isCanceledByClient +
                '}';
    }
}
