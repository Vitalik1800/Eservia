package com.eservia.booking.ui.suggest_business.modal;

import android.content.Context;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.ui.suggest_business.standart.SuggestBusinessStandartPresenter;
import com.eservia.booking.util.AnalyticsHelper;
import com.eservia.model.prefs.BusinessSuggestion;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business.services.contact.EserviaFeedbackRequest;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class SuggestBusinessModalPresenter extends BasePresenter<SuggestBusinessModalView> {

    @Inject
    RestManager mRestManager;

    @Inject
    Context mContext;

    private Disposable mSuggestDisposable;

    public SuggestBusinessModalPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void onLaterClick() {
        AnalyticsHelper.logAddSalonLaterPopup(mContext);
        getViewState().finish();
    }

    public void onAcceptClick(String name, String city, String address) {
        if (paginationInProgress(mSuggestDisposable)) {
            return;
        }
        AnalyticsHelper.logAddSalonSendPopup(mContext);
        sendSuggestion(name, city, address);
    }

    private void sendSuggestion(String name, String city, String address) {

        getViewState().showProgress();

        EserviaFeedbackRequest request = SuggestBusinessStandartPresenter.composeSuggestRequest(
                mContext, name, city, address);

        mSuggestDisposable = mRestManager
                .sendEserviaContactFeedback(request)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onSendSuggestionSuccess(),
                        this::onSendSuggestionFailed);
        addSubscription(mSuggestDisposable);
    }

    private void onSendSuggestionSuccess() {
        BusinessSuggestion.setDontShowAgain(true);
        getViewState().hideProgress();
        getViewState().onSendSuggestionSuccess();
        getViewState().showSuccess();
        mSuggestDisposable = null;
    }

    private void onSendSuggestionFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onSendSuggestionFailed(throwable);
        mSuggestDisposable = null;
    }
}
