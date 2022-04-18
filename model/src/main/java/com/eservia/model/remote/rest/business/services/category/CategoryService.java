package com.eservia.model.remote.rest.business.services.category;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CategoryService {

    @GET("categories")
    Observable<BusinessCategoriesResponse> getCategories(@Query("q") String query,
                                                         @Query("sector_id") String sectorId,
                                                         @Query("status") Integer status,
                                                         @Query("sort") String sort,
                                                         @Query("limit") Integer limit,
                                                         @Query("page") Integer page);

    @GET("businesses/{businessId}/categories")
    Observable<BusinessCategoriesResponse> getCategoriesByBusiness(@Path("businessId") Integer businessId,
                                                                   @Query("limit") Integer limit,
                                                                   @Query("page") Integer page);
}
