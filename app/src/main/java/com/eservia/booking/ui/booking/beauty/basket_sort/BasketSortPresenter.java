package com.eservia.booking.ui.booking.beauty.basket_sort;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.model.remote.rest.RestManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import moxy.InjectViewState;

@InjectViewState
public class BasketSortPresenter extends BasePresenter<BasketSortView> implements
        BasketSortAdapter.OnPreparationsClickListener {

    private final List<Preparation> mPreparations = new ArrayList<>();

    private final List<BasketSortAdapterItem> mPreparationItems = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    @Inject
    BookingStatus mBookingStatus;

    private boolean mIsEditMode = false;

    public BasketSortPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showSelectedAddress(mBookingStatus.getBeautyStatus().getAddress());

        mPreparations.addAll(preparations());

        mPreparationItems.addAll(mapToAdapterItems(mPreparations, mIsEditMode));

        getViewState().onPreparations(mPreparationItems);
    }

    @Override
    public void onPreparationClick(BasketSortAdapterItem preparationItem) {
    }

    @Override
    public void onDeletePreparationClick(BasketSortAdapterItem preparationItem) {
    }

    public void onAcceptClick(List<BasketSortAdapterItem> adapterItems) {
        updatePreparations(adapterItems);

        invalidateBookingStatusPreparations();

        if (preparations().size() > 0) {
            getViewState().showBookingFragment();
        }
    }

    public void onGoBackClick(List<BasketSortAdapterItem> adapterItems) {
        updatePreparations(adapterItems);

        invalidateBookingStatusPreparations();

        getViewState().goBack();
    }

    public void editModeEnabled(boolean enabled) {
        if (!enabled || preparations().size() > 0) {

            mIsEditMode = enabled;

            getViewState().onEditMode(mIsEditMode);
            getViewState().onPreparations(mapToAdapterItems(mPreparations, mIsEditMode));

            if (!mIsEditMode) {
                invalidateBookingStatusPreparations();
            }
        }
    }

    private void invalidateBookingStatusPreparations() {
        mBookingStatus.getBeautyStatus().getPreparations().clear();
        mBookingStatus.getBeautyStatus().getPreparations().addAll(mPreparations);
        onStatusPreparationsUpdated();
    }

    private void onStatusPreparationsUpdated() {
        getViewState().onPreparations(mapToAdapterItems(mPreparations, mIsEditMode));
    }

    private void updatePreparations(List<BasketSortAdapterItem> adapterItems) {
        if (nothingWasChanged(adapterItems, mPreparations)) {
            return;
        }
        mPreparations.clear();
        for (BasketSortAdapterItem item : adapterItems) {
            Preparation preparation = item.getPreparation();
            clearPreparationDetails(preparation);
            mPreparations.add(item.getPreparation());
        }
    }

    private boolean nothingWasChanged(List<BasketSortAdapterItem> adapterItems,
                                      List<Preparation> preparations) {

        if (adapterItems.size() != preparations.size()) {
            return false;
        }
        for (int i = 0; i < adapterItems.size(); i++) {
            Preparation p1 = adapterItems.get(i).getPreparation();
            Preparation p2 = preparations.get(i);
            if (!p1.equals(p2)) {
                return false;
            }
        }
        return true;
    }

    private List<Preparation> preparations() {
        return mBookingStatus.getBeautyStatus().getPreparations();
    }

    private List<BasketSortAdapterItem> mapToAdapterItems(
            List<Preparation> preparations, boolean editMode) {

        List<BasketSortAdapterItem> result = new ArrayList<>();

        for (Preparation p : preparations) {
            result.add(new BasketSortAdapterItem(p, editMode));
        }
        return result;
    }

    private void clearPreparationDetails(Preparation preparation) {
        preparation.setStaff(null);
        preparation.setDay(null);
        preparation.setTimeSlot(null);
        preparation.setDiscount(null);
    }
}
