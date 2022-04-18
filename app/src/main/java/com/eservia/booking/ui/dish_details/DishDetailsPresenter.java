package com.eservia.booking.ui.dish_details;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.DishUtil;
import com.eservia.model.entity.OrderResto;
import com.eservia.model.entity.OrderRestoItem;
import com.eservia.model.entity.OrderRestoNomenclature;
import com.eservia.model.entity.OrderRestoSaleMethod;
import com.eservia.model.entity.OrderRestoSizePrice;
import com.eservia.model.local.menu.MenuRepository;
import com.eservia.model.local.order_resto.OrderRestoRepository;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.utils.NumberUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class DishDetailsPresenter extends BasePresenter<DishDetailsView> {

    @Inject
    RestManager mRestManager;

    @Inject
    OrderRestoRepository mOrderRestoRepository;

    @Inject
    MenuRepository mMenuRepository;

    private final String ERROR_INVALID_PORTIONS_AMOUNT = "Error invalid portions amount";

    private Disposable mAddToBasketDisposable;

    private OrderRestoNomenclature mDish;
    private Long mSaleMethodId;

    private int mNumberOfPortions;

    private OrderRestoSizePrice mSelectedSizePrice;

    public DishDetailsPresenter() {
        App.getAppComponent().inject(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setMaxPortionsCount(DishUtil.MAX_DELIVERY_DISH_PORTIONS_COUNT);
        onDish(EventBus.getDefault().getStickyEvent(DishDetailsExtra.class).dish);
    }

    void onPortionsChanged(int portions) {
        mNumberOfPortions = portions;
        getViewState().bindTotalPrice(calculateTotalPrice());
    }

    void onSelectedSizePriceAtPosition(int position) {
        mSelectedSizePrice = mDish.getSizePrices().get(position);
        getViewState().bindTotalPrice(calculateTotalPrice());
        getViewState().bindDishWeight(mDish, mSelectedSizePrice.getSize());
    }

    void onAddToBasket() {
        addToBasket();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onDish(OrderRestoNomenclature dish) {
        mDish = dish;
        refresh();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refresh() {
        mSaleMethodId = mDish.getSaleMethodId();
        mNumberOfPortions = 1;
        mSelectedSizePrice = null;
        bindDish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bindDish() {
        if (mSaleMethodId.equals(OrderRestoSaleMethod.SIZE_PRICE)) {
            bindDishWithSizePriceMethod();
        } else if (mSaleMethodId.equals(OrderRestoSaleMethod.PORTION)) {
            bindDishWithPortionMethod();
        } else if (mSaleMethodId.equals(OrderRestoSaleMethod.BY_WEIGHT)) {
            bindDishWithByWeightMethod();
        } else {
            bindDishWithUnknownMethod();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bindDishWithSizePriceMethod() {
        if (mDish.getSizePrices() == null || mDish.getSizePrices().isEmpty()) {
            return;
        }
        getViewState().bindDishImage(mDish.getPhotoPath());
        getViewState().bindDishName(mDish.getName());
        getViewState().bindDishMinWeight(mDish);
        getViewState().bindDishCookingTime(mDish);
        getViewState().bindDishDescription(mDish.getDescription());
        getViewState().bindNumberOfPortions(mNumberOfPortions);
        getViewState().bindDishSizePrices(mDish, refreshSizePrices());
        getViewState().bindTotalPrice(calculateTotalPriceForSizePriceMethod());
    }

    private void bindDishWithPortionMethod() {
        //TODO: implement
    }

    private void bindDishWithByWeightMethod() {
        //TODO: implement
    }

    private void bindDishWithUnknownMethod() {
        //TODO: implement
    }

    private void addToBasket() {
        if (paginationInProgress(mAddToBasketDisposable)) {
            return;
        }
        Observable<List<OrderResto>> ordersWithBusinessIdObservable = mOrderRestoRepository
                .findAllWithBusinessId(mDish.getBusinessId());

        Observable<Long> menuVersionObservable = mMenuRepository
                .getMenuVersion(mDish.getBusinessId());

        Observable<Pair<List<OrderResto>, Long>> combinedObservable = Observable.zip(
                ordersWithBusinessIdObservable, menuVersionObservable, Pair::new);

        Observable<OrderResto> observable = combinedObservable
                .flatMap(ordersAndMenuVersion -> {
                    List<OrderResto> ordersWithBusinessId = ordersAndMenuVersion.first;
                    Long menuVersion = ordersAndMenuVersion.second;

                    if (ordersWithBusinessId.isEmpty()) {
                        return mOrderRestoRepository.createOrUpdate(
                                decorateOrderResto(new OrderResto(), menuVersion));
                    } else {
                        OrderResto orderResto = ordersWithBusinessId.get(0);
                        for (OrderRestoItem orderRestoItem : orderResto.getOrderItems()) {
                            if (!isEquivalentToCurrent(orderRestoItem)) {
                                continue;
                            }
                            if (isTotalNumberOfPortionsValid(orderRestoItem.getAmount())) {
                                orderRestoItem.setAmount(newPortionsAmount(orderRestoItem));
                                return mOrderRestoRepository.createOrUpdate(orderResto);
                            } else {
                                return Observable.error(new Throwable(ERROR_INVALID_PORTIONS_AMOUNT));
                            }
                        }
                        orderResto.getOrderItems().add(decorateOrderRestoItem(new OrderRestoItem()));
                        return mOrderRestoRepository.createOrUpdate(orderResto);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mAddToBasketDisposable = observable.subscribe(this::onAddToBasketSuccess,
                this::onAddToBasketFailed);
        addSubscription(mAddToBasketDisposable);
    }

    private boolean isTotalNumberOfPortionsValid(long oldNumberOfPortions) {
        long addedAmount = mNumberOfPortions;
        int totalAmount = (int) (oldNumberOfPortions + addedAmount);
        return totalAmount > 0 && totalAmount <= DishUtil.MAX_DELIVERY_DISH_PORTIONS_COUNT;
    }

    private long newPortionsAmount(OrderRestoItem orderRestoItem) {
        return orderRestoItem.getAmount() + ((long) mNumberOfPortions);
    }

    private void onAddToBasketSuccess(OrderResto orderResto) {
        getViewState().showAddedToCartMessage();
        getViewState().goBack();
        mAddToBasketDisposable = null;
    }

    private void onAddToBasketFailed(Throwable throwable) {
        if (throwable.getMessage() != null
                && throwable.getMessage().equals(ERROR_INVALID_PORTIONS_AMOUNT)) {
            getViewState().showExceededMaxAmountOfPortionsError(DishUtil.MAX_DELIVERY_DISH_PORTIONS_COUNT);
        } else {
            getViewState().showAddedToCartErrorMessage();
        }
        mAddToBasketDisposable = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int refreshSizePrices() {
        sortSizePrices();
        int smallestSizePricePosition = 0;
        mSelectedSizePrice = mDish.getSizePrices().get(smallestSizePricePosition);
        return smallestSizePricePosition;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortSizePrices() {
        Collections.sort(mDish.getSizePrices(), Comparator.comparingDouble(OrderRestoSizePrice::getSize));
    }

    private Double calculateTotalPrice() {
        if (mSaleMethodId.equals(OrderRestoSaleMethod.SIZE_PRICE)) {
            return calculateTotalPriceForSizePriceMethod();
        } else if (mSaleMethodId.equals(OrderRestoSaleMethod.PORTION)) {
            return calculateTotalPriceForPortionMethod();
        } else if (mSaleMethodId.equals(OrderRestoSaleMethod.BY_WEIGHT)) {
            return calculateTotalPriceForWeightMethod();
        } else {
            return 0.0;
        }
    }

    private Double calculateTotalPriceForSizePriceMethod() {
        return mSelectedSizePrice.getPrice() * mNumberOfPortions;
    }

    private Double calculateTotalPriceForPortionMethod() {
        //TODO: implement
        return 0.0;
    }

    private Double calculateTotalPriceForWeightMethod() {
        //TODO: implement
        return 0.0;
    }

    private Double getOrderSize() {
        if (mSaleMethodId.equals(OrderRestoSaleMethod.SIZE_PRICE)) {
            return mSelectedSizePrice.getSize();
        } else if (mSaleMethodId.equals(OrderRestoSaleMethod.PORTION)) {
            return 0.0;//TODO: implement
        } else if (mSaleMethodId.equals(OrderRestoSaleMethod.BY_WEIGHT)) {
            return 0.0;//TODO: implement
        } else {
            return 0.0;
        }
    }

    private Double getOrderPriceByPortion() {
        if (mSaleMethodId.equals(OrderRestoSaleMethod.SIZE_PRICE)) {
            return mSelectedSizePrice.getPrice();
        } else if (mSaleMethodId.equals(OrderRestoSaleMethod.PORTION)) {
            return 0.0;//TODO: implement
        } else if (mSaleMethodId.equals(OrderRestoSaleMethod.BY_WEIGHT)) {
            return 0.0;//TODO: implement
        } else {
            return 0.0;
        }
    }

    private OrderRestoItem decorateOrderRestoItem(OrderRestoItem orderItem) {
        orderItem.setBusinessId(mDish.getBusinessId());
        orderItem.setPriceByPortion(getOrderPriceByPortion());
        orderItem.setNomenclatureId(mDish.getId());
        orderItem.setAmount((long) mNumberOfPortions);
        orderItem.setSize(getOrderSize());
        orderItem.setInitializationId(UUID.randomUUID().toString());
        return orderItem;
    }

    private OrderResto decorateOrderResto(OrderResto order, Long menuVersion) {
        order.setBusinessId(mDish.getBusinessId());
        order.getOrderItems().add(decorateOrderRestoItem(new OrderRestoItem()));
        order.setAddressId(mDish.getAddressId());
        order.setMenuVersion(menuVersion);
        order.setInitializationId(UUID.randomUUID().toString());
        return order;
    }

    private boolean isEquivalentToCurrent(OrderRestoItem otherOrderItem) {
        return otherOrderItem.getNomenclatureId().equals(mDish.getId())
                && NumberUtil.isEqual(otherOrderItem.getSize(), getOrderSize());
    }
}
