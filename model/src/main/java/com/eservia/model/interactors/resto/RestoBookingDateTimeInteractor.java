package com.eservia.model.interactors.resto;

import com.eservia.model.entity.RestoBookingSettings;

import org.joda.time.DateTime;

import java.util.List;

import io.reactivex.Observable;

public interface RestoBookingDateTimeInteractor {

    Observable<RestoBookingSettings> getRestoBookingSettings(Long addressId);

    Observable<List<DateTime>> computeWorkDays(RestoBookingSettings settings);

    Observable<Boolean> validateBookingTime(DateTime time, RestoBookingSettings settings);
}
