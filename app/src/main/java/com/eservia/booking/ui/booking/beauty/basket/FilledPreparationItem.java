package com.eservia.booking.ui.booking.beauty.basket;

import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.util.BookingUtil;

import org.joda.time.DateTime;

public class FilledPreparationItem extends FilledPreparationListItem {

    private Preparation preparation;

    private DateTime serviceEndTime;

    private boolean editMode;

    public FilledPreparationItem(Preparation preparation, boolean editMode) {
        this.preparation = preparation;
        this.serviceEndTime = BookingUtil.serviceEndTime(preparation.getTimeSlot().getTime(),
                preparation.getService().getDuration());
        this.editMode = editMode;
    }

    public Preparation getPreparation() {
        return preparation;
    }

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
    }

    public DateTime getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(DateTime serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    @Override
    public int getItemType() {
        return TYPE_PREPARATION;
    }
}
