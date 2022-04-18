package com.eservia.model.remote.rest.delivery.services.street_servicing;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StreetServicingService {

    @GET("StreetServicing/Settlements/Customer")
    Observable<DeliveryRestoSettlementsResponse> getDeliveryRestoSettlements();

    @GET("StreetServicing/Streets/Customer")
    Observable<DeliveryRestoStreetsResponse> getDeliveryRestoStreets(@Query("settlementId") Long settlementId);
}
