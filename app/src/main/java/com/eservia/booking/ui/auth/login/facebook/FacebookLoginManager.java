package com.eservia.booking.ui.auth.login.facebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class FacebookLoginManager {

    public interface FacebookLoginListener {
        void onSuccessFacebookLogin(String token);

        void onErrorFacebookLogin(String message);
    }

    private FacebookLoginListener listener;

    private final CallbackManager callbackManager;

    public FacebookLoginManager() {
        callbackManager = CallbackManager.Factory.create();
    }

    public void setResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void signIn(@NonNull Activity activity, @NonNull FacebookLoginListener listener) {
        this.listener = listener;
        registerCallback();
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
    }

    private void registerCallback() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (listener != null) {
                    listener.onSuccessFacebookLogin(loginResult.getAccessToken().getToken());
                    //getUserDetails(loginResult);
                }
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(@NonNull FacebookException error) {
                if (error instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                }
                if (listener != null) {
                    listener.onErrorFacebookLogin(error.getMessage());
                }
            }
        });
    }

    protected void getUserDetails(LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                (json_object, response) -> {
                    String profile = json_object.toString();
                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email, picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();
    }
}
