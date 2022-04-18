package com.eservia.model.remote.rest.business.services.working_day;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WorkingDayService {

    @GET("addresses/{id}/working-days")
    Observable<BusinessWorkingDayResponse> getAddressWorkingDays(@Path("id") Integer addressId);
}
