package com.eservia.booking.ui.delivery.resto.address;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.model.entity.RestoDeliverySettlement;
import com.eservia.model.entity.RestoDeliveryStreet;
import com.eservia.model.remote.rest.RestManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class DeliveryAddressPresenter extends BasePresenter<DeliveryAddressView>
        implements DeliveryAddressAdapter.OnAddressClickListener {

    @Inject
    RestManager mRestManager;

    @Inject
    BookingStatus mBookingStatus;

    private Disposable mDisposable;

    private final List<RestoDeliverySettlement> mSettlements = new ArrayList<>();

    private final List<RestoDeliveryStreet> mStreets = new ArrayList<>();

    private final List<DeliveryAddressAdapterItem> mAdapterItems = new ArrayList<>();

    @Nullable
    private Long mSettlementId;

    @DeliveryAddressMode
    private int mMode;

    @Nullable
    private String mSearchQuery;

    public DeliveryAddressPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mMode = EventBus.getDefault().getStickyEvent(DeliveryAddressRestoExtra.class).type;
        RestoDeliverySettlement settlement = mBookingStatus.getDeliveryStatus().getSettlement();
        mSettlementId = settlement != null ? settlement.getId() : null;
        refresh();
    }

    @Override
    public void onAddressItemClicked(DeliveryAddressAdapterItem item, int position) {
        switch (mMode) {
            case DeliveryAddressMode.SETTLEMENT: {
                RestoDeliverySettlement oldSettlement = mBookingStatus.getDeliveryStatus().getSettlement();
                if (oldSettlement != null && !oldSettlement.getId().equals(item.getId())) {
                    mBookingStatus.getDeliveryStatus().setStreet(null);
                }
                RestoDeliverySettlement settlement = new RestoDeliverySettlement();
                settlement.setId(item.getId());
                settlement.setName(item.getTitle());
                mBookingStatus.getDeliveryStatus().setSettlement(settlement);
                getViewState().closePage();
                break;
            }
            case DeliveryAddressMode.STREET: {
                RestoDeliveryStreet street = new RestoDeliveryStreet();
                street.setId(item.getId());
                street.setName(item.getTitle());
                mBookingStatus.getDeliveryStatus().setStreet(street);
                getViewState().closePage();
                break;
            }
        }
    }

    void onSearchQuery(String query) {
        mSearchQuery = query;
        filterList();
    }

    @SuppressLint("CheckResult")
    private void filterList() {
        if (mSearchQuery == null || mSearchQuery.trim().isEmpty()) {
            getViewState().setItems(mAdapterItems);
        }
        Observable.fromIterable(mAdapterItems)
                .filter(item -> item.getTitle().toLowerCase().contains(mSearchQuery.toLowerCase()))
                .toList()
                .toObservable()
                .subscribe(filtered -> getViewState().setItems(filtered));
    }

    private void refresh() {
        mSettlements.clear();
        mStreets.clear();
        mAdapterItems.clear();

        switch (mMode) {
            case DeliveryAddressMode.SETTLEMENT:
                getViewState().setCityTitles();
                loadSettlements();
                break;
            case DeliveryAddressMode.STREET:
                getViewState().setStreetTitles();
                loadStreets();
                break;
        }
    }

    private void initStreets(List<RestoDeliveryStreet> streets) {
        mStreets.clear();
        mStreets.addAll(streets);
        mAdapterItems.clear();
        mAdapterItems.addAll(mapStreetsToAdapterItems(mStreets));
        getViewState().setItems(mAdapterItems);
    }

    private void initSettlements(List<RestoDeliverySettlement> settlements) {
        mSettlements.clear();
        mSettlements.addAll(settlements);
        mAdapterItems.clear();
        mAdapterItems.addAll(mapSettlementsToAdapterItems(mSettlements));
        getViewState().setItems(mAdapterItems);
    }

    private void loadStreets() {
        getViewState().showProgress();
        cancelPagination(mDisposable);
        Observable<List<RestoDeliveryStreet>> observable = mRestManager
                .getDeliveryRestoStreets(mSettlementId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mDisposable = observable.subscribe(this::onStreetsLoaded,
                this::onStreetsLoadingFailed);
        addSubscription(mDisposable);
    }

    private void loadSettlements() {
        getViewState().showProgress();
        cancelPagination(mDisposable);
        Observable<List<RestoDeliverySettlement>> observable = mRestManager
                .getDeliveryRestoSettlements()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mDisposable = observable.subscribe(this::onSettlementsLoaded,
                this::onSettlementsLoadingFailed);
        addSubscription(mDisposable);
    }

    private void onStreetsLoaded(List<RestoDeliveryStreet> streets) {
        getViewState().hideProgress();
        initStreets(streets);
        mDisposable = null;
    }

    private void onStreetsLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().showPageLoadingError();
        mDisposable = null;
    }

    private void onSettlementsLoaded(List<RestoDeliverySettlement> settlements) {
        getViewState().hideProgress();
        initSettlements(settlements);
        mDisposable = null;
    }

    private void onSettlementsLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().showPageLoadingError();
        mDisposable = null;
    }

    private List<DeliveryAddressAdapterItem> mapSettlementsToAdapterItems(
            List<RestoDeliverySettlement> settlements) {
        List<DeliveryAddressAdapterItem> result = new ArrayList<>();
        for (RestoDeliverySettlement settlement : settlements) {
            result.add(new DeliveryAddressAdapterItem(settlement.getId(), settlement.getName()));
        }
        return result;
    }

    private List<DeliveryAddressAdapterItem> mapStreetsToAdapterItems(
            List<RestoDeliveryStreet> streets) {
        List<DeliveryAddressAdapterItem> result = new ArrayList<>();
        for (RestoDeliveryStreet street : streets) {
            result.add(new DeliveryAddressAdapterItem(street.getId(), street.getName()));
        }
        return result;
    }
}
