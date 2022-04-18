package com.eservia.model.remote.socket.request;

import com.eservia.model.remote.socket.message.BookingEvent;
import com.google.gson.annotations.SerializedName;

public class CancelRequestData {

    @SerializedName("token")
    private String token;

    @SerializedName("guid")
    private String guid;

    @SerializedName("booking")
    private BookingEvent booking;

    @SerializedName("service_duration")
    private Integer serviceDuration;

    @SerializedName("business_id")
    private Long businessId;

    public CancelRequestData() {}

    public CancelRequestData(String token, String guid, BookingEvent booking,
                             Integer serviceDuration, Long businessId) {
        this.token = token;
        this.guid = guid;
        this.booking = booking;
        this.serviceDuration = serviceDuration;
        this.businessId = businessId;
    }

    public Integer getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(Integer serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public BookingEvent getBooking() {
        return booking;
    }

    public void setBooking(BookingEvent booking) {
        this.booking = booking;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }
}
