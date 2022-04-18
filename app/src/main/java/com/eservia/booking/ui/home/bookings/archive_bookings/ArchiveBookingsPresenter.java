package com.eservia.booking.ui.home.bookings.archive_bookings;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.remote.rest.RestManager;

import javax.inject.Inject;

import moxy.InjectViewState;

@InjectViewState
public class ArchiveBookingsPresenter extends BasePresenter<ArchiveBookingsView> {

    @Inject
    RestManager mRestManager;

    public ArchiveBookingsPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }
}
