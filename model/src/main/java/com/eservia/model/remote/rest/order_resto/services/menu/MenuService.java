package com.eservia.model.remote.rest.order_resto.services.menu;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MenuService {

    @GET("Menu")
    Observable<MenuResponse> getMenu(@Query("AddressId") Long addressId,
                                     @Query("DepartmentId") Long departmentId);

    @GET("Menu/Version")
    Observable<MenuVersionResponse> getMenuVersion(@Query("AddressId") Long addressId,
                                                   @Query("DepartmentId") Long departmentId);
}
