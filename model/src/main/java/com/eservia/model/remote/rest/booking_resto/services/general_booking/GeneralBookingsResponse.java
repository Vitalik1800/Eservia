package com.eservia.model.remote.rest.booking_resto.services.general_booking;

import com.eservia.model.remote.rest.booking_resto.services.BookingRestoServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GeneralBookingsResponse extends BookingRestoServerResponse {

    @SerializedName("data")
    private List<GeneralBookingsResponseData> data = new ArrayList<>();

    @Override
    public boolean isItemValid() {
        return data != null;
    }

    public List<GeneralBookingsResponseData> getData() {
        return data;
    }

    public void setData(List<GeneralBookingsResponseData> data) {
        this.data = data;
    }
}
