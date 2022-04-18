package com.eservia.booking.util;

import com.eservia.model.entity.BusinessCategory;

import java.util.List;

public class CategoryUtil {

    public static boolean isBeautyBookingType(List<BusinessCategory> categories) {
        for (BusinessCategory category : categories) {
            if (category.getCategory().equals(BusinessCategory.CATEGORY_BEAUTY_BOOKING)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRestoDeliveryType(List<BusinessCategory> categories) {
        for (BusinessCategory category : categories) {
            if (category.getCategory().equals(BusinessCategory.CATEGORY_RESTO_DELIVERY)) {
                return true;
            }
        }
        return false;
    }
}
