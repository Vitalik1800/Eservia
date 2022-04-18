package com.eservia.model.remote.rest.booking_resto.services.general_booking;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeneralBookingService {

    @GET("GeneralBooking/Active")
    Observable<GeneralBookingsResponse> getGeneralActiveBookings(@Query("pageIndex") Integer pageIndex,
                                                                 @Query("pageSize") Integer pageSize);

    @GET("GeneralBooking/History")
    Observable<GeneralBookingsResponse> getGeneralHistoryBookings(@Query("pageIndex") Integer pageIndex,
                                                                  @Query("pageSize") Integer pageSize);
}
