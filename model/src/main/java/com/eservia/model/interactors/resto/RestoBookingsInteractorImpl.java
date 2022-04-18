package com.eservia.model.interactors.resto;

import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.RestoBooking;
import com.eservia.model.entity.RestoDelivery;
import com.eservia.model.entity.RestoDepartment;
import com.eservia.model.entity.RestoOrderResponseData;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.booking_resto.services.booking.CancelRestoBookingRequest;
import com.eservia.model.remote.rest.booking_resto.services.booking.CancelRestoBookingResponse;
import com.eservia.model.remote.rest.request.KeyList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;

public class RestoBookingsInteractorImpl implements RestoBookingsInteractor {

    private final RestManager restManager;

    public RestoBookingsInteractorImpl(RestManager restManager) {
        this.restManager = restManager;
    }

    @Override
    public Observable<List<RestoBooking>> getRestoBookingsActive(Integer pageSize, Integer pageIndex) {
        return restManager.getRestoBookingsActive(pageSize, pageIndex)
                .flatMap(response -> Observable.just(response.getData()))
                .flatMap(this::onBookingsResponse);
    }

    @Override
    public Observable<List<RestoBooking>> getRestoBookingsHistory(Integer pageSize, Integer pageIndex) {
        return restManager.getRestoBookingsHistory(pageSize, pageIndex)
                .flatMap(response -> Observable.just(response.getData()))
                .flatMap(this::onBookingsResponse);
    }

    @Override
    public Observable<List<RestoDelivery>> getRestoDeliveriesActive(Integer pageSize, Integer pageIndex) {
        return restManager.getRestoDeliveries(Arrays.asList(RestoDelivery.STATUS_CREATED,
                RestoDelivery.STATUS_NOT_AGREED,
                RestoDelivery.STATUS_WAITING,
                RestoDelivery.STATUS_ACCEPTED,
                RestoDelivery.STATUS_IN_PROGRESS,
                RestoDelivery.STATUS_PENDING), pageSize, pageIndex)
                .flatMap(response -> Observable.just(response.getData()))
                .flatMap(this::onDeliveriesResponse);
    }

    @Override
    public Observable<List<RestoDelivery>> getRestoDeliveriesArchive(Integer pageSize, Integer pageIndex) {
        return restManager.getRestoDeliveries(Arrays.asList(RestoDelivery.STATUS_DONE,
                RestoDelivery.STATUS_CANCELLED,
                RestoDelivery.STATUS_OVERDUE), pageSize, pageIndex)
                .flatMap(response -> Observable.just(response.getData()))
                .flatMap(this::onDeliveriesResponse);
    }

    @Override
    public Observable<RestoBooking> cancelRestoBooking(Long id, Long addressId) {
        return restManager.cancelRestoBooking(
                new CancelRestoBookingRequest(id, null, addressId))
                .map(CancelRestoBookingResponse::getData);
    }

    @Override
    public Observable<List<RestoBooking>> fillRestoBookings(List<RestoBooking> bookings) {
        return onBookingsResponse(bookings);
    }

    @Override
    public Observable<List<RestoDelivery>> fillRestoDeliveries(List<RestoDelivery> deliveries) {
        return onDeliveriesResponse(deliveries);
    }

    private Observable<List<RestoBooking>> onBookingsResponse(List<RestoBooking> bookings) {
        if (bookings.isEmpty()) {
            return Observable.just(new ArrayList<>());
        }
        HashSet<Integer> businessesIdsSet = new HashSet<>();
        HashSet<Integer> addressesIdsSet = new HashSet<>();
        HashSet<Integer> departmentIdsSet = new HashSet<>();

        for (RestoBooking booking : bookings) {
            businessesIdsSet.add((int) booking.getBusinessId().longValue());
            addressesIdsSet.add((int) booking.getAddressId().longValue());
            if (booking.getDepartmentId() != null) {
                departmentIdsSet.add((int) booking.getDepartmentId().longValue());
            }
        }
        return fillRestoBookings(bookings, businessesIdsSet, addressesIdsSet, departmentIdsSet);
    }

    private Observable<List<RestoDelivery>> onDeliveriesResponse(List<RestoDelivery> loadedDeliveries) {
        if (loadedDeliveries.isEmpty()) {
            return Observable.just(new ArrayList<>());
        }
        HashSet<Integer> ordersIdsSet = new HashSet<>();
        for (RestoDelivery delivery : loadedDeliveries) {
            ordersIdsSet.add((int) delivery.getOrderId().longValue());
        }
        return fillRestoDeliveries(loadedDeliveries, ordersIdsSet);
    }

    private Observable<List<RestoDelivery>> fillRestoDeliveries(List<RestoDelivery> deliveries,
                                                                HashSet<Integer> orderIds) {

        return restManager.getRestoOrders(new ArrayList<>(orderIds))
                .flatMap(orders -> {
                    for (RestoDelivery delivery : deliveries) {
                        for (RestoOrderResponseData order : orders) {
                            if (delivery.getOrderId().equals(order.getId())) {
                                delivery.setOrder(order);
                            }
                        }
                    }
                    return Observable.just(deliveries);
                })
                .flatMap(deliveriesWithOrders -> {
                    if (deliveriesWithOrders.isEmpty()) {
                        return Observable.just(new ArrayList<>());
                    }

                    HashSet<Integer> businessesIdsSet = new HashSet<>();
                    HashSet<Integer> addressesIdsSet = new HashSet<>();

                    for (RestoDelivery delivery : deliveriesWithOrders) {
                        businessesIdsSet.add((int) delivery.getOrder().getBusinessId().longValue());
                        addressesIdsSet.add((int) delivery.getOrder().getAddressId().longValue());
                    }
                    return fillRestoDeliveriesWithBusinessesAndAddresses(deliveriesWithOrders,
                            businessesIdsSet, addressesIdsSet);
                });
    }

    private Observable<List<RestoDelivery>> fillRestoDeliveriesWithBusinessesAndAddresses(
            List<RestoDelivery> deliveries,
            HashSet<Integer> businessesIds,
            HashSet<Integer> addressesIds) {

        return restManager.getBusinesses(null, new KeyList().addAll(businessesIds), null, null, null, null, null, Business.WITH_TRASHED, null, null, null, null)
                .flatMap(businessEntities -> {
                    for (RestoDelivery delivery : deliveries) {
                        for (Business business : businessEntities) {
                            if (delivery.getOrder().getBusinessId().equals((long) business.getId())) {
                                delivery.setBusiness(business);
                            }
                        }
                    }
                    return Observable.just(deliveries);
                })
                .flatMap(deliveries1 -> restManager.getAddresses(null, new KeyList().addAll(addressesIds), null, null, null, null))
                .flatMap(addressesResponse -> {
                    for (RestoDelivery delivery : deliveries) {
                        for (Address address : addressesResponse.getData()) {
                            if (delivery.getOrder().getAddressId().equals((long) address.getId())) {
                                delivery.setAddress(address);
                            }
                        }
                    }
                    return Observable.just(deliveries);
                });
    }

    private Observable<List<RestoBooking>> fillRestoBookings(List<RestoBooking> bookings,
                                                             HashSet<Integer> businessesIdsSet,
                                                             HashSet<Integer> addressesIdsSet,
                                                             HashSet<Integer> departmentsIdsSet) {

        return restManager.getBusinesses(null, new KeyList().addAll(businessesIdsSet), null, null, null, null, null, Business.WITH_TRASHED, null, null, null, null)
                .flatMap(businessEntities -> {
                    for (RestoBooking booking : bookings) {
                        for (Business business : businessEntities) {
                            if (booking.getBusinessId().equals((long) business.getId())) {
                                booking.setBusiness(business);
                            }
                        }
                    }
                    return Observable.just(bookings);
                })
                .flatMap(bookings1 -> restManager.getAddresses(null, new KeyList().addAll(addressesIdsSet), null, null, null, null))
                .flatMap(addressesResponse -> {
                    for (RestoBooking booking : bookings) {
                        for (Address address : addressesResponse.getData()) {
                            if (booking.getAddressId().equals((long) address.getId())) {
                                booking.setAddress(address);
                            }
                        }
                    }
                    return Observable.just(bookings);
                })
                .flatMap(bookings1 -> restManager.getRestoDepartmentsByIds(new ArrayList<>(departmentsIdsSet)))
                .flatMap(departmentsResponse -> {
                    for (RestoBooking booking : bookings) {
                        for (RestoDepartment department : departmentsResponse.getData()) {
                            if (booking.getDepartmentId() != null
                                    && booking.getDepartmentId().equals(department.getId())) {
                                booking.setDepartment(department);
                            }
                        }
                    }
                    return Observable.just(bookings);
                });
    }
}
