package com.eservia.booking.ui.delivery.resto;

import androidx.annotation.NonNull;

import com.eservia.model.entity.Business;


public class DeliveryRestoExtra {

    @NonNull
    public Business business;

    DeliveryRestoExtra(@NonNull Business business) {
        this.business = business;
    }
}
