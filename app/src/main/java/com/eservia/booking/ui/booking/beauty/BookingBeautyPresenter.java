package com.eservia.booking.ui.booking.beauty;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.interactors.sector.SectorInteractor;
import com.eservia.utils.Contract;
import com.eservia.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class BookingBeautyPresenter extends BasePresenter<BookingBeautyView> {

    @Inject
    SectorInteractor mSectorInteractor;

    @Inject
    BookingStatus mBookingStatus;

    private Business mBusiness = new Business();

    public BookingBeautyPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mBusiness = EventBus.getDefault().getStickyEvent(BookingBeautyExtra.class).business;
        if (mBusiness == null || mBusiness.getAddresses().isEmpty()) {
            return;
        }
        addSubscription(mSectorInteractor
                .getSector(mBusiness.getSectorId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onBusinessSectorLoaded,
                        error -> LogUtils.debug(Contract.LOG_TAG, "Failed to load sector")));

    }

    private void onBusinessSectorLoaded(List<BusinessSector> sectorsList) {
        if (sectorsList.isEmpty()) {
            LogUtils.debug(Contract.LOG_TAG, "Sector was not found");
            return;
        }
        mBusiness.setSector(sectorsList.get(0));

        mBookingStatus.removeBeautyStatus();
        mBookingStatus.setStatus(mBusiness, BookingStatus.BookingType.BEAUTY);

        Address address = EventBus.getDefault().getStickyEvent(BookingBeautyExtra.class).address;
        if (address == null) {
            address = mBusiness.getAddresses().get(0);
        }
        mBookingStatus.getBeautyStatus().setAddress(address);
        showStartPage();
    }

    private void showStartPage() {
        List<Preparation> preparations = EventBus.getDefault().getStickyEvent(BookingBeautyExtra.class).preparations;
        if (preparations.isEmpty()) {
            getViewState().openServiceGroups();
        } else {
            for (Preparation preparation : preparations) {
                mBookingStatus.getBeautyStatus().getPreparations().add(preparation);
            }
            if (preparations.size() == 1) {
                getViewState().openBookingFragment();
            } else {
                getViewState().openBasketSortFragment();
            }
        }
    }
}
