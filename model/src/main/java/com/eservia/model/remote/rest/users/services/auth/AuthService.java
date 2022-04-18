package com.eservia.model.remote.rest.users.services.auth;


import com.eservia.model.remote.rest.users.services.CommonUsersRestResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthService {

    @POST("Auth/SignIn")
    Observable<AuthResponse> singIn(@Body SignInRequest request);

    @POST("Auth/SocialLogin")
    Observable<AuthResponse> singIn(@Body SocialSignInRequest request);

    @POST("Auth/SignUp")
    Observable<SignUpResponse> signUp(@Body SignUpRequest request);

    @POST("Auth/Logout")
    Observable<CommonUsersRestResponse> logout(@Body LogoutRequest request);

    @POST("Auth/Code")
    Observable<CommonUsersRestResponse> signUpUserWithProvider(@Body SignUpProviderRequest request);

    @POST("Auth/ConfirmPhone")
    Observable<AuthResponse> confirmPhone(@Body ConfirmPhoneRequest request);

    @POST("Auth/ResendConfirmCode")
    Observable<CommonUsersRestResponse> resendConfirmCode(@Body ResendConfirmCodeRequest request);

    @POST("Auth/SendEmailConfirmCode")
    Observable<CommonUsersRestResponse> sendEmailConfirm();

    @POST("Auth/ForgotPassword")
    Observable<CommonUsersRestResponse> resetForgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("Auth/RestorePassword")
    Observable<CommonUsersRestResponse> confirmResetPassword(@Body RestorePasswordRequest restore);

    @POST("Auth/RefreshToken")
    Observable<AuthResponse> refreshToken(@Query("refreshToken") String refreshToken);

    @PATCH("Auth/DeviceToken")
    Observable<DeviceTokenResponse> registerDeviceToken(@Body DeviceTokenRequest deviceTokenRequest);
}
