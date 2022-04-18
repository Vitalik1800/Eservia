package com.eservia.model.remote.rest.department_resto.services.department;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DepartmentService {

    @GET("Departments/Customer")
    Observable<DepartmentsResponse> getRestoDepartments(@Query("addressId") Long addressId);

    @GET("Departments/ids")
    Observable<DepartmentsResponse> getRestoDepartmentsByIds(@Query("ids") List<Integer> ids);
}
