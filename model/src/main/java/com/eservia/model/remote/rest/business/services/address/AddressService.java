package com.eservia.model.remote.rest.business.services.address;

import com.eservia.model.remote.rest.request.KeyList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddressService {

    @GET("addresses")
    Observable<BusinessAddressesResponse> getAddresses(@Query("q") String query,
                                                       @Query("id") KeyList addressesIds,
                                                       @Query("business_id") KeyList businessId,
                                                       @Query("status") Integer status,
                                                       @Query("limit") Integer limit,
                                                       @Query("page") Integer page);
}
