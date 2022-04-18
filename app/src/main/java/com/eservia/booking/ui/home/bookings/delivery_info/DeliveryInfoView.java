package com.eservia.booking.ui.home.bookings.delivery_info;

import androidx.annotation.Nullable;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.OrderRestoItem;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface DeliveryInfoView extends LoadingView {

    void onOrderItemsLoaded(List<OrderRestoItem> adapterItems);

    @StateStrategyType(value = SkipStrategy.class)
    void onOrderItemsLoadingFailed(Throwable error);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindOrderInformation(String clientName, String phone, String location, @Nullable String comment);
}
