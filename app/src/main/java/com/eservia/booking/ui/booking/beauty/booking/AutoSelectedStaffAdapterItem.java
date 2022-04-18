package com.eservia.booking.ui.booking.beauty.booking;

import com.eservia.booking.model.booking_status.beauty.Preparation;

public class AutoSelectedStaffAdapterItem {

    private Preparation preparation;

    private int colorId;

    public AutoSelectedStaffAdapterItem(Preparation preparation, int colorId) {
        this.preparation = preparation;
        this.colorId = colorId;
    }

    public Preparation getPreparation() {
        return preparation;
    }

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }
}
