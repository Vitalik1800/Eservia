package com.eservia.booking.ui.auth.approve_phone;

import android.os.CountDownTimer;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.users.services.auth.ConfirmPhoneRequest;
import com.eservia.utils.PhoneUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class PhoneApprovePresenter extends BasePresenter<PhoneApproveView> {

    @Inject
    RestManager mRestManager;

    private final CountDownTimer mCountDownTimer;

    private Disposable mApproveDisposable;
    private Disposable mRequestSmsDisposable;

    private String mPhone;

    public PhoneApprovePresenter() {
        App.getAppComponent().inject(this);

        mCountDownTimer = new CountDownTimer(
                60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                getViewState().updateTime((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                getViewState().enableTimer(false);
            }
        };
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().enableTimer(true);
        mCountDownTimer.cancel();
        mCountDownTimer.start();

        getViewState().initExtra();
        getViewState().setPhoneNumber();
    }

    public void onExtra(String phone, String provider, String socialToken) {
        mPhone = phone;
    }

    public void requestSms(String phone) {
        getViewState().requestSent(true);
        cancelPagination(mRequestSmsDisposable);

        getViewState().enableTimer(true);
        mCountDownTimer.cancel();
        mCountDownTimer.start();

        mRequestSmsDisposable = mRestManager
                .resendConfirmCode(PhoneUtil.phoneWithPlus(phone))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onSmsSendSuccess(), this::onSmsSendFailed);
        addSubscription(mRequestSmsDisposable);
    }

    public void approveSmsCode(String smsCode) {
        getViewState().requestSent(true);
        cancelPagination(mApproveDisposable);

        getViewState().approveStarted();

        ConfirmPhoneRequest request = new ConfirmPhoneRequest(PhoneUtil.phoneWithPlus(mPhone), smsCode);
        mApproveDisposable = mRestManager
                .confirmPhone(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onPhoneVerifySuccess(), this::onPhoneVerifyFailed);

        addSubscription(mApproveDisposable);
    }

    private void onSmsSendSuccess() {
        getViewState().requestSent(false);
        getViewState().onSmsSendSuccess();
    }

    private void onSmsSendFailed(Throwable throwable) {
        getViewState().requestSent(false);
        getViewState().onSmsSendFailed(throwable);
    }

    private void onPhoneVerifySuccess() {
        getViewState().requestSent(false);
        getViewState().openHomeActivity();
        getViewState().approveFinished();
    }

    private void onPhoneVerifyFailed(Throwable throwable) {
        getViewState().requestSent(false);
        getViewState().onApproveFail(throwable);
        getViewState().approveFinished();
    }
}
