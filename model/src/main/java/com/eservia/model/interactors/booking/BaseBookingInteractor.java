package com.eservia.model.interactors.booking;

import com.eservia.model.remote.rest.RestManager;

public abstract class BaseBookingInteractor implements BookingInteractor {

    protected RestManager restManager;

    public BaseBookingInteractor(RestManager restManager) {
        this.restManager = restManager;
    }
}
