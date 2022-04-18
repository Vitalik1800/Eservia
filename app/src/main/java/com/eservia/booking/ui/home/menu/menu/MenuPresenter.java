package com.eservia.booking.ui.home.menu.menu;


import androidx.annotation.NonNull;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.local.ContentChangesObservable;
import com.eservia.model.local.SyncEvent;
import com.eservia.model.prefs.Profile;
import com.eservia.model.remote.UrlList;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.utils.Contract;
import com.eservia.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class MenuPresenter extends BasePresenter<MenuView> {

    @Inject
    RestManager mRestManager;

    public MenuPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        subscribeOnProfileUpdate();
        try {
            loadProfile();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onProfileSettingsClicked() {
        getViewState().openProfileSettings();
    }

    public void onWriteToUsClicked() {
        getViewState().openWriteToUs();
    }

    public void onPrivacyClicked() {
        getViewState().openPrivacy(UrlList.getPolicyOfConfidence());
    }

    public void onTermsClicked() {
        getViewState().openTerms(UrlList.getTermsOfUsing());
    }

    public void onAboutAppClicked() {
        getViewState().openAboutApp();
    }

    private void loadProfile() throws ClassNotFoundException {
        loadUserPhoto();
        loadUserNameSurname();
        loadUserPhone();
    }

    private void loadUserPhoto() {
        getViewState().onUserPhotoLoaded(Profile.getPhotoId());
    }

    private void loadUserNameSurname() {
        getViewState().onUserNameSurnameLoaded(Profile.getFullName());
    }

    private void loadUserPhone() {
        getViewState().onUserPhoneLoaded(Profile.getUserPhoneNumber());
    }

    private void subscribeOnProfileUpdate() {
        addSubscription(ContentChangesObservable.profile(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessHandledProfileUpdate, this::onErrorHandledProfileUpdate));
    }

    private void onSuccessHandledProfileUpdate(SyncEvent syncEvent) throws ClassNotFoundException {
        loadProfile();
    }

    private void onErrorHandledProfileUpdate(@NonNull Throwable throwable) {
        LogUtils.debug(Contract.LOG_TAG, throwable.getMessage());
    }
}
