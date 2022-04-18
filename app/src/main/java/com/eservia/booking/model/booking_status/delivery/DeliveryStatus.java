package com.eservia.booking.model.booking_status.delivery;

import android.util.Pair;

import com.eservia.booking.model.booking_status.Status;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.RestoDeliverySettlement;
import com.eservia.model.entity.RestoDeliveryStreet;

import org.joda.time.DateTime;

public class DeliveryStatus extends Status {

    private RestoDeliverySettlement settlement;

    private RestoDeliveryStreet street;

    private String name;

    private String phone;

    private String city;

    private String deliveryAddress;

    private String house;

    private String apartment;

    private String doorPhoneCode;

    private String comment;

    private Pair<Integer, Integer> expectedDeliveryHourMin;

    private DateTime expectedDeliveryDay;

    public DeliveryStatus(Business business) {
        super(business);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getDoorPhoneCode() {
        return doorPhoneCode;
    }

    public Pair<Integer, Integer> getExpectedDeliveryHourMin() {
        return expectedDeliveryHourMin;
    }

    public void setExpectedDeliveryHourMin(Pair<Integer, Integer> expectedDeliveryHourMin) {
        this.expectedDeliveryHourMin = expectedDeliveryHourMin;
    }

    public DateTime getExpectedDeliveryDay() {
        return expectedDeliveryDay;
    }

    public void setExpectedDeliveryDay(DateTime expectedDeliveryDay) {
        this.expectedDeliveryDay = expectedDeliveryDay;
    }

    public void setDoorPhoneCode(String doorPhoneCode) {
        this.doorPhoneCode = doorPhoneCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RestoDeliverySettlement getSettlement() {
        return settlement;
    }

    public void setSettlement(RestoDeliverySettlement settlement) {
        this.settlement = settlement;
    }

    public RestoDeliveryStreet getStreet() {
        return street;
    }

    public void setStreet(RestoDeliveryStreet street) {
        this.street = street;
    }
}
