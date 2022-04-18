package com.eservia.booking.ui.business_page.beauty;

import androidx.annotation.Nullable;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessPhoto;
import com.eservia.model.entity.RestoBookingSettings;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BusinessPageBeautyView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onBusiness(@Nullable Business business);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onPhotosLoadingSuccess(Business business, List<BusinessPhoto> businessPhotos);

    @StateStrategyType(value = SkipStrategy.class)
    void onPhotosLoadingFailed(Throwable throwable);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void enableFavorite();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void disableFavorite();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onAddBusinessToFavoriteSuccess();

    @StateStrategyType(value = SkipStrategy.class)
    void onAddBusinessToFavoriteFailed(Throwable throwable);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onDeleteFavoriteSuccess();

    @StateStrategyType(value = SkipStrategy.class)
    void onDeleteFavoriteBusinessFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void showBookingBeautyPage(Business business);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void createBeautyFragments(boolean showDepartments);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void createRestoFragments();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void createOtherDeliveryFragments();

    @StateStrategyType(value = SkipStrategy.class)
    void openMenuActivity(Business business, Long categoryId);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showBeautyButtons();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showRestoButtons();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showOtherDeliveryButtons();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideRestoBookingButton();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideAllButtons();

    @StateStrategyType(value = SkipStrategy.class)
    void openRestoDeliveryBasket(Business business);

    @StateStrategyType(value = SkipStrategy.class)
    void openRestoTableBooking(Business business, RestoBookingSettings bookingSettings);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void refreshBasketState(int orderItemsCount);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showBasketMenu();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideBasketMenu();

    @StateStrategyType(value = SkipStrategy.class)
    void showPrepaymentDialog();

    @StateStrategyType(value = SkipStrategy.class)
    void hidePrepaymentDialog();
}
