package com.eservia.booking.ui.business_page.marketing;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.Marketing;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface BusinessMarketingsView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onNewsLoadingSuccess(List<Marketing> marketingList);

    @StateStrategyType(value = SkipStrategy.class)
    void onNewsLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void showEventDetailBeautyPage(Marketing marketing);
}
