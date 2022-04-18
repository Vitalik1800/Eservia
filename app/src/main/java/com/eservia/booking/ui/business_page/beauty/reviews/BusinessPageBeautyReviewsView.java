package com.eservia.booking.ui.business_page.beauty.reviews;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.Business;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BusinessPageBeautyReviewsView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onCommentsLoadingSuccess(List<ReviewsAdapterItem> comments);

    @StateStrategyType(value = SkipStrategy.class)
    void onCommentsLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void openBeautyFeedback(Business business);
}
