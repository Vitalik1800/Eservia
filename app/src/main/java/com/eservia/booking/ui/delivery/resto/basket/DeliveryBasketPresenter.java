package com.eservia.booking.ui.delivery.resto.basket;

import androidx.core.util.Pair;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.booking.util.DishUtil;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.OrderRestoItem;
import com.eservia.model.entity.OrderRestoNomenclature;
import com.eservia.model.entity.RestoDeliverySettlement;
import com.eservia.model.entity.RestoDeliveryStreet;
import com.eservia.model.interactors.resto.RestoDeliveryRules;
import com.eservia.model.local.menu.MenuRepository;
import com.eservia.model.local.order_resto.OrderRestoRepository;
import com.eservia.model.prefs.Profile;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.utils.ArrayUtil;
import com.eservia.utils.Contract;
import com.eservia.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class DeliveryBasketPresenter extends BasePresenter<DeliveryBasketView> implements
        DeliveryOrderAdapter.DeliveryOrderAdapterListener {

    private final List<Pair<OrderRestoItem, OrderRestoNomenclature>> mOrders = new ArrayList<>();

    private final List<DeliveryOrderAdapterItem> mOrderAdapterItems = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    @Inject
    OrderRestoRepository mOrderRestoRepository;

    @Inject
    MenuRepository mMenuRepository;

    @Inject
    BookingStatus mBookingStatus;

    private Business mBusiness;

    private Disposable mOrdersDisposable;

    private boolean mIsEditMode = false;

    private String mName = "";
    private String mPhone = "";
    private String mCity = "";
    private String mAddress = "";
    private String mHouse = "";
    private String mApartment = "";
    private String mDoorPhoneCode = "";
    private String mComment = "";

    public DeliveryBasketPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mBusiness = mBookingStatus.getDeliveryStatus().getBusiness();
        refreshBasketOrderItems();
        getViewState().setUserName(Profile.getFullName());
        getViewState().setUserPhone(Profile.getUserPhoneNumber());
        getViewState().refreshAcceptState(true);
        getViewState().bindMinDeliveryTime(RestoDeliveryRules.MIN_MINUTES_FOR_DELIVERY_RESTO_ORDER);
    }

    @Override
    public void onDeleteDeliveryOrderClick(DeliveryOrderAdapterItem item, int position) {
        deleteBasketItem(item.getOrderRestoItem());
    }

    @Override
    public void onDeliveryOrderPortionsChanged(DeliveryOrderAdapterItem item, int position,
                                               int portions) {
        item.getOrderRestoItem().setAmount((long) portions);
        getViewState().onOrderItemsUpdated();
        getViewState().revalidateTotalPrice();
    }

    void onPause() {
        saveBasketItems(actualOrderItems());
    }

    void onResume() {
        RestoDeliverySettlement settlement = mBookingStatus.getDeliveryStatus().getSettlement();
        RestoDeliveryStreet street = mBookingStatus.getDeliveryStatus().getStreet();
        getViewState().setUserCity(settlement != null ? settlement.getName() : "");
        getViewState().setUserAddress(street != null ? street.getName() : "");
    }

    void onBackClicked() {
        getViewState().goBack();
    }

    void editModeEnabled(boolean enabled) {
        if (!enabled || mOrders.size() > 0) {

            mIsEditMode = enabled;

            getViewState().onEditMode(mIsEditMode);
            getViewState().refreshAcceptState(!mIsEditMode);
            getViewState().onOrderItemsLoaded(mapToOrderListItems(mOrders, mIsEditMode));
        }
    }

    void onAcceptClick(String name, String phone, String city, String address, String house,
                       String apartment, String doorPhoneCode, String comment) {
        mName = name;
        mPhone = phone;
        mCity = city;
        mAddress = address;
        mHouse = house;
        mApartment = apartment;
        mDoorPhoneCode = doorPhoneCode;
        mComment = comment;
        getViewState().setUserName(mName);
        getViewState().setUserPhone(mPhone);
        getViewState().setUserCity(mCity);
        getViewState().setUserAddress(mAddress);
        getViewState().setUserHouse(mHouse);
        getViewState().setUserApartment(mApartment);
        getViewState().setUserDoorPhoneCode(mDoorPhoneCode);
        getViewState().setUserComment(mComment);

        if (paginationInProgress(mOrdersDisposable)) {
            return;
        }
        if (mIsEditMode) {
            return;
        }
        if (mOrders.isEmpty()) {
            return;
        }
        if (!canGoToNextPage()) {
            getViewState().showNotFilledUserInfoError();
            validateInputFields();
            return;
        }
        saveDeliveryParametersToState();
        getViewState().openDeliveryTimePage();
    }

    void onCityClick() {
        getViewState().showCitySelectPage();
    }

    void onAddressClick() {
        getViewState().showStreetSelectPage();
    }

    private boolean canGoToNextPage() {
        return !mName.isEmpty() && !mPhone.isEmpty() && !mCity.isEmpty() && !mAddress.isEmpty()
                && !mHouse.isEmpty();
    }

    private void validateInputFields() {
        if (mHouse.isEmpty()) {
            getViewState().showEmptyHouseError();
        }
        if (mAddress.isEmpty()) {
            getViewState().showEmptyAddressError();
        }
        if (mCity.isEmpty()) {
            getViewState().showEmptyCityError();
        }
        if (mPhone.isEmpty()) {
            getViewState().showEmptyPhoneError();
        }
        if (mName.isEmpty()) {
            getViewState().showEmptyNameError();
        }
    }

    private void saveDeliveryParametersToState() {
        mBookingStatus.getDeliveryStatus().setName(mName);
        mBookingStatus.getDeliveryStatus().setPhone(mPhone);
        mBookingStatus.getDeliveryStatus().setCity(mCity);
        mBookingStatus.getDeliveryStatus().setDeliveryAddress(mAddress);
        mBookingStatus.getDeliveryStatus().setHouse(mHouse);
        mBookingStatus.getDeliveryStatus().setApartment(mApartment);
        mBookingStatus.getDeliveryStatus().setDoorPhoneCode(mDoorPhoneCode);
        mBookingStatus.getDeliveryStatus().setComment(mComment);
    }

    private void refreshBasketOrderItems() {
        if (!paginationInProgress(mOrdersDisposable)) {
            loadBasket();
        }
    }

    private void deleteBasketItem(OrderRestoItem orderRestoItem) {
        if (paginationInProgress(mOrdersDisposable)) {
            return;
        }
        Observable<OrderRestoItem> observable = mOrderRestoRepository
                .createOrUpdateItems(actualOrderItems())
                .flatMap(updatedItems -> mOrderRestoRepository.delete(orderRestoItem).toObservable())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mOrdersDisposable = observable.subscribe(this::onDeletedBasketItem,
                this::onDeletingBasketItemFailed);
        addSubscription(mOrdersDisposable);
    }

    private void saveBasketItems(List<OrderRestoItem> orderRestoItems) {
        if (paginationInProgress(mOrdersDisposable)) {
            return;
        }
        Observable<List<OrderRestoItem>> observable = mOrderRestoRepository
                .createOrUpdateItems(orderRestoItems)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mOrdersDisposable = observable.subscribe(this::onSavedBasketItems,
                this::onSaveBasketItemsFailed);
        addSubscription(mOrdersDisposable);
    }

    private void loadBasket() {
        getViewState().showProgress();
        cancelPagination(mOrdersDisposable);
        Observable<List<Pair<OrderRestoItem, OrderRestoNomenclature>>> observable = mOrderRestoRepository
                .findAllOrderItemsForBusinessId(mBusiness.getId())
                .flatMap(orderItems -> {
                    if (orderItems.isEmpty()) {
                        return Observable.just(new ArrayList<Pair<OrderRestoItem, OrderRestoNomenclature>>());
                    } else {
                        Set<Long> nomenctatureIds = new HashSet<>();
                        for (OrderRestoItem orderRestoItem : orderItems) {
                            nomenctatureIds.add(orderRestoItem.getNomenclatureId());
                        }
                        long[] primitives = ArrayUtil.toPrimitivesArray(nomenctatureIds);
                        return mMenuRepository.findNomenclaturesWithId(primitives, mBusiness.getId())
                                .flatMap(dishes -> Observable.just(combine(orderItems, dishes)));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mOrdersDisposable = observable.subscribe(this::onOrderItemsLoaded,
                this::onOrderItemsLoadingFailed);
        addSubscription(mOrdersDisposable);
    }

    private void onOrderItemsLoaded(List<Pair<OrderRestoItem, OrderRestoNomenclature>> orders) {
        getViewState().hideProgress();
        mOrders.clear();
        mOrders.addAll(orders);
        mOrderAdapterItems.clear();
        mOrderAdapterItems.addAll(mapToOrderListItems(orders, mIsEditMode));
        getViewState().onOrderItemsLoaded(mOrderAdapterItems);
        mOrdersDisposable = null;
    }

    private void onOrderItemsLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        LogUtils.debug(Contract.LOG_TAG, throwable.getMessage());
        getViewState().onOrderItemsLoadingFailed(throwable);
        mOrdersDisposable = null;
    }

    private void onDeletedBasketItem(OrderRestoItem deletedItem) {
        mOrdersDisposable = null;
        refreshBasketOrderItems();
    }

    private void onDeletingBasketItemFailed(Throwable throwable) {
        LogUtils.debug(Contract.LOG_TAG, throwable.getMessage());
        getViewState().onDeletingBasketItemFailed(throwable);
        mOrdersDisposable = null;
    }

    private void onSavedBasketItems(List<OrderRestoItem> orderRestoItems) {
        mOrdersDisposable = null;
    }

    private void onSaveBasketItemsFailed(Throwable throwable) {
        LogUtils.debug(Contract.LOG_TAG, throwable.getMessage());
        mOrdersDisposable = null;
    }

    private List<Pair<OrderRestoItem, OrderRestoNomenclature>> combine(List<OrderRestoItem> orderItems,
                                                                       List<OrderRestoNomenclature> dishes) {
        List<Pair<OrderRestoItem, OrderRestoNomenclature>> combined = new ArrayList<>();
        for (OrderRestoItem orderItem : orderItems) {
            for (OrderRestoNomenclature dish : dishes) {
                if (orderItem.getNomenclatureId().equals(dish.getId())) {
                    combined.add(new Pair<>(orderItem, dish));
                    break;
                }
            }
        }
        return combined;
    }

    private List<DeliveryOrderAdapterItem> mapToOrderListItems(
            List<Pair<OrderRestoItem, OrderRestoNomenclature>> orders, boolean isEditMode) {

        List<DeliveryOrderAdapterItem> result = new ArrayList<>();
        for (Pair<OrderRestoItem, OrderRestoNomenclature> o : orders) {
            result.add(new DeliveryOrderAdapterItem(o.first, o.second, isEditMode,
                    DishUtil.MAX_DELIVERY_DISH_PORTIONS_COUNT));
        }
        return result;
    }

    private List<OrderRestoItem> actualOrderItems() {
        List<OrderRestoItem> items = new ArrayList<>();
        for (Pair<OrderRestoItem, OrderRestoNomenclature> itemPair : mOrders) {
            items.add(itemPair.first);
        }
        return items;
    }
}
