package com.eservia.booking.ui.booking.resto.thank_you;

import com.eservia.booking.common.presenter.BasePresenter;

import moxy.InjectViewState;

@InjectViewState
public class ThankYouRestoPresenter extends BasePresenter<ThankYouRestoView> {

    public ThankYouRestoPresenter() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void onAcceptClick() {
        getViewState().closeView();
    }
}
