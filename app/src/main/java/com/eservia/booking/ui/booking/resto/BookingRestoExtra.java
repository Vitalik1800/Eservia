package com.eservia.booking.ui.booking.resto;

import com.eservia.model.entity.Business;
import com.eservia.model.entity.RestoBookingSettings;

public class BookingRestoExtra {

    Business business;

    RestoBookingSettings bookingSettings;

    BookingRestoExtra(Business business, RestoBookingSettings settings) {
        this.business = business;
        this.bookingSettings = settings;
    }
}
