package com.eservia.model.interactors.resto;

import com.eservia.model.entity.RestoBooking;
import com.eservia.model.entity.RestoDelivery;

import java.util.List;

import io.reactivex.Observable;

public interface RestoBookingsInteractor {

    Observable<List<RestoBooking>> getRestoBookingsActive(Integer pageSize, Integer pageIndex);

    Observable<List<RestoBooking>> getRestoBookingsHistory(Integer pageSize, Integer pageIndex);

    Observable<RestoBooking> cancelRestoBooking(Long id, Long addressId);

    Observable<List<RestoDelivery>> getRestoDeliveriesActive(Integer pageSize, Integer pageIndex);

    Observable<List<RestoDelivery>> getRestoDeliveriesArchive(Integer pageSize, Integer pageIndex);

    Observable<List<RestoBooking>> fillRestoBookings(List<RestoBooking> bookings);

    Observable<List<RestoDelivery>> fillRestoDeliveries(List<RestoDelivery> deliveries);
}
