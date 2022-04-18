package com.eservia.model.remote.rest.booking_health.services.booking;

import com.eservia.model.remote.rest.booking_beauty.services.booking.BeautyBookingsResponse;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CancelBookingBeautyResponse;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyRequest;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyResponse;
import com.eservia.model.remote.rest.request.KeyList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookingService {

    @POST("bookings")
    Observable<CreateBookingBeautyResponse> createBookingBeauty(@Body CreateBookingBeautyRequest request);

    @GET("customers/{userId}/bookings")
    Observable<BeautyBookingsResponse> getBeautyBookings(@Path("userId") String userId,
                                                         @Query("q") String query,
                                                         @Query("id") KeyList ids,
                                                         @Query("business_id") Integer businessId,
                                                         @Query("address_id") Integer addressId,
                                                         @Query("staff_id") Integer staffId,
                                                         @Query("service_id") Integer serviceId,
                                                         @Query("status") KeyList status,
                                                         @Query("decision") KeyList decision,
                                                         @Query("type") Integer type,
                                                         @Query("is_appeared") Boolean isAppeared,
                                                         @Query("sort") String sort,
                                                         @Query("limit") Integer limit,
                                                         @Query("page") Integer page,
                                                         @Query("let") Integer let);

    @PATCH("bookings/{id}/cancel")
    Observable<CancelBookingBeautyResponse> cancelBookingBeauty(@Path("id") Integer bookingId);
}
