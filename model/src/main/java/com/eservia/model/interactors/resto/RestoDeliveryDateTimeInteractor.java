package com.eservia.model.interactors.resto;

import org.joda.time.DateTime;

import java.util.List;

import io.reactivex.Observable;

public interface RestoDeliveryDateTimeInteractor {

    Observable<List<DateTime>> computeWorkDays();

    Observable<Boolean> validateDeliveryTime(DateTime time);

    Observable<Boolean> createOrder(long businessId, long addressId,
                                    String expectedDeliveryDate, Long streetId,
                                    String location, String description,
                                    String clientPhone, String clientName);
}
