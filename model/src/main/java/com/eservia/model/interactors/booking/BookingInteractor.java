package com.eservia.model.interactors.booking;

import com.eservia.model.entity.BeautyBooking;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.remote.rest.booking_beauty.services.booking.BeautyBookingsResponse;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CancelBookingBeautyResponse;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyRequest;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyResponse;
import com.eservia.model.remote.rest.booking_beauty.services.time_slot.BeautyGradualTimeSlotResponse;
import com.eservia.model.remote.rest.booking_beauty.services.time_slot.BeautyTimeSlotResponse;
import com.eservia.model.remote.rest.request.KeyList;

import java.util.List;

import io.reactivex.Observable;

public interface BookingInteractor {

    Observable<CreateBookingBeautyResponse> createBooking(BusinessSector sector,
                                                          CreateBookingBeautyRequest request);

    Observable<BeautyBookingsResponse> getBookings(BusinessSector sector,
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
                                                   Integer let);

    Observable<List<BeautyBooking>> getBookingsFilled(String sectorAlias,
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
                                                      Integer let);

    Observable<CancelBookingBeautyResponse> cancelBooking(BusinessSector sector,
                                                          Integer bookingId);

    Observable<BeautyTimeSlotResponse> getBusinessTimeSlots(BusinessSector sector,
                                                            Integer businessId,
                                                            Integer addressId,
                                                            Integer serviceId,
                                                            Integer staffId,
                                                            String date);

    Observable<BeautyGradualTimeSlotResponse> getBusinessGradualTimeSlots(BusinessSector sector,
                                                                          Integer businessId,
                                                                          Integer addressId,
                                                                          KeyList services,
                                                                          String date);

    Observable<List<BeautyBooking>> fillBookings(String sectorAlias, List<BeautyBooking> bookings);
}
