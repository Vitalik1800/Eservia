package com.eservia.model.interactors.resto;

import com.eservia.model.entity.OrderResto;
import com.eservia.model.entity.OrderTypeResto;
import com.eservia.model.entity.RestoGuestOrder;
import com.eservia.model.local.order_resto.OrderRestoRepository;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.delivery.services.deliveries.PostRestoDeliveryRequest;
import com.eservia.model.remote.rest.order_resto.services.orders.PostRestoOrderRequest;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RestoDeliveryDateTimeInteractorImpl implements RestoDeliveryDateTimeInteractor {

    private final RestManager restManager;

    private final OrderRestoRepository orderRestoRepository;

    public RestoDeliveryDateTimeInteractorImpl(RestManager restManager,
                                               OrderRestoRepository orderRestoRepository) {
        this.restManager = restManager;
        this.orderRestoRepository = orderRestoRepository;
    }

    @Override
    public Observable<List<DateTime>> computeWorkDays() {
        return Observable.fromCallable(this::getWorkDays);
    }

    @Override
    public Observable<Boolean> validateDeliveryTime(DateTime time) {
        return Observable.fromCallable(() -> isDeliveryTimeValid(time));
    }

    @Override
    public Observable<Boolean> createOrder(long businessId, long addressId,
                                           String expectedDeliveryDate, Long streetId,
                                           String location, String description,
                                           String clientPhone, String clientName) {
        return orderRestoRepository.findAllWithBusinessId(businessId)
                .flatMap(orders -> {
                    OrderResto order = orders.get(0);

                    RestoGuestOrder guestOrder = new RestoGuestOrder();
                    guestOrder.getOrderItems().addAll(order.getOrderItems());

                    PostRestoOrderRequest request = new PostRestoOrderRequest();
                    request.setAddressId(order.getAddressId());
                    request.setDepartmentId(order.getDepartmentId());
                    request.setMenuVersion(order.getMenuVersion());
                    request.setInitializationId(order.getInitializationId());
                    request.setOrderTypeId((long) OrderTypeResto.DELIVERY);
                    request.getGuestOrders().add(guestOrder);

                    return restManager.postRestoOrder(request);
                })
                .flatMap(orderResponse -> {
                    PostRestoDeliveryRequest request = new PostRestoDeliveryRequest();
                    request.setAddressId(addressId);
                    request.setExpectedDeliveryDate(expectedDeliveryDate);
                    request.setStreetId(streetId);
                    request.setLocation(location);
                    request.setDescription(description);
                    request.setPrice(String.valueOf(orderResponse.getData().getTotalPrice()));
                    request.setOrderId(orderResponse.getData().getId());
                    request.setClientPhone(clientPhone);
                    request.setClientName(clientName);

                    return restManager.postRestoDelivery(request);
                })
                .flatMap(deliveryResponse -> orderRestoRepository.deleteAllWithBusinessId(businessId))
                .map(success -> Boolean.TRUE);
    }

    private Boolean isDeliveryTimeValid(DateTime time) {
        DateTime now = DateTime.now();
        DateTime minDeliveryTime = now.plusMinutes(
                RestoDeliveryRules.MIN_MINUTES_FOR_DELIVERY_RESTO_ORDER);
        DateTime maxDeliveryTime = now.plusDays(
                RestoDeliveryRules.MAX_DAYS_FOR_DELIVERY_RESTO_ORDER);

        return time.getMillis() >= minDeliveryTime.getMillis()
                && time.getMillis() <= maxDeliveryTime.getMillis();
    }

    private List<DateTime> getWorkDays() {
        int maxDaysForDelivery = 14;
        List<DateTime> workDays = new ArrayList<>();
        DateTime day = DateTime.now();
        for (int i = 0; i < maxDaysForDelivery; i++) {
            workDays.add(day);
            day = day.plusDays(1);
        }
        return workDays;
    }
}
