package com.eservia.model.remote.rest.booking_beauty.services.time_slot;

import com.eservia.model.entity.BeautyGradualTimeSlot;
import com.eservia.model.remote.rest.booking_beauty.services.BookingBeautyServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeautyGradualTimeSlotResponse extends BookingBeautyServerResponse {

    @SerializedName("data")
    private List<BeautyGradualTimeSlot> data;

    public List<BeautyGradualTimeSlot> getData() {
        return data;
    }

    public void setData(List<BeautyGradualTimeSlot> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
