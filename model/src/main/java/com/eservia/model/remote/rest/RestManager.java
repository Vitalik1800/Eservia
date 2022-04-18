package com.eservia.model.remote.rest;


import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.eservia.model.entity.Address;
import com.eservia.model.entity.BeautyGradualTimeSlot;
import com.eservia.model.entity.BeautyPromotion;
import com.eservia.model.entity.BeautyTimeSlot;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessComment;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.entity.ProfileUserData;
import com.eservia.model.entity.PromoterCustomerBusiness;
import com.eservia.model.entity.RestoDeliverySettlement;
import com.eservia.model.entity.RestoDeliveryStreet;
import com.eservia.model.entity.RestoDepartment;
import com.eservia.model.entity.RestoOrderResponseData;
import com.eservia.model.entity.UserInfoShort;
import com.eservia.model.local.ContentChangesObservable;
import com.eservia.model.local.SyncEvent;
import com.eservia.model.prefs.AccessToken;
import com.eservia.model.prefs.Profile;
import com.eservia.model.remote.rest.booking_beauty.services.booking.BeautyBookingsResponse;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CancelBookingBeautyResponse;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyRequest;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyResponse;
import com.eservia.model.remote.rest.booking_beauty.services.time_slot.BeautyGradualTimeSlotResponse;
import com.eservia.model.remote.rest.booking_beauty.services.time_slot.BeautyTimeSlotResponse;
import com.eservia.model.remote.rest.booking_resto.services.booking.CancelRestoBookingRequest;
import com.eservia.model.remote.rest.booking_resto.services.booking.CancelRestoBookingResponse;
import com.eservia.model.remote.rest.booking_resto.services.booking.GetRestoBookingsResponse;
import com.eservia.model.remote.rest.booking_resto.services.booking.RestoBookingRequest;
import com.eservia.model.remote.rest.booking_resto.services.booking.RestoBookingResponse;
import com.eservia.model.remote.rest.booking_resto.services.booking_settings.BookingSettingsResponse;
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponse;
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData;
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
import com.eservia.model.remote.rest.business.services.favorite.UserFavoritesResponse;
import com.eservia.model.remote.rest.business.services.gallery.BusinessPhotosResponse;
import com.eservia.model.remote.rest.business.services.sector.BusinessSectorsResponse;
import com.eservia.model.remote.rest.business.services.working_day.BusinessWorkingDayResponse;
import com.eservia.model.remote.rest.business_beauty.services.promotion.BeautyPromotionsResponse;
import com.eservia.model.remote.rest.business_beauty.services.service.BeautyServiceResponse;
import com.eservia.model.remote.rest.business_beauty.services.service_group.BeautyServiceGroupResponse;
import com.eservia.model.remote.rest.business_beauty.services.staff.BusinessBeautyStaffResponse;
import com.eservia.model.remote.rest.business_beauty.services.working_day.BeautyWorkingDayResponse;
import com.eservia.model.remote.rest.customer.services.promoter_customer.PromoterCustomerBusinessesResponse;
import com.eservia.model.remote.rest.delivery.services.deliveries.GetRestoDeliveriesResponse;
import com.eservia.model.remote.rest.delivery.services.deliveries.PostRestoDeliveryRequest;
import com.eservia.model.remote.rest.delivery.services.deliveries.PostRestoDeliveryResponse;
import com.eservia.model.remote.rest.delivery.services.street_servicing.DeliveryRestoSettlementsResponse;
import com.eservia.model.remote.rest.delivery.services.street_servicing.DeliveryRestoStreetsResponse;
import com.eservia.model.remote.rest.department_resto.services.department.DepartmentsResponse;
import com.eservia.model.remote.rest.marketing.services.Marketing.MarketingResponse;
import com.eservia.model.remote.rest.order_resto.services.menu.MenuResponse;
import com.eservia.model.remote.rest.order_resto.services.menu.MenuVersionResponse;
import com.eservia.model.remote.rest.order_resto.services.orders.GetRestoOrdersResponse;
import com.eservia.model.remote.rest.order_resto.services.orders.PostRestoOrderRequest;
import com.eservia.model.remote.rest.order_resto.services.orders.PostRestoOrderResponse;
import com.eservia.model.remote.rest.request.KeyList;
import com.eservia.model.remote.rest.request.StringKeyList;
import com.eservia.model.remote.rest.retrofit.WrongTokenHandler;
import com.eservia.model.remote.rest.users.services.CommonUsersRestResponse;
import com.eservia.model.remote.rest.users.services.auth.ConfirmPhoneRequest;
import com.eservia.model.remote.rest.users.services.auth.DeviceTokenResponse;
import com.eservia.model.remote.rest.users.services.auth.ForgotPasswordRequest;
import com.eservia.model.remote.rest.users.services.auth.LogoutRequest;
import com.eservia.model.remote.rest.users.services.auth.RestorePasswordRequest;
import com.eservia.model.remote.rest.users.services.auth.SignInRequest;
import com.eservia.model.remote.rest.users.services.auth.SignUpProviderRequest;
import com.eservia.model.remote.rest.users.services.auth.SignUpRequest;
import com.eservia.model.remote.rest.users.services.auth.SignUpResponse;
import com.eservia.model.remote.rest.users.services.auth.SocialSignInRequest;
import com.eservia.model.remote.rest.users.services.profile.ChangePassRequest;
import com.eservia.model.remote.rest.users.services.profile.EditProfileRequest;
import com.eservia.model.remote.rest.users.services.profile.EmailRequest;
import com.eservia.model.remote.rest.users.services.profile.ProfileResponse;
import com.eservia.model.remote.rest.users.services.users.UsersResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

public class RestManager {

    private final EserviaBookingRestClient restClient;

    public RestManager(EserviaBookingRestClient restClient) {
        this.restClient = restClient;
    }

    public Observable<SignUpResponse> registerUser(SignUpRequest request) {
        return restClient.registerUser(request)
                .doOnNext(response -> Profile.update(response.getData()));
    }

    public Observable<Boolean> confirmPhone(ConfirmPhoneRequest request) {
        return restClient.confirmPhone(request)
                .doOnNext(response -> AccessToken.setToken(response.getData()))
                .flatMap(response -> loadProfile());
    }

    public Observable<Boolean> resendConfirmCode(String phone) {
        return restClient.resendConfirmCode(phone)
                .map(response -> Boolean.TRUE);
    }

    public Observable<Boolean> signInUser(@NonNull String username, @NonNull String password) {
        return restClient.signInUser(new SignInRequest(username, password))
                .doOnNext(response -> AccessToken.setToken(response.getData()))
                .flatMap(response -> loadProfile());
    }

    public Observable<Boolean> signInUserWithProvider(String provider, String token) {
        return restClient.signInUser(provider, token)
                .doOnNext(response -> AccessToken.setToken(response.getData()))
                .flatMap(response -> loadProfile());
    }

    public Observable<Boolean> signUpUserWithProvider(String phone, String provider, String token) {
        return restClient.signUpUserWithProvider(new SignUpProviderRequest(phone, provider, token))
                .map(response -> Boolean.TRUE);
    }

    public Observable<Boolean> logout(LogoutRequest request) {
        return restClient.logout(request)
                .retryWhen(new WrongTokenHandler<>())
                .doOnNext(response -> Profile.logOut())
                .map(response -> Boolean.TRUE);
    }

    public Observable<Boolean> sendEmailConfirm() {
        return restClient.sendEmailConfirm()
                .retryWhen(new WrongTokenHandler<>())
                .map(response -> Boolean.TRUE);
    }

    public Observable<CommonUsersRestResponse> resetForgotPassword(String phone) {
        return restClient.resetForgotPassword(new ForgotPasswordRequest(phone));
    }

    public Observable<CommonUsersRestResponse> confirmResetPassword(@NonNull String phone,
                                                                    @NonNull String password,
                                                                    @NonNull String code) {
        return restClient.confirmResetPassword(new RestorePasswordRequest(phone, password, code));
    }

    public Observable<Boolean> refreshToken(String refreshToken) {
        return restClient.refreshToken(refreshToken)
                .doOnNext(response -> AccessToken.setToken(response.getData()))
                .map(response -> Boolean.TRUE);
    }

    public Observable<Boolean> postDeviceToken(String deviceToken) {
        return restClient.postDeviceToken(deviceToken)
                .map(DeviceTokenResponse::getData);
    }

    public Observable<Boolean> loadProfile() {
        return restClient.loadProfile()
                .retryWhen(new WrongTokenHandler<>())
                .doOnNext(response -> Profile.update(response.getData()))
                .map(response -> Boolean.TRUE);
    }

    public Observable<Boolean> updateProfile(EditProfileRequest editProfileRequest) {
        return restClient.updateProfile(editProfileRequest)
                .retryWhen(new WrongTokenHandler<>())
                .doOnNext(response -> Profile.update(response.getData()))
                .map(response -> Boolean.TRUE);
    }

    public Observable<Boolean> uploadPhoto(MultipartBody.Part photo) {
        return restClient.uploadPhoto(photo)
                .retryWhen(new WrongTokenHandler<>())
                .doOnNext(response -> {
                    Profile.setPhotoId(response.getData());
                    ContentChangesObservable.send(SyncEvent.PROFILE);
                })
                .map(response -> Boolean.TRUE);
    }

    public Observable<CommonUsersRestResponse> changePassword(ChangePassRequest request) {
        return restClient.changePassword(request)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<ProfileUserData> attachSocialAccount(@NotNull SocialSignInRequest request) {
        return restClient.attachSocialAccount(request)
                .retryWhen(new WrongTokenHandler<>())
                .map(ProfileResponse::getData)
                .doOnNext(Profile::update);
    }

    public Observable<ProfileUserData> detachSocialAccount(String provider) {
        return restClient.detachSocialAccount(provider)
                .retryWhen(new WrongTokenHandler<>())
                .map(ProfileResponse::getData)
                .doOnNext(Profile::update);
    }

    public Observable<Boolean> sendEmail(EmailRequest request) {
        return restClient.sendEmail(request)
                .retryWhen(new WrongTokenHandler<>())
                .doOnNext(response -> Profile.update(response.getData()))
                .map(response -> Boolean.TRUE);
    }

    public Observable<BusinessSectorsResponse> getSectors(StringKeyList sectors, Integer status,
                                                          String sort, Integer limit, Integer page) {
        return restClient.getSectors(sectors, status, sort, limit, page)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<List<BusinessSector>> getSector(Integer sectorId) {
        return restClient.getSector(sectorId)
                .retryWhen(new WrongTokenHandler<>())
                .map(BusinessSectorsResponse::getData);
    }

    public Observable<List<Business>> getBusinesses(String query, KeyList businessesIds,
                                                    String sectorId, String status,
                                                    BusinessTags tags, BusinessCategories categories,
                                                    String sort, Integer withTrashed, String city,
                                                    Integer limit, Integer page,
                                                    Integer isSearchable) {
        return restClient.getBusiness(query, businessesIds, sectorId, status, tags, categories,
                sort, withTrashed, city, limit, page, isSearchable)
                .retryWhen(new WrongTokenHandler<>())
                .map(BusinessesResponse::getData);
    }

    public Observable<List<Business>> getBusinessesWithAddresses(String query,
                                                                 KeyList businessesIds,
                                                                 String sectorId,
                                                                 String status,
                                                                 BusinessTags tags,
                                                                 BusinessCategories categories,
                                                                 String sort, Integer withTrashed,
                                                                 String city, Integer limit,
                                                                 Integer page, Integer isSearchable) {

        return restClient.getBusiness(query, businessesIds, sectorId, status, tags, categories, sort, withTrashed, city, limit, page, isSearchable)
                .retryWhen(new WrongTokenHandler<>())
                .flatMap(businessesResponse -> {
                    HashSet<Integer> businessesIdsSet = new HashSet<>();
                    for (Business business : businessesResponse.getData()) {
                        businessesIdsSet.add(business.getId());
                    }
                    return getAllAddresses(null, null,
                            new KeyList().addAll(businessesIdsSet), Address.STATUS_ACTIVE)

                            .flatMap(addresses -> {

                                for (Business business : businessesResponse.getData()) {
                                    for (Address address : addresses) {
                                        if (business.getId().equals(address.getBusinessId())) {
                                            business.getAddresses().add(address);
                                        }
                                    }
                                }
                                return Observable.just(businessesResponse.getData());
                            });
                });
    }

    public Observable<List<Address>> getAllAddresses(String query, KeyList addressIds,
                                                     KeyList businessId, Integer status) {

        int limit = 50;
        return getAddresses(query, addressIds, businessId, status, limit, 1)
                .flatMap(addressResponse -> {
                    int totalPages = addressResponse.getMeta().getPages();
                    if (totalPages == 1) {
                        return Observable.just(addressResponse.getData());
                    } else {
                        return Observable.range(2, --totalPages)
                                .concatMap(page -> getAddresses(query, addressIds, businessId, status, limit, page))
                                .map(BusinessAddressesResponse::getData)
                                .flatMap(Observable::just)
                                .scan((addresses1, addresses2) -> {
                                    List<Address> list = new ArrayList<>();
                                    list.addAll(addresses1);
                                    list.addAll(addresses2);
                                    return list;
                                })
                                .last(new ArrayList<>())
                                .map(list -> {
                                    list.addAll(addressResponse.getData());
                                    return list;
                                })
                                .toObservable();
                    }
                });
    }

    public Observable<BusinessAddressesResponse> getAddresses(String query, KeyList addressIds,
                                                              KeyList businessId,
                                                              Integer status, Integer limit,
                                                              Integer page) {
        return restClient.getAddresses(query, addressIds, businessId, status, limit, page)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BusinessCategoriesResponse> getCategories(String query, String sectorId,
                                                                Integer status, String sort,
                                                                Integer limit, Integer page) {
        return restClient.getCategories(query, sectorId, status, sort, limit, page)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BusinessCategoriesResponse> getCategoriesByBusiness(Integer businessId, Integer limit,
                                                                          Integer page) {
        return restClient.getCategoriesByBusiness(businessId, limit, page)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BusinessPhotosResponse> getBusinessPhotos(Integer businessId, String sort,
                                                                Integer limit, Integer page) {
        return restClient.getBusinessPhotos(businessId, sort, limit, page)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BusinessCitiesResponse> getBusinessCities(String sort) {
        return restClient.getBusinessCities(sort)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BusinessBeautyStaffResponse> getBusinessBeautyStaffs(String query, KeyList ids,
                                                                           Integer businessId, Integer addressId,
                                                                           KeyList services, Integer status,
                                                                           String sort, Integer withTrashed,
                                                                           Integer limit, Integer page, Integer let) {
        return restClient.getBusinessBeautyStaffs(query, ids, businessId, addressId, services, status,
                sort, withTrashed, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BusinessBeautyStaffResponse> getBusinessHealthStaffs(String query, KeyList ids,
                                                                           Integer businessId, Integer addressId,
                                                                           KeyList services, Integer status,
                                                                           String sort, Integer withTrashed,
                                                                           Integer limit, Integer page, Integer let) {
        return restClient.getBusinessHealthStaffs(query, ids, businessId, addressId, services, status,
                sort, withTrashed, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BusinessCommentsResponse> getBusinessComments(Integer businessId, String sort,
                                                                    Integer limit, Integer page,
                                                                    Integer let) {
        return restClient.getBusinessComments(businessId, sort, limit, page, let)
                .retryWhen(new WrongTokenHandler<>())
                .flatMap(commentResponse -> {
                    StringKeyList keys = new StringKeyList();
                    for (BusinessComment comment : commentResponse.getData()) {
                        keys.getKeys().add(comment.getUserId());
                    }
                    return getUsers(keys)
                            .map(users -> {
                                for (BusinessComment comment : commentResponse.getData()) {
                                    for (UserInfoShort info : users) {
                                        if (comment.getUserId().equals(info.getId())) {
                                            comment.setUserInfo(info);
                                        }
                                    }
                                }
                                return commentResponse;
                            });
                });
    }

    public Observable<List<UserInfoShort>> getUsers(StringKeyList usersId) {
        if (usersId.getKeys().isEmpty()) {
            return Observable.just(new ArrayList<>());
        } else {
            return restClient.getUsers(usersId)
                    .retryWhen(new WrongTokenHandler<>())
                    .map(UsersResponse::getData);
        }
    }

    public Observable<Boolean> businessAddFavorite(Integer businessId) {
        return restClient.businessAddFavorite(businessId)
                .retryWhen(new WrongTokenHandler<>())
                .doOnNext(response -> ContentChangesObservable.send(SyncEvent.FAVORITE))
                .map(response -> Boolean.TRUE);
    }

    public Observable<Boolean> businessDeleteFavorite(Integer businessId) {
        return restClient.businessDeleteFavorite(businessId)
                .retryWhen(new WrongTokenHandler<>())
                .doOnNext(response -> ContentChangesObservable.send(SyncEvent.FAVORITE))
                .map(response -> Boolean.TRUE);
    }

    public Observable<BeautyServiceGroupResponse> getBeautyServiceGroups(String query, KeyList id,
                                                                         KeyList services, KeyList addresses,
                                                                         KeyList staffs, Integer businessId,
                                                                         String sort, Integer withTrashed,
                                                                         Integer limit, Integer page,
                                                                         Integer let) {
        return restClient.getBeautyServiceGroups(query, id, services, addresses, staffs, businessId,
                sort, withTrashed, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BeautyServiceGroupResponse> getHealthServiceGroups(String query, KeyList id,
                                                                         KeyList services, KeyList addresses,
                                                                         KeyList staffs, Integer businessId,
                                                                         String sort, Integer withTrashed,
                                                                         Integer limit, Integer page,
                                                                         Integer let) {
        return restClient.getHealthServiceGroups(query, id, services, addresses, staffs, businessId,
                sort, withTrashed, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BeautyServiceResponse> getServiceGroupBeautyServices(Integer id, String query,
                                                                           Integer businessId, Integer serviceGroupId,
                                                                           String sort, Integer status, Integer withTrashed,
                                                                           Integer limit, Integer page, Integer let) {
        return restClient.getServiceGroupBeautyServices(id, query, businessId, serviceGroupId, sort, status,
                withTrashed, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BeautyServiceResponse> getServiceGroupHealthServices(Integer id, String query,
                                                                           Integer businessId, Integer serviceGroupId,
                                                                           String sort, Integer status, Integer withTrashed,
                                                                           Integer limit, Integer page, Integer let) {
        return restClient.getServiceGroupHealthServices(id, query, businessId, serviceGroupId, sort, status,
                withTrashed, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BeautyServiceResponse> getAddressBeautyServices(Integer id, String query,
                                                                      Integer businessId, Integer serviceGroupId,
                                                                      String sort, Integer status, Integer withTrashed,
                                                                      Integer limit, Integer page, Integer let) {
        return restClient.getAddressBeautyServices(id, query, businessId, serviceGroupId, sort, status,
                withTrashed, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BeautyServiceResponse> getAddressHealthServices(Integer id, String query,
                                                                      Integer businessId, Integer serviceGroupId,
                                                                      String sort, Integer status, Integer withTrashed,
                                                                      Integer limit, Integer page, Integer let) {
        return restClient.getAddressHealthServices(id, query, businessId, serviceGroupId, sort, status,
                withTrashed, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BeautyWorkingDayResponse> getStaffBeautyWorkingDays(Integer staffId) {
        return restClient.getStaffBeautyWorkingDays(staffId)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BeautyWorkingDayResponse> getStaffHealthWorkingDays(Integer staffId) {
        return restClient.getStaffHealthWorkingDays(staffId)
                .retryWhen(new WrongTokenHandler<>());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Observable<BeautyTimeSlotResponse> getBusinessBeautyTimeSlots(Integer businessId,
                                                                         Integer addressId, Integer serviceId,
                                                                         Integer staffId, String date) {
        return restClient.getBusinessBeautyTimeSlots(businessId, addressId, serviceId, staffId, date)
                .map(r -> {
                    Collections.sort(r.getData(), Comparator.comparing(BeautyTimeSlot::getDate));
                    return r;
                })
                .retryWhen(new WrongTokenHandler<>());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Observable<BeautyTimeSlotResponse> getBusinessHealthTimeSlots(Integer businessId,
                                                                         Integer addressId, Integer serviceId,
                                                                         Integer staffId, String date) {
        return restClient.getBusinessHealthTimeSlots(businessId, addressId, serviceId, staffId, date)
                .map(r -> {
                    Collections.sort(r.getData(),
                            Comparator.comparing(BeautyTimeSlot::getDate));
                    return r;
                })
                .retryWhen(new WrongTokenHandler<>());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Observable<BeautyGradualTimeSlotResponse> getBusinessBeautyGradualTimeSlots(Integer businessId,
                                                                                       Integer addressId,
                                                                                       KeyList services,
                                                                                       String date) {
        return restClient.getBusinessBeautyGradualTimeSlots(businessId, addressId, services, date)
                .map(r -> {
                    Collections.sort(r.getData(), Comparator.comparing(BeautyGradualTimeSlot::getDate));
                    return r;
                })
                .retryWhen(new WrongTokenHandler<>());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Observable<BeautyGradualTimeSlotResponse> getBusinessHealthGradualTimeSlots(Integer businessId,
                                                                                       Integer addressId,
                                                                                       KeyList services,
                                                                                       String date) {
        return restClient.getBusinessHealthGradualTimeSlots(businessId, addressId, services, date)
                .map(r -> {
                    Collections.sort(r.getData(), Comparator.comparing(BeautyGradualTimeSlot::getDate));
                    return r;
                })
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<CreateBookingBeautyResponse> createBookingBeauty(CreateBookingBeautyRequest request) {
        return restClient.createBookingBeauty(request)
                .retryWhen(new WrongTokenHandler<>())
                .doOnNext(response -> ContentChangesObservable.send(SyncEvent.BOOKINGS));
    }

    public Observable<CreateBookingBeautyResponse> createBookingHealth(CreateBookingBeautyRequest request) {
        return restClient.createBookingHealth(request)
                .retryWhen(new WrongTokenHandler<>())
                .doOnNext(response -> ContentChangesObservable.send(SyncEvent.BOOKINGS));
    }

    public Observable<BusinessWorkingDayResponse> getBusinessAddressWorkingDays(Integer addressId) {
        return restClient.getBusinessAddressWorkingDays(addressId)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<UserFavoritesResponse> getFavoritesByUser(String userId, String objectType,
                                                                String sort, Integer limit,
                                                                Integer page, Integer let) {
        return restClient.getFavoritesByUser(userId, objectType, sort, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<UserFavoritesResponse> getFavoritesByUserAndSector(String userId, Integer sectorId,
                                                                         String objectType, String sort,
                                                                         Integer limit, Integer page,
                                                                         Integer let) {
        return restClient.getFavoritesByUserAndSector(userId, sectorId, objectType,
                sort, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BeautyServiceResponse> getBeautyServices(String query, KeyList ids,
                                                               Integer businessId, Integer serviceGroupId,
                                                               String sort, Integer status, Integer withTrashed,
                                                               Integer limit, Integer page,
                                                               Integer let) {
        return restClient.getBeautyServices(query, ids, businessId, serviceGroupId, sort, status,
                withTrashed, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BeautyServiceResponse> getHealthServices(String query, KeyList ids,
                                                               Integer businessId, Integer serviceGroupId,
                                                               String sort, Integer status, Integer withTrashed,
                                                               Integer limit, Integer page,
                                                               Integer let) {
        return restClient.getHealthServices(query, ids, businessId, serviceGroupId, sort, status,
                withTrashed, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BeautyBookingsResponse> getBeautyBookings(String userId, String query,
                                                                KeyList ids, Integer businessId,
                                                                Integer addressId, Integer staffId,
                                                                Integer serviceId, KeyList status,
                                                                KeyList decision, Integer type,
                                                                Boolean isAppeared, String sort,
                                                                Integer limit, Integer page,
                                                                Integer let) {
        return restClient.getBeautyBookings(userId, query, ids, businessId, addressId, staffId,
                serviceId, status, decision, type, isAppeared, sort, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<BeautyBookingsResponse> getHealthBookings(String userId, String query,
                                                                KeyList ids, Integer businessId,
                                                                Integer addressId, Integer staffId,
                                                                Integer serviceId, KeyList status,
                                                                KeyList decision, Integer type,
                                                                Boolean isAppeared, String sort,
                                                                Integer limit, Integer page,
                                                                Integer let) {
        return restClient.getHealthBookings(userId, query, ids, businessId, addressId, staffId,
                serviceId, status, decision, type, isAppeared, sort, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<CancelBookingBeautyResponse> cancelBookingBeauty(Integer bookingId) {
        return restClient.cancelBookingBeauty(bookingId)
                .retryWhen(new WrongTokenHandler<>());
        //.doOnNext(response -> ContentChangesObservable.send(SyncEvent.BOOKINGS));
    }

    public Observable<CancelBookingBeautyResponse> cancelBookingHealth(Integer bookingId) {
        return restClient.cancelBookingHealth(bookingId)
                .retryWhen(new WrongTokenHandler<>());
        //.doOnNext(response -> ContentChangesObservable.send(SyncEvent.BOOKINGS));
    }

    public Observable<CreateBusinessCommentResponse> createBusinessComment(
            CreateBusinessCommentRequest request) {
        return restClient.createBusinessComment(request)
                .retryWhen(new WrongTokenHandler<>())
                .doOnNext(response -> {
                    ContentChangesObservable.send(SyncEvent.COMMENT);
                    ContentChangesObservable.send(SyncEvent.ADDED_COMMENT);
                });
    }

    public Observable<MarketingResponse> getMarketings(KeyList businessId, String title,
                                                       String status, String city, String from,
                                                       String to, Integer pageSize,
                                                       Integer pageIndex) {
        return restClient.getMarketings(businessId, title, status, city, from, to, pageSize, pageIndex)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<Boolean> sendEserviaContactFeedback(EserviaFeedbackRequest request) {
        return restClient.sendEserviaContactFeedback(request)
                .retryWhen(new WrongTokenHandler<>())
                .map(response -> Boolean.TRUE);
    }

    public Observable<Boolean> registerCustomer(Integer businessId) {
        return restClient.registerCustomer(businessId)
                .retryWhen(new WrongTokenHandler<>())
                .map(response -> Boolean.TRUE);
    }

    public Observable<MenuResponse> getMenu(Long addressId, Long departmentId) {
        return restClient.getMenu(addressId, departmentId)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<Long> getMenuVersion(Long addressId, Long departmentId) {
        return restClient.getMenuVersion(addressId, departmentId)
                .retryWhen(new WrongTokenHandler<>())
                .map(MenuVersionResponse::getData);
    }

    public Observable<BookingSettingsResponse> getRestoBookingSettings(Long addressId) {
        return restClient.getRestoBookingSettings(addressId)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<List<GeneralBookingsResponseData>> getGeneralActiveBookings(Integer pageIndex, Integer pageSize) {
        return restClient.getGeneralActiveBookings(pageIndex, pageSize)
                .retryWhen(new WrongTokenHandler<>())
                .map(GeneralBookingsResponse::getData);
    }

    public Observable<List<GeneralBookingsResponseData>> getGeneralHistoryBookings(Integer pageIndex, Integer pageSize) {
        return restClient.getGeneralHistoryBookings(pageIndex, pageSize)
                .retryWhen(new WrongTokenHandler<>())
                .map(GeneralBookingsResponse::getData);
    }

    public Observable<RestoBookingResponse> postRestoBooking(RestoBookingRequest request) {
        return restClient.postRestoBooking(request)
                .retryWhen(new WrongTokenHandler<>())
                .doOnNext(response -> ContentChangesObservable.send(SyncEvent.BOOKINGS));
    }

    public Observable<List<RestoDepartment>> getRestoDepartments(Long addressId) {
        return restClient.getRestoDepartments(addressId)
                .retryWhen(new WrongTokenHandler<>())
                .map(DepartmentsResponse::getData);
    }

    public Observable<List<RestoDeliverySettlement>> getDeliveryRestoSettlements() {
        return restClient.getDeliveryRestoSettlements()
                .retryWhen(new WrongTokenHandler<>())
                .map(DeliveryRestoSettlementsResponse::getData);
    }

    public Observable<List<RestoDeliveryStreet>> getDeliveryRestoStreets(Long settlementId) {
        return restClient.getDeliveryRestoStreets(settlementId)
                .retryWhen(new WrongTokenHandler<>())
                .map(DeliveryRestoStreetsResponse::getData);
    }

    public Observable<PostRestoOrderResponse> postRestoOrder(PostRestoOrderRequest request) {
        return restClient.postRestoOrder(request)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<List<RestoOrderResponseData>> getRestoOrders(List<Integer> ids) {
        return restClient.getRestoOrders(ids)
                .retryWhen(new WrongTokenHandler<>())
                .map(GetRestoOrdersResponse::getData);
    }

    public Observable<PostRestoDeliveryResponse> postRestoDelivery(PostRestoDeliveryRequest request) {
        return restClient.postRestoDelivery(request)
                .retryWhen(new WrongTokenHandler<>())
                .doOnNext(response -> ContentChangesObservable.send(SyncEvent.BOOKINGS));
    }

    public Observable<GetRestoDeliveriesResponse> getRestoDeliveries(List<String> deliveryStatuses,
                                                                     Integer pageSize, Integer pageIndex) {
        return restClient.getRestoDeliveries(deliveryStatuses, pageSize, pageIndex)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<GetRestoBookingsResponse> getRestoBookingsActive(Integer pageSize, Integer pageIndex) {
        return restClient.getRestoBookingsActive(pageSize, pageIndex)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<GetRestoBookingsResponse> getRestoBookingsHistory(Integer pageSize, Integer pageIndex) {
        return restClient.getRestoBookingsHistory(pageSize, pageIndex)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<CancelRestoBookingResponse> cancelRestoBooking(CancelRestoBookingRequest request) {
        return restClient.cancelRestoBooking(request)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<DepartmentsResponse> getRestoDepartmentsByIds(List<Integer> ids) {
        return restClient.getRestoDepartmentsByIds(ids)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<List<BeautyPromotion>> getBeautyBusinessPromotions(Integer businessId, KeyList ids,
                                                                         Integer marketingId, Integer serviceId,
                                                                         Integer staffId, Integer withTrashed,
                                                                         Integer limit, Integer page, Integer let) {
        return restClient.getBeautyBusinessPromotions(businessId, ids, marketingId, serviceId,
                staffId, withTrashed, limit, page, let)
                .retryWhen(new WrongTokenHandler<>())
                .map(BeautyPromotionsResponse::getData);
    }

    public Observable<BeautyServiceResponse> getBeautyPromotionServices(Integer promotionId, Integer limit,
                                                                        Integer page, Integer let) {
        return restClient.getBeautyPromotionServices(promotionId, limit, page, let)
                .retryWhen(new WrongTokenHandler<>());
    }

    public Observable<List<PromoterCustomerBusiness>> getPromoterCustomerBusinesses() {
        return restClient.getPromoterCustomerBusinesses()
                .retryWhen(new WrongTokenHandler<>())
                .map(PromoterCustomerBusinessesResponse::getData);
    }
}
