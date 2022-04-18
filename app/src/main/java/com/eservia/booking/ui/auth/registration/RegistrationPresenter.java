package com.eservia.booking.ui.auth.registration;


import android.content.Context;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.users.services.auth.SignUpRequest;
import com.eservia.model.remote.rest.users.services.auth.SignUpResponse;
import com.eservia.utils.PhoneUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class RegistrationPresenter extends BasePresenter<RegistrationView> {

    private String mName = "";
    private String mLogin = "";
    private String mPassword = "";
    private String mPasswordConfirmation = "";

    @Inject
    RestManager mRestManager;

    @Inject
    Context mContext;

    public RegistrationPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    private void registerUser(String name, String phone, String password) {
        getViewState().showProgress();
        String phoneWithPlus = PhoneUtil.phoneWithPlus(phone);
        SignUpRequest request = new SignUpRequest(name, phoneWithPlus, password);
        addSubscription(mRestManager
                .registerUser(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRegistrationSuccess, this::onRegistrationFailed));
    }

    private void onRegistrationSuccess(SignUpResponse response) {
        getViewState().hideProgress();
        getViewState().onRegistrationSuccess(response.getData().getPhoneNumber());
    }

    private void onRegistrationFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onRegistrationFailed(throwable);
    }

    public void register() {
        if (isValid()) {
            getViewState().hideKeyboard();
            getViewState().showProgress();
            registerUser(mName, mLogin, mPassword);
        }
    }

    private boolean isValid() {
        String nameError = ValidatorUtil.isNameValid(mContext, mName);
        String loginError = ValidatorUtil.isPhoneValid(mContext, mLogin);
        String passwordError = ValidatorUtil.isPasswordValid(mContext, mPassword);
        String confirmPasswordError = ValidatorUtil.isConfirmPasswordValid(mContext, mPassword, mPasswordConfirmation);

        boolean isValid = true;

        if (confirmPasswordError != null) {
            isValid = false;
            getViewState().confirmPasswordError(confirmPasswordError);
        }
        if (passwordError != null) {
            isValid = false;
            getViewState().passwordError(passwordError);
        }
        if (loginError != null) {
            isValid = false;
            getViewState().loginError(loginError);
        }
        if (nameError != null) {
            isValid = false;
            getViewState().nameError(nameError);
        }
        return isValid;
    }

    public void nameChanged(String s) {
        mName = s;
    }

    public void loginChanged(String s) {
        mLogin = s;
    }

    public void passwordChanged(String s) {
        mPassword = s;
    }

    public void passwordConfirmationChanged(String s) {
        mPasswordConfirmation = s;
    }
}
