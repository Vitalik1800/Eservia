package com.eservia.booking.ui.auth.register_phone;

import android.content.Context;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.utils.PhoneUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class RegisterPhonePresenter extends BasePresenter<RegisterPhoneView> {

    @Inject
    RestManager mRestManager;

    @Inject
    Context mContext;

    private String mPhone = "";
    private String mProvider = "";
    private String mSocialToken = "";

    public RegisterPhonePresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().initExtra();
    }

    public void onExtra(String provider, String socialToken) {
        mProvider = provider;
        mSocialToken = socialToken;
    }

    public void onPhoneChanged(String phone) {
        mPhone = phone;
    }

    public void onRegister() {
        if (isValid()) {
            getViewState().hideKeyboard();
            registerProvider(mPhone);
        }
    }

    private void registerProvider(String phone) {
        getViewState().showProgress();
        addSubscription(mRestManager
                .signUpUserWithProvider(PhoneUtil.phoneWithPlus(phone), mProvider, mSocialToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onSuccessRegisterProvider(PhoneUtil.phoneWithPlus(phone)),
                        this::onErrorRegisterProvider));
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

    private void onSuccessRegisterProvider(String phone) {
        getViewState().hideProgress();
        getViewState().onSuccessRegisterProvider(phone);
    }

    private void onErrorRegisterProvider(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onErrorRegisterProvider(throwable);
    }
}
