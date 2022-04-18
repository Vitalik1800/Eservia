package com.eservia.model.remote.rest.booking_resto.services.booking;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BookingService {

    @POST("Bookings/Customer")
    Observable<RestoBookingResponse> postRestoBooking(@Body RestoBookingRequest request);

    @GET("Bookings/Customer/Active")
    Observable<GetRestoBookingsResponse> getRestoBookingsActive(@Query("pageSize") Integer pageSize,
                                                                @Query("pageIndex") Integer pageIndex);

    @GET("Bookings/Customer/History")
    Observable<GetRestoBookingsResponse> getRestoBookingsHistory(@Query("pageSize") Integer pageSize,
                                                                 @Query("pageIndex") Integer pageIndex);

    @PATCH("Bookings/Customer/Reject")
    Observable<CancelRestoBookingResponse> cancelRestoBooking(@Body CancelRestoBookingRequest request);
}
