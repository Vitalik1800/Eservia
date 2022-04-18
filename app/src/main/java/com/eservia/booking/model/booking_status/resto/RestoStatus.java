package com.eservia.booking.model.booking_status.resto;

import android.util.Pair;

import com.eservia.booking.model.booking_status.Status;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.RestoBookingSettings;
import com.eservia.model.entity.RestoDepartment;

import org.joda.time.DateTime;

public class RestoStatus extends Status {

    private RestoBookingSettings bookingSettings;

    private int numberOfPersons;

    private Integer visitDuration;

    private Pair<Integer, Integer> bookingHourMin;

    private DateTime bookingDay;

    private RestoDepartment department;

    public RestoStatus(Business business) {
        super(business);
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public Integer getVisitDuration() {
        return visitDuration;
    }

    public void setVisitDuration(Integer visitDuration) {
        this.visitDuration = visitDuration;
    }

    public Pair<Integer, Integer> getBookingHourMin() {
        return bookingHourMin;
    }

    public void setBookingHourMin(Pair<Integer, Integer> bookingHourMin) {
        this.bookingHourMin = bookingHourMin;
    }

    public DateTime getBookingDay() {
        return bookingDay;
    }

    public void setBookingDay(DateTime bookingDay) {
        this.bookingDay = bookingDay;
    }

    public RestoBookingSettings getBookingSettings() {
        return bookingSettings;
    }

    public void setBookingSettings(RestoBookingSettings bookingSettings) {
        this.bookingSettings = bookingSettings;
    }

    public RestoDepartment getDepartment() {
        return department;
    }

    public void setDepartment(RestoDepartment department) {
        this.department = department;
    }
}
