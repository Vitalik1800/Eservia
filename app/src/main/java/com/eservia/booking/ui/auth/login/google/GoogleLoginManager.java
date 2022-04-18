package com.eservia.booking.ui.auth.login.google;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.eservia.booking.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GoogleLoginManager implements GoogleApiClient.OnConnectionFailedListener {

    public interface GoogleLoginListener {
        void onGoogleLoginSucceeded(String token);

        void onGoogleLoginFailed(String message);
    }

    public static final int REQUEST_GOOGLE_SIGN_IN = 14;

    private final FragmentActivity activity;
    private GoogleApiClient googleApiClient;
    private GoogleLoginListener googleLoginListener;

    public GoogleLoginManager(FragmentActivity activity, GoogleLoginListener googleLoginListener) {
        this.activity = activity;
        this.googleLoginListener = googleLoginListener;
    }

    public GoogleLoginListener getGoogleLoginListener() {
        return googleLoginListener;
    }

    public void setGoogleLoginListener(GoogleLoginListener googleLoginListener) {
        this.googleLoginListener = googleLoginListener;
    }

    @NonNull
    private GoogleApiClient getGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(activity)
                    .enableAutoManage(activity, this)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {
                            if (googleApiClient != null) {
                                startGoogleLogin();
                            }
                        }

                        @Override
                        public void onConnectionSuspended(int i) {

                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, getGoogleSignInOptions())
                    .build();
        }
        return googleApiClient;
    }

    public void startGoogleLogin() {
        if (getGoogleApiClient().isConnected()) {
            Auth.GoogleSignInApi.signOut(googleApiClient);
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(getGoogleApiClient());
            activity.startActivityForResult(signInIntent, REQUEST_GOOGLE_SIGN_IN);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GOOGLE_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    String authCode = account.getServerAuthCode();
                    onSuccess(authCode);
                } else {
                    onFailed();
                }
            } else {
                onFailed();
            }
        }
    }

    public void getAccessToken(String authCode) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("client_id", activity.getString(R.string.server_client_id))
                .add("client_secret", activity.getString(R.string.secret_id))
                .add("code", authCode)
                .build();
        final Request request = new Request.Builder()
                .url("https://www.googleapis.com/oauth2/v4/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                onFailed();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String accessToken = jsonObject.get("access_token").toString();
                    onSuccess(accessToken);
                } catch (JSONException e) {
                    onFailed();
                }
            }
        });
    }

    @NonNull
    private GoogleSignInOptions getGoogleSignInOptions() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(activity.getString(R.string.server_client_id))
                .requestEmail()
                .requestProfile()
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (googleLoginListener != null) {
            googleLoginListener.onGoogleLoginFailed(connectionResult.getErrorMessage());
        }
    }

    public void dispose() {
        if (googleApiClient != null) {
            googleApiClient.stopAutoManage(activity);
            googleApiClient.disconnect();
            googleApiClient = null;
        }
    }

    private void onSuccess(String accessToken) {
        if (googleLoginListener != null) {
            new Handler(Looper.getMainLooper()).post(() ->
                    googleLoginListener.onGoogleLoginSucceeded(accessToken));
        }
    }

    private void onFailed() {
        if (googleLoginListener != null) {
            new Handler(Looper.getMainLooper()).post(() ->
                    googleLoginListener.onGoogleLoginFailed("Login Failed!"));
        }
    }
}
