package com.eservia.model.remote.rest.business.services.comment;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommentService {

    @GET("businesses/{id}/comments")
    Observable<BusinessCommentsResponse> getBusinessComments(@Path("id") Integer businessId,
                                                             @Query("sort") String sort,
                                                             @Query("limit") Integer limit,
                                                             @Query("page") Integer page,
                                                             @Query("let") Integer let);

    @POST("comments")
    Observable<CreateBusinessCommentResponse> createBusinessComment(@Body CreateBusinessCommentRequest request);
}
