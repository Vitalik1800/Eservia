package com.eservia.booking.model.booking_status.beauty;

import android.content.Context;
import android.os.CountDownTimer;

import androidx.annotation.ColorInt;

import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.ColorUtil;
import com.eservia.model.entity.BeautyDiscount;
import com.eservia.model.entity.BeautyService;
import com.eservia.model.entity.BeautyStaff;

import org.joda.time.DateTime;

import java.util.UUID;

public class Preparation {

    private final String id;

    private BeautyService mService;

    private BeautyStaff mStaff;

    private TimeSlot mTimeSlot;

    private BeautyDiscount mDiscount;

    private DateTime mDay;

    private String mComment;

    private final CountDownTimer mCountDownTimer;

    private long mMillisUntilExpire;

    private boolean mIsExpired;

    @ColorInt
    private final int colorId;

    public Preparation(Context context) {

        this.id = UUID.randomUUID().toString();

        this.colorId = ColorUtil.randomColor(context);

        mCountDownTimer = new CountDownTimer(BookingUtil.BOOKING_EXP_TIME, 1000) {
            @Override
            public void onTick(long millis) {
                mMillisUntilExpire = millis;
            }

            @Override
            public void onFinish() {
                mIsExpired = true;
            }
        };
    }

    public String getId() {
        return id;
    }

    public int getColorId() {
        return colorId;
    }

    public boolean isFull() {
        return mService != null
                && mStaff != null
                && mDay != null
                && mTimeSlot != null;
    }

    public DateTime getDay() {
        return mDay;
    }

    public void setDay(DateTime day) {
        this.mDay = day;
    }

    public long getMillisUntilExpire() {
        return mMillisUntilExpire;
    }

    public boolean isExpired() {
        return mIsExpired;
    }

    public CountDownTimer getCountDownTimer() {
        return mCountDownTimer;
    }

    public BeautyService getService() {
        return mService;
    }

    public void setService(BeautyService service) {
        mService = service;
    }

    public BeautyStaff getStaff() {
        return mStaff;
    }

    public void setStaff(BeautyStaff staff) {
        this.mStaff = staff;
    }

    public TimeSlot getTimeSlot() {
        return mTimeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        mTimeSlot = timeSlot;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public BeautyDiscount getDiscount() {
        return mDiscount;
    }

    public void setDiscount(BeautyDiscount discount) {
        this.mDiscount = discount;
    }
}
