package com.eservia.booking.ui.staff.beauty;

import androidx.annotation.Nullable;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.BeautyStaff;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface StaffBeautyView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onStaff(@Nullable BeautyStaff staff);

    @StateStrategyType(value = SkipStrategy.class)
    void closeActivity();
}
