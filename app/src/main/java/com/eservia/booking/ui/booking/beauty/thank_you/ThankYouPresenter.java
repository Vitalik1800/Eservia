package com.eservia.booking.ui.booking.beauty.thank_you;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.remote.rest.RestManager;

import javax.inject.Inject;

import moxy.InjectViewState;

@InjectViewState
public class ThankYouPresenter extends BasePresenter<ThankYouView> {

    @Inject
    RestManager mRestManager;

    public ThankYouPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void onAcceptClick() {
        getViewState().closeView();
    }
}
