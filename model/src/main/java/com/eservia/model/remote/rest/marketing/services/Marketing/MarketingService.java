package com.eservia.model.remote.rest.marketing.services.Marketing;

import com.eservia.model.remote.rest.request.KeyList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarketingService {

    @GET("Marketings")
    Observable<MarketingResponse> getMarketings(@Query("businessId") KeyList businessId,
                                                @Query("title") String title,
                                                @Query("status") String status,
                                                @Query("city") String city,
                                                @Query("from") String from,
                                                @Query("to") String to,
                                                @Query("pageSize") Integer pageSize,
                                                @Query("pageIndex") Integer pageIndex);
}
