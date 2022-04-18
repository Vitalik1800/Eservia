package com.eservia.booking.ui.home.bookings.delivery_info;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.entity.OrderRestoItem;
import com.eservia.model.entity.RestoDelivery;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import moxy.InjectViewState;

@InjectViewState
public class DeliveryInfoPresenter extends BasePresenter<DeliveryInfoView> {

    private RestoDelivery mDelivery;

    public DeliveryInfoPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mDelivery = EventBus.getDefault().getStickyEvent(DeliveryInfoExtra.class).delivery;
        bindDeliveryInfo();
        bindOrderItems();
    }

    private void bindDeliveryInfo() {
        if (mDelivery == null) return;
        getViewState().bindOrderInformation(mDelivery.getClientName(), mDelivery.getClientPhone(),
                mDelivery.getLocation(), mDelivery.getDescription());
    }

    private void bindOrderItems() {
        List<OrderRestoItem> orderRestoItems = new ArrayList<>();
        if (mDelivery != null
                && mDelivery.getOrder() != null
                && mDelivery.getOrder().getGuestOrders() != null
                && !mDelivery.getOrder().getGuestOrders().isEmpty()) {
            orderRestoItems.addAll(mDelivery.getOrder().getGuestOrders().get(0).getOrderItems());
        }
        getViewState().onOrderItemsLoaded(orderRestoItems);
    }
}
