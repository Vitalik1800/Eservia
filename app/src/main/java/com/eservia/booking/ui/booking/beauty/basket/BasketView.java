package com.eservia.booking.ui.booking.beauty.basket;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.model.entity.Address;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyRequest;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BasketView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showSelectedAddress(Address address);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onFilledPreparations(List<FilledPreparationListItem> items);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onUnFilledPreparations(List<UnFilledPreparationItem> items);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onEditMode(boolean enabled);

    @StateStrategyType(value = SkipStrategy.class)
    void showThankYouFragment();

    @StateStrategyType(value = SkipStrategy.class)
    void onCreateBookingSuccess(Preparation p);

    @StateStrategyType(value = SkipStrategy.class)
    void onCreateBookingFailed(Throwable throwable, Preparation p);

    @StateStrategyType(value = SkipStrategy.class)
    void onCreateBookingFailedAlreadyBooked(Throwable throwable, Preparation p);

    @StateStrategyType(value = SkipStrategy.class)
    void onCreateMassiveBookingSuccess(List<CreateBookingBeautyRequest> requestList);

    @StateStrategyType(value = SkipStrategy.class)
    void onCreateMassiveBookingFailed(Throwable throwable, List<CreateBookingBeautyRequest> requestList);

    @StateStrategyType(value = SkipStrategy.class)
    void showWarningUnfilledDialog(int filledCount, int unFilledCount);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideWarningUnfilledDialog();

    @StateStrategyType(value = SkipStrategy.class)
    void goBack();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setComment(String comment);
}
