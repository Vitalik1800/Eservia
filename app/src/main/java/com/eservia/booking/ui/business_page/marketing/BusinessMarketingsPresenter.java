package com.eservia.booking.ui.business_page.marketing;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.Marketing;
import com.eservia.model.interactors.marketing.MarketingInteractor;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.request.KeyList;

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
public class BusinessMarketingsPresenter extends BasePresenter<BusinessMarketingsView> implements
        BusinessMarketingsAdapter.NewsClickListener,
        BusinessMarketingsAdapter.OnNewsPaginationListener {

    private final List<Marketing> mMarketings = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    @Inject
    MarketingInteractor mMarketingInteractor;

    private Disposable mNewsPaginationDisposable;

    private boolean mIsAllNewsLoaded = false;

    private Business mBusiness;

    public BusinessMarketingsPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mBusiness = EventBus.getDefault().getStickyEvent(Business.class);
        if (mBusiness != null) {
            mMarketings.addAll(mBusiness.getMarketings());
            getViewState().onNewsLoadingSuccess(mMarketings);
        }
        refreshNews();
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

    public void refreshNews() {
        cancelPagination(mNewsPaginationDisposable);
        mMarketings.clear();
        mIsAllNewsLoaded = false;
        makeNewsPagination();
    }

    private void makeNewsPagination() {
        if (mBusiness == null) {
            return;
        }

        if (mMarketings.isEmpty()) {
            getViewState().showProgress();
        }
        cancelPagination(mNewsPaginationDisposable);

        Observable<List<Marketing>> observable = mMarketingInteractor
                .getMarketings(new KeyList().add(mBusiness.getId()), null, Marketing.STATUS_ACTIVE,
                        null, null, null, PART, mMarketings.size() / PART)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mNewsPaginationDisposable = observable.subscribe(this::onNewsLoadingSuccess,
                this::onNewsLoadingFailed);
        addSubscription(mNewsPaginationDisposable);
    }

    private void onNewsLoadingSuccess(List<Marketing> marketings) {
        mMarketings.addAll(marketings);
        getViewState().hideProgress();
        getViewState().onNewsLoadingSuccess(mMarketings);
        mNewsPaginationDisposable = null;
        if (marketings.size() != PART) {
            mIsAllNewsLoaded = true;
        }
    }

    private void onNewsLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onNewsLoadingFailed(throwable);
        mNewsPaginationDisposable = null;
    }
}
