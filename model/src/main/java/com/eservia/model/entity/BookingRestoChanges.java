package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class BookingRestoChanges {

    @SerializedName("peopleCount")
    @Expose
    private Long peopleCount;

    @SerializedName("requestDateTime")
    @Expose
    private String requestDateTime;

    @SerializedName("bookingDateTime")
    @Expose
    private String bookingDateTime;

    @SerializedName("bookingEndTime")
    @Expose
    private String bookingEndTime;

    @SerializedName("departmentId")
    @Expose
    private Long departmentId;

    @SerializedName("requestDescription")
    @Expose
    private String requestDescription;

    @SerializedName("isPreviousBookingAvailable")
    @Expose
    private Boolean isPreviousBookingAvailable;

    @SerializedName("previousStatusId")
    @Expose
    private Long previousStatusId;

    @SerializedName("tableIds")
    @Expose
    private List<Long> tableIds = null;

    public Long getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Long peopleCount) {
        this.peopleCount = peopleCount;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public Boolean getIsPreviousBookingAvailable() {
        return isPreviousBookingAvailable;
    }

    public void setIsPreviousBookingAvailable(Boolean isPreviousBookingAvailable) {
        this.isPreviousBookingAvailable = isPreviousBookingAvailable;
    }

    public Long getPreviousStatusId() {
        return previousStatusId;
    }

    public void setPreviousStatusId(Long previousStatusId) {
        this.previousStatusId = previousStatusId;
    }

    public List<Long> getTableIds() {
        return tableIds;
    }

    public void setTableIds(List<Long> tableIds) {
        this.tableIds = tableIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookingRestoChanges that = (BookingRestoChanges) o;

        if (!Objects.equals(peopleCount, that.peopleCount))
            return false;
        if (!Objects.equals(requestDateTime, that.requestDateTime))
            return false;
        if (!Objects.equals(bookingDateTime, that.bookingDateTime))
            return false;
        if (!Objects.equals(bookingEndTime, that.bookingEndTime))
            return false;
        if (!Objects.equals(departmentId, that.departmentId))
            return false;
        if (!Objects.equals(requestDescription, that.requestDescription))
            return false;
        if (!Objects.equals(isPreviousBookingAvailable, that.isPreviousBookingAvailable))
            return false;
        if (!Objects.equals(previousStatusId, that.previousStatusId))
            return false;
        return Objects.equals(tableIds, that.tableIds);
    }

    @Override
    public int hashCode() {
        int result = peopleCount != null ? peopleCount.hashCode() : 0;
        result = 31 * result + (requestDateTime != null ? requestDateTime.hashCode() : 0);
        result = 31 * result + (bookingDateTime != null ? bookingDateTime.hashCode() : 0);
        result = 31 * result + (bookingEndTime != null ? bookingEndTime.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (requestDescription != null ? requestDescription.hashCode() : 0);
        result = 31 * result + (isPreviousBookingAvailable != null ? isPreviousBookingAvailable.hashCode() : 0);
        result = 31 * result + (previousStatusId != null ? previousStatusId.hashCode() : 0);
        result = 31 * result + (tableIds != null ? tableIds.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "BookingRestoChanges{" +
                "peopleCount=" + peopleCount +
                ", requestDateTime='" + requestDateTime + '\'' +
                ", bookingDateTime='" + bookingDateTime + '\'' +
                ", bookingEndTime='" + bookingEndTime + '\'' +
                ", departmentId=" + departmentId +
                ", requestDescription='" + requestDescription + '\'' +
                ", isPreviousBookingAvailable=" + isPreviousBookingAvailable +
                ", previousStatusId=" + previousStatusId +
                ", tableIds=" + tableIds +
                '}';
    }
}
