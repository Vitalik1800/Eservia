package com.eservia.model.remote.rest.booking_resto.services.booking_settings;

import com.eservia.model.entity.RestoBookingSettings;
import com.eservia.model.remote.rest.booking_resto.services.BookingRestoServerResponse;
import com.google.gson.annotations.SerializedName;

public class BookingSettingsResponse extends BookingRestoServerResponse {

    @SerializedName("data")
    private RestoBookingSettings data;

    @Override
    public boolean isItemValid() {
        return data != null;
    }

    public RestoBookingSettings getData() {
        return data;
    }

    public void setData(RestoBookingSettings data) {
        this.data = data;
    }
}
