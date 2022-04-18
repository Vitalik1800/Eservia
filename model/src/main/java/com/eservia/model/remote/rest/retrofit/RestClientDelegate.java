package com.eservia.model.remote.rest.retrofit;

import com.eservia.model.remote.rest.booking_beauty.BookingBeautyServer;
import com.eservia.model.remote.rest.booking_health.BookingHealthServer;
import com.eservia.model.remote.rest.booking_resto.BookingRestoServer;
import com.eservia.model.remote.rest.business.BusinessServer;
import com.eservia.model.remote.rest.business_beauty.BusinessBeautyServer;
import com.eservia.model.remote.rest.business_health.BusinessHealthServer;
import com.eservia.model.remote.rest.customer.CustomerServer;
import com.eservia.model.remote.rest.delivery.DeliveryRestoServer;
import com.eservia.model.remote.rest.department_resto.DepartmentRestoServer;
import com.eservia.model.remote.rest.file.FileServer;
import com.eservia.model.remote.rest.marketing.MarketingServer;
import com.eservia.model.remote.rest.order_resto.OrderRestoServer;
import com.eservia.model.remote.rest.users.UsersServer;

public interface RestClientDelegate {

    BusinessServer getBusinessServer();

    BusinessBeautyServer getBusinessBeautyServer();

    BookingBeautyServer getBookingBeautyServer();

    BusinessHealthServer getBusinessHealthServer();

    BookingHealthServer getBookingHealthServer();

    MarketingServer getMarketingServer();

    CustomerServer getCustomerServer();

    UsersServer getUsersCafeServer();

    FileServer getFileServer();

    OrderRestoServer getOrderRestoServer();

    BookingRestoServer getBookingRestoServer();

    DepartmentRestoServer getDepartmentsRestoServer();

    DeliveryRestoServer getDeliveryRestoServer();

    void removeBusinessServer();

    void removeBusinessBeautyServer();

    void removeBookingBeautyServer();

    void removeBusinessHealthServer();

    void removeBookingHealthServer();

    void removeMarketingServer();

    void removeCustomerServer();

    void removeUserCafeServer();

    void removeFileServer();

    void removeOrderRestoServer();

    void removeBookingRestoServer();

    void removeDepartmentRestoServer();

    void removeDeliveryRestoServer();

    void removeServices();
}
