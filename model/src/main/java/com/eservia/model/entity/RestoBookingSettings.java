package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class RestoBookingSettings {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("automaticBookingConfirmation")
    @Expose
    private Boolean automaticBookingConfirmation;

    @SerializedName("automaticBookingRejection")
    @Expose
    private Boolean automaticBookingRejection;

    @SerializedName("maxAmountOfDaysAdvanceForBooking")
    @Expose
    private Long maxAmountOfDaysAdvanceForBooking;

    @SerializedName("availableTimeForEditBooking")
    @Expose
    private Long availableTimeForEditBooking;

    @SerializedName("maxAmountPeopleForBooking")
    @Expose
    private Long maxAmountPeopleForBooking;

    @SerializedName("availableTimeForCreateBooking")
    @Expose
    private Long availableTimeForCreateBooking;

    @SerializedName("minimumDurationOfBooking")
    @Expose
    private Long minimumDurationOfBooking;

    @SerializedName("serviceTimeAfterBookingEnd")
    @Expose
    private Long serviceTimeAfterBookingEnd;

    @SerializedName("idTimeZone")
    @Expose
    private String idTimeZone;

    @SerializedName("addressId")
    @Expose
    private Long addressId;

    @SerializedName("bookingIsAllowed")
    @Expose
    private Boolean bookingIsAllowed;

    @SerializedName("workSchedule")
    @Expose
    private List<RestoBookingWorkSchedule> workSchedule = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAutomaticBookingConfirmation() {
        return automaticBookingConfirmation;
    }

    public void setAutomaticBookingConfirmation(Boolean automaticBookingConfirmation) {
        this.automaticBookingConfirmation = automaticBookingConfirmation;
    }

    public Boolean getAutomaticBookingRejection() {
        return automaticBookingRejection;
    }

    public void setAutomaticBookingRejection(Boolean automaticBookingRejection) {
        this.automaticBookingRejection = automaticBookingRejection;
    }

    public Long getMaxAmountOfDaysAdvanceForBooking() {
        return maxAmountOfDaysAdvanceForBooking;
    }

    public void setMaxAmountOfDaysAdvanceForBooking(Long maxAmountOfDaysAdvanceForBooking) {
        this.maxAmountOfDaysAdvanceForBooking = maxAmountOfDaysAdvanceForBooking;
    }

    public Long getAvailableTimeForEditBooking() {
        return availableTimeForEditBooking;
    }

    public void setAvailableTimeForEditBooking(Long availableTimeForEditBooking) {
        this.availableTimeForEditBooking = availableTimeForEditBooking;
    }

    public Long getMaxAmountPeopleForBooking() {
        return maxAmountPeopleForBooking;
    }

    public void setMaxAmountPeopleForBooking(Long maxAmountPeopleForBooking) {
        this.maxAmountPeopleForBooking = maxAmountPeopleForBooking;
    }

    public Long getAvailableTimeForCreateBooking() {
        return availableTimeForCreateBooking;
    }

    public void setAvailableTimeForCreateBooking(Long availableTimeForCreateBooking) {
        this.availableTimeForCreateBooking = availableTimeForCreateBooking;
    }

    public Long getMinimumDurationOfBooking() {
        return minimumDurationOfBooking;
    }

    public void setMinimumDurationOfBooking(Long minimumDurationOfBooking) {
        this.minimumDurationOfBooking = minimumDurationOfBooking;
    }

    public Long getServiceTimeAfterBookingEnd() {
        return serviceTimeAfterBookingEnd;
    }

    public void setServiceTimeAfterBookingEnd(Long serviceTimeAfterBookingEnd) {
        this.serviceTimeAfterBookingEnd = serviceTimeAfterBookingEnd;
    }

    public String getIdTimeZone() {
        return idTimeZone;
    }

    public void setIdTimeZone(String idTimeZone) {
        this.idTimeZone = idTimeZone;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Boolean getBookingIsAllowed() {
        return bookingIsAllowed;
    }

    public void setBookingIsAllowed(Boolean bookingIsAllowed) {
        this.bookingIsAllowed = bookingIsAllowed;
    }

    public List<RestoBookingWorkSchedule> getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(List<RestoBookingWorkSchedule> workSchedule) {
        this.workSchedule = workSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestoBookingSettings that = (RestoBookingSettings) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(automaticBookingConfirmation, that.automaticBookingConfirmation))
            return false;
        if (!Objects.equals(automaticBookingRejection, that.automaticBookingRejection))
            return false;
        if (!Objects.equals(maxAmountOfDaysAdvanceForBooking, that.maxAmountOfDaysAdvanceForBooking))
            return false;
        if (!Objects.equals(availableTimeForEditBooking, that.availableTimeForEditBooking))
            return false;
        if (!Objects.equals(maxAmountPeopleForBooking, that.maxAmountPeopleForBooking))
            return false;
        if (!Objects.equals(availableTimeForCreateBooking, that.availableTimeForCreateBooking))
            return false;
        if (!Objects.equals(minimumDurationOfBooking, that.minimumDurationOfBooking))
            return false;
        if (!Objects.equals(serviceTimeAfterBookingEnd, that.serviceTimeAfterBookingEnd))
            return false;
        if (!Objects.equals(idTimeZone, that.idTimeZone))
            return false;
        if (!Objects.equals(addressId, that.addressId))
            return false;
        if (!Objects.equals(bookingIsAllowed, that.bookingIsAllowed))
            return false;
        return Objects.equals(workSchedule, that.workSchedule);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (automaticBookingConfirmation != null ? automaticBookingConfirmation.hashCode() : 0);
        result = 31 * result + (automaticBookingRejection != null ? automaticBookingRejection.hashCode() : 0);
        result = 31 * result + (maxAmountOfDaysAdvanceForBooking != null ? maxAmountOfDaysAdvanceForBooking.hashCode() : 0);
        result = 31 * result + (availableTimeForEditBooking != null ? availableTimeForEditBooking.hashCode() : 0);
        result = 31 * result + (maxAmountPeopleForBooking != null ? maxAmountPeopleForBooking.hashCode() : 0);
        result = 31 * result + (availableTimeForCreateBooking != null ? availableTimeForCreateBooking.hashCode() : 0);
        result = 31 * result + (minimumDurationOfBooking != null ? minimumDurationOfBooking.hashCode() : 0);
        result = 31 * result + (serviceTimeAfterBookingEnd != null ? serviceTimeAfterBookingEnd.hashCode() : 0);
        result = 31 * result + (idTimeZone != null ? idTimeZone.hashCode() : 0);
        result = 31 * result + (addressId != null ? addressId.hashCode() : 0);
        result = 31 * result + (bookingIsAllowed != null ? bookingIsAllowed.hashCode() : 0);
        result = 31 * result + (workSchedule != null ? workSchedule.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "RestoBookingSettings{" +
                "id=" + id +
                ", automaticBookingConfirmation=" + automaticBookingConfirmation +
                ", automaticBookingRejection=" + automaticBookingRejection +
                ", maxAmountOfDaysAdvanceForBooking=" + maxAmountOfDaysAdvanceForBooking +
                ", availableTimeForEditBooking=" + availableTimeForEditBooking +
                ", maxAmountPeopleForBooking=" + maxAmountPeopleForBooking +
                ", availableTimeForCreateBooking=" + availableTimeForCreateBooking +
                ", minimumDurationOfBooking=" + minimumDurationOfBooking +
                ", serviceTimeAfterBookingEnd=" + serviceTimeAfterBookingEnd +
                ", idTimeZone='" + idTimeZone + '\'' +
                ", addressId=" + addressId +
                ", bookingIsAllowed=" + bookingIsAllowed +
                ", workSchedule=" + workSchedule +
                '}';
    }
}
