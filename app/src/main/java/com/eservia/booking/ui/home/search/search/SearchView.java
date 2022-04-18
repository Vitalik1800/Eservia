package com.eservia.booking.ui.home.search.search;

import androidx.annotation.Nullable;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.booking.ui.home.search.search.sheet.SheetAdapterItem;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessSector;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface SearchView extends LoadingView {

    @StateStrategyType(value = SkipStrategy.class)
    void requiredArgs();

    @StateStrategyType(value = SkipStrategy.class)
    void requestFocus();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onBusinessesLoadingSuccess(List<Business> businessList, BusinessSector sector);

    @StateStrategyType(value = SkipStrategy.class)
    void onBusinessesLoadingFailed(Throwable throwable);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onCategoriesLoadingSuccess(List<CategoryAdapterItem> categories);

    @StateStrategyType(value = SkipStrategy.class)
    void onCategoriesLoadingFailed(Throwable throwable);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showCategoriesProgress();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideCategoriesProgress();

    @StateStrategyType(value = AddToEndStrategy.class)
    void setSelectedCategory(boolean selected, Integer categoryId);

    @StateStrategyType(value = SkipStrategy.class)
    void showSortDialog(List<SheetAdapterItem> items);

    @StateStrategyType(value = SkipStrategy.class)
    void showCitiesDialog(List<SheetAdapterItem> items);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideSheetDialog();

    @StateStrategyType(value = SkipStrategy.class)
    void showBookingBeautyActivity(Business business);

    @StateStrategyType(value = SkipStrategy.class)
    void showBusinessBeautyActivity(Business business);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onBusinessesPaginationStarted();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onBusinessesPaginationFinished();

    @StateStrategyType(value = SkipStrategy.class)
    void showSuggestBusinessActivity();

    @StateStrategyType(value = SkipStrategy.class)
    void openSearchBusinessesMap(List<Business> businesses, BusinessSector sector);

    @StateStrategyType(value = SkipStrategy.class)
    void requestFineLocationPermission();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showSelectedCity(@Nullable String city);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onPopularBusinessesLoadingSuccess(List<Business> businessList);

    @StateStrategyType(value = SkipStrategy.class)
    void onPopularBusinessesLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void goBack();
}
