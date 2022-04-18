package com.eservia.model.remote.rest.booking_resto.services.booking;

import com.eservia.model.entity.RestoBooking;
import com.eservia.model.remote.rest.booking_resto.services.BookingRestoServerResponse;
import com.google.gson.annotations.SerializedName;

public class RestoBookingResponse extends BookingRestoServerResponse {

    @SerializedName("data")
    private RestoBooking data;

    @Override
    public boolean isItemValid() {
        return data != null;
    }

    public RestoBooking getData() {
        return data;
    }

    public void setData(RestoBooking data) {
        this.data = data;
    }
}
