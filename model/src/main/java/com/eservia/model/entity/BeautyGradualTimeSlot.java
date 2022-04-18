package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeautyGradualTimeSlot {

    @SerializedName("date")
    private String date;

    @SerializedName("time_slots")
    private List<BeautyTimeSlot> timeSlots = null;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<BeautyTimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<BeautyTimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
