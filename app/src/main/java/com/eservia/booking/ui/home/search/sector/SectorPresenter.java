package com.eservia.booking.ui.home.search.sector;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.common.view.OnPaginationListener;
import com.eservia.booking.model.city.CityStatus;
import com.eservia.booking.model.event_bus.EventFineLocationNotGranted;
import com.eservia.booking.model.event_bus.EventFineLocationResult;
import com.eservia.booking.ui.home.search.search.sheet.SheetAdapterItem;
import com.eservia.booking.util.SectorUtil;
import com.eservia.model.entity.BusinessCity;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.entity.Gender;
import com.eservia.model.interactors.sector.SectorInteractor;
import com.eservia.model.interactors.sector.SectorModel;
import com.eservia.model.prefs.Profile;
import com.eservia.utils.SortUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class SectorPresenter extends BasePresenter<SectorView> implements OnPaginationListener,
        SectorAdapter.OnSectorClickListener {

    private final List<BusinessSector> mSectors = new ArrayList<>();

    private final List<SheetAdapterItem> mCitiesAdapterItems = new ArrayList<>();

    @Inject
    SectorInteractor mSectorInteractor;

    @Inject
    CityStatus mCityStatus;

    private Disposable mPaginationDisposable;

    private boolean mIsAllLoaded = false;

    private boolean mWasReceivedLocationResult = false;

    private boolean mIsReadyToRedirectForward = false;

    private boolean mIsViewVisible = false;

    public SectorPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        EventBus.getDefault().register(this);
        getViewState().showSelectedCity(selectedCityKey());
        refresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void loadMore() {
        if (!paginationInProgress(mPaginationDisposable) && !mIsAllLoaded) {
            makePagination();
        }
    }

    @Override
    public void onSectorClick(BusinessSector sector) {
        getViewState().goToSearch(sector);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFineLocationPermissionGrantedEvent(EventFineLocationResult event) {
        if (mWasReceivedLocationResult) {
            return;
        }
        mWasReceivedLocationResult = true;
        mIsReadyToRedirectForward = true;
        mCityStatus.getCities().clear();
        mCityStatus.setSelectedCity(null);
        refresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventFineLocationNotGranted(EventFineLocationNotGranted event) {
        onMayRedirectForward();
    }

    void onResume() {
        mIsViewVisible = true;
        String city = mCityStatus.getSelectedCity() != null ?
                mCityStatus.getSelectedCity().getCity() : null;
        for (SheetAdapterItem item : mCitiesAdapterItems) {
            if (item.getKey().equals(city)) {
                onCitySelected(item, false);
                return;
            }
        }
        refresh();
    }

    void onStop() {
        mIsViewVisible = false;
    }

    void onDialogCitySelected(SheetAdapterItem<BusinessCity> itemClicked) {
        onCitySelected(itemClicked, true);
    }

    void onShowCitySelection() {
        getViewState().showCitiesDialog(mCitiesAdapterItems);
    }

    void onPointsClick() {
        getViewState().goToPoints();
    }

    void onDiscountsClick() {
        getViewState().goToDiscounts();
    }

    void onWalletClick() {
        getViewState().goToWallet();
    }

    void refresh() {
        cancelPagination(mPaginationDisposable);
        mSectors.clear();
        mIsAllLoaded = false;
        makePagination();
    }

    private void makePagination() {
        if (mSectors.isEmpty()) {
            getViewState().showProgress();
        }
        cancelPagination(mPaginationDisposable);
        Observable<List<SectorModel>> observable = Observable.just(mCityStatus.getCities())
                .flatMap(businessCities -> {
                    if (!mCityStatus.getCities().isEmpty()) {
                        return Observable.just(mCityStatus.getCities());
                    }
                    return mSectorInteractor.getBusinessCities(SortUtil.distanceAsc())
                            .flatMap(responseCities -> {
                                mCityStatus.getCities().addAll(responseCities);
                                mCityStatus.setSelectedCity(mCityStatus.getCities().get(0));
                                return Observable.just(mCityStatus.getCities());
                            });
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(businessCities -> fillCitiesAdapterItems())
                .observeOn(Schedulers.io())
                .flatMap(businessCities -> mSectorInteractor.getSectors(SectorUtil.getSectorsKeys(), selectedCityKey()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mPaginationDisposable = observable.subscribe(this::onSectorsLoadingSuccess, this::onSectorsLoadingFailed);
        addSubscription(mPaginationDisposable);
    }

    private void onSectorsLoadingSuccess(List<SectorModel> sectorModels) {
        mSectors.addAll(filterSectorModels(sectorModels));
        getViewState().hideProgress();
        getViewState().onSectorsLoadingSuccess(mSectors, Profile.getGender() == Gender.MALE);
        getViewState().showSelectedCity(selectedCityKey());
        if (mIsReadyToRedirectForward) {
            onMayRedirectForward();
            mIsReadyToRedirectForward = false;
        }
        mPaginationDisposable = null;
        mIsAllLoaded = true;
    }

    private void onSectorsLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onSectorsLoadingFailed(throwable);
        mPaginationDisposable = null;
    }

    private void onMayRedirectForward() {
        if (mIsViewVisible && mSectors.size() == 1) {
            getViewState().goToSearch(mSectors.get(0));
        }
    }

    private void onCitySelected(SheetAdapterItem<BusinessCity> itemClicked, boolean shouldRedirect) {
        checkCity(itemClicked, true);
        getViewState().hideSheetDialog();
        getViewState().showSelectedCity(selectedCityKey());
        mIsReadyToRedirectForward = shouldRedirect;
        refresh();
    }

    private List<BusinessSector> filterSectorModels(List<SectorModel> sectorModels) {
        List<BusinessSector> businessSectors = new ArrayList<>();
        for (SectorModel model : sectorModels) {
            if (model.hasBusinesses()) {
                businessSectors.add(model.getSector());
            }
        }
        return businessSectors;
    }

    private List<BusinessSector> mapToSectors(List<SectorModel> sectorModels) {
        List<BusinessSector> businessSectors = new ArrayList<>();
        for (SectorModel model : sectorModels) {
            businessSectors.add(model.getSector());
        }
        return businessSectors;
    }

    private String selectedCityKey() {
        return mCityStatus.getSelectedCity() != null ?
                mCityStatus.getSelectedCity().getCity() :
                null;
    }

    private void checkCity(SheetAdapterItem<BusinessCity> item, boolean check) {
        for (SheetAdapterItem i : mCitiesAdapterItems) {
            if (i.getKey().equals(item.getKey())) {
                i.setChecked(check);
            } else {
                i.setChecked(false);
            }
        }
        if (check) {
            mCityStatus.setSelectedCity(item.getModel());
        }
    }

    private void fillCitiesAdapterItems() {
        mCitiesAdapterItems.clear();

        for (BusinessCity city : mCityStatus.getCities()) {
            String selectedCityKey = selectedCityKey();
            boolean isSameCity = selectedCityKey != null && selectedCityKey.equals(city.getCity());

            SheetAdapterItem<BusinessCity> adapterItem = new SheetAdapterItem<>(city.getCity(),
                    null,
                    city.getCity(),
                    isSameCity,
                    city);
            mCitiesAdapterItems.add(adapterItem);
        }
    }
}
