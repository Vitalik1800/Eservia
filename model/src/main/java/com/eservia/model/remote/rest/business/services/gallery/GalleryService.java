package com.eservia.model.remote.rest.business.services.gallery;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GalleryService {

    @GET("businesses/{businessId}/photos")
    Observable<BusinessPhotosResponse> getBusinessPhotos(@Path("businessId") Integer businessId,
                                                         @Query("sort") String sort,
                                                         @Query("limit") Integer limit,
                                                         @Query("page") Integer page);
}
