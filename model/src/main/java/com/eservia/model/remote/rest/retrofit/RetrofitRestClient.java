package com.eservia.model.remote.rest.retrofit;

import com.eservia.model.entity.Session;
import com.eservia.model.prefs.AccessToken;
import com.eservia.model.remote.error.NetworkErrorCode;
import com.eservia.model.remote.rest.EserviaBookingRestClient;
import com.eservia.model.remote.rest.booking_beauty.BookingBeautyServer;
import com.eservia.model.remote.rest.booking_beauty.services.booking.BeautyBookingsResponse;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CancelBookingBeautyResponse;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyRequest;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyResponse;
import com.eservia.model.remote.rest.booking_beauty.services.time_slot.BeautyGradualTimeSlotResponse;
import com.eservia.model.remote.rest.booking_beauty.services.time_slot.BeautyTimeSlotResponse;
import com.eservia.model.remote.rest.booking_health.BookingHealthServer;
import com.eservia.model.remote.rest.booking_resto.BookingRestoServer;
import com.eservia.model.remote.rest.booking_resto.services.booking.CancelRestoBookingRequest;
import com.eservia.model.remote.rest.booking_resto.services.booking.CancelRestoBookingResponse;
import com.eservia.model.remote.rest.booking_resto.services.booking.GetRestoBookingsResponse;
import com.eservia.model.remote.rest.booking_resto.services.booking.RestoBookingRequest;
import com.eservia.model.remote.rest.booking_resto.services.booking.RestoBookingResponse;
import com.eservia.model.remote.rest.booking_resto.services.booking_settings.BookingSettingsResponse;
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponse;
import com.eservia.model.remote.rest.business.BusinessServer;
import com.eservia.model.remote.rest.business.services.address.BusinessAddressesResponse;
import com.eservia.model.remote.rest.business.services.business.BusinessCategories;
import com.eservia.model.remote.rest.business.services.business.BusinessTags;
import com.eservia.model.remote.rest.business.services.business.BusinessesResponse;
import com.eservia.model.remote.rest.business.services.category.BusinessCategoriesResponse;
import com.eservia.model.remote.rest.business.services.cities.BusinessCitiesResponse;
import com.eservia.model.remote.rest.business.services.comment.BusinessCommentsResponse;
import com.eservia.model.remote.rest.business.services.comment.CreateBusinessCommentRequest;
import com.eservia.model.remote.rest.business.services.comment.CreateBusinessCommentResponse;
import com.eservia.model.remote.rest.business.services.contact.EserviaFeedbackRequest;
import com.eservia.model.remote.rest.business.services.contact.EserviaFeedbackResponse;
import com.eservia.model.remote.rest.business.services.favorite.AddFavoriteResponse;
import com.eservia.model.remote.rest.business.services.favorite.DeleteFavoriteResponse;
import com.eservia.model.remote.rest.business.services.favorite.UserFavoritesResponse;
import com.eservia.model.remote.rest.business.services.gallery.BusinessPhotosResponse;
import com.eservia.model.remote.rest.business.services.sector.BusinessSectorsResponse;
import com.eservia.model.remote.rest.business.services.working_day.BusinessWorkingDayResponse;
import com.eservia.model.remote.rest.business_beauty.BusinessBeautyServer;
import com.eservia.model.remote.rest.business_beauty.services.promotion.BeautyPromotionsResponse;
import com.eservia.model.remote.rest.business_beauty.services.service.BeautyServiceResponse;
import com.eservia.model.remote.rest.business_beauty.services.service_group.BeautyServiceGroupResponse;
import com.eservia.model.remote.rest.business_beauty.services.staff.BusinessBeautyStaffResponse;
import com.eservia.model.remote.rest.business_beauty.services.working_day.BeautyWorkingDayResponse;
import com.eservia.model.remote.rest.business_health.BusinessHealthServer;
import com.eservia.model.remote.rest.customer.CustomerServer;
import com.eservia.model.remote.rest.customer.services.promoter_customer.PromoterCustomerBusinessesResponse;
import com.eservia.model.remote.rest.customer.services.promoter_customer.PromoterCustomerRegisterResponse;
import com.eservia.model.remote.rest.customer.services.promoter_customer.PromoterCustomerService;
import com.eservia.model.remote.rest.delivery.DeliveryRestoServer;
import com.eservia.model.remote.rest.delivery.services.deliveries.GetRestoDeliveriesResponse;
import com.eservia.model.remote.rest.delivery.services.deliveries.PostRestoDeliveryRequest;
import com.eservia.model.remote.rest.delivery.services.deliveries.PostRestoDeliveryResponse;
import com.eservia.model.remote.rest.delivery.services.street_servicing.DeliveryRestoSettlementsResponse;
import com.eservia.model.remote.rest.delivery.services.street_servicing.DeliveryRestoStreetsResponse;
import com.eservia.model.remote.rest.delivery.services.street_servicing.StreetServicingService;
import com.eservia.model.remote.rest.department_resto.DepartmentRestoServer;
import com.eservia.model.remote.rest.department_resto.services.department.DepartmentsResponse;
import com.eservia.model.remote.rest.file.FileServer;
import com.eservia.model.remote.rest.file.services.photo.UploadPhotoResponse;
import com.eservia.model.remote.rest.marketing.MarketingServer;
import com.eservia.model.remote.rest.marketing.services.Marketing.MarketingResponse;
import com.eservia.model.remote.rest.order_resto.OrderRestoServer;
import com.eservia.model.remote.rest.order_resto.services.menu.MenuResponse;
import com.eservia.model.remote.rest.order_resto.services.menu.MenuVersionResponse;
import com.eservia.model.remote.rest.order_resto.services.orders.GetRestoOrdersResponse;
import com.eservia.model.remote.rest.order_resto.services.orders.PostRestoOrderRequest;
import com.eservia.model.remote.rest.order_resto.services.orders.PostRestoOrderResponse;
import com.eservia.model.remote.rest.request.KeyList;
import com.eservia.model.remote.rest.request.StringKeyList;
import com.eservia.model.remote.rest.users.UsersServer;
import com.eservia.model.remote.rest.users.services.CommonUsersRestResponse;
import com.eservia.model.remote.rest.users.services.auth.AuthResponse;
import com.eservia.model.remote.rest.users.services.auth.AuthService;
import com.eservia.model.remote.rest.users.services.auth.ConfirmPhoneRequest;
import com.eservia.model.remote.rest.users.services.auth.DeviceTokenRequest;
import com.eservia.model.remote.rest.users.services.auth.DeviceTokenResponse;
import com.eservia.model.remote.rest.users.services.auth.ForgotPasswordRequest;
import com.eservia.model.remote.rest.users.services.auth.LogoutRequest;
import com.eservia.model.remote.rest.users.services.auth.ResendConfirmCodeRequest;
import com.eservia.model.remote.rest.users.services.auth.RestorePasswordRequest;
import com.eservia.model.remote.rest.users.services.auth.SignInRequest;
import com.eservia.model.remote.rest.users.services.auth.SignUpProviderRequest;
import com.eservia.model.remote.rest.users.services.auth.SignUpRequest;
import com.eservia.model.remote.rest.users.services.auth.SignUpResponse;
import com.eservia.model.remote.rest.users.services.auth.SocialSignInRequest;
import com.eservia.model.remote.rest.users.services.profile.ChangePassRequest;
import com.eservia.model.remote.rest.users.services.profile.EditProfileRequest;
import com.eservia.model.remote.rest.users.services.profile.EmailRequest;
import com.eservia.model.remote.rest.users.services.profile.EmailResponse;
import com.eservia.model.remote.rest.users.services.profile.ProfileResponse;
import com.eservia.model.remote.rest.users.services.profile.ProfileService;
import com.eservia.model.remote.rest.users.services.users.UsersResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


public class RetrofitRestClient implements EserviaBookingRestClient {

    private final RestClientDelegate restClientDelegate;

    private final NetworkStateProvider provider;

    public RetrofitRestClient(NetworkStateProvider provider, RestClientDelegate restClientDelegate) {
        this.provider = provider;
        this.restClientDelegate = restClientDelegate;
    }

    private Observable<BusinessServer> businessServer() {
        return Observable.fromCallable(() -> {
            checkInternetConnection();
            return restClientDelegate.getBusinessServer();
        });
    }

    private Observable<BusinessBeautyServer> businessBeautyServer() {
        return Observable.fromCallable(() -> {
            checkInternetConnection();
            return restClientDelegate.getBusinessBeautyServer();
        });
    }

    private Observable<BookingBeautyServer> bookingBeautyServer() {
        return Observable.fromCallable(() -> {
            checkInternetConnection();
            return restClientDelegate.getBookingBeautyServer();
        });
    }

    private Observable<BusinessHealthServer> businessHealthServer() {
        return Observable.fromCallable(() -> {
            checkInternetConnection();
            return restClientDelegate.getBusinessHealthServer();
        });
    }

    private Observable<BookingHealthServer> bookingHealthServer() {
        return Observable.fromCallable(() -> {
            checkInternetConnection();
            return restClientDelegate.getBookingHealthServer();
        });
    }

    private Observable<MarketingServer> marketingServer() {
        return Observable.fromCallable(() -> {
            checkInternetConnection();
            return restClientDelegate.getMarketingServer();
        });
    }

    private Observable<CustomerServer> customerServer() {
        return Observable.fromCallable(() -> {
            checkInternetConnection();
            return restClientDelegate.getCustomerServer();
        });
    }

    private Observable<UsersServer> usersCafeServer() {
        return Observable.fromCallable(() -> {
            checkInternetConnection();
            return restClientDelegate.getUsersCafeServer();
        });
    }

    private Observable<FileServer> fileServer() {
        return Observable.fromCallable(() -> {
            checkInternetConnection();
            return restClientDelegate.getFileServer();
        });
    }

    private Observable<OrderRestoServer> orderRestoServer() {
        return Observable.fromCallable(() -> {
            checkInternetConnection();
            return restClientDelegate.getOrderRestoServer();
        });
    }

    private Observable<BookingRestoServer> bookingRestoServer() {
        return Observable.fromCallable(() -> {
            checkInternetConnection();
            return restClientDelegate.getBookingRestoServer();
        });
    }

    private Observable<DepartmentRestoServer> departmentRestoServer() {
        return Observable.fromCallable(() -> {
            checkInternetConnection();
            return restClientDelegate.getDepartmentsRestoServer();
        });
    }

    private Observable<DeliveryRestoServer> deliveryRestoServer() {
        return Observable.fromCallable(() -> {
            checkInternetConnection();
            return restClientDelegate.getDeliveryRestoServer();
        });
    }

    private void checkInternetConnection() {
        if (!provider.isNetworkAvailable()) {
            throw new RetrofitException(NetworkErrorCode.NO_INTERNET);
        }
    }

    @Override
    public Observable<SignUpResponse> registerUser(SignUpRequest request) {
        return usersCafeServer()
                .flatMap(UsersServer::provideAuthService)
                .flatMap(server -> server.signUp(request));
    }

    @Override
    public Observable<AuthResponse> confirmPhone(ConfirmPhoneRequest request) {
        return usersCafeServer()
                .flatMap(UsersServer::provideAuthService)
                .flatMap(server -> server.confirmPhone(request));
    }

    @Override
    public Observable<CommonUsersRestResponse> resendConfirmCode(String phone) {
        return usersCafeServer()
                .flatMap(UsersServer::provideAuthService)
                .flatMap(server -> server.resendConfirmCode(new ResendConfirmCodeRequest(phone)));
    }

    @Override
    public Observable<AuthResponse> signInUser(SignInRequest request) {
        return usersCafeServer()
                .flatMap(UsersServer::provideAuthService)
                .flatMap(server -> server.singIn(request))
                .doOnNext(authResponse -> restClientDelegate.removeUserCafeServer());
    }

    @Override
    public Observable<AuthResponse> signInUser(String provider, String token) {
        return usersCafeServer()
                .flatMap(UsersServer::provideAuthService)
                .flatMap(server -> server.singIn(new SocialSignInRequest(provider, token)))
                .doOnNext(authResponse -> restClientDelegate.removeUserCafeServer());
    }

    @Override
    public Observable<CommonUsersRestResponse> signUpUserWithProvider(SignUpProviderRequest request) {
        return usersCafeServer()
                .flatMap(UsersServer::provideAuthService)
                .flatMap(server -> server.signUpUserWithProvider(request));
    }

    @Override
    public Observable<CommonUsersRestResponse> logout(LogoutRequest request) {
        return usersCafeServer()
                .flatMap(UsersServer::provideAuthService)
                .flatMap(server -> server.logout(request));
    }

    @Override
    public Observable<CommonUsersRestResponse> sendEmailConfirm() {
        return usersCafeServer()
                .flatMap(UsersServer::provideAuthService)
                .flatMap(AuthService::sendEmailConfirm);
    }

    @Override
    public Observable<CommonUsersRestResponse> resetForgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        return usersCafeServer()
                .flatMap(UsersServer::provideAuthService)
                .flatMap(server -> server.resetForgotPassword(forgotPasswordRequest));
    }

    @Override
    public Observable<CommonUsersRestResponse> confirmResetPassword(RestorePasswordRequest restore) {
        return usersCafeServer()
                .flatMap(UsersServer::provideAuthService)
                .flatMap(server -> server.confirmResetPassword(restore));
    }

    @Override
    public Observable<AuthResponse> refreshToken(String refreshToken) {
        return usersCafeServer()
                .flatMap(UsersServer::provideAuthService)
                .flatMap(service -> service.refreshToken(refreshToken))
                .doOnNext(refreshTokenResponse -> restClientDelegate.removeServices());
    }

    @Override
    public Observable<DeviceTokenResponse> postDeviceToken(String fcmToken) {
        return usersCafeServer()
                .flatMap(UsersServer::provideAuthService)
                .flatMap(service -> service.registerDeviceToken(
                        new DeviceTokenRequest(Session.GCM, AccessToken.getSessionId(), fcmToken)));
    }

    @Override
    public Observable<ProfileResponse> loadProfile() {
        return usersCafeServer()
                .flatMap(UsersServer::provideProfileService)
                .flatMap(ProfileService::profileInfo);
    }

    @Override
    public Observable<ProfileResponse> updateProfile(EditProfileRequest editProfileRequest) {
        return usersCafeServer()
                .flatMap(UsersServer::provideProfileService)
                .flatMap(server -> server.updateProfile(editProfileRequest));
    }

    @Override
    public Observable<UploadPhotoResponse> uploadPhoto(MultipartBody.Part photo) {
        return fileServer()
                .flatMap(FileServer::providePhotoService)
                .flatMap(service -> service.uploadFile(photo));
    }

    @Override
    public Observable<CommonUsersRestResponse> changePassword(ChangePassRequest request) {
        return usersCafeServer()
                .flatMap(UsersServer::provideProfileService)
                .flatMap(server -> server.changePassword(request));
    }

    @Override
    public Observable<ProfileResponse> attachSocialAccount(@NotNull SocialSignInRequest request) {
        return usersCafeServer()
                .flatMap(UsersServer::provideProfileService)
                .flatMap(server -> server.attachSocialAccount(request));
    }

    @Override
    public Observable<ProfileResponse> detachSocialAccount(String provider) {
        return usersCafeServer()
                .flatMap(UsersServer::provideProfileService)
                .flatMap(server -> server.detachSocialAccount(provider));
    }

    @Override
    public Observable<EmailResponse> sendEmail(EmailRequest request) {
        return usersCafeServer()
                .flatMap(UsersServer::provideProfileService)
                .flatMap(server -> server.sendEmail(request));
    }

    @Override
    public Observable<BusinessSectorsResponse> getSectors(StringKeyList sector, Integer status, String sort,
                                                          Integer limit, Integer page) {
        return businessServer()
                .flatMap(BusinessServer::provideSectorService)
                .flatMap(server -> server.getSectors(sector, status, sort, limit, page));
    }

    @Override
    public Observable<BusinessSectorsResponse> getSector(Integer sectorId) {
        return businessServer()
                .flatMap(BusinessServer::provideSectorService)
                .flatMap(server -> server.getSector(sectorId));
    }

    @Override
    public Observable<BusinessesResponse> getBusiness(String query, KeyList businessesIds,
                                                      String sectorId, String status,
                                                      BusinessTags tags, BusinessCategories categories,
                                                      String sort, Integer withTrashed, String city,
                                                      Integer limit, Integer page, Integer isSearchable) {
        return businessServer()
                .flatMap(BusinessServer::provideBusinessService)
                .flatMap(server -> server.getBusinesses(query, businessesIds, sectorId, status, tags,
                        categories, sort, withTrashed, city, limit, page, isSearchable));
    }

    @Override
    public Observable<BusinessAddressesResponse> getAddresses(String query, KeyList addressesIds,
                                                              KeyList businessId,
                                                              Integer status, Integer limit,
                                                              Integer page) {
        return businessServer()
                .flatMap(BusinessServer::provideAddressService)
                .flatMap(server -> server.getAddresses(
                        query, addressesIds, businessId, status, limit, page));
    }

    @Override
    public Observable<BusinessCategoriesResponse> getCategories(String query, String sectorId,
                                                                Integer status, String sort,
                                                                Integer limit, Integer page) {
        return businessServer()
                .flatMap(BusinessServer::provideCategoryService)
                .flatMap(server -> server.getCategories(query, sectorId, status, sort, limit, page));
    }

    @Override
    public Observable<BusinessCategoriesResponse> getCategoriesByBusiness(Integer businessId, Integer limit, Integer page) {
        return businessServer()
                .flatMap(BusinessServer::provideCategoryService)
                .flatMap(server -> server.getCategoriesByBusiness(businessId, limit, page));
    }

    @Override
    public Observable<BusinessPhotosResponse> getBusinessPhotos(Integer businessId, String sort,
                                                                Integer limit, Integer page) {
        return businessServer()
                .flatMap(BusinessServer::provideGalleryService)
                .flatMap(server -> server.getBusinessPhotos(businessId, sort, limit, page));
    }

    @Override
    public Observable<BusinessBeautyStaffResponse> getBusinessBeautyStaffs(String query, KeyList ids,
                                                                           Integer businessId, Integer addressId,
                                                                           KeyList services, Integer status,
                                                                           String sort, Integer withTrashed,
                                                                           Integer limit, Integer page, Integer let) {
        return businessBeautyServer()
                .flatMap(BusinessBeautyServer::provideStaffService)
                .flatMap(server -> server.getBusinessBeautyStaffs(query, ids, businessId, addressId,
                        services, status, sort, withTrashed, limit, page, let));
    }

    @Override
    public Observable<BusinessBeautyStaffResponse> getBusinessHealthStaffs(String query, KeyList ids,
                                                                           Integer businessId, Integer addressId,
                                                                           KeyList services, Integer status,
                                                                           String sort, Integer withTrashed,
                                                                           Integer limit, Integer page, Integer let) {
        return businessHealthServer()
                .flatMap(BusinessHealthServer::provideStaffService)
                .flatMap(server -> server.getBusinessBeautyStaffs(query, ids, businessId, addressId,
                        services, status, sort, withTrashed, limit, page, let));
    }

    @Override
    public Observable<BusinessCommentsResponse> getBusinessComments(Integer businessId, String sort,
                                                                    Integer limit, Integer page,
                                                                    Integer let) {
        return businessServer()
                .flatMap(BusinessServer::provideCommentService)
                .flatMap(server -> server.getBusinessComments(businessId, sort, limit, page, let));
    }

    @Override
    public Observable<BusinessCitiesResponse> getBusinessCities(String sort) {
        return businessServer()
                .flatMap(BusinessServer::provideCitiesService)
                .flatMap(server -> server.getBusinessCities(sort));
    }

    @Override
    public Observable<UsersResponse> getUsers(StringKeyList usersId) {
        return usersCafeServer()
                .flatMap(UsersServer::provideUsersService)
                .flatMap(server -> server.getUsers(usersId));
    }

    @Override
    public Observable<AddFavoriteResponse> businessAddFavorite(Integer id) {
        return businessServer()
                .flatMap(BusinessServer::provideFavoriteService)
                .flatMap(server -> server.businessAddFavorite(id));
    }

    @Override
    public Observable<DeleteFavoriteResponse> businessDeleteFavorite(Integer id) {
        return businessServer()
                .flatMap(BusinessServer::provideFavoriteService)
                .flatMap(server -> server.businessDeleteFavorite(id));
    }

    @Override
    public Observable<BeautyServiceGroupResponse> getBeautyServiceGroups(String query, KeyList id,
                                                                         KeyList services, KeyList addresses, KeyList staffs,
                                                                         Integer businessId, String sort, Integer withTrashed,
                                                                         Integer limit, Integer page, Integer let) {
        return businessBeautyServer()
                .flatMap(BusinessBeautyServer::provideServiceGroupService)
                .flatMap(server -> server.getBeautyServiceGroups(query, id, services, addresses,
                        staffs, businessId, sort, withTrashed, limit, page, let));
    }

    @Override
    public Observable<BeautyServiceGroupResponse> getHealthServiceGroups(String query, KeyList id,
                                                                         KeyList services, KeyList addresses, KeyList staffs,
                                                                         Integer businessId, String sort, Integer withTrashed,
                                                                         Integer limit, Integer page, Integer let) {
        return businessHealthServer()
                .flatMap(BusinessHealthServer::provideServiceGroupService)
                .flatMap(server -> server.getBeautyServiceGroups(query, id, services, addresses,
                        staffs, businessId, sort, withTrashed, limit, page, let));
    }

    @Override
    public Observable<BeautyServiceResponse> getServiceGroupBeautyServices(Integer id, String query,
                                                                           Integer businessId, Integer serviceGroupId, String sort,
                                                                           Integer status, Integer withTrashed, Integer limit, Integer page, Integer let) {
        return businessBeautyServer()
                .flatMap(BusinessBeautyServer::provideServiceService)
                .flatMap(server -> server.getServiceGroupBeautyServices(id, query, businessId,
                        serviceGroupId, sort, status, withTrashed, limit, page, let));
    }

    @Override
    public Observable<BeautyServiceResponse> getServiceGroupHealthServices(Integer id, String query,
                                                                           Integer businessId, Integer serviceGroupId, String sort,
                                                                           Integer status, Integer withTrashed, Integer limit, Integer page, Integer let) {
        return businessHealthServer()
                .flatMap(BusinessHealthServer::provideServiceService)
                .flatMap(server -> server.getServiceGroupBeautyServices(id, query, businessId,
                        serviceGroupId, sort, status, withTrashed, limit, page, let));
    }

    @Override
    public Observable<BeautyServiceResponse> getAddressBeautyServices(Integer id, String query,
                                                                      Integer businessId, Integer serviceGroupId, String sort,
                                                                      Integer status, Integer withTrashed, Integer limit, Integer page, Integer let) {
        return businessBeautyServer()
                .flatMap(BusinessBeautyServer::provideServiceService)
                .flatMap(server -> server.getAddressBeautyServices(id, query, businessId,
                        serviceGroupId, sort, status, withTrashed, limit, page, let));
    }

    @Override
    public Observable<BeautyServiceResponse> getAddressHealthServices(Integer id, String query,
                                                                      Integer businessId, Integer serviceGroupId, String sort,
                                                                      Integer status, Integer withTrashed, Integer limit, Integer page, Integer let) {
        return businessHealthServer()
                .flatMap(BusinessHealthServer::provideServiceService)
                .flatMap(server -> server.getAddressBeautyServices(id, query, businessId,
                        serviceGroupId, sort, status, withTrashed, limit, page, let));
    }

    @Override
    public Observable<BeautyWorkingDayResponse> getStaffBeautyWorkingDays(Integer staffId) {
        return businessBeautyServer()
                .flatMap(BusinessBeautyServer::provideWorkingDayService)
                .flatMap(server -> server.getStaffBeautyWorkingDays(staffId));
    }

    @Override
    public Observable<BeautyWorkingDayResponse> getStaffHealthWorkingDays(Integer staffId) {
        return businessHealthServer()
                .flatMap(BusinessHealthServer::provideWorkingDayService)
                .flatMap(server -> server.getStaffBeautyWorkingDays(staffId));
    }

    @Override
    public Observable<BusinessWorkingDayResponse> getBusinessAddressWorkingDays(Integer addressId) {
        return businessServer()
                .flatMap(BusinessServer::provideWorkingDayService)
                .flatMap(server -> server.getAddressWorkingDays(addressId));
    }

    @Override
    public Observable<BeautyTimeSlotResponse> getBusinessBeautyTimeSlots(Integer businessId,
                                                                         Integer addressId, Integer serviceId, Integer staffId,
                                                                         String date) {
        return bookingBeautyServer()
                .flatMap(BookingBeautyServer::provideTimeSlotService)
                .flatMap(server -> server.getBusinessBeautyTimeSlots(businessId, addressId,
                        serviceId, staffId, date));
    }

    @Override
    public Observable<BeautyGradualTimeSlotResponse> getBusinessBeautyGradualTimeSlots(Integer businessId,
                                                                                       Integer addressId,
                                                                                       KeyList services,
                                                                                       String date) {
        return bookingBeautyServer()
                .flatMap(BookingBeautyServer::provideTimeSlotService)
                .flatMap(server -> server.getBusinessBeautyGradualTimeSlots(businessId, addressId,
                        services, date));
    }

    @Override
    public Observable<BeautyTimeSlotResponse> getBusinessHealthTimeSlots(Integer businessId,
                                                                         Integer addressId, Integer serviceId, Integer staffId,
                                                                         String date) {
        return bookingHealthServer()
                .flatMap(BookingHealthServer::provideTimeSlotService)
                .flatMap(server -> server.getBusinessBeautyTimeSlots(businessId, addressId,
                        serviceId, staffId, date));
    }

    @Override
    public Observable<BeautyGradualTimeSlotResponse> getBusinessHealthGradualTimeSlots(Integer businessId,
                                                                                       Integer addressId,
                                                                                       KeyList services,
                                                                                       String date) {
        return bookingHealthServer()
                .flatMap(BookingHealthServer::provideTimeSlotService)
                .flatMap(server -> server.getBusinessBeautyGradualTimeSlots(businessId, addressId,
                        services, date));
    }

    @Override
    public Observable<CreateBookingBeautyResponse> createBookingBeauty(CreateBookingBeautyRequest request) {
        return bookingBeautyServer()
                .flatMap(BookingBeautyServer::provideBookingService)
                .flatMap(server -> server.createBookingBeauty(request));
    }

    @Override
    public Observable<CreateBookingBeautyResponse> createBookingHealth(CreateBookingBeautyRequest request) {
        return bookingHealthServer()
                .flatMap(BookingHealthServer::provideBookingService)
                .flatMap(server -> server.createBookingBeauty(request));
    }

    @Override
    public Observable<UserFavoritesResponse> getFavoritesByUser(String userId, String objectType,
                                                                String sort, Integer limit,
                                                                Integer page, Integer let) {
        return businessServer()
                .flatMap(BusinessServer::provideFavoriteService)
                .flatMap(server -> server.getFavoritesByUser(
                        userId, objectType, sort, limit, page, let));
    }

    @Override
    public Observable<UserFavoritesResponse> getFavoritesByUserAndSector(String userId, Integer sectorId,
                                                                         String objectType, String sort,
                                                                         Integer limit, Integer page,
                                                                         Integer let) {
        return businessServer()
                .flatMap(BusinessServer::provideFavoriteService)
                .flatMap(server -> server.getFavoritesByUserAndSector(
                        userId, sectorId, objectType, sort, limit, page, let));
    }

    @Override
    public Observable<BeautyBookingsResponse> getBeautyBookings(String userId, String query,
                                                                KeyList ids, Integer businessId,
                                                                Integer addressId, Integer staffId,
                                                                Integer serviceId, KeyList status,
                                                                KeyList decision, Integer type,
                                                                Boolean isAppeared, String sort,
                                                                Integer limit, Integer page,
                                                                Integer let) {
        return bookingBeautyServer()
                .flatMap(BookingBeautyServer::provideBookingService)
                .flatMap(server -> server.getBeautyBookings(userId, query, ids, businessId,
                        addressId, staffId, serviceId, status, decision, type, isAppeared,
                        sort, limit, page, let));
    }

    @Override
    public Observable<BeautyBookingsResponse> getHealthBookings(String userId, String query,
                                                                KeyList ids, Integer businessId,
                                                                Integer addressId, Integer staffId,
                                                                Integer serviceId, KeyList status,
                                                                KeyList decision, Integer type,
                                                                Boolean isAppeared, String sort,
                                                                Integer limit, Integer page,
                                                                Integer let) {
        return bookingHealthServer()
                .flatMap(BookingHealthServer::provideBookingService)
                .flatMap(server -> server.getBeautyBookings(userId, query, ids, businessId,
                        addressId, staffId, serviceId, status, decision, type, isAppeared,
                        sort, limit, page, let));
    }

    @Override
    public Observable<BeautyServiceResponse> getBeautyServices(String query, KeyList ids,
                                                               Integer businessId, Integer serviceGroupId,
                                                               String sort, Integer status, Integer withTrashed,
                                                               Integer limit, Integer page,
                                                               Integer let) {
        return businessBeautyServer()
                .flatMap(BusinessBeautyServer::provideServiceService)
                .flatMap(server -> server.getBeautyServices(query, ids, businessId, serviceGroupId,
                        sort, status, withTrashed, limit, page, let));
    }

    @Override
    public Observable<BeautyServiceResponse> getHealthServices(String query, KeyList ids,
                                                               Integer businessId, Integer serviceGroupId,
                                                               String sort, Integer status, Integer withTrashed,
                                                               Integer limit, Integer page,
                                                               Integer let) {
        return businessHealthServer()
                .flatMap(BusinessHealthServer::provideServiceService)
                .flatMap(server -> server.getBeautyServices(query, ids, businessId, serviceGroupId,
                        sort, status, withTrashed, limit, page, let));
    }

    @Override
    public Observable<CancelBookingBeautyResponse> cancelBookingBeauty(Integer bookingId) {
        return bookingBeautyServer()
                .flatMap(BookingBeautyServer::provideBookingService)
                .flatMap(server -> server.cancelBookingBeauty(bookingId));
    }

    @Override
    public Observable<CancelBookingBeautyResponse> cancelBookingHealth(Integer bookingId) {
        return bookingHealthServer()
                .flatMap(BookingHealthServer::provideBookingService)
                .flatMap(server -> server.cancelBookingBeauty(bookingId));
    }

    @Override
    public Observable<CreateBusinessCommentResponse> createBusinessComment(
            CreateBusinessCommentRequest request) {
        return businessServer()
                .flatMap(BusinessServer::provideCommentService)
                .flatMap(server -> server.createBusinessComment(request));
    }

    @Override
    public Observable<MarketingResponse> getMarketings(KeyList businessId, String title,
                                                       String status, String city, String from, String to,
                                                       Integer pageSize, Integer pageIndex) {
        return marketingServer()
                .flatMap(MarketingServer::provideMarketingService)
                .flatMap(server -> server.getMarketings(businessId, title, status, city, from, to,
                        pageSize, pageIndex));
    }

    @Override
    public Observable<EserviaFeedbackResponse> sendEserviaContactFeedback(EserviaFeedbackRequest request) {
        return businessServer()
                .flatMap(BusinessServer::provideContactService)
                .flatMap(server -> server.sendEserviaContactFeedback(request));
    }

    @Override
    public Observable<PromoterCustomerRegisterResponse> registerCustomer(Integer businessId) {
        return customerServer()
                .flatMap(CustomerServer::providePromoterCustomerService)
                .flatMap(server -> server.registerCustomer(businessId));
    }

    @Override
    public Observable<MenuResponse> getMenu(Long addressId, Long departmentId) {
        return orderRestoServer()
                .flatMap(OrderRestoServer::provideMenuService)
                .flatMap(server -> server.getMenu(addressId, departmentId));
    }

    @Override
    public Observable<MenuVersionResponse> getMenuVersion(Long addressId, Long departmentId) {
        return orderRestoServer()
                .flatMap(OrderRestoServer::provideMenuService)
                .flatMap(server -> server.getMenuVersion(addressId, departmentId));
    }

    @Override
    public Observable<BookingSettingsResponse> getRestoBookingSettings(Long addressId) {
        return bookingRestoServer()
                .flatMap(BookingRestoServer::provideBookingSettingsService)
                .flatMap(server -> server.getRestoBookingSettings(addressId));
    }

    @Override
    public Observable<GeneralBookingsResponse> getGeneralActiveBookings(Integer pageIndex, Integer pageSize) {
        return bookingRestoServer()
                .flatMap(BookingRestoServer::provideGeneralBokingsService)
                .flatMap(server -> server.getGeneralActiveBookings(pageIndex, pageSize));
    }

    @Override
    public Observable<GeneralBookingsResponse> getGeneralHistoryBookings(Integer pageIndex, Integer pageSize) {
        return bookingRestoServer()
                .flatMap(BookingRestoServer::provideGeneralBokingsService)
                .flatMap(server -> server.getGeneralHistoryBookings(pageIndex, pageSize));
    }

    @Override
    public Observable<RestoBookingResponse> postRestoBooking(RestoBookingRequest request) {
        return bookingRestoServer()
                .flatMap(BookingRestoServer::provideBookingService)
                .flatMap(server -> server.postRestoBooking(request));
    }

    @Override
    public Observable<DepartmentsResponse> getRestoDepartments(Long addressId) {
        return departmentRestoServer()
                .flatMap(DepartmentRestoServer::provideDepartmentService)
                .flatMap(server -> server.getRestoDepartments(addressId));
    }

    @Override
    public Observable<DeliveryRestoSettlementsResponse> getDeliveryRestoSettlements() {
        return deliveryRestoServer()
                .flatMap(DeliveryRestoServer::provideStreetServicingService)
                .flatMap(StreetServicingService::getDeliveryRestoSettlements);
    }

    @Override
    public Observable<DeliveryRestoStreetsResponse> getDeliveryRestoStreets(Long settlementId) {
        return deliveryRestoServer()
                .flatMap(DeliveryRestoServer::provideStreetServicingService)
                .flatMap(server -> server.getDeliveryRestoStreets(settlementId));
    }

    @Override
    public Observable<PostRestoOrderResponse> postRestoOrder(PostRestoOrderRequest request) {
        return orderRestoServer()
                .flatMap(OrderRestoServer::provideOrdersService)
                .flatMap(server -> server.postRestoOrder(request));
    }

    @Override
    public Observable<GetRestoOrdersResponse> getRestoOrders(List<Integer> ids) {
        return orderRestoServer()
                .flatMap(OrderRestoServer::provideOrdersService)
                .flatMap(server -> server.getRestoOrders(ids));
    }

    @Override
    public Observable<PostRestoDeliveryResponse> postRestoDelivery(PostRestoDeliveryRequest request) {
        return deliveryRestoServer()
                .flatMap(DeliveryRestoServer::provideDeliveriesService)
                .flatMap(server -> server.postRestoDelivery(request));
    }

    @Override
    public Observable<GetRestoDeliveriesResponse> getRestoDeliveries(List<String> deliveryStatuses,
                                                                     Integer pageSize,
                                                                     Integer pageIndex) {
        return deliveryRestoServer()
                .flatMap(DeliveryRestoServer::provideDeliveriesService)
                .flatMap(server -> server.getRestoDeliveries(deliveryStatuses, pageSize, pageIndex));
    }

    @Override
    public Observable<GetRestoBookingsResponse> getRestoBookingsActive(Integer pageSize, Integer pageIndex) {
        return bookingRestoServer()
                .flatMap(BookingRestoServer::provideBookingService)
                .flatMap(server -> server.getRestoBookingsActive(pageSize, pageIndex));
    }

    @Override
    public Observable<GetRestoBookingsResponse> getRestoBookingsHistory(Integer pageSize, Integer pageIndex) {
        return bookingRestoServer()
                .flatMap(BookingRestoServer::provideBookingService)
                .flatMap(server -> server.getRestoBookingsHistory(pageSize, pageIndex));
    }

    @Override
    public Observable<CancelRestoBookingResponse> cancelRestoBooking(CancelRestoBookingRequest request) {
        return bookingRestoServer()
                .flatMap(BookingRestoServer::provideBookingService)
                .flatMap(server -> server.cancelRestoBooking(request));
    }

    @Override
    public Observable<DepartmentsResponse> getRestoDepartmentsByIds(List<Integer> ids) {
        return departmentRestoServer()
                .flatMap(DepartmentRestoServer::provideDepartmentService)
                .flatMap(server -> server.getRestoDepartmentsByIds(ids));
    }

    @Override
    public Observable<BeautyPromotionsResponse> getBeautyBusinessPromotions(Integer businessId, KeyList ids,
                                                                            Integer marketingId, Integer serviceId,
                                                                            Integer staffId, Integer withTrashed,
                                                                            Integer limit, Integer page, Integer let) {
        return businessBeautyServer()
                .flatMap(BusinessBeautyServer::providePromotionService)
                .flatMap(server -> server.getBeautyBusinessPromotions(businessId, ids, marketingId,
                        serviceId, staffId, withTrashed, limit, page, let));
    }

    @Override
    public Observable<BeautyServiceResponse> getBeautyPromotionServices(Integer promotionId, Integer limit,
                                                                        Integer page, Integer let) {
        return businessBeautyServer()
                .flatMap(BusinessBeautyServer::providePromotionService)
                .flatMap(server -> server.getBeautyPromotionServices(promotionId, limit, page, let));
    }

    @Override
    public Observable<PromoterCustomerBusinessesResponse> getPromoterCustomerBusinesses() {
        return customerServer()
                .flatMap(CustomerServer::providePromoterCustomerService)
                .flatMap(PromoterCustomerService::getPromoterCustomerBusinesses);
    }
}
