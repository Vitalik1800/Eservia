package com.eservia.booking.ui.business_page.beauty;

import android.annotation.SuppressLint;
import android.util.Pair;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.event_bus.BusinessFavoriteAdded;
import com.eservia.booking.model.event_bus.BusinessFavoriteRemoved;
import com.eservia.booking.ui.business_page.dialog.PrepaymentSheetDialog;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.CategoryUtil;
import com.eservia.booking.util.SectorUtil;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessCategory;
import com.eservia.model.entity.BusinessPhoto;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.entity.OrderRestoCategory;
import com.eservia.model.entity.RestoBookingSettings;
import com.eservia.model.interactors.resto.RestoBookingDateTimeInteractor;
import com.eservia.model.interactors.sector.SectorInteractor;
import com.eservia.model.local.order_resto.OrderRestoRepository;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business.services.category.BusinessCategoriesResponse;
import com.eservia.model.remote.rest.business.services.gallery.BusinessPhotosResponse;
import com.eservia.utils.Contract;
import com.eservia.utils.LogUtils;

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
public class BusinessPageBeautyPresenter extends BasePresenter<BusinessPageBeautyView> implements
        PrepaymentSheetDialog.Listener {

    private final List<BusinessPhoto> mPhotos = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    @Inject
    RestoBookingDateTimeInteractor mRestoBookingDateTimeInteractor;

    @Inject
    OrderRestoRepository mOrderRestoRepository;

    @Inject
    SectorInteractor mSectorInteractor;

    private Disposable mPhotosPaginationDisposable;

    private Disposable mFavoriteDisposable;

    private Disposable mRestoBasketOrderItemsCountDisposable;

    private boolean mIsAllBusinessPhotosLoaded = false;

    private Business mBusiness;

    private Address mAddress;

    private BusinessSector mBusinessSector;

    private final List<BusinessCategory> mBusinessCategories = new ArrayList<>();

    private RestoBookingSettings mRestoBookingSettings;

    private int mOrderItemsInBasket;

    public BusinessPageBeautyPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mBusiness = EventBus.getDefault().getStickyEvent(Business.class);
        mAddress = !mBusiness.getAddresses().isEmpty() ? mBusiness.getAddresses().get(0) : null;
        getViewState().onBusiness(mBusiness);
        checkBusinessSector();
        refreshBusinessPhotos();
        //TODO: getViewState().showPrepaymentDialog();
    }

    @Override
    public void onPaymentAcceptClick() {
        getViewState().hidePrepaymentDialog();
    }

    @Override
    public void onPaymentLaterClick() {
        getViewState().hidePrepaymentDialog();
    }

    public void loadMoreBusinessPhotos() {
        if (!paginationInProgress(mPhotosPaginationDisposable) && !mIsAllBusinessPhotosLoaded) {
            makePhotosPagination();
        }
    }

    void onResume() {
        if (isRestaurantOrOtherDelivery()) {
            loadRestoBasketOrderItemsCount();
        }
    }

    void onBasketClicked() {
        if (isRestaurantOrOtherDelivery()) {
            getViewState().openRestoDeliveryBasket(mBusiness);
        }
    }

    void onPreparedOptionsMenu() {
        invalidateBasketMenuVisibility();
    }

    void refreshBusinessPhotos() {
        if (mBusiness == null) {
            return;
        }
        mPhotos.clear();
        mIsAllBusinessPhotosLoaded = false;
        makePhotosPagination();
    }

    void onFavoriteClicked() {
        if (mBusiness == null) {
            return;
        }
        if (paginationInProgress(mFavoriteDisposable)) {
            return;
        }
        if (mBusiness.getIs().getFavorited()) {
            deleteFavoriteBusiness();
        } else {
            addBusinessToFavorite();
        }
    }

    void onGoBookingClicked() {
        if (mBusiness.getAddresses().isEmpty()) {
            return;
        }
        getViewState().showBookingBeautyPage(mBusiness);
    }

    void onMenuCategorySelected(OrderRestoCategory category) {
        getViewState().openMenuActivity(mBusiness, category.getId());
    }

    void onRestoDeliveryBasketClicked() {
        if (mOrderItemsInBasket > 0) {
            getViewState().openRestoDeliveryBasket(mBusiness);
        } else {
            getViewState().openMenuActivity(mBusiness, null);
        }
    }

    void onRestoBookTableClicked() {
        getViewState().openRestoTableBooking(mBusiness, mRestoBookingSettings);
    }

    void addBusinessToFavorite() {
        getViewState().disableFavorite();
        mFavoriteDisposable = mRestManager
                .businessAddFavorite(mBusiness.getId())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onAddBusinessToFavoriteSuccess(),
                        this::onAddBusinessToFavoriteFailed);
        addSubscription(mFavoriteDisposable);
    }

    void deleteFavoriteBusiness() {
        getViewState().disableFavorite();
        mFavoriteDisposable = mRestManager
                .businessDeleteFavorite(mBusiness.getId())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onDeleteFavoriteSuccess(),
                        this::onDeleteFavoriteBusinessFailed);
        addSubscription(mFavoriteDisposable);
    }

    private void invalidateBasketMenuVisibility() {
        if (isRestaurantOrOtherDelivery()) {
            getViewState().showBasketMenu();
        } else {
            getViewState().hideBasketMenu();
        }
    }

    private void makePhotosPagination() {
        cancelPagination(mPhotosPaginationDisposable);
        Observable<BusinessPhotosResponse> observable = mRestManager
                .getBusinessPhotos(mBusiness.getId(), null,
                        PART, mPhotos.size() / PART + 1)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        mPhotosPaginationDisposable = observable.subscribe(this::onPhotosLoadingSuccess,
                this::onPhotosLoadingFailed);
        addSubscription(mPhotosPaginationDisposable);
    }

    private void onPhotosLoadingSuccess(BusinessPhotosResponse response) {
        mPhotos.addAll(response.getData());
        getViewState().onPhotosLoadingSuccess(mBusiness, mPhotos);
        mPhotosPaginationDisposable = null;
        if (mPhotos.size() == response.getMeta().getTotal()) {
            mIsAllBusinessPhotosLoaded = true;
        }
    }

    private void onPhotosLoadingFailed(Throwable throwable) {
        getViewState().onPhotosLoadingFailed(throwable);
        mPhotosPaginationDisposable = null;
    }

    private void onAddBusinessToFavoriteSuccess() {
        BusinessUtil.favoriteAdded(mBusiness);
        getViewState().onAddBusinessToFavoriteSuccess();
        getViewState().enableFavorite();
        EventBus.getDefault().post(new BusinessFavoriteAdded(mBusiness, mBusinessSector));
        mFavoriteDisposable = null;
    }

    private void onAddBusinessToFavoriteFailed(Throwable throwable) {
        getViewState().onAddBusinessToFavoriteFailed(throwable);
        getViewState().enableFavorite();
        mFavoriteDisposable = null;
    }

    private void onDeleteFavoriteSuccess() {
        BusinessUtil.favoriteRemoved(mBusiness);
        getViewState().onDeleteFavoriteSuccess();
        getViewState().enableFavorite();
        EventBus.getDefault().post(new BusinessFavoriteRemoved(mBusiness, mBusinessSector));
        mFavoriteDisposable = null;
    }

    private void onDeleteFavoriteBusinessFailed(Throwable throwable) {
        getViewState().onDeleteFavoriteBusinessFailed(throwable);
        getViewState().enableFavorite();
        mFavoriteDisposable = null;
    }

    @SuppressLint("CheckResult")
    private void checkBusinessSector() {
        Observable<List<BusinessSector>> sectorsObservable =
                mSectorInteractor.getSector(mBusiness.getSectorId());
        Observable<BusinessCategoriesResponse> categoriesObservable =
                mRestManager.getCategoriesByBusiness(mBusiness.getId(), 50, 1);

        Observable.zip(sectorsObservable, categoriesObservable, (sectors, categories) -> new Pair<>(sectors, categories.getData()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pair -> {
                            BusinessSector sector = !pair.first.isEmpty() ? pair.first.get(0) : null;
                            mBusinessSector = sector;
                            mBusinessCategories.clear();
                            mBusinessCategories.addAll(pair.second);
                            mBusiness.setSector(mBusinessSector);
                            invalidateBasketMenuVisibility();
                            if (sector == null) {
                                LogUtils.debug(Contract.LOG_TAG, "Sector was not found");
                                return;
                            }
                            if (SectorUtil.isBeauty(sector.getSector())) {
                                getViewState().createBeautyFragments(hasManyDepartments());
                                if (!hasManyDepartments()) {
                                    getViewState().showBeautyButtons();
                                }

                            } else if (SectorUtil.isRestaurant(sector.getSector())) {
                                getViewState().createRestoFragments();
                                getViewState().hideAllButtons();
                                loadRestoBasketOrderItemsCount();
                                loadRestoBookingSettings();

                            } else if (SectorUtil.isHealth(sector.getSector())) {
                                getViewState().createBeautyFragments(hasManyDepartments());
                                if (!hasManyDepartments()) {
                                    getViewState().showBeautyButtons();
                                }

                            } else if (SectorUtil.isOther(sector.getSector())) {
                                if (CategoryUtil.isBeautyBookingType(pair.second)) {
                                    getViewState().createBeautyFragments(hasManyDepartments());
                                    if (!hasManyDepartments()) {
                                        getViewState().showBeautyButtons();
                                    }
                                } else if (CategoryUtil.isRestoDeliveryType(pair.second)) {
                                    getViewState().createOtherDeliveryFragments();
                                    getViewState().hideAllButtons();
                                    loadRestoBasketOrderItemsCount();
                                    loadRestoBookingSettings();
                                } else {
                                    getViewState().hideAllButtons();
                                }

                            } else {
                                getViewState().hideAllButtons();
                            }
                        },
                        e -> {
                            getViewState().hideAllButtons();
                            LogUtils.debug(Contract.LOG_TAG, "Failed to load sector");
                        });
    }

    private boolean hasManyDepartments() {
        return mBusiness != null
                && mBusiness.getAddresses() != null
                && mBusiness.getAddresses().size() > 1;
    }

    private void loadRestoBookingSettings() {
        getViewState().showProgress();
        Observable<RestoBookingSettings> observable = mRestoBookingDateTimeInteractor
                .getRestoBookingSettings(Long.valueOf(mAddress.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        addSubscription(observable.subscribe(this::onRestoBookingSettingsLoaded,
                this::onRestoBookingSettingsLoadingFailed));
    }

    private void onRestoBookingSettingsLoaded(RestoBookingSettings bookingSettings) {
        getViewState().hideProgress();
        if (CategoryUtil.isRestoDeliveryType(mBusinessCategories)) {
            getViewState().showOtherDeliveryButtons();
        } else {
            getViewState().showRestoButtons();
        }
        initRestoBookingSettings(bookingSettings);
    }

    private void onRestoBookingSettingsLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().hideAllButtons();
    }

    private void initRestoBookingSettings(RestoBookingSettings bookingSettings) {
        mRestoBookingSettings = bookingSettings;
        if (!mRestoBookingSettings.getBookingIsAllowed()) {
            getViewState().hideRestoBookingButton();
        }
    }

    private void loadRestoBasketOrderItemsCount() {
        if (paginationInProgress(mRestoBasketOrderItemsCountDisposable)) {
            return;
        }
        Observable<Integer> observable = mOrderRestoRepository
                .orderItemsForBusinessIdCount(mBusiness.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mRestoBasketOrderItemsCountDisposable = observable.subscribe(
                this::onRestoBasketOrderItemsCountLoaded,
                this::onRestoBasketOrderItemsCountLoadingFailed);
        addSubscription(mRestoBasketOrderItemsCountDisposable);
    }

    private void onRestoBasketOrderItemsCountLoaded(Integer basketOrderItemsCount) {
        mOrderItemsInBasket = basketOrderItemsCount;
        getViewState().refreshBasketState(basketOrderItemsCount);
        mRestoBasketOrderItemsCountDisposable = null;
    }

    private void onRestoBasketOrderItemsCountLoadingFailed(Throwable throwable) {
        mRestoBasketOrderItemsCountDisposable = null;
    }

    private boolean isRestaurantOrOtherDelivery() {
        return mBusinessSector != null &&
                (
                        (SectorUtil.isRestaurant(mBusinessSector.getSector()))
                                || (SectorUtil.isOther(mBusinessSector.getSector()) && CategoryUtil.isRestoDeliveryType(mBusinessCategories))
                );
    }
}
