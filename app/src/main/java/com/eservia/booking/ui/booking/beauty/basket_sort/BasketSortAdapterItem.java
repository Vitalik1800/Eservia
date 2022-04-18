package com.eservia.booking.ui.booking.beauty.basket_sort;

import com.eservia.booking.model.booking_status.beauty.Preparation;

public class BasketSortAdapterItem {

    private Preparation preparation;

    private boolean editMode;

    public BasketSortAdapterItem(Preparation preparation, boolean editMode) {
        this.preparation = preparation;
        this.editMode = editMode;
    }

    public Preparation getPreparation() {
        return preparation;
    }

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
}
