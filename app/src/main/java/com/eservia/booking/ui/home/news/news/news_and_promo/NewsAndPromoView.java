package com.eservia.booking.ui.home.news.news.news_and_promo;

import androidx.annotation.Nullable;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.booking.ui.home.search.search.sheet.SheetAdapterItem;
import com.eservia.model.entity.Marketing;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface NewsAndPromoView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onNewsLoadingSuccess(List<Marketing> marketingList);

    @StateStrategyType(value = SkipStrategy.class)
    void onNewsLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void showEventDetailBeautyPage(Marketing marketing);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showSelectedCity(@Nullable String city);

    @StateStrategyType(value = SkipStrategy.class)
    void showCitiesDialog(List<SheetAdapterItem> items);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideSheetDialog();
}
