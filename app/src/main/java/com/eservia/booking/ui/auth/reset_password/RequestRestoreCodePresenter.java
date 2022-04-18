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
public class RequestRestoreCodePresenter extends BasePresenter<RequestRestoreCodeView> {

    @Inject
    RestManager mRestManager;

    @Inject
    Context mContext;

    private String mPhone = "";

    public RequestRestoreCodePresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void onPhoneChanged(String phone) {
        mPhone = phone;
    }

    public void onSendSms() {
        if (isValid()) {
            getViewState().hideKeyboard();
            requestSms(mPhone);
        }
    }

    private void requestSms(String phone) {
        getViewState().showProgress();
        addSubscription(mRestManager
                .resetForgotPassword(PhoneUtil.phoneWithPlus(phone))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onSmsSendSuccess(), this::onSmsSendFailed));
    }

    private boolean isValid() {
        String phoneError = ValidatorUtil.isPhoneValid(mContext, mPhone);

        boolean isValid = true;

        if (phoneError != null) {
            isValid = false;
            getViewState().onPhoneError(phoneError);
        }
        return isValid;
    }

    private void onSmsSendSuccess() {
        EventBus.getDefault().postSticky(mPhone);
        getViewState().hideProgress();
        getViewState().onSendSuccess();
        getViewState().openResetPass();
    }

    private void onSmsSendFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onSendFailed(throwable);
    }
}
