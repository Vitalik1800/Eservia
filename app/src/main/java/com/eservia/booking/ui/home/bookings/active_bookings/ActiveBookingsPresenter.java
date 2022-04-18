package com.eservia.booking.ui.home.bookings.active_bookings;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings.GeneralActiveBookingsFragment;
import com.eservia.model.remote.rest.RestManager;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import moxy.InjectViewState;

@InjectViewState
public class ActiveBookingsPresenter extends BasePresenter<ActiveBookingsView> {

    @Inject
    Context mContext;

    @Inject
    RestManager mRestManager;

    private final Map<String, Fragment> mFragments = new LinkedHashMap<>();

    public ActiveBookingsPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mFragments.put("", GeneralActiveBookingsFragment.newInstance());
        getViewState().setFragments(mFragments);
    }

    void onArchiveClick() {
        getViewState().showArchiveBookings();
    }
}
