package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class RestoBookingWorkSchedule {

    public static final int TYPE_MONDAY = 1;
    public static final int TYPE_TUESDAY = 2;
    public static final int TYPE_WEDNESDAY = 3;
    public static final int TYPE_THURSDAY = 4;
    public static final int TYPE_FRIDAY = 5;
    public static final int TYPE_SATURDAY = 6;
    public static final int TYPE_SUNDAY = 7;

    @SerializedName("day")
    @Expose
    private Long day;

    @SerializedName("startTime")
    @Expose
    private Long startTime;

    @SerializedName("endTime")
    @Expose
    private Long endTime;

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestoBookingWorkSchedule that = (RestoBookingWorkSchedule) o;

        if (!Objects.equals(day, that.day)) return false;
        if (!Objects.equals(startTime, that.startTime))
            return false;
        return Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        int result = day != null ? day.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "RestoBookingWorkSchedule{" +
                "day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
