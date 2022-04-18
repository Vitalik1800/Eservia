package com.eservia.booking.di;

import com.eservia.booking.di.modules.ApiModule;
import com.eservia.booking.di.modules.ContextModule;
import com.eservia.booking.di.modules.NetworkStateProviderModule;
import com.eservia.booking.di.modules.NotificationControllerModule;
import com.eservia.booking.di.modules.ObjectBoxModule;
import com.eservia.booking.di.modules.RestClientDelegateModule;
import com.eservia.booking.di.modules.RetrofitRestClientModule;
import com.eservia.booking.service.message.FirebaseIdService;
import com.eservia.booking.service.message.FirebaseMessageService;
import com.eservia.booking.ui.auth.approve_phone.PhoneApprovePresenter;
import com.eservia.booking.ui.auth.login.LoginPresenter;
import com.eservia.booking.ui.auth.register_phone.RegisterPhonePresenter;
import com.eservia.booking.ui.auth.registration.RegistrationPresenter;
import com.eservia.booking.ui.auth.reset_password.RequestRestoreCodePresenter;
import com.eservia.booking.ui.auth.reset_password.ResetPasswordPresenter;
import com.eservia.booking.ui.booking.beauty.BookingBeautyPresenter;
import com.eservia.booking.ui.booking.beauty.basket.BasketPresenter;
import com.eservia.booking.ui.booking.beauty.basket_sort.BasketSortPresenter;
import com.eservia.booking.ui.booking.beauty.booking.BookingPresenter;
import com.eservia.booking.ui.booking.beauty.service.ServicePresenter;
import com.eservia.booking.ui.booking.beauty.service_group.ServiceGroupPresenter;
import com.eservia.booking.ui.booking.beauty.thank_you.ThankYouPresenter;
import com.eservia.booking.ui.booking.resto.BookingRestoPresenter;
import com.eservia.booking.ui.booking.resto.date_time.BookingDateTimePresenter;
import com.eservia.booking.ui.booking.resto.placement.BookingRestoPlacementPresenter;
import com.eservia.booking.ui.business_page.beauty.BusinessPageBeautyPresenter;
import com.eservia.booking.ui.business_page.beauty.contacts.BusinessPageBeautyContactsPresenter;
import com.eservia.booking.ui.business_page.beauty.departments.BusinessPageBeautyDepartmentsPresenter;
import com.eservia.booking.ui.business_page.beauty.feedback.BusinessFeedbackBeautyPresenter;
import com.eservia.booking.ui.business_page.beauty.info.BusinessPageBeautyInfoPresenter;
import com.eservia.booking.ui.business_page.beauty.reviews.BusinessPageBeautyReviewsPresenter;
import com.eservia.booking.ui.business_page.gallery.GalleryPresenter;
import com.eservia.booking.ui.business_page.map.BusinessMapPresenter;
import com.eservia.booking.ui.business_page.marketing.BusinessMarketingsPresenter;
import com.eservia.booking.ui.business_page.staff.BusinessStaffPresenter;
import com.eservia.booking.ui.contacts.ContactsPresenter;
import com.eservia.booking.ui.delivery.resto.DeliveryPresenter;
import com.eservia.booking.ui.delivery.resto.address.DeliveryAddressPresenter;
import com.eservia.booking.ui.delivery.resto.basket.DeliveryBasketPresenter;
import com.eservia.booking.ui.delivery.resto.date.DeliveryDatePresenter;
import com.eservia.booking.ui.dish_details.DishDetailsPresenter;
import com.eservia.booking.ui.event_details.beauty.EventDetailsBeautyPresenter;
import com.eservia.booking.ui.home.HomePresenter;
import com.eservia.booking.ui.home.bookings.active_bookings.ActiveBookingsPresenter;
import com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings.GeneralActiveBookingsPresenter;
import com.eservia.booking.ui.home.bookings.archive_bookings.ArchiveBookingsPresenter;
import com.eservia.booking.ui.home.bookings.archive_bookings.general_archive_bookings.GeneralArchiveBookingsPresenter;
import com.eservia.booking.ui.home.bookings.delivery_info.DeliveryInfoPresenter;
import com.eservia.booking.ui.home.favorite.favorite.FavoritePresenter;
import com.eservia.booking.ui.home.favorite.favorite.favorites_sector.FavoritesSectorPresenter;
import com.eservia.booking.ui.home.menu.menu.MenuPresenter;
import com.eservia.booking.ui.home.news.news.news_and_promo.NewsAndPromoPresenter;
import com.eservia.booking.ui.home.search.search.SearchPresenter;
import com.eservia.booking.ui.home.search.sector.SectorPresenter;
import com.eservia.booking.ui.menu.RestoMenuPresenter;
import com.eservia.booking.ui.payment.PaymentPresenter;
import com.eservia.booking.ui.profile.ProfilePresenter;
import com.eservia.booking.ui.profile.password.UpdatePasswordPresenter;
import com.eservia.booking.ui.search_business_map.SearchBusinessesMapPresenter;
import com.eservia.booking.ui.splash.SplashPresenter;
import com.eservia.booking.ui.staff.beauty.StaffBeautyPresenter;
import com.eservia.booking.ui.staff.beauty.info.StaffInfoBeautyPresenter;
import com.eservia.booking.ui.suggest_business.SuggestBusinessPresenter;
import com.eservia.booking.ui.suggest_business.modal.SuggestBusinessModalPresenter;
import com.eservia.booking.ui.suggest_business.standart.SuggestBusinessStandartPresenter;
import com.eservia.booking.ui.suggest_business.thank_you.SuggestBusinessThankYouPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ContextModule.class,
        NetworkStateProviderModule.class,
        RestClientDelegateModule.class,
        RetrofitRestClientModule.class,
        ApiModule.class,
        NotificationControllerModule.class,
        ObjectBoxModule.class})
public interface AppComponent {

    void inject(RegistrationPresenter presenter);

    void inject(LoginPresenter presenter);

    void inject(ResetPasswordPresenter presenter);

    void inject(RequestRestoreCodePresenter presenter);

    void inject(RegisterPhonePresenter presenter);

    void inject(SplashPresenter presenter);

    void inject(PhoneApprovePresenter phoneApprovePresenter);

    void inject(SectorPresenter presenter);

    void inject(SearchPresenter presenter);

    void inject(HomePresenter presenter);

    void inject(ServiceGroupPresenter presenter);

    void inject(ServicePresenter presenter);

    void inject(BasketPresenter presenter);

    void inject(BusinessPageBeautyPresenter presenter);

    void inject(BusinessPageBeautyInfoPresenter presenter);

    void inject(GalleryPresenter presenter);

    void inject(BusinessPageBeautyReviewsPresenter presenter);

    void inject(BusinessPageBeautyContactsPresenter presenter);

    void inject(BookingPresenter presenter);

    void inject(ThankYouPresenter presenter);

    void inject(FavoritePresenter presenter);

    void inject(ActiveBookingsPresenter presenter);

    void inject(BookingBeautyPresenter presenter);

    void inject(MenuPresenter presenter);

    void inject(BusinessFeedbackBeautyPresenter presenter);

    void inject(NewsAndPromoPresenter presenter);

    void inject(EventDetailsBeautyPresenter presenter);

    void inject(BusinessMarketingsPresenter presenter);

    void inject(BasketSortPresenter presenter);

    void inject(SuggestBusinessPresenter presenter);

    void inject(SuggestBusinessStandartPresenter presenter);

    void inject(SuggestBusinessModalPresenter presenter);

    void inject(SuggestBusinessThankYouPresenter presenter);

    void inject(SearchBusinessesMapPresenter presenter);

    void inject(ProfilePresenter presenter);

    void inject(UpdatePasswordPresenter presenter);

    void inject(BusinessStaffPresenter presenter);

    void inject(StaffInfoBeautyPresenter presenter);

    void inject(StaffBeautyPresenter presenter);

    void inject(ContactsPresenter presenter);

    void inject(FirebaseIdService service);

    void inject(FirebaseMessageService service);

    void inject(BusinessMapPresenter presenter);

    void inject(ArchiveBookingsPresenter presenter);

    void inject(com.eservia.booking.ui.business_page.restaurant.menu.MenuPresenter presenter);

    void inject(DishDetailsPresenter presenter);

    void inject(DeliveryBasketPresenter presenter);

    void inject(RestoMenuPresenter presenter);

    void inject(BookingRestoPresenter presenter);

    void inject(BookingDateTimePresenter presenter);

    void inject(BookingRestoPlacementPresenter presenter);

    void inject(DeliveryAddressPresenter presenter);

    void inject(DeliveryPresenter presenter);

    void inject(DeliveryDatePresenter presenter);

    void inject(DeliveryInfoPresenter presenter);

    void inject(BusinessPageBeautyDepartmentsPresenter presenter);

    void inject(PaymentPresenter presenter);

    void inject(GeneralActiveBookingsPresenter presenter);

    void inject(GeneralArchiveBookingsPresenter presenter);

    void inject(FavoritesSectorPresenter presenter);
}
