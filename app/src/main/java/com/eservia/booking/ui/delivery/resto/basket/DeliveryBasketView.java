package com.eservia.booking.ui.delivery.resto.basket;

import com.eservia.booking.common.view.LoadingView;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface DeliveryBasketView extends LoadingView {

    void onOrderItemsLoaded(List<DeliveryOrderAdapterItem> adapterItems);

    @StateStrategyType(value = SkipStrategy.class)
    void onOrderItemsLoadingFailed(Throwable error);

    @StateStrategyType(value = SkipStrategy.class)
    void onDeletingBasketItemFailed(Throwable error);

    void onEditMode(boolean enabled);

    @StateStrategyType(value = SkipStrategy.class)
    void goBack();

    void onOrderItemsUpdated();

    void revalidateTotalPrice();

    void setUserName(String name);

    void setUserPhone(String phone);

    void setUserCity(String city);

    void setUserAddress(String address);

    void setUserHouse(String house);

    void setUserApartment(String apartment);

    void setUserDoorPhoneCode(String doorPhoneCode);

    void setUserComment(String comment);

    @StateStrategyType(value = SkipStrategy.class)
    void showCitySelectPage();

    @StateStrategyType(value = SkipStrategy.class)
    void showStreetSelectPage();

    void refreshAcceptState(boolean isActive);

    @StateStrategyType(value = SkipStrategy.class)
    void openDeliveryTimePage();

    @StateStrategyType(value = SkipStrategy.class)
    void showNotFilledUserInfoError();

    void bindMinDeliveryTime(int minutes);

    @StateStrategyType(value = SkipStrategy.class)
    void showEmptyHouseError();

    @StateStrategyType(value = SkipStrategy.class)
    void showEmptyPhoneError();

    @StateStrategyType(value = SkipStrategy.class)
    void showEmptyCityError();

    @StateStrategyType(value = SkipStrategy.class)
    void showEmptyAddressError();

    @StateStrategyType(value = SkipStrategy.class)
    void showEmptyNameError();
}
