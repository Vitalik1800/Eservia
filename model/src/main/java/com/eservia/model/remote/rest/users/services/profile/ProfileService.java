package com.eservia.model.remote.rest.users.services.profile;

import com.eservia.model.remote.rest.users.services.CommonUsersRestResponse;
import com.eservia.model.remote.rest.users.services.auth.SocialSignInRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ProfileService {

    @GET("Profile")
    Observable<ProfileResponse> profileInfo();

    @PUT("Profile")
    Observable<ProfileResponse> updateProfile(@Body EditProfileRequest editProfileRequest);

    @POST("Profile/Password")
    Observable<CommonUsersRestResponse> changePassword(@Body ChangePassRequest request);

    @POST("Profile/AttachSocialAccount")
    Observable<ProfileResponse> attachSocialAccount(@Body SocialSignInRequest request);

    @PATCH("Profile/Email")
    Observable<EmailResponse> sendEmail(@Body EmailRequest emailRequest);

    @DELETE("Profile/DetachSocialAccount/{provider}")
    Observable<ProfileResponse> detachSocialAccount(@Path("provider") String provider);
}
