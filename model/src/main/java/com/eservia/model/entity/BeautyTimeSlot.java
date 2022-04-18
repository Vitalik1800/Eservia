package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BeautyTimeSlot {

    @SerializedName("date")
    private String date;

    @SerializedName("service_id")
    private Integer serviceId;

    @SerializedName("staffs")
    private List<Integer> staffs = new ArrayList<>();

    @SerializedName("discounts")
    private List<BeautyDiscount> discounts = new ArrayList<>();

    public List<BeautyDiscount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<BeautyDiscount> discounts) {
        this.discounts = discounts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public List<Integer> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Integer> staffs) {
        this.staffs = staffs;
    }
}
