package com.eservia.model.remote.rest.booking_beauty.services.time_slot;

import com.eservia.model.remote.rest.request.KeyList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TimeSlotService {

    @GET("businesses/{id}/time-slots")
    Observable<BeautyTimeSlotResponse> getBusinessBeautyTimeSlots(
            @Path("id") Integer businessId,
            @Query("address_id") Integer addressId,
            @Query("service_id") Integer serviceId,
            @Query("staff_id") Integer staffId,
            @Query("date") String date);

    @GET("businesses/{id}/time-slots/gradual")
    Observable<BeautyGradualTimeSlotResponse> getBusinessBeautyGradualTimeSlots(
            @Path("id") Integer businessId,
            @Query("address_id") Integer addressId,
            @Query("services") KeyList services,
            @Query("date") String date);
}
