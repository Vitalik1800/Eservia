package com.eservia.model.remote.rest.business.services.business;

import com.eservia.model.remote.rest.request.KeyList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BusinessService {

    @GET("businesses")
    Observable<BusinessesResponse> getBusinesses(@Query("q") String query,
                                                 @Query("id") KeyList businessesIds,
                                                 @Query("sector_id") String sectorId,
                                                 @Query("status") String status,
                                                 @Query("tags") BusinessTags tags,
                                                 @Query("categories") BusinessCategories categories,
                                                 @Query("sort") String sort,
                                                 @Query("withTrashed") Integer withTrashed,
                                                 @Query("city") String city,
                                                 @Query("limit") Integer limit,
                                                 @Query("page") Integer page,
                                                 @Query("is_searchable") Integer isSearchable);
}
