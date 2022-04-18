package com.eservia.booking.ui.home.favorite.favorite;

import android.content.Context;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.AnalyticsHelper;
import com.eservia.model.remote.rest.RestManager;

import javax.inject.Inject;

import moxy.InjectViewState;

@InjectViewState
public class FavoritePresenter extends BasePresenter<FavoriteView> {

    @Inject
    RestManager mRestManager;

    @Inject
    Context mContext;

    public FavoritePresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void onSuggestClicked() {
        AnalyticsHelper.logAddSalonButton(mContext);
        getViewState().showBusinessSuggestion();
    }
}
