package com.eservia.model.remote.rest.business_health.services.staff;

import com.eservia.model.remote.rest.business_beauty.services.staff.BusinessBeautyStaffResponse;
import com.eservia.model.remote.rest.request.KeyList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StaffService {

    @GET("staffs")
    Observable<BusinessBeautyStaffResponse> getBusinessBeautyStaffs(@Query("q") String query,
                                                                    @Query("id") KeyList ids,
                                                                    @Query("business_id") Integer businessId,
                                                                    @Query("address_id") Integer addressId,
                                                                    @Query("services") KeyList services,
                                                                    @Query("status") Integer status,
                                                                    @Query("sort") String sort,
                                                                    @Query("withTrashed") Integer withTrashed,
                                                                    @Query("limit") Integer limit,
                                                                    @Query("page") Integer page,
                                                                    @Query("let") Integer let);
}
