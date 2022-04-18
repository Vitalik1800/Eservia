package com.eservia.booking.ui.business_page.restaurant.menu;

import android.util.Pair;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.OrderRestoCategory;
import com.eservia.model.entity.OrderRestoNomenclature;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MenuView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onMenuLoaded(Pair<List<OrderRestoCategory>, List<OrderRestoNomenclature>> categoryItem);

    @StateStrategyType(value = SkipStrategy.class)
    void onMenuLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void initCategoryId();
}
