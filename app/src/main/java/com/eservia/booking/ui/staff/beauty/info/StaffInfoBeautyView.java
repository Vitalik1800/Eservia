package com.eservia.booking.ui.staff.beauty.info;

import androidx.annotation.Nullable;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.booking.ui.gallery.GalleryExtra;
import com.eservia.model.entity.BeautyStaff;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface StaffInfoBeautyView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onStaff(@Nullable BeautyStaff staff);

    @StateStrategyType(value = SkipStrategy.class)
    void showGalleryActivity(GalleryExtra extra);
}
