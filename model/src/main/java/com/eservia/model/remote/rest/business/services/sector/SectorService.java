package com.eservia.model.remote.rest.business.services.sector;

import com.eservia.model.remote.rest.request.StringKeyList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SectorService {

    @GET("sectors")
    Observable<BusinessSectorsResponse> getSectors(@Query("sector") StringKeyList sector,
                                                   @Query("status") Integer status,
                                                   @Query("sort") String sort,
                                                   @Query("limit") Integer limit,
                                                   @Query("page") Integer page);

    @GET("sectors/{id}")
    Observable<BusinessSectorsResponse> getSector(@Path("id") Integer sectorId);
}
