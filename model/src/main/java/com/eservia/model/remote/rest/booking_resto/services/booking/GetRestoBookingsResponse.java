package com.eservia.model.remote.rest.booking_resto.services.booking;

import com.eservia.model.entity.RestoBooking;
import com.eservia.model.remote.rest.booking_resto.services.BookingRestoServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetRestoBookingsResponse extends BookingRestoServerResponse {

    @SerializedName("data")
    private List<RestoBooking> data = new ArrayList<>();

    @Override
    public boolean isItemValid() {
        return data != null;
    }

    public List<RestoBooking> getData() {
        return data;
    }

    public void setData(List<RestoBooking> data) {
        this.data = data;
    }
}
