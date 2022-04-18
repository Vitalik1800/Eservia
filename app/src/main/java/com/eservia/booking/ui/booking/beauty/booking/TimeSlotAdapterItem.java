package com.eservia.booking.ui.booking.beauty.booking;

import com.eservia.model.entity.BeautyDiscount;

import org.joda.time.DateTime;

public class TimeSlotAdapterItem {

    private boolean isSelected;

    private boolean isDisabled;

    private BeautyDiscount discount;

    private DateTime dateTime;

    public TimeSlotAdapterItem(DateTime dateTime) {
        this.dateTime = dateTime;
        this.isSelected = false;
        this.isDisabled = false;
    }

    public TimeSlotAdapterItem(DateTime dateTime, BeautyDiscount discount) {
        this.dateTime = dateTime;
        this.isSelected = false;
        this.isDisabled = false;
        this.discount = discount;
    }

    public TimeSlotAdapterItem(boolean isSelected, boolean isDisabled, DateTime dateTime) {
        this.isSelected = isSelected;
        this.isDisabled = isDisabled;
        this.dateTime = dateTime;
    }

    public TimeSlotAdapterItem(boolean isSelected, boolean isDisabled, BeautyDiscount discount, DateTime dateTime) {
        this.isSelected = isSelected;
        this.isDisabled = isDisabled;
        this.discount = discount;
        this.dateTime = dateTime;
    }

    public BeautyDiscount getDiscount() {
        return discount;
    }

    public void setDiscount(BeautyDiscount discount) {
        this.discount = discount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
