package com.eservia.booking.ui.home.news.news.news_and_promo;

import android.content.Context;

import androidx.annotation.Nullable;

import com.eservia.booking.App;
import com.eservia.booking.R;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.city.CityStatus;
import com.eservia.booking.ui.home.search.search.sheet.SheetAdapterItem;
import com.eservia.model.entity.BusinessCity;
import com.eservia.model.entity.Marketing;
import com.eservia.model.interactors.marketing.MarketingInteractor;
import com.eservia.model.remote.rest.RestManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class NewsAndPromoPresenter extends BasePresenter<NewsAndPromoView> implements
        NewsAndPromoAdapter.NewsClickListener,
        NewsAndPromoAdapter.OnNewsPaginationListener {

    private final List<Marketing> mMarketings = new ArrayList<>();
    private final List<Marketing> mMarketingsNotFiltered = new ArrayList<>();

    private final List<SheetAdapterItem> mCitiesAdapterItems = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    @Inject
    MarketingInteractor mMarketingInteractor;

    @Inject
    CityStatus mCityStatus;

    @Inject
    Context mContext;

    private Disposable mNewsPaginationDisposable;

    private boolean mIsAllNewsLoaded = false;

    @Nullable
    private BusinessCity mSelectedCity;

    public NewsAndPromoPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showSelectedCity(selectedCityKey());
    }

    @Override
    public void onNewsClick(Marketing item, int position) {
        if (item.getBusiness() != null) {
            getViewState().showEventDetailBeautyPage(item);
        }
    }

    @Override
    public void loadMoreNews() {
        if (!paginationInProgress(mNewsPaginationDisposable) && !mIsAllNewsLoaded) {
            makeNewsPagination();
        }
    }

    void willBeDisplayed() {
        if (!mCitiesAdapterItems.isEmpty()) {
            return;
        }
        fillCitiesAdapterItems();
        for (SheetAdapterItem item : mCitiesAdapterItems) {
            if ((item.getKey() == null && selectedCityKey() == null) ||
                    (item.getKey() != null && item.getKey().equals(selectedCityKey()))) {
                onCitySelected(item);
                return;
            }
        }
    }

    void onDialogCitySelected(SheetAdapterItem<BusinessCity> itemClicked) {
        onCitySelected(itemClicked);
    }

    void onShowCitySelection() {
        getViewState().showCitiesDialog(mCitiesAdapterItems);
    }

    void refreshNews() {
        cancelPagination(mNewsPaginationDisposable);
        mMarketings.clear();
        mMarketingsNotFiltered.clear();
        mIsAllNewsLoaded = false;
        makeNewsPagination();
    }

    private void makeNewsPagination() {
        if (mMarketings.isEmpty()) {
            getViewState().showProgress();
        }
        cancelPagination(mNewsPaginationDisposable);
        Observable<List<Marketing>> observable = mMarketingInteractor
                .getMarketings(null,
                        null,
                        Marketing.STATUS_ACTIVE,
                        selectedCityKey(),
                        null,
                        null,
                        PART,
                        mMarketingsNotFiltered.size() / PART)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mNewsPaginationDisposable = observable.subscribe(this::onNewsLoadingSuccess,
                this::onNewsLoadingFailed);
        addSubscription(mNewsPaginationDisposable);
    }

    private void onNewsLoadingSuccess(List<Marketing> marketingsFromResponse) {
        List<Marketing> filteredMarketings = filterMarketings(marketingsFromResponse);
        mMarketings.addAll(filteredMarketings);
        mMarketingsNotFiltered.addAll(marketingsFromResponse);
        getViewState().hideProgress();
        getViewState().onNewsLoadingSuccess(mMarketings);
        mNewsPaginationDisposable = null;
        if (marketingsFromResponse.size() != PART) {
            mIsAllNewsLoaded = true;
        } else if (filteredMarketings.isEmpty()) {
            loadMoreNews();
        }
    }

    private void onNewsLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onNewsLoadingFailed(throwable);
        mNewsPaginationDisposable = null;
    }

    private List<Marketing> filterMarketings(List<Marketing> marketings) {
        List<Marketing> filtered = new ArrayList<>();
        for (Marketing marketing : marketings) {
            if (marketing.getBusiness() != null) {
                filtered.add(marketing);
            }
        }
        return filtered;
    }

    private void onCitySelected(SheetAdapterItem<BusinessCity> itemClicked) {
        checkCity(itemClicked);
        getViewState().hideSheetDialog();
        getViewState().showSelectedCity(selectedCityKey());
        refreshNews();
    }

    private String selectedCityKey() {
        return mSelectedCity != null ? mSelectedCity.getCity() : null;
    }

    private void checkCity(SheetAdapterItem<BusinessCity> cityToCheck) {
        for (SheetAdapterItem i : mCitiesAdapterItems) {
            i.setChecked((cityToCheck.getKey() == null && i.getKey() == null) ||
                    (i.getKey() != null && i.getKey().equals(cityToCheck.getKey())));
        }
        mSelectedCity = cityToCheck.getModel();
    }

    private void fillCitiesAdapterItems() {
        mCitiesAdapterItems.clear();

        SheetAdapterItem<BusinessCity> allCitiesItem = new SheetAdapterItem<>(null,
                null,
                mContext.getResources().getString(R.string.all_cities),
                selectedCityKey() == null,
                null);
        mCitiesAdapterItems.add(allCitiesItem);

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
