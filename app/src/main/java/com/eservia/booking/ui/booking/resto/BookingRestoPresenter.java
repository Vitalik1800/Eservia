package com.eservia.booking.ui.booking.resto;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.RestoBookingSettings;
import com.eservia.model.remote.rest.RestManager;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import moxy.InjectViewState;

@InjectViewState
public class BookingRestoPresenter extends BasePresenter<BookingRestoView> {

    @Inject
    RestManager mRestManager;

    @Inject
    BookingStatus mBookingStatus;

    public BookingRestoPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Business mBusiness = EventBus.getDefault().getStickyEvent(BookingRestoExtra.class).business;
        RestoBookingSettings mBookingSettings = EventBus.getDefault().getStickyEvent(BookingRestoExtra.class).bookingSettings;

        if (mBusiness == null || mBusiness.getAddresses().isEmpty()) {
            return;
        }
        // TODO: make ability to choose address if  addresses size > 1
        Address address = mBusiness.getAddresses().get(0);
        mBookingStatus.removeRestoStatus();
        mBookingStatus.setStatus(mBusiness, BookingStatus.BookingType.RESTO);
        mBookingStatus.getRestoStatus().setAddress(address);
        mBookingStatus.getRestoStatus().setBookingSettings(mBookingSettings);
        getViewState().openDateTimeSelectFragment();
    }
}
