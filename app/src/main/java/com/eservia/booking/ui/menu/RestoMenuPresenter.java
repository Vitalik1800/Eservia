package com.eservia.booking.ui.menu;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.entity.Business;
import com.eservia.model.local.order_resto.OrderRestoRepository;
import com.eservia.utils.Contract;
import com.eservia.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class RestoMenuPresenter extends BasePresenter<RestoMenuView> {

    @Inject
    OrderRestoRepository mOrderRestoRepository;

    private Disposable mBasketOrderItemsCountDisposable;

    private Business mBusiness;

    public RestoMenuPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        RestoMenuExtra extra = EventBus.getDefault().getStickyEvent(RestoMenuExtra.class);
        mBusiness = extra.business;
        Long mCategoryId = extra.categoryId;
        loadBasketOrderItemsCount();
        getViewState().openFirstMenuFragment(mCategoryId);
    }

    void onResume() {
        loadBasketOrderItemsCount();
    }

    void onBackClicked() {
        getViewState().goBack();
    }

    void onBasketClicked() {
        getViewState().openBasket(mBusiness);
    }

    private void loadBasketOrderItemsCount() {
        if (paginationInProgress(mBasketOrderItemsCountDisposable)) {
            return;
        }
        Observable<Integer> observable = mOrderRestoRepository
                .orderItemsForBusinessIdCount(mBusiness.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mBasketOrderItemsCountDisposable = observable.subscribe(this::onBasketOrderItemsCountLoaded,
                this::onBasketOrderItemsCountLoadingFailed);
        addSubscription(mBasketOrderItemsCountDisposable);
    }

    private void onBasketOrderItemsCountLoaded(Integer basketOrderItemsCount) {
        getViewState().refreshBasketState(basketOrderItemsCount);
        mBasketOrderItemsCountDisposable = null;
    }

    private void onBasketOrderItemsCountLoadingFailed(Throwable throwable) {
        LogUtils.debug(Contract.LOG_TAG, throwable.getMessage());
        mBasketOrderItemsCountDisposable = null;
    }
}
