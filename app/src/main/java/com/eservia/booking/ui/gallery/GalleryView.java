package com.eservia.booking.ui.gallery;

import com.eservia.booking.common.view.LoadingView;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface GalleryView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onStartPosition(int startPosition);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void initWith(List<String> urls);
}
