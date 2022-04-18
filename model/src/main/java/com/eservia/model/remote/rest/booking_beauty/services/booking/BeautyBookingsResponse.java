package com.eservia.model.remote.rest.booking_beauty.services.booking;

import com.eservia.model.entity.BeautyBooking;
import com.eservia.model.remote.rest.booking_beauty.services.BookingBeautyServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeautyBookingsResponse extends BookingBeautyServerResponse {

    @SerializedName("data")
    private List<BeautyBooking> data;

    public List<BeautyBooking> getData() {
        return data;
    }

    public void setData(List<BeautyBooking> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
