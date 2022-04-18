package com.eservia.model.remote.socket.message;

import com.eservia.model.entity.Validator;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LockedBookingsResponse implements Validator {

    @SerializedName("state")
    private Integer state;
    @SerializedName("data")
    private List<BookingEvent> data = new ArrayList<>();

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<BookingEvent> getData() {
        return data;
    }

    public void setData(List<BookingEvent> data) {
        this.data = data;
    }

    @Override
    public boolean isItemValid() {
        return state != null && data != null;
    }
}
