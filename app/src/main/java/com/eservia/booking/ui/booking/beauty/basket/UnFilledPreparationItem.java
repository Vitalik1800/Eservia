package com.eservia.booking.ui.booking.beauty.basket;

import com.eservia.booking.model.booking_status.beauty.Preparation;

public class UnFilledPreparationItem {

    private Preparation preparation;

    public UnFilledPreparationItem(Preparation preparation) {
        this.preparation = preparation;
    }

    public Preparation getPreparation() {
        return preparation;
    }

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
    }
}
