package com.eservia.model.remote.rest.booking_resto.services.booking;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class RestoBookingRequest {

    @SerializedName("departmentId")
    @Expose
    private Long departmentId;

    @SerializedName("tableIds")
    @Expose
    private List<Long> tableIds = null;

    @SerializedName("peopleCount")
    @Expose
    private Long peopleCount;

    @SerializedName("requestDescription")
    @Expose
    private String requestDescription;

    @SerializedName("bookingDateTime")
    @Expose
    private String bookingDateTime;

    @SerializedName("bookingEndTime")
    @Expose
    private String bookingEndTime;

    @SerializedName("addressId")
    @Expose
    private Long addressId;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public List<Long> getTableIds() {
        return tableIds;
    }

    public void setTableIds(List<Long> tableIds) {
        this.tableIds = tableIds;
    }

    public Long getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Long peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
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

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestoBookingRequest that = (RestoBookingRequest) o;

        if (!Objects.equals(departmentId, that.departmentId))
            return false;
        if (!Objects.equals(tableIds, that.tableIds))
            return false;
        if (!Objects.equals(peopleCount, that.peopleCount))
            return false;
        if (!Objects.equals(requestDescription, that.requestDescription))
            return false;
        if (!Objects.equals(bookingDateTime, that.bookingDateTime))
            return false;
        if (!Objects.equals(bookingEndTime, that.bookingEndTime))
            return false;
        return Objects.equals(addressId, that.addressId);
    }

    @Override
    public int hashCode() {
        int result = departmentId != null ? departmentId.hashCode() : 0;
        result = 31 * result + (tableIds != null ? tableIds.hashCode() : 0);
        result = 31 * result + (peopleCount != null ? peopleCount.hashCode() : 0);
        result = 31 * result + (requestDescription != null ? requestDescription.hashCode() : 0);
        result = 31 * result + (bookingDateTime != null ? bookingDateTime.hashCode() : 0);
        result = 31 * result + (bookingEndTime != null ? bookingEndTime.hashCode() : 0);
        result = 31 * result + (addressId != null ? addressId.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "RestoBookingRequest{" +
                "departmentId=" + departmentId +
                ", tableIds=" + tableIds +
                ", peopleCount=" + peopleCount +
                ", requestDescription='" + requestDescription + '\'' +
                ", bookingDateTime='" + bookingDateTime + '\'' +
                ", bookingEndTime='" + bookingEndTime + '\'' +
                ", addressId=" + addressId +
                '}';
    }
}
