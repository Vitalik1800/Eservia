package com.eservia.model.remote.rest.booking_beauty.services.booking;

import com.eservia.model.entity.BeautyBooking;
import com.eservia.model.remote.rest.booking_beauty.services.BookingBeautyServerResponse;
import com.google.gson.annotations.SerializedName;

public class CancelBookingBeautyResponse extends BookingBeautyServerResponse {

    @SerializedName("data")
    private BeautyBooking data;

    public BeautyBooking getData() {
        return data;
    }

    public void setData(BeautyBooking data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
