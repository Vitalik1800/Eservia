package com.eservia.model.remote.rest.order_resto.services.orders;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrdersService {

    @POST("Orders")
    Observable<PostRestoOrderResponse> postRestoOrder(@Body PostRestoOrderRequest request);

    @GET("Orders/ids")
    Observable<GetRestoOrdersResponse> getRestoOrders(@Query("ids") List<Integer> ids);
}
