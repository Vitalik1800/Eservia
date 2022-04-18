package com.eservia.booking.di.modules;

import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.booking.model.city.CityStatus;
import com.eservia.model.interactors.booking.BookingInteractor;
import com.eservia.model.interactors.booking.BookingInteractorImpl;
import com.eservia.model.interactors.business.BusinessInteractor;
import com.eservia.model.interactors.business.BusinessInteractorImpl;
import com.eservia.model.interactors.generalbooking.GeneralBookingInteractor;
import com.eservia.model.interactors.generalbooking.GeneralBookingInteractorImpl;
import com.eservia.model.interactors.marketing.MarketingInteractor;
import com.eservia.model.interactors.marketing.MarketingInteractorImpl;
import com.eservia.model.interactors.payment.PaymentInteractor;
import com.eservia.model.interactors.payment.PaymentInteractorImpl;
import com.eservia.model.interactors.resto.RestoBookingDateTimeInteractor;
import com.eservia.model.interactors.resto.RestoBookingDateTimeInteractorImpl;
import com.eservia.model.interactors.resto.RestoBookingsInteractor;
import com.eservia.model.interactors.resto.RestoBookingsInteractorImpl;
import com.eservia.model.interactors.resto.RestoDeliveryDateTimeInteractor;
import com.eservia.model.interactors.resto.RestoDeliveryDateTimeInteractorImpl;
import com.eservia.model.interactors.resto.RestoMenuInteractor;
import com.eservia.model.interactors.resto.RestoMenuInteractorImpl;
import com.eservia.model.interactors.sector.SectorInteractor;
import com.eservia.model.interactors.sector.SectorInteractorImpl;
import com.eservia.model.local.menu.MenuRepository;
import com.eservia.model.local.menu.MenuRepositoryImpl;
import com.eservia.model.local.order_resto.OrderRestoRepository;
import com.eservia.model.local.order_resto.OrderRestoRepositoryImpl;
import com.eservia.model.local.sector.SectorRepository;
import com.eservia.model.local.sector.SectorRepositoryImpl;
import com.eservia.model.remote.UrlList;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.retrofit.RetrofitRestClient;
import com.eservia.model.remote.socket.WebSocketManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public RestManager provideRestManager(RetrofitRestClient restClient) {
        return new RestManager(restClient);
    }

    @Provides
    @Singleton
    public WebSocketManager provideWebSocketManager() {
        return new WebSocketManager(UrlList.getBroadcastUrl());
    }

    @Provides
    @Singleton
    public BookingStatus provideBookingStatus() {
        return new BookingStatus();
    }

    @Provides
    @Singleton
    public CityStatus provideCityStatus() {
        return new CityStatus();
    }

    @Provides
    @Singleton
    public OrderRestoRepository provideOrderRestoRepository(BoxStore boxStore) {
        return new OrderRestoRepositoryImpl(boxStore);
    }

    @Provides
    @Singleton
    public SectorRepository provideSectorRepository(BoxStore boxStore) {
        return new SectorRepositoryImpl(boxStore);
    }

    @Provides
    @Singleton
    public MenuRepository provideMenuRestoRepository(BoxStore boxStore) {
        return new MenuRepositoryImpl(boxStore);
    }

    @Provides
    public RestoBookingDateTimeInteractor provideRestoBookingDateTimeInteractor(RestManager manager) {
        return new RestoBookingDateTimeInteractorImpl(manager);
    }

    @Provides
    public RestoBookingsInteractor provideRestoBookingsInteractor(RestManager manager) {
        return new RestoBookingsInteractorImpl(manager);
    }

    @Provides
    public RestoDeliveryDateTimeInteractor provideRestoDeliveryDateTimeInteractor(RestManager manager,
                                                                                  OrderRestoRepository orderRestoRepository) {
        return new RestoDeliveryDateTimeInteractorImpl(manager, orderRestoRepository);
    }

    @Provides
    public RestoMenuInteractor provideRestoMenuInteractor(RestManager manager,
                                                          MenuRepository menuRepository) {
        return new RestoMenuInteractorImpl(manager, menuRepository);
    }

    @Provides
    public SectorInteractor provideSectorInteractor(RestManager manager, SectorRepository repository) {
        return new SectorInteractorImpl(manager, repository);
    }

    @Provides
    public BusinessInteractor provideBusinessInteractor(RestManager manager, SectorInteractor sectorInteractor) {
        return new BusinessInteractorImpl(manager, sectorInteractor);
    }

    @Provides
    public BookingInteractor provideBookingInteractor(RestManager manager) {
        return new BookingInteractorImpl(manager);
    }

    @Provides
    public MarketingInteractor provideMarketingInteractor(RestManager manager) {
        return new MarketingInteractorImpl(manager);
    }

    @Provides
    public PaymentInteractor providePaymentInteractor(RestManager manager) {
        return new PaymentInteractorImpl(manager);
    }

    @Provides
    public GeneralBookingInteractor provideGeneralBookingInteractor(RestManager restManager,
                                                                    RestoBookingsInteractor restoBookingsInteractor,
                                                                    BookingInteractor bookingInteractor) {
        return new GeneralBookingInteractorImpl(restManager, restoBookingsInteractor, bookingInteractor);
    }
}
