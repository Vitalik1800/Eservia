package com.eservia.booking.ui.booking.beauty;

import androidx.annotation.Nullable;

import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;

import java.util.ArrayList;
import java.util.List;

class BookingBeautyExtra {

    Business business;

    @Nullable
    Address address;

    final List<Preparation> preparations = new ArrayList<>();

    BookingBeautyExtra(Business business, @Nullable Address address,
                       @Nullable List<Preparation> preparations) {
        this.business = business;
        this.address = address;
        if (preparations != null) {
            this.preparations.addAll(preparations);
        }
    }
}
