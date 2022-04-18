package com.eservia.booking.ui.business_page.beauty.feedback;

import com.eservia.booking.common.view.LoadingView;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BusinessFeedbackBeautyView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void initMaxMinDefault(int maxRating, int minRating, int defaultRating);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setQualityStars(List<StarItem> stars);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setPurityStars(List<StarItem> stars);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setConvenienceStars(List<StarItem> stars);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onTotalRating(float rating);

    @StateStrategyType(value = SkipStrategy.class)
    void onCreateCommentSuccess();

    @StateStrategyType(value = SkipStrategy.class)
    void onCreateCommentFailed(Throwable error);
}
