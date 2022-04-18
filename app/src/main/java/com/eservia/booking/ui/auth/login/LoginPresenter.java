package com.eservia.booking.ui.auth.login;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.ServerErrorUtil;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.users.codes.UsersErrorCode;
import com.eservia.utils.PhoneUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> {

    public static final String TAG = "LoginPresenter";

    @Inject
    RestManager mRestManager;

    public LoginPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void loginUser(String phone, String password) {
        getViewState().showProgress();
        addSubscription(mRestManager
                .signInUser(PhoneUtil.phoneWithPlus(phone), password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onLoginSuccess(),
                        t -> onLoginFailed(t, PhoneUtil.phoneWithPlus(phone))));
    }

    public void signInUserWithProvider(String provider, String token) {
        getViewState().showProgress();
        addSubscription(mRestManager
                .signInUserWithProvider(provider, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> onLoginWithProviderSuccess(),
                        error -> onLoginWithProviderFailed(provider, token, error)));
    }

    private void onLoginWithProviderSuccess() {
        getViewState().hideProgress();
        getViewState().onLoginWithProviderSuccess();
    }

    private void onLoginWithProviderFailed(String provider, String token, Throwable throwable) {
        getViewState().hideProgress();
        if (ServerErrorUtil.getErrorCode(throwable) == UsersErrorCode.USER_NOT_FOUND) {
            getViewState().providerUserNotFound(provider, token);
        } else {
            getViewState().onLoginWithProviderFailed(throwable);
        }
    }

    private void onLoginSuccess() {
        getViewState().hideProgress();
        getViewState().onLoginSuccess();
    }

    private void onLoginFailed(Throwable throwable, String phone) {
        getViewState().hideProgress();
        if (ServerErrorUtil.getErrorCode(throwable) == UsersErrorCode.EMAIL_NOT_CONFIRMED) {
            getViewState().openPhoneApprove(phone);
        } else {
            getViewState().onLoginFailed(throwable);
        }
    }
}
