package com.eservia.booking.ui.auth.reset_password;


import android.content.Context;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.utils.PhoneUtil;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class ResetPasswordPresenter extends BasePresenter<ResetPasswordView> {

    @Inject
    RestManager mRestManager;

    @Inject
    Context mContext;

    private String mPhone = "";
    private String mPassword = "";
    private String mPasswordConfirm = "";
    private String mCode = "";

    public ResetPasswordPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        String phone = EventBus.getDefault().removeStickyEvent(String.class);
        if (phone != null) {
            mPhone = phone;
        }
    }

    public void onPhoneChanged(String phone) {
        mPhone = phone;
    }

    public void onPasswordChanged(String password) {
        mPassword = password;
    }

    public void onPasswordConfirmChanged(String passwordConfirm) {
        mPasswordConfirm = passwordConfirm;
    }

    public void onCodeChanged(String code) {
        mCode = code;
    }

    public void onReset() {
        if (isValid()) {
            getViewState().hideKeyboard();
            restorePass(mPhone, mPassword, mCode);
        }
    }

    private void restorePass(String phone, String password, String code) {
        getViewState().showProgress();
        addSubscription(mRestManager
                .confirmResetPassword(PhoneUtil.phoneWithPlus(phone), password, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onSuccess(), this::onFailed));
    }

    private boolean isValid() {
        String passwordError = ValidatorUtil.isPasswordValid(mContext, mPassword);
        String passwordConfirmError = ValidatorUtil.isConfirmPasswordValid(mContext,
                mPassword,
                mPasswordConfirm);
        String codeError = ValidatorUtil.isSecureCodeValid(mContext, mCode);

        boolean isValid = true;

        if (passwordError != null) {
            isValid = false;
            getViewState().onPasswordError(passwordError);
        }
        if (passwordConfirmError != null) {
            isValid = false;
            getViewState().onPasswordConfirmError(passwordConfirmError);
        }
        if (codeError != null) {
            isValid = false;
            getViewState().onCodeError(codeError);
        }
        return isValid;
    }

    private void onSuccess() {
        getViewState().hideProgress();
        getViewState().onResetSuccess();
        getViewState().close();
    }

    private void onFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onResetFailed(throwable);
    }
}
