package com.eservia.model.remote.rest.business.services.favorite;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FavoriteService {

    @POST("businesses/{id}/favorites")
    Observable<AddFavoriteResponse> businessAddFavorite(@Path("id") Integer id);

    @DELETE("businesses/{id}/favorites")
    Observable<DeleteFavoriteResponse> businessDeleteFavorite(@Path("id") Integer id);

    @GET("users/{id}/favorites")
    Observable<UserFavoritesResponse> getFavoritesByUser(@Path("id") String userId,
                                                         @Query("object_type") String objectType,
                                                         @Query("sort") String sort,
                                                         @Query("limit") Integer limit,
                                                         @Query("page") Integer page,
                                                         @Query("let") Integer let);

    @GET("users/{userId}/sectors/{sectorId}/favorites")
    Observable<UserFavoritesResponse> getFavoritesByUserAndSector(@Path("userId") String userId,
                                                                  @Path("sectorId") Integer sectorId,
                                                                  @Query("object_type") String objectType,
                                                                  @Query("sort") String sort,
                                                                  @Query("limit") Integer limit,
                                                                  @Query("page") Integer page,
                                                                  @Query("let") Integer let);
}
