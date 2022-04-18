package com.eservia.booking.ui.delivery.resto;

import androidx.annotation.NonNull;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import moxy.InjectViewState;

@InjectViewState
public class DeliveryPresenter extends BasePresenter<DeliveryView> {

    @Inject
    BookingStatus mBookingStatus;
    @NonNull
    Business business;
    public DeliveryPresenter() {
        App.getAppComponent().inject(this);
        business = new Business();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        business = EventBus.getDefault().getStickyEvent(DeliveryRestoExtra.class).business;
        if (business == null || business.getAddresses().isEmpty()) {
            return;
        }
        Address address = business.getAddresses().get(0);
        mBookingStatus.removeDeliveryStatus();
        mBookingStatus.setStatus(business, BookingStatus.BookingType.DELIVERY);
        mBookingStatus.getDeliveryStatus().setAddress(address);
        getViewState().openBasketFragment();
    }
}
