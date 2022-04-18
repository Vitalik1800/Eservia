package com.eservia.model.remote.rest.business_beauty.services.promotion;

import com.eservia.model.remote.rest.business_beauty.services.service.BeautyServiceResponse;
import com.eservia.model.remote.rest.request.KeyList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PromotionService {

    @GET("businesses/{businessId}/promotions")
    Observable<BeautyPromotionsResponse> getBeautyBusinessPromotions(@Path("businessId") Integer businessId,
                                                                     @Query("id") KeyList ids,
                                                                     @Query("marketing_id") Integer marketingId,
                                                                     @Query("service_id") Integer serviceId,
                                                                     @Query("staff_id") Integer staffId,
                                                                     @Query("withTrashed") Integer withTrashed,
                                                                     @Query("limit") Integer limit,
                                                                     @Query("page") Integer page,
                                                                     @Query("let") Integer let);

    @GET("promotions/{promotionId}/services")
    Observable<BeautyServiceResponse> getBeautyPromotionServices(@Path("promotionId") Integer promotionId,
                                                                 @Query("limit") Integer limit,
                                                                 @Query("page") Integer page,
                                                                 @Query("let") Integer let);
}
