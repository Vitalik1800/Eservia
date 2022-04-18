package com.eservia.model.remote;

import com.eservia.model.BuildConfig;

public class UrlList {

    private static final Route route;

    static {
        route = null;
    }

    //stage

    private static final String STAGE_BOOKING_IMAGE_RESIZE_PREFIX = "/preview/r/";

    private static final String STAGE_BROADCAST_URL = "https://stage.eservia.com";
    private static final String STAGE_BROADCAST_PATH = "/ws/socket.io";

    private static final String STAGE_LOCKED_BOOKINGS_URL = "https://stage.eservia.com/ws/booking/lock";

    private static final String STAGE_USERS_API_URL = "https://auth.staging.eservia.com";
    private static final String STAGE_USERS_API_VERSION = "/api/v0.0/";
    private static final String STAGE_USERS_API_IMAGE = "https://staging.eservia.com/image";

    private static final String STAGE_TERMS_OF_USING = "https://eservia.com/pages/terms";
    private static final String STAGE_POLICY_OF_CONFIDENCE = "https://eservia.com/pages/privacy";

    private static final String STAGE_FILE_API_URL = "https://media.staging.eservia.com";
    private static final String STAGE_FILE_API_VERSION = "/api/v0.0/";

    private static final String STAGE_BUSINESS_API_URL = "https://business.staging.eservia.com";
    private static final String STAGE_BUSINESS_API_VERSION = "/api/v1.0/";

    private static final String STAGE_BUSINESS_BEAUTY_API_URL = "https://business-beauty.staging.eservia.com";
    private static final String STAGE_BUSINESS_BEAUTY_API_VERSION = "/api/v1.0/";

    private static final String STAGE_BOOKING_BEAUTY_API_URL = "https://booking-beauty.staging.eservia.com";
    private static final String STAGE_BOOKING_BEAUTY_API_VERSION = "/api/v1.0/";

    private static final String STAGE_BUSINESS_HEALTH_API_URL = "https://business-health.staging.eservia.com";
    private static final String STAGE_BUSINESS_HEALTH_API_VERSION = "/api/v1.0/";

    private static final String STAGE_BOOKING_HEALTH_API_URL = "https://booking-health.staging.eservia.com";
    private static final String STAGE_BOOKING_HEALTH_API_VERSION = "/api/v1.0/";

    private static final String STAGE_MARKETING_API_URL = "https://marketing.staging.eservia.com";
    private static final String STAGE_MARKETING_API_VERSION = "/api/v0.0/";

    private static final String STAGE_CUSTOMER_API_URL = "https://customer.staging.eservia.com";
    private static final String STAGE_CUSTOMER_API_VERSION = "/api/v0.0/";

    private static final String STAGE_ORDER_RESTO_API_URL = "https://order.staging.eservia.com";
    private static final String STAGE_ORDER_RESTO_API_VERSION = "/api/v0.0/";

    private static final String STAGE_BOOKING_RESTO_API_URL = "https://resto-booking.staging.eservia.com";
    private static final String STAGE_BOOKING_RESTO_API_VERSION = "/api/v0.0/";

    private static final String STAGE_DEPARTMENTS_RESTO_API_URL = "https://department.staging.eservia.com";
    private static final String STAGE_DEPARTMENTS_RESTO_API_VERSION = "/api/v0.0/";

    private static final String STAGE_DELIVERY_RESTO_API_URL = "https://delivery.staging.eservia.com";
    private static final String STAGE_DELIVERY_RESTO_API_VERSION = "/api/";

    //production

    private static final String PRODUCTION_BOOKING_IMAGE_RESIZE_PREFIX = "/preview/r/";

    private static final String PRODUCTION_BROADCAST_URL = "https://eservia.com";
    private static final String PRODUCTION_BROADCAST_PATH = "/ws/socket.io";

    private static final String PRODUCTION_LOCKED_BOOKINGS_URL = "https://eservia.com/ws/booking/lock";

    private static final String PRODUCTION_USERS_API_URL = "https://auth.eservia.com";
    private static final String PRODUCTION_USERS_API_VERSION = "/api/v0.0/";
    private static final String PRODUCTION_USERS_API_IMAGE = "https://media.eservia.com/image";

    private static final String PRODUCTION_TERMS_OF_USING = "https://eservia.com/pages/terms";
    private static final String PRODUCTION_POLICY_OF_CONFIDENCE = "https://eservia.com/pages/privacy";

    private static final String PRODUCTION_FILE_API_URL = "https://media.eservia.com";
    private static final String PRODUCTION_FILE_API_VERSION = "/api/v0.0/";

    private static final String PRODUCTION_BUSINESS_API_URL = "https://business.eservia.com";
    private static final String PRODUCTION_BUSINESS_API_VERSION = "/api/v1.0/";

    private static final String PRODUCTION_BUSINESS_BEAUTY_API_URL = "https://business-beauty.eservia.com";
    private static final String PRODUCTION_BUSINESS_BEAUTY_API_VERSION = "/api/v1.0/";

    private static final String PRODUCTION_BOOKING_BEAUTY_API_URL = "https://booking-beauty.eservia.com";
    private static final String PRODUCTION_BOOKING_BEAUTY_API_VERSION = "/api/v1.0/";

    private static final String PRODUCTION_BUSINESS_HEALTH_API_URL = "https://business-health.eservia.com";
    private static final String PRODUCTION_BUSINESS_HEALTH_API_VERSION = "/api/v1.0/";

    private static final String PRODUCTION_BOOKING_HEALTH_API_URL = "https://booking-health.eservia.com";
    private static final String PRODUCTION_BOOKING_HEALTH_API_VERSION = "/api/v1.0/";

    private static final String PRODUCTION_MARKETING_API_URL = "https://marketing.eservia.com";
    private static final String PRODUCTION_MARKETING_API_VERSION = "/api/v0.0/";

    private static final String PRODUCTION_CUSTOMER_API_URL = "https://customer.eservia.com";
    private static final String PRODUCTION_CUSTOMER_API_VERSION = "/api/v0.0/";

    private static final String PRODUCTION_ORDER_RESTO_API_URL = "https://order.eservia.com";
    private static final String PRODUCTION_ORDER_RESTO_API_VERSION = "/api/v0.0/";

    private static final String PRODUCTION_BOOKING_RESTO_API_URL = "https://resto-booking.eservia.com";
    private static final String PRODUCTION_BOOKING_RESTO_API_VERSION = "/api/v0.0/";

    private static final String PRODUCTION_DEPARTMENTS_RESTO_API_URL = "https://department.eservia.com";
    private static final String PRODUCTION_DEPARTMENTS_RESTO_API_VERSION = "/api/v0.0/";

    private static final String PRODUCTION_DELIVERY_RESTO_API_URL = "https://delivery.eservia.com";
    private static final String PRODUCTION_DELIVERY_RESTO_API_VERSION = "/api/";

    public static String getBookingImageResizePrefix() {
        if (productionRoute()) {
            return PRODUCTION_BOOKING_IMAGE_RESIZE_PREFIX;
        } else {
            return STAGE_BOOKING_IMAGE_RESIZE_PREFIX;
        }
    }

    public static String getBroadcastUrl() {
        if (productionRoute()) {
            return PRODUCTION_BROADCAST_URL;
        } else {
            return STAGE_BROADCAST_URL;
        }
    }

    public static String getBroadcastPath() {
        if (productionRoute()) {
            return PRODUCTION_BROADCAST_PATH;
        } else {
            return STAGE_BROADCAST_PATH;
        }
    }

    public static String getLockedBookingsUrl() {
        if (productionRoute()) {
            return PRODUCTION_LOCKED_BOOKINGS_URL;
        } else {
            return STAGE_LOCKED_BOOKINGS_URL;
        }
    }

    public static String getUsersApiUrl() {
        if (productionRoute()) {
            return PRODUCTION_USERS_API_URL;
        } else {
            return STAGE_USERS_API_URL;
        }
    }

    public static String getUsersApiVersion() {
        if (productionRoute()) {
            return PRODUCTION_USERS_API_VERSION;
        } else {
            return STAGE_USERS_API_VERSION;
        }
    }

    public static String getUsersApiImage() {
        if (productionRoute()) {
            return PRODUCTION_USERS_API_IMAGE;
        } else {
            return STAGE_USERS_API_IMAGE;
        }
    }

    public static String getTermsOfUsing() {
        if (productionRoute()) {
            return PRODUCTION_TERMS_OF_USING;
        } else {
            return STAGE_TERMS_OF_USING;
        }
    }

    public static String getPolicyOfConfidence() {
        if (productionRoute()) {
            return PRODUCTION_POLICY_OF_CONFIDENCE;
        } else {
            return STAGE_POLICY_OF_CONFIDENCE;
        }
    }

    public static String getFileApiUrl() {
        if (productionRoute()) {
            return PRODUCTION_FILE_API_URL;
        } else {
            return STAGE_FILE_API_URL;
        }
    }

    public static String getFileApiVersion() {
        if (productionRoute()) {
            return PRODUCTION_FILE_API_VERSION;
        } else {
            return STAGE_FILE_API_VERSION;
        }
    }

    public static String getOrderRestoApiUrl() {
        if (productionRoute()) {
            return PRODUCTION_ORDER_RESTO_API_URL;
        } else {
            return STAGE_ORDER_RESTO_API_URL;
        }
    }

    public static String getOrderRestoApiVersion() {
        if (productionRoute()) {
            return PRODUCTION_ORDER_RESTO_API_VERSION;
        } else {
            return STAGE_ORDER_RESTO_API_VERSION;
        }
    }

    public static String getBookingRestoApiUrl() {
        if (productionRoute()) {
            return PRODUCTION_BOOKING_RESTO_API_URL;
        } else {
            return STAGE_BOOKING_RESTO_API_URL;
        }
    }

    public static String getBookingRestoApiVersion() {
        if (productionRoute()) {
            return PRODUCTION_BOOKING_RESTO_API_VERSION;
        } else {
            return STAGE_BOOKING_RESTO_API_VERSION;
        }
    }

    public static String getDepartmentRestoApiUrl() {
        if (productionRoute()) {
            return PRODUCTION_DEPARTMENTS_RESTO_API_URL;
        } else {
            return STAGE_DEPARTMENTS_RESTO_API_URL;
        }
    }

    public static String getDepartmentRestoApiVersion() {
        if (productionRoute()) {
            return PRODUCTION_DEPARTMENTS_RESTO_API_VERSION;
        } else {
            return STAGE_DEPARTMENTS_RESTO_API_VERSION;
        }
    }

    public static String getDeliveryRestoApiUrl() {
        if (productionRoute()) {
            return PRODUCTION_DELIVERY_RESTO_API_URL;
        } else {
            return STAGE_DELIVERY_RESTO_API_URL;
        }
    }

    public static String getDeliveryRestoApiVersion() {
        if (productionRoute()) {
            return PRODUCTION_DELIVERY_RESTO_API_VERSION;
        } else {
            return STAGE_DELIVERY_RESTO_API_VERSION;
        }
    }

    public static String getBusinessApiUrl() {
        if (productionRoute()) {
            return PRODUCTION_BUSINESS_API_URL;
        } else {
            return STAGE_BUSINESS_API_URL;
        }
    }

    public static String getBusinessApiVersion() {
        if (productionRoute()) {
            return PRODUCTION_BUSINESS_API_VERSION;
        } else {
            return STAGE_BUSINESS_API_VERSION;
        }
    }

    public static String getBusinessBeautyApiUrl() {
        if (productionRoute()) {
            return PRODUCTION_BUSINESS_BEAUTY_API_URL;
        } else {
            return STAGE_BUSINESS_BEAUTY_API_URL;
        }
    }

    public static String getBusinessBeautyApiVersion() {
        if (productionRoute()) {
            return PRODUCTION_BUSINESS_BEAUTY_API_VERSION;
        } else {
            return STAGE_BUSINESS_BEAUTY_API_VERSION;
        }
    }

    public static String getBookingBeautyApiUrl() {
        if (productionRoute()) {
            return PRODUCTION_BOOKING_BEAUTY_API_URL;
        } else {
            return STAGE_BOOKING_BEAUTY_API_URL;
        }
    }

    public static String getBookingBeautyApiVersion() {
        if (productionRoute()) {
            return PRODUCTION_BOOKING_BEAUTY_API_VERSION;
        } else {
            return STAGE_BOOKING_BEAUTY_API_VERSION;
        }
    }

    public static String getBusinessHealthApiUrl() {
        if (productionRoute()) {
            return PRODUCTION_BUSINESS_HEALTH_API_URL;
        } else {
            return STAGE_BUSINESS_HEALTH_API_URL;
        }
    }

    public static String getBusinessHealthApiVersion() {
        if (productionRoute()) {
            return PRODUCTION_BUSINESS_HEALTH_API_VERSION;
        } else {
            return STAGE_BUSINESS_HEALTH_API_VERSION;
        }
    }

    public static String getBookingHealthApiUrl() {
        if (productionRoute()) {
            return PRODUCTION_BOOKING_HEALTH_API_URL;
        } else {
            return STAGE_BOOKING_HEALTH_API_URL;
        }
    }

    public static String getBookingHealthApiVersion() {
        if (productionRoute()) {
            return PRODUCTION_BOOKING_HEALTH_API_VERSION;
        } else {
            return STAGE_BOOKING_HEALTH_API_VERSION;
        }
    }

    public static String getMarketingApiUrl() {
        if (productionRoute()) {
            return PRODUCTION_MARKETING_API_URL;
        } else {
            return STAGE_MARKETING_API_URL;
        }
    }

    public static String getMarketingApiVersion() {
        if (productionRoute()) {
            return PRODUCTION_MARKETING_API_VERSION;
        } else {
            return STAGE_MARKETING_API_VERSION;
        }
    }

    public static String getCustomerApiUrl() {
        if (productionRoute()) {
            return PRODUCTION_CUSTOMER_API_URL;
        } else {
            return STAGE_CUSTOMER_API_URL;
        }
    }

    public static String getCustomerApiVersion() {
        if (productionRoute()) {
            return PRODUCTION_CUSTOMER_API_VERSION;
        } else {
            return STAGE_CUSTOMER_API_VERSION;
        }
    }

    public static String provideBusinessApiUrlPlusVer() {
        return buildServerApiUrl(getBusinessApiUrl(), getBusinessApiVersion());
    }

    public static String provideBusinessBeautyApiUrlPlusVer() {
        return buildServerApiUrl(getBusinessBeautyApiUrl(), getBusinessBeautyApiVersion());
    }

    public static String provideBookingBeautyApiUrlPlusVer() {
        return buildServerApiUrl(getBookingBeautyApiUrl(), getBookingBeautyApiVersion());
    }

    public static String provideBusinessHealthApiUrlPlusVer() {
        return buildServerApiUrl(getBusinessHealthApiUrl(), getBusinessHealthApiVersion());
    }

    public static String provideBookingHealthApiUrlPlusVer() {
        return buildServerApiUrl(getBookingHealthApiUrl(), getBookingHealthApiVersion());
    }

    public static String provideMarketingApiUrlPlusVer() {
        return buildServerApiUrl(getMarketingApiUrl(), getMarketingApiVersion());
    }

    public static String provideCustomerApiUrlPlusVer() {
        return buildServerApiUrl(getCustomerApiUrl(), getCustomerApiVersion());
    }

    public static String provideUsersApiUrlPlusVer() {
        return buildServerApiUrl(getUsersApiUrl(), getUsersApiVersion());
    }

    public static String provideFileApiUrlPlusVer() {
        return buildServerApiUrl(getFileApiUrl(), getFileApiVersion());
    }

    public static String provideOrderRestoApiUrlPlusVer() {
        return buildServerApiUrl(getOrderRestoApiUrl(), getOrderRestoApiVersion());
    }

    public static String provideBookingRestoApiUrlPlusVer() {
        return buildServerApiUrl(getBookingRestoApiUrl(), getBookingRestoApiVersion());
    }

    public static String provideDepartmentRestoApiUrlPlusVer() {
        return buildServerApiUrl(getDepartmentRestoApiUrl(), getDepartmentRestoApiVersion());
    }

    public static String provideDeliveryRestoApiUrlPlusVer() {
        return buildServerApiUrl(getDeliveryRestoApiUrl(), getDeliveryRestoApiVersion());
    }

    private static String buildServerApiUrl(String url, String path) {

        if (url.endsWith("/")) {
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
        } else {
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
        }
        return url + path;
    }

    private static String buildBookingApiVer(String ver) {
        return "/api/v" + ver + "/";
    }

    private static boolean productionRoute() {
        if (route == null) {
            return !BuildConfig.DEBUG;
        } else {
            return route.equals(Route.PRODUCTION);
        }
    }

    private enum Route {
        STAGE, PRODUCTION
    }
}
