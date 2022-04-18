package com.eservia.booking.ui.suggest_business.thank_you;

import android.content.Context;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.AnalyticsHelper;

import javax.inject.Inject;

import moxy.InjectViewState;

@InjectViewState
public class SuggestBusinessThankYouPresenter extends BasePresenter<SuggestBusinessThankYouView> {

    @Inject
    Context mContext;

    public SuggestBusinessThankYouPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        AnalyticsHelper.logAddSalonThanks(mContext);
    }

    public void onAcceptClick() {
        getViewState().closeView();
    }
}
