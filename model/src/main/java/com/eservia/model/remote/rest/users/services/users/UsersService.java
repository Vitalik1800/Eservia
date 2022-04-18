package com.eservia.model.remote.rest.users.services.users;

import com.eservia.model.remote.rest.request.StringKeyList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UsersService {

    @GET("Users/ByIdsForUser")
    Observable<UsersResponse> getUsers(@Query("ids") StringKeyList usersId);
}
