package com.eservia.booking.ui.delivery.resto.thank_you;

import com.eservia.booking.common.presenter.BasePresenter;

import moxy.InjectViewState;

@InjectViewState
public class ThankYouRestoDeliveryPresenter extends BasePresenter<ThankYouRestoDeliveryView> {

    public ThankYouRestoDeliveryPresenter() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void onAcceptClick() {
        getViewState().closeView();
    }
}
