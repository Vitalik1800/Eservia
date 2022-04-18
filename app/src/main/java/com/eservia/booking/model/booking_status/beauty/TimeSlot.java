package com.eservia.booking.model.booking_status.beauty;

import com.eservia.model.entity.BeautyTimeSlot;

import org.joda.time.DateTime;

public class TimeSlot {

    private BeautyTimeSlot beautyTimeSlot;

    private DateTime mTime;

    public TimeSlot(DateTime time) {
        this.mTime = time;
    }

    public BeautyTimeSlot getBeautyTimeSlot() {
        return beautyTimeSlot;
    }

    public void setBeautyTimeSlot(BeautyTimeSlot beautyTimeSlot) {
        this.beautyTimeSlot = beautyTimeSlot;
    }

    public DateTime getTime() {
        return mTime;
    }

    public void setTime(DateTime time) {
        this.mTime = time;
    }
}
