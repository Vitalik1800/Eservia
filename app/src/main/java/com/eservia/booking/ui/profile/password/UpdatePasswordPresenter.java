package com.eservia.booking.ui.profile.password;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.users.services.profile.ChangePassRequest;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class UpdatePasswordPresenter extends BasePresenter<UpdatePasswordView> {

    @Inject
    RestManager mRestManager;

    private Disposable mPasswordDisposable;

    public UpdatePasswordPresenter() {
        App.getAppComponent().inject(this);
    }

    void onChangePassword(String currentPass, String newPass) {
        if (paginationInProgress(mPasswordDisposable)) {
            return;
        }
        changePassword(currentPass, newPass);
    }

    private void changePassword(String currentPass, String newPass) {
        getViewState().showProgress();
        ChangePassRequest request = new ChangePassRequest();
        mPasswordDisposable = mRestManager
                .changePassword(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onPasswordUpdated(), this::onPasswordUpdateFailed);
        addSubscription(mPasswordDisposable);
    }

    private void onPasswordUpdated() {
        getViewState().hideProgress();
        getViewState().onPasswordUpdated();
        getViewState().closeActivity();
        mPasswordDisposable = null;
    }

    private void onPasswordUpdateFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onPasswordUpdateFailed(throwable);
        mPasswordDisposable = null;
    }
}
