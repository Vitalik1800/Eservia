package com.eservia.model.remote.rest.business.services.cities;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CitiesService {

    @GET("addresses/cities")
    Observable<BusinessCitiesResponse> getBusinessCities(@Query("sort") String sort);
}
