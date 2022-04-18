package com.eservia.model.remote.rest.business_beauty.services.service_group;

import com.eservia.model.remote.rest.request.KeyList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceGroupService {

    @GET("service-groups")
    Observable<BeautyServiceGroupResponse> getBeautyServiceGroups(@Query("q") String query,
                                                                  @Query("id") KeyList id,
                                                                  @Query("services") KeyList services,
                                                                  @Query("addresses") KeyList addresses,
                                                                  @Query("staffs") KeyList staffs,
                                                                  @Query("business_id") Integer businessId,
                                                                  @Query("sort") String sort,
                                                                  @Query("withTrashed") Integer withTrashed,
                                                                  @Query("limit") Integer limit,
                                                                  @Query("page") Integer page,
                                                                  @Query("let") Integer let);
}
