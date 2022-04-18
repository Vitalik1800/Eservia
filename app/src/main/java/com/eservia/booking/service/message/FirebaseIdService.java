package com.eservia.booking.service.message;

import android.annotation.SuppressLint;

import com.eservia.booking.App;
import com.eservia.model.prefs.AccessToken;
import com.eservia.model.remote.rest.RestManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FirebaseIdService extends FirebaseInstanceIdService {

    @Inject
    RestManager mRestManager;

    @Override
    public void onCreate() {
        super.onCreate();
        App.getAppComponent().inject(this);
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if (refreshedToken != null) {
            sendRegistrationToServer(refreshedToken);
        }
    }

    @SuppressLint("CheckResult")
    private void sendRegistrationToServer(String token) {
        if (AccessToken.getSessionId() != null) {
            mRestManager.postDeviceToken(token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(r -> {
                    }, e -> {
                    });
        }
    }
}
