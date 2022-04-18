package com.eservia.model.remote.rest.business_health.services.service;

import com.eservia.model.remote.rest.business_beauty.services.service.BeautyServiceResponse;
import com.eservia.model.remote.rest.request.KeyList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceService {

    @GET("service-groups/{id}/services")
    Observable<BeautyServiceResponse> getServiceGroupBeautyServices(@Path("id") Integer id,
                                                                    @Query("q") String query,
                                                                    @Query("business_id") Integer businessId,
                                                                    @Query("service_group_id") Integer serviceGroupId,
                                                                    @Query("sort") String sort,
                                                                    @Query("status") Integer status,
                                                                    @Query("withTrashed") Integer withTrashed,
                                                                    @Query("limit") Integer limit,
                                                                    @Query("page") Integer page,
                                                                    @Query("let") Integer let);

    @GET("addresses/{id}/services")
    Observable<BeautyServiceResponse> getAddressBeautyServices(@Path("id") Integer id,
                                                               @Query("q") String query,
                                                               @Query("business_id") Integer businessId,
                                                               @Query("service_group_id") Integer serviceGroupId,
                                                               @Query("sort") String sort,
                                                               @Query("status") Integer status,
                                                               @Query("withTrashed") Integer withTrashed,
                                                               @Query("limit") Integer limit,
                                                               @Query("page") Integer page,
                                                               @Query("let") Integer let);

    @GET("services")
    Observable<BeautyServiceResponse> getBeautyServices(@Query("q") String query,
                                                        @Query("id") KeyList ids,
                                                        @Query("business_id") Integer businessId,
                                                        @Query("service_group_id") Integer serviceGroupId,
                                                        @Query("sort") String sort,
                                                        @Query("status") Integer status,
                                                        @Query("withTrashed") Integer withTrashed,
                                                        @Query("limit") Integer limit,
                                                        @Query("page") Integer page,
                                                        @Query("let") Integer let);
}