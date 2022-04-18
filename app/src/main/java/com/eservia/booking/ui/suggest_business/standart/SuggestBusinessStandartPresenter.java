package com.eservia.booking.ui.suggest_business.standart;

import android.content.Context;

import com.eservia.booking.App;
import com.eservia.booking.R;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.AnalyticsHelper;
import com.eservia.booking.util.ContactUtil;
import com.eservia.model.prefs.Profile;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business.services.contact.EserviaFeedbackRequest;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class SuggestBusinessStandartPresenter extends BasePresenter<SuggestBusinessStandartView> {

    @Inject
    RestManager mRestManager;

    @Inject
    Context mContext;

    private Disposable mSuggestDisposable;

    public SuggestBusinessStandartPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void onAcceptClick(String name, String city, String address) {
        if (paginationInProgress(mSuggestDisposable)) {
            return;
        }
        AnalyticsHelper.logAddSalonSend(mContext);
        sendSuggestion(name, city, address);
    }

    private void sendSuggestion(String name, String city, String address) {

        getViewState().showProgress();

        EserviaFeedbackRequest request = composeSuggestRequest(mContext, name, city, address);

        mSuggestDisposable = mRestManager
                .sendEserviaContactFeedback(request)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onSendSuggestionSuccess(),
                        this::onSendSuggestionFailed);
        addSubscription(mSuggestDisposable);
    }

    private void onSendSuggestionSuccess() {
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

    public static EserviaFeedbackRequest composeSuggestRequest(Context context,
                                                               String name, String city,
                                                               String address) {

        EserviaFeedbackRequest request = new EserviaFeedbackRequest();

        request.setName(Profile.getFullName());

        request.setPhone(Profile.getUserPhoneNumber());

        request.setEmail(Profile.getUserEmail() != null ?
                Profile.getUserEmail() : ContactUtil.DEFAULT_EMAIL);

        request.setSubject(context.getString(R.string.business_suggestion_request_theme,
                Profile.getUserPhoneNumber(), Profile.getFullName()));

        request.setMessage(context.getString(R.string.business_suggestion_request_message,
                name, city, address));

        return request;
    }
}
