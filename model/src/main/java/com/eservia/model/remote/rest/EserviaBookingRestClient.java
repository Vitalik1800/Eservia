package com.eservia.model.remote.rest;

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
import com.eservia.model.remote.rest.business_beauty.services.promotion.BeautyPromotionsResponse;
import com.eservia.model.remote.rest.business_beauty.services.service.BeautyServiceResponse;
import com.eservia.model.remote.rest.business_beauty.services.service_group.BeautyServiceGroupResponse;
import com.eservia.model.remote.rest.business_beauty.services.staff.BusinessBeautyStaffResponse;
import com.eservia.model.remote.rest.business_beauty.services.working_day.BeautyWorkingDayResponse;
import com.eservia.model.remote.rest.customer.services.promoter_customer.PromoterCustomerBusinessesResponse;
import com.eservia.model.remote.rest.customer.services.promoter_customer.PromoterCustomerRegisterResponse;
import com.eservia.model.remote.rest.delivery.services.deliveries.GetRestoDeliveriesResponse;
import com.eservia.model.remote.rest.delivery.services.deliveries.PostRestoDeliveryRequest;
import com.eservia.model.remote.rest.delivery.services.deliveries.PostRestoDeliveryResponse;
import com.eservia.model.remote.rest.delivery.services.street_servicing.DeliveryRestoSettlementsResponse;
import com.eservia.model.remote.rest.delivery.services.street_servicing.DeliveryRestoStreetsResponse;
import com.eservia.model.remote.rest.department_resto.services.department.DepartmentsResponse;
import com.eservia.model.remote.rest.file.services.photo.UploadPhotoResponse;
import com.eservia.model.remote.rest.marketing.services.Marketing.MarketingResponse;
import com.eservia.model.remote.rest.order_resto.services.menu.MenuResponse;
import com.eservia.model.remote.rest.order_resto.services.menu.MenuVersionResponse;
import com.eservia.model.remote.rest.order_resto.services.orders.GetRestoOrdersResponse;
import com.eservia.model.remote.rest.order_resto.services.orders.PostRestoOrderRequest;
import com.eservia.model.remote.rest.order_resto.services.orders.PostRestoOrderResponse;
import com.eservia.model.remote.rest.request.KeyList;
import com.eservia.model.remote.rest.request.StringKeyList;
import com.eservia.model.remote.rest.users.services.CommonUsersRestResponse;
import com.eservia.model.remote.rest.users.services.auth.AuthResponse;
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
import com.eservia.model.remote.rest.users.services.profile.EmailResponse;
import com.eservia.model.remote.rest.users.services.profile.ProfileResponse;
import com.eservia.model.remote.rest.users.services.users.UsersResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


public interface EserviaBookingRestClient {

    Observable<SignUpResponse> registerUser(SignUpRequest request);

    Observable<AuthResponse> confirmPhone(ConfirmPhoneRequest request);

    Observable<CommonUsersRestResponse> resendConfirmCode(String phone);

    Observable<AuthResponse> signInUser(SignInRequest request);

    Observable<AuthResponse> signInUser(String provider, String token);

    Observable<CommonUsersRestResponse> signUpUserWithProvider(SignUpProviderRequest request);

    Observable<CommonUsersRestResponse> logout(LogoutRequest request);

    Observable<CommonUsersRestResponse> sendEmailConfirm();

    Observable<CommonUsersRestResponse> resetForgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    Observable<CommonUsersRestResponse> confirmResetPassword(RestorePasswordRequest restore);

    Observable<AuthResponse> refreshToken(String refreshToken);

    Observable<DeviceTokenResponse> postDeviceToken(String fcmToken);

    Observable<ProfileResponse> loadProfile();

    Observable<ProfileResponse> updateProfile(EditProfileRequest editProfileRequest);

    Observable<UploadPhotoResponse> uploadPhoto(MultipartBody.Part photo);

    Observable<CommonUsersRestResponse> changePassword(ChangePassRequest request);

    Observable<ProfileResponse> attachSocialAccount(SocialSignInRequest request);

    Observable<EmailResponse> sendEmail(EmailRequest emailRequest);

    Observable<ProfileResponse> detachSocialAccount(String provider);

    Observable<BusinessSectorsResponse> getSectors(StringKeyList sector, Integer status, String sort,
                                                   Integer limit, Integer page);

    Observable<BusinessSectorsResponse> getSector(Integer sectorId);

    Observable<BusinessesResponse> getBusiness(String query, KeyList businessesIds,
                                               String sectorId, String status,
                                               BusinessTags tags, BusinessCategories categories,
                                               String sort, Integer withTrashed, String city, Integer limit,
                                               Integer page, Integer isSearchable);

    Observable<BusinessAddressesResponse> getAddresses(String query, KeyList addressesIds,
                                                       KeyList businessId,
                                                       Integer status, Integer limit, Integer page);

    Observable<BusinessCategoriesResponse> getCategories(String query, String sectorId,
                                                         Integer status, String sort,
                                                         Integer limit, Integer page);

    Observable<BusinessCategoriesResponse> getCategoriesByBusiness(Integer businessId, Integer limit, Integer page);

    Observable<BusinessPhotosResponse> getBusinessPhotos(Integer businessId, String sort,
                                                         Integer limit, Integer page);

    Observable<BusinessBeautyStaffResponse> getBusinessBeautyStaffs(String query, KeyList ids,
                                                                    Integer businessId,
                                                                    Integer addressId, KeyList services,
                                                                    Integer status, String sort,
                                                                    Integer withTrashed, Integer limit,
                                                                    Integer page, Integer let);

    Observable<BusinessBeautyStaffResponse> getBusinessHealthStaffs(String query, KeyList ids,
                                                                    Integer businessId,
                                                                    Integer addressId, KeyList services,
                                                                    Integer status, String sort,
                                                                    Integer withTrashed, Integer limit,
                                                                    Integer page, Integer let);

    Observable<BusinessCommentsResponse> getBusinessComments(Integer businessId, String sort,
                                                             Integer limit, Integer page, Integer let);

    Observable<UsersResponse> getUsers(StringKeyList usersId);

    Observable<AddFavoriteResponse> businessAddFavorite(Integer id);

    Observable<DeleteFavoriteResponse> businessDeleteFavorite(Integer id);

    Observable<BeautyServiceGroupResponse> getBeautyServiceGroups(String query, KeyList id,
                                                                  KeyList services, KeyList addresses,
                                                                  KeyList staffs, Integer businessId,
                                                                  String sort, Integer withTrashed,
                                                                  Integer limit, Integer page,
                                                                  Integer let);

    Observable<BeautyServiceGroupResponse> getHealthServiceGroups(String query, KeyList id,
                                                                  KeyList services, KeyList addresses,
                                                                  KeyList staffs, Integer businessId,
                                                                  String sort, Integer withTrashed,
                                                                  Integer limit, Integer page,
                                                                  Integer let);

    Observable<BeautyServiceResponse> getServiceGroupBeautyServices(Integer id, String query,
                                                                    Integer businessId, Integer serviceGroupId,
                                                                    String sort, Integer status, Integer withTrashed,
                                                                    Integer limit, Integer page, Integer let);

    Observable<BeautyServiceResponse> getServiceGroupHealthServices(Integer id, String query,
                                                                    Integer businessId, Integer serviceGroupId,
                                                                    String sort, Integer status, Integer withTrashed,
                                                                    Integer limit, Integer page, Integer let);

    Observable<BeautyServiceResponse> getAddressBeautyServices(Integer id, String query,
                                                               Integer businessId, Integer serviceGroupId,
                                                               String sort, Integer status, Integer withTrashed,
                                                               Integer limit, Integer page, Integer let);

    Observable<BeautyServiceResponse> getAddressHealthServices(Integer id, String query,
                                                               Integer businessId, Integer serviceGroupId,
                                                               String sort, Integer status, Integer withTrashed,
                                                               Integer limit, Integer page, Integer let);

    Observable<BeautyWorkingDayResponse> getStaffBeautyWorkingDays(Integer staffId);

    Observable<BeautyWorkingDayResponse> getStaffHealthWorkingDays(Integer staffId);

    Observable<BusinessWorkingDayResponse> getBusinessAddressWorkingDays(Integer addressId);

    Observable<BeautyTimeSlotResponse> getBusinessBeautyTimeSlots(Integer businessId, Integer addressId,
                                                                  Integer serviceId, Integer staffId,
                                                                  String date);

    Observable<BeautyGradualTimeSlotResponse> getBusinessBeautyGradualTimeSlots(Integer businessId,
                                                                                Integer addressId,
                                                                                KeyList services,
                                                                                String date);

    Observable<BeautyTimeSlotResponse> getBusinessHealthTimeSlots(Integer businessId, Integer addressId,
                                                                  Integer serviceId, Integer staffId,
                                                                  String date);

    Observable<BeautyGradualTimeSlotResponse> getBusinessHealthGradualTimeSlots(Integer businessId,
                                                                                Integer addressId,
                                                                                KeyList services,
                                                                                String date);

    Observable<CreateBookingBeautyResponse> createBookingBeauty(CreateBookingBeautyRequest request);

    Observable<CreateBookingBeautyResponse> createBookingHealth(CreateBookingBeautyRequest request);

    Observable<UserFavoritesResponse> getFavoritesByUser(String userId, String objectType, String sort,
                                                         Integer limit, Integer page, Integer let);

    Observable<UserFavoritesResponse> getFavoritesByUserAndSector(String userId, Integer sectorId,
                                                                  String objectType, String sort,
                                                                  Integer limit, Integer page,
                                                                  Integer let);

    Observable<BeautyBookingsResponse> getBeautyBookings(String userId, String query,
                                                         KeyList ids, Integer businessId,
                                                         Integer addressId, Integer staffId,
                                                         Integer serviceId, KeyList status,
                                                         KeyList decision, Integer type,
                                                         Boolean isAppeared, String sort,
                                                         Integer limit, Integer page,
                                                         Integer let);

    Observable<BeautyBookingsResponse> getHealthBookings(String userId, String query,
                                                         KeyList ids, Integer businessId,
                                                         Integer addressId, Integer staffId,
                                                         Integer serviceId, KeyList status,
                                                         KeyList decision, Integer type,
                                                         Boolean isAppeared, String sort,
                                                         Integer limit, Integer page,
                                                         Integer let);

    Observable<BeautyServiceResponse> getBeautyServices(String query, KeyList ids,
                                                        Integer businessId, Integer serviceGroupId,
                                                        String sort, Integer status, Integer withTrashed,
                                                        Integer limit, Integer page,
                                                        Integer let);

    Observable<BeautyServiceResponse> getHealthServices(String query, KeyList ids,
                                                        Integer businessId, Integer serviceGroupId,
                                                        String sort, Integer status, Integer withTrashed,
                                                        Integer limit, Integer page,
                                                        Integer let);

    Observable<CancelBookingBeautyResponse> cancelBookingBeauty(Integer bookingId);

    Observable<CancelBookingBeautyResponse> cancelBookingHealth(Integer bookingId);

    Observable<CreateBusinessCommentResponse> createBusinessComment(CreateBusinessCommentRequest request);

    Observable<MarketingResponse> getMarketings(KeyList businessId, String title,
                                                String status, String city, String from,
                                                String to, Integer pageSize,
                                                Integer pageIndex);

    Observable<EserviaFeedbackResponse> sendEserviaContactFeedback(EserviaFeedbackRequest request);

    Observable<PromoterCustomerRegisterResponse> registerCustomer(Integer businessId);

    Observable<MenuResponse> getMenu(Long addressId, Long departmentId);

    Observable<MenuVersionResponse> getMenuVersion(Long addressId, Long departmentId);

    Observable<BookingSettingsResponse> getRestoBookingSettings(Long addressId);

    Observable<GeneralBookingsResponse> getGeneralActiveBookings(Integer pageIndex, Integer pageSize);

    Observable<GeneralBookingsResponse> getGeneralHistoryBookings(Integer pageIndex, Integer pageSize);

    Observable<RestoBookingResponse> postRestoBooking(RestoBookingRequest request);

    Observable<DepartmentsResponse> getRestoDepartments(Long addressId);

    Observable<DeliveryRestoSettlementsResponse> getDeliveryRestoSettlements();

    Observable<DeliveryRestoStreetsResponse> getDeliveryRestoStreets(Long settlementId);

    Observable<PostRestoOrderResponse> postRestoOrder(PostRestoOrderRequest request);

    Observable<GetRestoOrdersResponse> getRestoOrders(List<Integer> ids);

    Observable<PostRestoDeliveryResponse> postRestoDelivery(PostRestoDeliveryRequest request);

    Observable<GetRestoDeliveriesResponse> getRestoDeliveries(List<String> deliveryStatuses,
                                                              Integer pageSize, Integer pageIndex);

    Observable<GetRestoBookingsResponse> getRestoBookingsActive(Integer pageSize, Integer pageIndex);

    Observable<GetRestoBookingsResponse> getRestoBookingsHistory(Integer pageSize, Integer pageIndex);

    Observable<CancelRestoBookingResponse> cancelRestoBooking(CancelRestoBookingRequest request);

    Observable<DepartmentsResponse> getRestoDepartmentsByIds(List<Integer> ids);

    Observable<BusinessCitiesResponse> getBusinessCities(String sort);

    Observable<BeautyPromotionsResponse> getBeautyBusinessPromotions(Integer businessId,
                                                                     KeyList ids,
                                                                     Integer marketingId,
                                                                     Integer serviceId,
                                                                     Integer staffId,
                                                                     Integer withTrashed,
                                                                     Integer limit,
                                                                     Integer page,
                                                                     Integer let);

    Observable<BeautyServiceResponse> getBeautyPromotionServices(Integer promotionId,
                                                                 Integer limit,
                                                                 Integer page,
                                                                 Integer let);

    Observable<PromoterCustomerBusinessesResponse> getPromoterCustomerBusinesses();
}
