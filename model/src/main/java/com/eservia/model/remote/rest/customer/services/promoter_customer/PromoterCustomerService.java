package com.eservia.model.remote.rest.customer.services.promoter_customer;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PromoterCustomerService {

    @POST("PromoterCustomers/Register/{businessId}")
    Observable<PromoterCustomerRegisterResponse> registerCustomer(@Path("businessId") Integer businessId);

    @GET("PromoterCustomers/Businesses")
    Observable<PromoterCustomerBusinessesResponse> getPromoterCustomerBusinesses();
}
