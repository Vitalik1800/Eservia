package com.eservia.model.remote.rest.booking_resto.services.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelRestoBookingRequest {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("responseDescription")
    @Expose
    private String responseDescription;

    @SerializedName("addressId")
    @Expose
    private Long addressId;

    public CancelRestoBookingRequest(Long id, String responseDescription, Long addressId) {
        this.id = id;
        this.responseDescription = responseDescription;
        this.addressId = addressId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
