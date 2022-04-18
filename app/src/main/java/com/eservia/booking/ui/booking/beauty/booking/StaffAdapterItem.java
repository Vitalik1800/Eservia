package com.eservia.booking.ui.booking.beauty.booking;

import com.eservia.model.entity.BeautyStaff;

public class StaffAdapterItem {

    public static final int TYPE_SELECTED = 1;
    public static final int TYPE_NOT_SELECTED = 0;

    private boolean isSelected;

    private BeautyStaff staff;

    public StaffAdapterItem(BeautyStaff staff) {
        this.staff = staff;
        this.isSelected = false;
    }

    public StaffAdapterItem(boolean isSelected, BeautyStaff staff) {
        this.isSelected = isSelected;
        this.staff = staff;
    }

    public  int getItemType() {
        if (isSelected) {
            return TYPE_SELECTED;
        } else {
            return TYPE_NOT_SELECTED;
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public BeautyStaff getStaff() {
        return staff;
    }

    public void setStaff(BeautyStaff staff) {
        this.staff = staff;
    }
}
