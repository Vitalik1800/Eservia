package com.eservia.booking.ui.booking.beauty.booking;

import com.eservia.booking.model.booking_status.beauty.Preparation;

public class PreparedServicesAdapterItem {

    private boolean isSelected;

    private boolean isFinished;

    private boolean isExpired;

    private Preparation preparation;

    public PreparedServicesAdapterItem(Preparation preparation) {
        this.preparation = preparation;
        this.isSelected = false;
        this.isFinished = false;
        this.isExpired = false;
    }

    public PreparedServicesAdapterItem(Preparation preparation, boolean isSelected) {
        this.preparation = preparation;
        this.isSelected = isSelected;
        this.isFinished = false;
        this.isExpired = false;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Preparation getPreparation() {
        return preparation;
    }

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
    }
}
