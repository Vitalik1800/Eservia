package com.eservia.booking.ui.home.search.search;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;

import com.eservia.booking.App;
import com.eservia.booking.R;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.common.view.OnPaginationListener;
import com.eservia.booking.model.city.CityStatus;
import com.eservia.booking.model.event_bus.BusinessFavoriteAdded;
import com.eservia.booking.model.event_bus.BusinessFavoriteRemoved;
import com.eservia.booking.model.event_bus.EventFineLocationResult;
import com.eservia.booking.ui.home.search.search.sheet.SheetAdapterItem;
import com.eservia.booking.util.AnalyticsHelper;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.SectorUtil;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessCategory;
import com.eservia.model.entity.BusinessCity;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.interactors.business.BusinessSearchableHelper;
import com.eservia.model.interactors.sector.SectorInteractor;
import com.eservia.model.interactors.sector.SectorModel;
import com.eservia.model.prefs.Profile;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business.services.business.BusinessCategories;
import com.eservia.model.remote.rest.business.services.category.BusinessCategoriesResponse;
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
public class SearchPresenter extends BasePresenter<SearchView> implements
        OnPaginationListener,
        CategoryAdapter.OnCategoryItemClickListener,
        HorizontalBusinessesListAdapter.OnBusinessClickListener,
        HorizontalBusinessesListAdapter.PaginationListener {

    private final List<Business> mBusinesses = new ArrayList<>();

    private final List<Business> mPopularBusinesses = new ArrayList<>();

    private final List<BusinessCategory> mCategories = new ArrayList<>();

    private final List<CategoryAdapterItem> mCategoryAdapterItems = new ArrayList<>();

    private final List<SheetAdapterItem> mSortAdapterItems = new ArrayList<>();

    private final List<SheetAdapterItem> mCitiesAdapterItems = new ArrayList<>();

    private final String DEFAULT_SORT = SortUtil.createdAtDesc();

    private final String SORT_NAME = SortUtil.nameAsc();

    private final String SORT_RATING = SortUtil.ratingDesc();

    private final String SORT_COMMENT = SortUtil.commentsDesc();

    private final String SORT_DISTANCE = SortUtil.distanceAsc();

    @Inject
    RestManager mRestManager;

    @Inject
    SectorInteractor mSectorInteractor;

    @Inject
    Context mContext;

    @Inject
    CityStatus mCityStatus;

    private Disposable mBusinessesPaginationDisposable;

    private Disposable mPopularBusinessesPaginationDisposable;

    private Disposable mCategoriesPaginationDisposable;

    private Disposable mSectorsPaginationDisposable;

    private boolean mIsAllBusinessesLoaded = false;

    private boolean mIsAllPopularBusinessesLoaded = false;

    private BusinessSector mSector;

    private final BusinessCategories mFilterCategories = new BusinessCategories();

    private String mSort = SortUtil.nameAsc();

    private String mQuery;

    private boolean mLoadMoreIntervalTimerFinished = true;

    private boolean mRequestedFineLocationPermission = false;

    public SearchPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        EventBus.getDefault().register(this);
        mSector = EventBus.getDefault().getStickyEvent(BusinessSector.class);
        loadCities();
        fillSortAdapterItems();
        getViewState().requiredArgs();
        getViewState().showSelectedCity(selectedCityKey());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBusinessFavoriteAdded(BusinessFavoriteAdded event) {
        List<Business> businesses = findBusinessesWithIdInLoadedList(event.business.getId());
        BusinessUtil.favoriteAdded(businesses);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBusinessFavoriteRemoved(BusinessFavoriteRemoved event) {
        List<Business> businesses = findBusinessesWithIdInLoadedList(event.business.getId());
        BusinessUtil.favoriteRemoved(businesses);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFineLocationPermissionGrantedEvent(EventFineLocationResult event) {
        if (mRequestedFineLocationPermission) {
            mRequestedFineLocationPermission = false;
            checkSortType(SORT_DISTANCE, true);
            mCityStatus.getCities().clear();
            mCityStatus.setSelectedCity(null);
            refreshBusinesses();
            refreshPopularBusinesses();
        }
    }

    @Override
    public void loadMore() {
        if (!paginationInProgress(mBusinessesPaginationDisposable) && !mIsAllBusinessesLoaded) {
            makeBusinessesPagination();
            if (mLoadMoreIntervalTimerFinished) {
                mLoadMoreIntervalTimerFinished = false;
                new LoadMoreIntervalTimer(900, 900).start();
                new Handler().post(() -> getViewState().onBusinessesPaginationStarted());
            }
        }
    }

    @Override
    public void loadMoreMostPopularBusinesses() {
        if (!paginationInProgress(mPopularBusinessesPaginationDisposable) && !mIsAllPopularBusinessesLoaded) {
            makePopularBusinessesPagination();
        }
    }

    @Override
    public void onHorizontalBusinessClick(Business business) {
        onSelectBusiness(business);
    }

    @Override
    public void onCategoryItemClick(CategoryAdapterItem adapterItem) {
        boolean selected = !adapterItem.isSelected();
        getViewState().setSelectedCategory(selected, adapterItem.getCategory().getId());
        for (CategoryAdapterItem item : mCategoryAdapterItems) {
            if (item.getCategory().getId().equals(adapterItem.getCategory().getId())) {
                item.setSelected(selected);
            }
        }
        if (selected) {
            mFilterCategories.getKeys().add(adapterItem.getCategory().getId());
        } else {
            mFilterCategories.getKeys().remove(adapterItem.getCategory().getId());
        }
        refreshBusinesses();
    }

    void onSortTypeSelected(SheetAdapterItem itemClicked) {
        if (itemClicked.getKey().equals(SORT_DISTANCE)
                && !itemClicked.isChecked()
                && !Profile.isUserLocationKnown()) {
            mRequestedFineLocationPermission = true;
            getViewState().requestFineLocationPermission();
            getViewState().hideSheetDialog();
            return;
        }
        mRequestedFineLocationPermission = false;
        boolean checked = !itemClicked.isChecked();
        checkSortType(itemClicked.getKey(), checked);
        getViewState().hideSheetDialog();
        refreshBusinesses();
    }

    void onCitySelected(SheetAdapterItem<BusinessCity> itemClicked) {
        String prevSelectedCityKey = selectedCityKey();

        checkCity(itemClicked, true);
        getViewState().hideSheetDialog();
        getViewState().showSelectedCity(selectedCityKey());

        if (prevSelectedCityKey != null && !prevSelectedCityKey.equals(itemClicked.getKey())) {
            loadSectorsByCity();
        } else {
            refreshBusinesses();
            refreshPopularBusinesses();
        }
    }

    void onSuggestBusinessClick() {
        AnalyticsHelper.logAddSalonButton(mContext);
        getViewState().showSuggestBusinessActivity();
    }

    void onShowSortDialog() {
        getViewState().showSortDialog(mSortAdapterItems);
    }

    void onShowCitySelection() {
        getViewState().showCitiesDialog(mCitiesAdapterItems);
    }

    void onShowNearestMapClicked() {
        getViewState().openSearchBusinessesMap(mBusinesses, mSector);
    }

    void onArgs(boolean startFromSearch) {
        if (startFromSearch) {
            getViewState().requestFocus();
        }
        refreshAll();
    }

    void onRefresh() {
        if (mCategories.isEmpty()) {
            refreshCategories();
        }
        if (mPopularBusinesses.isEmpty()) {
            refreshPopularBusinesses();
        }
        refreshBusinesses();
    }

    void onQuery(String query) {
        mQuery = query;
        refreshBusinesses();
    }

    private void refreshAll() {
        refreshBusinesses();
        refreshCategories();
        refreshPopularBusinesses();
    }

    private void refreshBusinesses() {
        if (paginationInProgress(mBusinessesPaginationDisposable)) {
            cancelPagination(mBusinessesPaginationDisposable);
            getViewState().onBusinessesPaginationFinished();
        }
        mBusinesses.clear();
        mIsAllBusinessesLoaded = false;
        makeBusinessesPagination();
    }

    private void refreshPopularBusinesses() {
        if (paginationInProgress(mPopularBusinessesPaginationDisposable)) {
            cancelPagination(mPopularBusinessesPaginationDisposable);
        }
        mPopularBusinesses.clear();
        mIsAllPopularBusinessesLoaded = false;
        makePopularBusinessesPagination();
    }

    public void refreshCategories() {
        if (!paginationInProgress(mCategoriesPaginationDisposable)) {
            mCategories.clear();
            makeCategoriesPagination();
        }
    }

    void onSelectBusiness(Business business) {
        getViewState().showBusinessBeautyActivity(business);
    }

    void onReserveClick(Business business) {
        if (business.getAddresses().isEmpty()) {
            return;
        }
        getViewState().showBookingBeautyActivity(business);
    }

    private void makeBusinessesPagination() {
        if (mBusinesses.size() == 0) {
            getViewState().showProgress();
        }
        cancelPagination(mBusinessesPaginationDisposable);
        Observable<List<Business>> observable = mRestManager.getBusinessesWithAddresses(mQuery,
                null,
                String.valueOf(mSector.getId()),
                String.valueOf(Business.STATUS_ACTIVE),
                null,
                mFilterCategories,
                mSort,
                Business.WITHOUT_TRASHED,
                selectedCityKey(),
                PART,
                mBusinesses.size() / PART + 1,
                BusinessSearchableHelper.getSearchableQuery())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mBusinessesPaginationDisposable = observable.subscribe(this::onBusinessesLoadingSuccess,
                this::onBusinessesLoadingFailed);
        addSubscription(mBusinessesPaginationDisposable);
    }

    private void makePopularBusinessesPagination() {
        cancelPagination(mPopularBusinessesPaginationDisposable);
        Observable<List<Business>> observable = mRestManager.getBusinessesWithAddresses(null,
                null,
                String.valueOf(mSector.getId()),
                String.valueOf(Business.STATUS_ACTIVE),
                null,
                null,
                SORT_RATING,
                Business.WITHOUT_TRASHED,
                selectedCityKey(),
                PART,
                mPopularBusinesses.size() / PART + 1,
                BusinessSearchableHelper.getSearchableQuery())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mPopularBusinessesPaginationDisposable = observable.subscribe(this::onPopularBusinessesLoadingSuccess,
                this::onPopularBusinessesLoadingFailed);
        addSubscription(mPopularBusinessesPaginationDisposable);
    }

    private void makeCategoriesPagination() {
        if (mCategories.size() == 0) {
            getViewState().showCategoriesProgress();
        }
        cancelPagination(mCategoriesPaginationDisposable);
        Observable<BusinessCategoriesResponse> observable = mRestManager
                .getCategories(null,
                        String.valueOf(mSector.getId()),
                        BusinessCategory.STATUS_ACTIVE,
                        null,
                        PART,
                        mCategories.size() / PART + 1)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        mCategoriesPaginationDisposable = observable.subscribe(this::onCategoriesLoadingSuccess,
                this::onCategoriesLoadingFailed);
        addSubscription(mCategoriesPaginationDisposable);
    }

    private void loadSectorsByCity() {
        cancelPagination(mSectorsPaginationDisposable);
        getViewState().showProgress();
        Observable<List<SectorModel>> observable = mSectorInteractor.getSectors(SectorUtil.getSectorsKeys(), selectedCityKey())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mSectorsPaginationDisposable = observable.subscribe(sectorModels -> {
                    BusinessSector sectorWithBusinesses = null;
                    int sectorsWithBusinessesCount = 0;
                    for (SectorModel model : sectorModels) {
                        if (model.hasBusinesses()) {
                            sectorsWithBusinessesCount++;
                            sectorWithBusinesses = model.getSector();
                        }
                    }
                    if (sectorsWithBusinessesCount == 1 && sectorWithBusinesses != null) {
                        mSector = sectorWithBusinesses;
                        mFilterCategories.getKeys().clear();
                        refreshAll();
                    } else {
                        getViewState().goBack();
                    }
                    getViewState().hideProgress();
                    mSectorsPaginationDisposable = null;
                },
                error -> {
                    getViewState().hideProgress();
                    mSectorsPaginationDisposable = null;
                });
        addSubscription(mSectorsPaginationDisposable);
    }

    private void onBusinessesLoadingSuccess(List<Business> entityList) {
        mBusinesses.addAll(entityList);
        getViewState().hideProgress();
        getViewState().onBusinessesPaginationFinished();
        getViewState().onBusinessesLoadingSuccess(mBusinesses, mSector);
        getViewState().showSelectedCity(selectedCityKey());
        mBusinessesPaginationDisposable = null;
        if (entityList.size() != PART) {
            mIsAllBusinessesLoaded = true;
        }
    }

    private void onBusinessesLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onBusinessesPaginationFinished();
        getViewState().onBusinessesLoadingFailed(throwable);
        mBusinessesPaginationDisposable = null;
    }

    private void onPopularBusinessesLoadingSuccess(List<Business> entityList) {
        mPopularBusinesses.addAll(entityList);
        getViewState().onPopularBusinessesLoadingSuccess(mPopularBusinesses);
        mPopularBusinessesPaginationDisposable = null;
        if (entityList.size() != PART) {
            mIsAllPopularBusinessesLoaded = true;
        }
    }

    private void onPopularBusinessesLoadingFailed(Throwable throwable) {
        getViewState().onPopularBusinessesLoadingFailed(throwable);
        mPopularBusinessesPaginationDisposable = null;
    }

    private void onCategoriesLoadingSuccess(BusinessCategoriesResponse response) {
        mCategories.addAll(response.getData());
        mCategoryAdapterItems.clear();
        mCategoryAdapterItems.addAll(mapToCategoryAdapterItems(mCategories));
        getViewState().hideCategoriesProgress();
        getViewState().onCategoriesLoadingSuccess(mCategoryAdapterItems.size() > 1
                ? mCategoryAdapterItems : new ArrayList<>());
        mCategoriesPaginationDisposable = null;
    }

    private void onCategoriesLoadingFailed(Throwable throwable) {
        getViewState().hideCategoriesProgress();
        getViewState().onCategoriesLoadingFailed(throwable);
        mCategoriesPaginationDisposable = null;
    }

    private List<CategoryAdapterItem> mapToCategoryAdapterItems(List<BusinessCategory> categories) {
        List<CategoryAdapterItem> result = new ArrayList<>();
        for (BusinessCategory c : categories) {
            result.add(new CategoryAdapterItem(c));
        }
        return result;
    }

    private void loadCities() {
        if (mCityStatus.getSelectedCity() == null && !mCityStatus.getCities().isEmpty()) {
            mCityStatus.setSelectedCity(mCityStatus.getCities().get(0));
        }
        fillCitiesAdapterItems();
    }

    private void fillSortAdapterItems() {
        mSortAdapterItems.clear();

        mSortAdapterItems.add(new SheetAdapterItem(SORT_NAME, R.string.by_establishment_name,
                null, SORT_NAME.equals(mSort)));
        mSortAdapterItems.add(new SheetAdapterItem(SORT_RATING, R.string.by_rating,
                null, SORT_RATING.equals(mSort)));
        mSortAdapterItems.add(new SheetAdapterItem(SORT_COMMENT, R.string.by_feedback_count,
                null, SORT_COMMENT.equals(mSort)));
        mSortAdapterItems.add(new SheetAdapterItem(SORT_DISTANCE, R.string.nearest_to_me,
                null, SORT_DISTANCE.equals(mSort)));
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

    private void checkSortType(String sort, boolean check) {
        for (SheetAdapterItem i : mSortAdapterItems) {
            if (i.getKey().equals(sort)) {
                i.setChecked(check);
            } else {
                i.setChecked(false);
            }
        }
        if (check) {
            mSort = sort;
        } else {
            mSort = DEFAULT_SORT;
        }
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

    private String selectedCityKey() {
        return mCityStatus.getSelectedCity() != null ?
                mCityStatus.getSelectedCity().getCity() :
                null;
    }

    private List<Business> findBusinessesWithIdInLoadedList(Integer id) {
        List<Business> result = new ArrayList<>();
        for (Business business : mBusinesses) {
            if (business.getId().equals(id)) {
                result.add(business);
            }
        }
        for (Business business : mPopularBusinesses) {
            if (business.getId().equals(id)) {
                result.add(business);
            }
        }
        return result;
    }

    private class LoadMoreIntervalTimer extends CountDownTimer {

        public LoadMoreIntervalTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {
            mLoadMoreIntervalTimerFinished = true;
        }
    }
}
