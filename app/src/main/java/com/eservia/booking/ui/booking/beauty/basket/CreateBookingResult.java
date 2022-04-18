package com.eservia.booking.ui.booking.beauty.basket;

import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyResponse;

public class CreateBookingResult {

    private CreateBookingBeautyResponse response;

    private Throwable error;

    public CreateBookingResult(CreateBookingBeautyResponse response) {
        this.response = response;
    }

    public CreateBookingResult(Throwable error) {
        this.error = error;
    }

    public CreateBookingBeautyResponse getResponse() {
        return response;
    }

    public void setResponse(CreateBookingBeautyResponse response) {
        this.response = response;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
