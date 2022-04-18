package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

public class MarketingWorkSchedule {

    public static final int TYPE_MONDAY = 1;
    public static final int TYPE_TUESDAY = 2;
    public static final int TYPE_WEDNESDAY = 3;
    public static final int TYPE_THURSDAY = 4;
    public static final int TYPE_FRIDAY = 5;
    public static final int TYPE_SATURDAY = 6;
    public static final int TYPE_SUNDAY = 7;

    @SerializedName("id")
    private Integer id;
    @SerializedName("day")
    private Integer day;
    @SerializedName("startTime")
    private Integer startTime;
    @SerializedName("endTime")
    private Integer endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }
}
