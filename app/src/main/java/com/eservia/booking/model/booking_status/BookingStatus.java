package com.eservia.booking.model.booking_status;

import com.eservia.booking.model.booking_status.beauty.BeautyStatus;
import com.eservia.booking.model.booking_status.delivery.DeliveryStatus;
import com.eservia.booking.model.booking_status.resto.RestoStatus;
import com.eservia.model.entity.Business;

import javax.annotation.Nullable;

public class BookingStatus {

    @Nullable
    private BeautyStatus beautyStatus;

    @Nullable
    private RestoStatus restoStatus;

    @Nullable
    private DeliveryStatus deliveryStatus;

    public void setStatus(Business business, BookingType type) {
        switch (type) {
            case BEAUTY: {
                beautyStatus = new BeautyStatus(business);
                break;
            }
            case RESTO: {
                restoStatus = new RestoStatus(business);
                break;
            }
            case DELIVERY: {
                deliveryStatus = new DeliveryStatus(business);
                break;
            }
            default: {
                throw new RuntimeException("Booking is not available for " + type.name() + ".");
            }
        }
    }

    @Nullable
    public BeautyStatus getBeautyStatus() {
        return beautyStatus;
    }

    @Nullable
    public RestoStatus getRestoStatus() {
        return restoStatus;
    }

    @Nullable
    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void removeBeautyStatus() {
        beautyStatus = null;
    }

    public void removeRestoStatus() {
        restoStatus = null;
    }

    public void removeDeliveryStatus() {
        deliveryStatus = null;
    }

    public enum BookingType {
        BEAUTY, RESTO, DELIVERY
    }
}
