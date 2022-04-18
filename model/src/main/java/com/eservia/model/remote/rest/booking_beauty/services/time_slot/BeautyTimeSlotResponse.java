package com.eservia.model.remote.rest.booking_beauty.services.time_slot;

import com.eservia.model.entity.BeautyTimeSlot;
import com.eservia.model.remote.rest.booking_beauty.services.BookingBeautyServerResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeautyTimeSlotResponse extends BookingBeautyServerResponse {

    @SerializedName("data")
    private List<BeautyTimeSlot> data;

    public List<BeautyTimeSlot> getData() {
        return data;
    }

    public void setData(List<BeautyTimeSlot> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return data != null;
    }
}
