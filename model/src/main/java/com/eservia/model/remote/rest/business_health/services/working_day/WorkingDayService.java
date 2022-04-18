package com.eservia.model.remote.rest.business_health.services.working_day;

import com.eservia.model.remote.rest.business_beauty.services.working_day.BeautyWorkingDayResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WorkingDayService {

    @GET("staffs/{id}/working-days")
    Observable<BeautyWorkingDayResponse> getStaffBeautyWorkingDays(@Path("id") Integer staffId);
}
