package com.eservia.model.remote.rest.retrofit;

import com.eservia.model.remote.UrlList;
import com.eservia.model.remote.rest.booking_beauty.BookingBeautyRequestInterceptor;
import com.eservia.model.remote.rest.booking_beauty.BookingBeautyServer;
import com.eservia.model.remote.rest.booking_health.BookingHealthServer;
import com.eservia.model.remote.rest.booking_resto.BookingRestoRequestInterceptor;
import com.eservia.model.remote.rest.booking_resto.BookingRestoServer;
import com.eservia.model.remote.rest.business.BusinessRequestInterceptor;
import com.eservia.model.remote.rest.business.BusinessServer;
import com.eservia.model.remote.rest.business_beauty.BusinessBeautyRequestInterceptor;
import com.eservia.model.remote.rest.business_beauty.BusinessBeautyServer;
import com.eservia.model.remote.rest.business_health.BusinessHealthServer;
import com.eservia.model.remote.rest.customer.CustomerRequestInterceptor;
import com.eservia.model.remote.rest.customer.CustomerServer;
import com.eservia.model.remote.rest.delivery.DeliveryRestoRequestInterceptor;
import com.eservia.model.remote.rest.delivery.DeliveryRestoServer;
import com.eservia.model.remote.rest.department_resto.DepartmentRestoRequestInterceptor;
import com.eservia.model.remote.rest.department_resto.DepartmentRestoServer;
import com.eservia.model.remote.rest.file.FileRequestInterceptor;
import com.eservia.model.remote.rest.file.FileServer;
import com.eservia.model.remote.rest.marketing.MarketingRequestInterceptor;
import com.eservia.model.remote.rest.marketing.MarketingServer;
import com.eservia.model.remote.rest.order_resto.OrderRestoRequestInterceptor;
import com.eservia.model.remote.rest.order_resto.OrderRestoServer;
import com.eservia.model.remote.rest.users.AuthRequestInterceptor;
import com.eservia.model.remote.rest.users.UsersServer;

import okhttp3.logging.HttpLoggingInterceptor;

public class ClientRestClientDelegate implements RestClientDelegate {

    private BusinessServer businessServer;
    private BusinessBeautyServer businessBeautyServer;
    private BookingBeautyServer bookingBeautyServer;
    private MarketingServer marketingServer;
    private CustomerServer customerServer;
    private UsersServer usersServer;
    private FileServer fileServer;
    private OrderRestoServer orderRestoServer;
    private BookingRestoServer bookingRestoServer;
    private DepartmentRestoServer departmentRestoServer;
    private DeliveryRestoServer deliveryRestoServer;
    private BusinessHealthServer businessHealthServer;
    private BookingHealthServer bookingHealthServer;

    private final Object businessServerLock = new Object();
    private final Object businessBeautyServerLock = new Object();
    private final Object bookingBeautyServerLock = new Object();
    private final Object marketingServerLock = new Object();
    private final Object customerServerLock = new Object();
    private final Object usersServerLock = new Object();
    private final Object fileServerLock = new Object();
    private final Object orderRestoServerLock = new Object();
    private final Object bookingRestoServerLock = new Object();
    private final Object departmentRestoServerLock = new Object();
    private final Object deliveryRestoServerLock = new Object();
    private final Object businessHealthServerLock = new Object();
    private final Object bookingHealthServerLock = new Object();

    @Override
    public BusinessServer getBusinessServer() {
        synchronized (businessServerLock) {
            if (businessServer == null) {
                businessServer = new BusinessServer(UrlList.provideBusinessApiUrlPlusVer(),
                        RetrofitBuilder.getClient(new BusinessRequestInterceptor(),
                                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ));
            }
            return businessServer;
        }
    }

    @Override
    public BusinessBeautyServer getBusinessBeautyServer() {
        synchronized (businessBeautyServerLock) {
            if (businessBeautyServer == null) {
                businessBeautyServer = new BusinessBeautyServer(UrlList.provideBusinessBeautyApiUrlPlusVer(),
                        RetrofitBuilder.getClient(new BusinessBeautyRequestInterceptor(),
                                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ));
            }
            return businessBeautyServer;
        }
    }

    @Override
    public BookingBeautyServer getBookingBeautyServer() {
        synchronized (bookingBeautyServerLock) {
            if (bookingBeautyServer == null) {
                bookingBeautyServer = new BookingBeautyServer(UrlList.provideBookingBeautyApiUrlPlusVer(),
                        RetrofitBuilder.getClient(new BookingBeautyRequestInterceptor(),
                                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ));
            }
            return bookingBeautyServer;
        }
    }

    @Override
    public MarketingServer getMarketingServer() {
        synchronized (marketingServerLock) {
            if (marketingServer == null) {
                marketingServer = new MarketingServer(UrlList.provideMarketingApiUrlPlusVer(),
                        RetrofitBuilder.getClient(new MarketingRequestInterceptor(),
                                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ));
            }
            return marketingServer;
        }
    }

    @Override
    public CustomerServer getCustomerServer() {
        synchronized (customerServerLock) {
            if (customerServer == null) {
                customerServer = new CustomerServer(UrlList.provideCustomerApiUrlPlusVer(),
                        RetrofitBuilder.getClient(new CustomerRequestInterceptor(),
                                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ));
            }
            return customerServer;
        }
    }

    @Override
    public UsersServer getUsersCafeServer() {
        synchronized (usersServerLock) {
            if (usersServer == null) {
                usersServer = new UsersServer(UrlList.provideUsersApiUrlPlusVer(),
                        RetrofitBuilder.getClient(new AuthRequestInterceptor(),
                                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ));
            }
            return usersServer;
        }
    }

    @Override
    public FileServer getFileServer() {
        synchronized (fileServerLock) {
            if (fileServer == null) {
                fileServer = new FileServer(UrlList.provideFileApiUrlPlusVer(),
                        RetrofitBuilder.getClient(new FileRequestInterceptor(),
                                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ));
            }
            return fileServer;
        }
    }

    @Override
    public OrderRestoServer getOrderRestoServer() {
        synchronized (orderRestoServerLock) {
            if (orderRestoServer == null) {
                orderRestoServer = new OrderRestoServer(UrlList.provideOrderRestoApiUrlPlusVer(),
                        RetrofitBuilder.getClient(new OrderRestoRequestInterceptor(),
                                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ));
            }
            return orderRestoServer;
        }
    }

    @Override
    public BookingRestoServer getBookingRestoServer() {
        synchronized (bookingRestoServerLock) {
            if (bookingRestoServer == null) {
                bookingRestoServer = new BookingRestoServer(UrlList.provideBookingRestoApiUrlPlusVer(),
                        RetrofitBuilder.getClient(new BookingRestoRequestInterceptor(),
                                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ));
            }
            return bookingRestoServer;
        }
    }

    @Override
    public DepartmentRestoServer getDepartmentsRestoServer() {
        synchronized (departmentRestoServerLock) {
            if (departmentRestoServer == null) {
                departmentRestoServer = new DepartmentRestoServer(UrlList.provideDepartmentRestoApiUrlPlusVer(),
                        RetrofitBuilder.getClient(new DepartmentRestoRequestInterceptor(),
                                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ));
            }
            return departmentRestoServer;
        }
    }

    @Override
    public DeliveryRestoServer getDeliveryRestoServer() {
        synchronized (deliveryRestoServerLock) {
            if (deliveryRestoServer == null) {
                deliveryRestoServer = new DeliveryRestoServer(UrlList.provideDeliveryRestoApiUrlPlusVer(),
                        RetrofitBuilder.getClient(new DeliveryRestoRequestInterceptor(),
                                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ));
            }
            return deliveryRestoServer;
        }
    }

    @Override
    public BusinessHealthServer getBusinessHealthServer() {
        synchronized (businessHealthServerLock) {
            if (businessHealthServer == null) {
                businessHealthServer = new BusinessHealthServer(UrlList.provideBusinessHealthApiUrlPlusVer(),
                        RetrofitBuilder.getClient(new BusinessBeautyRequestInterceptor(),
                                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ));
            }
            return businessHealthServer;
        }
    }

    @Override
    public BookingHealthServer getBookingHealthServer() {
        synchronized (bookingHealthServerLock) {
            if (bookingHealthServer == null) {
                bookingHealthServer = new BookingHealthServer(UrlList.provideBookingHealthApiUrlPlusVer(),
                        RetrofitBuilder.getClient(new BookingBeautyRequestInterceptor(),
                                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ));
            }
            return bookingHealthServer;
        }
    }

    @Override
    public void removeBusinessHealthServer() {
        synchronized (businessHealthServerLock) {
            businessHealthServer = null;
        }
    }

    @Override
    public void removeBookingHealthServer() {
        synchronized (bookingHealthServerLock) {
            bookingHealthServer = null;
        }
    }

    @Override
    public void removeDeliveryRestoServer() {
        synchronized (deliveryRestoServerLock) {
            deliveryRestoServer = null;
        }
    }

    @Override
    public void removeDepartmentRestoServer() {
        synchronized (departmentRestoServerLock) {
            departmentRestoServer = null;
        }
    }

    @Override
    public void removeBookingRestoServer() {
        synchronized (bookingRestoServerLock) {
            bookingRestoServer = null;
        }
    }

    @Override
    public void removeBusinessServer() {
        synchronized (businessServerLock) {
            businessServer = null;
        }
    }

    @Override
    public void removeBusinessBeautyServer() {
        synchronized (businessBeautyServerLock) {
            businessBeautyServer = null;
        }
    }

    @Override
    public void removeBookingBeautyServer() {
        synchronized (bookingBeautyServerLock) {
            bookingBeautyServer = null;
        }
    }

    @Override
    public void removeMarketingServer() {
        synchronized (marketingServerLock) {
            marketingServer = null;
        }
    }

    @Override
    public void removeCustomerServer() {
        synchronized (customerServerLock) {
            customerServer = null;
        }
    }

    @Override
    public void removeUserCafeServer() {
        synchronized (usersServerLock) {
            usersServer = null;
        }
    }

    @Override
    public void removeFileServer() {
        synchronized (fileServerLock) {
            fileServer = null;
        }
    }

    @Override
    public void removeOrderRestoServer() {
        synchronized (orderRestoServerLock) {
            orderRestoServer = null;
        }
    }

    @Override
    public void removeServices() {
        removeBusinessServer();
        removeBusinessBeautyServer();
        removeBookingBeautyServer();
        removeMarketingServer();
        removeCustomerServer();
        removeUserCafeServer();
        removeFileServer();
        removeOrderRestoServer();
        removeBookingRestoServer();
        removeDepartmentRestoServer();
        removeDeliveryRestoServer();
        removeBusinessHealthServer();
        removeBookingHealthServer();
    }
}
