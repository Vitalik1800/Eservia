package com.eservia.model.remote.rest.booking_resto.services.booking_settings;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookingSettingsService {

    @GET("BookingSettings/Customer")
    Observable<BookingSettingsResponse> getRestoBookingSettings(@Query("addressId") Long addressId);
}
