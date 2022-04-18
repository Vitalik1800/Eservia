package com.eservia.model.remote.rest.delivery.services.deliveries;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DeliveriesService {

    @POST("Deliveries/Customer")
    Observable<PostRestoDeliveryResponse> postRestoDelivery(@Body PostRestoDeliveryRequest request);

    @GET("Deliveries/Customer")
    Observable<GetRestoDeliveriesResponse> getRestoDeliveries(@Query("DeliveryStatuses") List<String> deliveryStatuses,
                                                              @Query("PageSize") Integer pageSize,
                                                              @Query("PageIndex") Integer pageIndex);
}
