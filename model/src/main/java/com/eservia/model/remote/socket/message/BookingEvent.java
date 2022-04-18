package com.eservia.model.remote.socket.message;

import androidx.annotation.NonNull;

import com.eservia.model.entity.Validator;
import com.google.gson.annotations.SerializedName;

public class BookingEvent implements Validator {

    @SerializedName("address_id")
    protected Long addressId;

    @SerializedName("staff_id")
    protected Long staffId;

    @SerializedName("service_id")
    protected Long serviceId;

    @SerializedName("service_duration")
    protected int serviceDuration;

    @SerializedName("date")
    protected String date;

    @SerializedName("guid")
    protected String guid;

    public BookingEvent() {
    }

    public BookingEvent(Long addressId, Long staffId, Long serviceId, int serviceDuration,
                        String date, String guid) {
        this.addressId = addressId;
        this.staffId = staffId;
        this.serviceId = serviceId;
        this.serviceDuration = serviceDuration;
        this.date = date;
        this.guid = guid;
    }

    public int getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(int serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public boolean isItemValid() {
        return addressId != null
                && staffId != null
                && serviceId != null
                && date != null;
    }

    @NonNull
    @Override
    public String toString() {
        return "BookingEvent{" +
                "addressId=" + addressId +
                ", staffId=" + staffId +
                ", serviceId=" + serviceId +
                ", serviceDuration=" + serviceDuration +
                ", date='" + date + '\'' +
                ", guid='" + guid + '\'' +
                '}';
    }
}
