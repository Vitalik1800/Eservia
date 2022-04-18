package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class RestoBooking extends GeneralBooking {

    public static final long STATUS_NEW = 1;
    public static final long STATUS_APPROVED = 2;
    public static final long STATUS_REJECTED = 3;
    public static final long STATUS_MODIFIED = 4;

    @SerializedName("businessId")
    @Expose
    private Long businessId;

    @SerializedName("addressId")
    @Expose
    private Long addressId;

    @SerializedName("departmentId")
    @Expose
    private Long departmentId;

    @SerializedName("changes")
    @Expose
    private BookingRestoChanges changes;

    @SerializedName("tables")
    @Expose
    private List<BookingRestoTable> tables = null;

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("statusId")
    @Expose
    private Long statusId;

    @SerializedName("peopleCount")
    @Expose
    private Long peopleCount;

    @SerializedName("isArchived")
    @Expose
    private Boolean isArchived;

    @SerializedName("requestDateTime")
    @Expose
    private String requestDateTime;

    @SerializedName("bookingDateTime")
    @Expose
    private String bookingDateTime;

    @SerializedName("bookingEndTime")
    @Expose
    private String bookingEndTime;

    @SerializedName("requestDescription")
    @Expose
    private String requestDescription;

    @SerializedName("responseDescription")
    @Expose
    private String responseDescription;

    private Business business;

    private Address address;

    private RestoDepartment department;

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

    public RestoDepartment getDepartment() {
        return department;
    }

    public void setDepartment(RestoDepartment department) {
        this.department = department;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
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

    public BookingRestoChanges getChanges() {
        return changes;
    }

    public void setChanges(BookingRestoChanges changes) {
        this.changes = changes;
    }

    public List<BookingRestoTable> getTables() {
        return tables;
    }

    public void setTables(List<BookingRestoTable> tables) {
        this.tables = tables;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Long peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public String getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(String requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public String getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public String getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(String bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestoBooking that = (RestoBooking) o;

        if (!Objects.equals(addressId, that.addressId))
            return false;
        if (!Objects.equals(departmentId, that.departmentId))
            return false;
        if (!Objects.equals(changes, that.changes)) return false;
        if (!Objects.equals(tables, that.tables)) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(statusId, that.statusId))
            return false;
        if (!Objects.equals(peopleCount, that.peopleCount))
            return false;
        if (!Objects.equals(isArchived, that.isArchived))
            return false;
        if (!Objects.equals(requestDateTime, that.requestDateTime))
            return false;
        if (!Objects.equals(bookingDateTime, that.bookingDateTime))
            return false;
        if (!Objects.equals(bookingEndTime, that.bookingEndTime))
            return false;
        if (!Objects.equals(requestDescription, that.requestDescription))
            return false;
        return Objects.equals(responseDescription, that.responseDescription);
    }

    @Override
    public int hashCode() {
        int result = addressId != null ? addressId.hashCode() : 0;
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (changes != null ? changes.hashCode() : 0);
        result = 31 * result + (tables != null ? tables.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (peopleCount != null ? peopleCount.hashCode() : 0);
        result = 31 * result + (isArchived != null ? isArchived.hashCode() : 0);
        result = 31 * result + (requestDateTime != null ? requestDateTime.hashCode() : 0);
        result = 31 * result + (bookingDateTime != null ? bookingDateTime.hashCode() : 0);
        result = 31 * result + (bookingEndTime != null ? bookingEndTime.hashCode() : 0);
        result = 31 * result + (requestDescription != null ? requestDescription.hashCode() : 0);
        result = 31 * result + (responseDescription != null ? responseDescription.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "RestoBookingResponse{" +
                "addressId=" + addressId +
                ", departmentId=" + departmentId +
                ", changes=" + changes +
                ", tables=" + tables +
                ", id=" + id +
                ", statusId=" + statusId +
                ", peopleCount=" + peopleCount +
                ", isArchived=" + isArchived +
                ", requestDateTime='" + requestDateTime + '\'' +
                ", bookingDateTime='" + bookingDateTime + '\'' +
                ", bookingEndTime='" + bookingEndTime + '\'' +
                ", requestDescription='" + requestDescription + '\'' +
                ", responseDescription='" + responseDescription + '\'' +
                '}';
    }
}
