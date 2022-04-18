package com.eservia.booking.ui.business_page.gallery;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.BusinessPhoto;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface GalleryView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onStartPosition(int startPosition);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void initWith(List<BusinessPhoto> businessPhotos);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onPhotosLoadingSuccess(List<BusinessPhoto> businessPhotos);

    @StateStrategyType(value = SkipStrategy.class)
    void onPhotosLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void toggleControls();
}
