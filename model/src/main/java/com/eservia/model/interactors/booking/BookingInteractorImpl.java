package com.eservia.model.interactors.booking;

import com.eservia.model.entity.Address;
import com.eservia.model.entity.BeautyBooking;
import com.eservia.model.entity.BeautyService;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.booking_beauty.services.booking.BeautyBookingsResponse;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CancelBookingBeautyResponse;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyRequest;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyResponse;
import com.eservia.model.remote.rest.booking_beauty.services.time_slot.BeautyGradualTimeSlotResponse;
import com.eservia.model.remote.rest.booking_beauty.services.time_slot.BeautyTimeSlotResponse;
import com.eservia.model.remote.rest.request.KeyList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;

public class BookingInteractorImpl extends BaseBookingInteractor {

    public BookingInteractorImpl(RestManager restManager) {
        super(restManager);
    }

    @Override
    public Observable<CreateBookingBeautyResponse> createBooking(BusinessSector sector,
                                                                 CreateBookingBeautyRequest request) {
        return isHealthBooking(sector) ?
                restManager.createBookingHealth(request) :
                restManager.createBookingBeauty(request);
    }

    @Override
    public Observable<BeautyBookingsResponse> getBookings(BusinessSector sector,
                                                          String userId,
                                                          String query,
                                                          KeyList ids,
                                                          Integer businessId,
                                                          Integer addressId,
                                                          Integer staffId,
                                                          Integer serviceId,
                                                          KeyList status,
                                                          KeyList decision,
                                                          Integer type,
                                                          Boolean isAppeared,
                                                          String sort,
                                                          Integer limit,
                                                          Integer page,
                                                          Integer let) {
        return isHealthBooking(sector) ?
                restManager.getHealthBookings(userId, query, ids, businessId, addressId, staffId,
                        serviceId, status, decision, type, isAppeared, sort, limit, page, let) :
                restManager.getBeautyBookings(userId, query, ids, businessId, addressId, staffId,
                        serviceId, status, decision, type, isAppeared, sort, limit, page, let);
    }

    @Override
    public Observable<List<BeautyBooking>> getBookingsFilled(String sectorAlias,
                                                             String userId,
                                                             String query,
                                                             KeyList ids,
                                                             Integer businessId,
                                                             Integer addressId,
                                                             Integer staffId,
                                                             Integer serviceId,
                                                             KeyList status,
                                                             KeyList decision,
                                                             Integer type,
                                                             Boolean isAppeared,
                                                             String sort,
                                                             Integer limit,
                                                             Integer page,
                                                             Integer let) {
        return isHealthBooking(sectorAlias) ?
                restManager.getHealthBookings(userId, query, ids, businessId, addressId, staffId,
                        serviceId, status, decision, type, isAppeared, sort, limit, page, let)
                        .flatMap(response -> Observable.just(response.getData()))
                        .flatMap(response -> onBookingsResponse(sectorAlias, response)) :
                restManager.getBeautyBookings(userId, query, ids, businessId, addressId, staffId,
                        serviceId, status, decision, type, isAppeared, sort, limit, page, let)
                        .flatMap(response -> Observable.just(response.getData()))
                        .flatMap(response -> onBookingsResponse(sectorAlias, response));
    }

    @Override
    public Observable<CancelBookingBeautyResponse> cancelBooking(BusinessSector sector,
                                                                 Integer bookingId) {
        return isHealthBooking(sector) ?
                restManager.cancelBookingHealth(bookingId) :
                restManager.cancelBookingBeauty(bookingId);
    }

    @Override
    public Observable<BeautyTimeSlotResponse> getBusinessTimeSlots(BusinessSector sector,
                                                                   Integer businessId,
                                                                   Integer addressId,
                                                                   Integer serviceId,
                                                                   Integer staffId,
                                                                   String date) {
        return isHealthBooking(sector) ?
                restManager.getBusinessHealthTimeSlots(businessId, addressId, serviceId, staffId, date) :
                restManager.getBusinessBeautyTimeSlots(businessId, addressId, serviceId, staffId, date);
    }

    @Override
    public Observable<BeautyGradualTimeSlotResponse> getBusinessGradualTimeSlots(BusinessSector sector,
                                                                                 Integer businessId,
                                                                                 Integer addressId,
                                                                                 KeyList services,
                                                                                 String date) {
        return isHealthBooking(sector) ?
                restManager.getBusinessHealthGradualTimeSlots(businessId, addressId, services, date) :
                restManager.getBusinessBeautyGradualTimeSlots(businessId, addressId, services, date);
    }

    @Override
    public Observable<List<BeautyBooking>> fillBookings(String sectorAlias, List<BeautyBooking> bookings) {
        return onBookingsResponse(sectorAlias, bookings);
    }

    private boolean isHealthBooking(BusinessSector sector) {
        return sector.getSector().equals(BusinessSector.HEALTH);
    }

    private boolean isHealthBooking(String sectorAlias) {
        return sectorAlias.equals(BusinessSector.HEALTH);
    }

    private Observable<List<BeautyBooking>> onBookingsResponse(String sectorAlias, List<BeautyBooking> response) {
        if (response.isEmpty()) {
            return Observable.just(new ArrayList<>());
        }
        HashSet<Integer> businessesIdsSet = new HashSet<>();
        HashSet<Integer> addressesIdsSet = new HashSet<>();
        HashSet<Integer> staffsIdsSet = new HashSet<>();
        HashSet<Integer> servicesIdsSet = new HashSet<>();

        for (BeautyBooking booking : response) {
            businessesIdsSet.add(booking.getBusinessId());
            addressesIdsSet.add(booking.getAddressId());
            staffsIdsSet.add(booking.getStaffId());
            servicesIdsSet.add(booking.getServiceId());
        }
        return fillBookings(sectorAlias, response,
                businessesIdsSet,
                addressesIdsSet,
                staffsIdsSet,
                servicesIdsSet);
    }

    private Observable<List<BeautyBooking>> fillBookings(String sectorAlias,
                                                         List<BeautyBooking> bookings,
                                                         HashSet<Integer> businessesIdsSet,
                                                         HashSet<Integer> addressesIdsSet,
                                                         HashSet<Integer> staffsIdsSet,
                                                         HashSet<Integer> servicesIdsSet) {

        return restManager.getBusinesses(null, new KeyList().addAll(businessesIdsSet), null, null, null, null, null, Business.WITH_TRASHED, null, null, null, null)
                .flatMap(businessEntities -> {
                    for (BeautyBooking booking : bookings) {
                        for (Business business : businessEntities) {
                            if (booking.getBusinessId().equals(business.getId())) {
                                booking.setBusiness(business);
                            }
                        }
                    }
                    return Observable.just(bookings);
                })
                .flatMap(bookings1 -> restManager.getAddresses(null, new KeyList().addAll(addressesIdsSet), null, null, null, null))
                .flatMap(addressesResponse -> {
                    for (BeautyBooking booking : bookings) {
                        for (Address address : addressesResponse.getData()) {
                            if (booking.getAddressId().equals(address.getId())) {
                                booking.setAddress(address);
                            }
                        }
                    }
                    return Observable.just(bookings);
                })
                .flatMap(bookings1 -> isHealthBooking(sectorAlias) ?
                        restManager.getBusinessHealthStaffs(null, new KeyList().addAll(staffsIdsSet), null, null, null, null, null, null, null, null, null) :
                        restManager.getBusinessBeautyStaffs(null, new KeyList().addAll(staffsIdsSet), null, null, null, null, null, null, null, null, null))
                .flatMap(staffResponse -> {
                    for (BeautyBooking booking : bookings) {
                        for (BeautyStaff staff : staffResponse.getData()) {
                            if (booking.getStaffId().equals(staff.getId())) {
                                booking.setStaff(staff);
                            }
                        }
                    }
                    return Observable.just(bookings);
                })
                .flatMap(bookings1 -> isHealthBooking(sectorAlias) ?
                        restManager.getHealthServices(null, new KeyList().addAll(servicesIdsSet), null, null, null, null, null, null, null, null) :
                        restManager.getBeautyServices(null, new KeyList().addAll(servicesIdsSet), null, null, null, null, null, null, null, null))
                .flatMap(servicesResponse -> {
                    for (BeautyBooking booking : bookings) {
                        for (BeautyService service : servicesResponse.getData()) {
                            if (booking.getServiceId().equals(service.getId())) {
                                booking.setService(service);
                            }
                        }
                    }
                    return Observable.just(bookings);
                });
    }
}
