package com.eservia.booking.ui.suggest_business;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;

import moxy.InjectViewState;

@InjectViewState
public class SuggestBusinessPresenter extends BasePresenter<SuggestBusinessView> {

    public SuggestBusinessPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().requiredExtra();
    }

    public void onExtra(boolean modal) {
        if (modal) {
            getViewState().openSuggestBusinessFragmentModal();
        } else {
            getViewState().openSuggestBusinessFragmentStandard();
        }
    }
}
