package com.eservia.booking.ui.business_page.restaurant.menu;

import android.util.Pair;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.OrderRestoCategory;
import com.eservia.model.entity.OrderRestoCategory_;
import com.eservia.model.entity.OrderRestoNomenclature;
import com.eservia.model.entity.OrderRestoNomenclature_;
import com.eservia.model.interactors.resto.RestoMenuInteractor;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class MenuPresenter extends BasePresenter<MenuView> {

    private final Pair<List<OrderRestoCategory>, List<OrderRestoNomenclature>> mMenu =
            new Pair<>(new ArrayList<>(), new ArrayList<>());

    @Inject
    RestoMenuInteractor mRestoMenuInteractor;

    @Inject
    BoxStore mBoxStore;

    private Disposable mMenuDisposable;

    private Long mBusinessId;

    private Long mAddressId;

    private Long mCategoryId;

    public MenuPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Business business = EventBus.getDefault().getStickyEvent(Business.class);
        if (!business.getAddresses().isEmpty()) {
            mBusinessId = (long) business.getId();
            mAddressId = (long) business.getAddresses().get(0).getId();
        }
        getViewState().initCategoryId();
    }

    void onCategoryId(Long categoryId) {
        mCategoryId = categoryId;
        refreshMenu();
    }

    public void refreshMenu() {
        if (mAddressId == null) {
            return;
        }
        loadMenu();
    }

    private void loadMenu() {
        getViewState().showProgress();
        cancelPagination(mMenuDisposable);
        Observable<Pair<List<OrderRestoCategory>, List<OrderRestoNomenclature>>> observable = mRestoMenuInteractor
                .loadMenu(mBusinessId, mAddressId, null)
                .map(event -> {
                    Box<OrderRestoCategory> categoryBox = mBoxStore.boxFor(OrderRestoCategory.class);
                    Box<OrderRestoNomenclature> nomenclatureBox = mBoxStore.boxFor(OrderRestoNomenclature.class);

                    OrderRestoCategory parentCategory;
                    if (mCategoryId != null) {
                        parentCategory = categoryBox.query()
                                .equal(OrderRestoCategory_.businessId, mBusinessId)
                                .equal(OrderRestoCategory_.id, mCategoryId)
                                .build().findFirst();
                    } else {
                        parentCategory = categoryBox.query()
                                .equal(OrderRestoCategory_.businessId, mBusinessId)
                                .equal(OrderRestoCategory_.name, Long.parseLong(OrderRestoCategory.ROOT_NAME))
                                .build().findFirst();
                    }

                    List<OrderRestoCategory> categories = categoryBox.query()
                            .equal(OrderRestoCategory_.businessId, mBusinessId)
                            .equal(OrderRestoCategory_.parentId, parentCategory.getId())
                            .build().find();

                    List<OrderRestoNomenclature> nomenclatures = nomenclatureBox.query()
                            .equal(OrderRestoNomenclature_.businessId, mBusinessId)
                            .equal(OrderRestoNomenclature_.parentId, parentCategory.getId())
                            .build().find();

                    for (OrderRestoNomenclature nomenclature : nomenclatures) {
                        nomenclature.getCategory().getTarget();
                        nomenclature.getDimension().getTarget();
                        nomenclature.getPortionGradation().getTarget();
                        nomenclature.getSizePrices();
                    }

                    mMenu.first.clear();
                    mMenu.first.addAll(categories);
                    mMenu.second.clear();
                    mMenu.second.addAll(nomenclatures);
                    return mMenu;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mMenuDisposable = observable.subscribe(this::onMenuLoadingSuccess, this::onMenuLoadingFailed);
        addSubscription(mMenuDisposable);
    }

    private void onMenuLoadingSuccess(Pair<List<OrderRestoCategory>, List<OrderRestoNomenclature>> menu) {
        getViewState().hideProgress();
        getViewState().onMenuLoaded(menu);
        mMenuDisposable = null;
    }

    private void onMenuLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onMenuLoadingFailed(throwable);
        mMenuDisposable = null;
    }
}
