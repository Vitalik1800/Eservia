package com.eservia.model.interactors.business;

import com.eservia.model.entity.BeautyPromotion;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.remote.rest.business_beauty.services.service.BeautyServiceResponse;
import com.eservia.model.remote.rest.business_beauty.services.service_group.BeautyServiceGroupResponse;
import com.eservia.model.remote.rest.business_beauty.services.staff.BusinessBeautyStaffResponse;
import com.eservia.model.remote.rest.business_beauty.services.working_day.BeautyWorkingDayResponse;
import com.eservia.model.remote.rest.request.KeyList;
import com.eservia.model.remote.rest.request.StringKeyList;

import java.util.List;

import io.reactivex.Observable;

public interface BusinessInteractor {

    Observable<BeautyServiceResponse> getServiceGroupServices(BusinessSector sector,
                                                              Integer id,
                                                              String query,
                                                              Integer businessId,
                                                              Integer serviceGroupId,
                                                              String sort,
                                                              Integer status,
                                                              Integer withTrashed,
                                                              Integer limit,
                                                              Integer page,
                                                              Integer let);

    Observable<BeautyServiceResponse> getAddressServices(BusinessSector sector,
                                                         Integer id,
                                                         String query,
                                                         Integer businessId,
                                                         Integer serviceGroupId,
                                                         String sort,
                                                         Integer status,
                                                         Integer withTrashed,
                                                         Integer limit,
                                                         Integer page,
                                                         Integer let);

    Observable<BeautyServiceResponse> getServices(BusinessSector sector,
                                                  String query,
                                                  KeyList ids,
                                                  Integer businessId,
                                                  Integer serviceGroupId,
                                                  String sort,
                                                  Integer status,
                                                  Integer withTrashed,
                                                  Integer limit,
                                                  Integer page,
                                                  Integer let);

    Observable<BeautyServiceGroupResponse> getServiceGroups(BusinessSector sector,
                                                            String query,
                                                            KeyList id,
                                                            KeyList services,
                                                            KeyList addresses,
                                                            KeyList staffs,
                                                            Integer businessId,
                                                            String sort,
                                                            Integer withTrashed,
                                                            Integer limit,
                                                            Integer page,
                                                            Integer let);

    Observable<BusinessBeautyStaffResponse> getBusinessStaffs(BusinessSector sector,
                                                              String query,
                                                              KeyList ids,
                                                              Integer businessId,
                                                              Integer addressId,
                                                              KeyList services,
                                                              Integer status,
                                                              String sort,
                                                              Integer withTrashed,
                                                              Integer limit,
                                                              Integer page,
                                                              Integer let);

    Observable<BeautyWorkingDayResponse> getStaffWorkingDays(BusinessSector sector,
                                                             Integer staffId);

    Observable<List<BeautyPromotion>> getBusinessPromotions(Integer businessId,
                                                            KeyList ids,
                                                            Integer marketingId,
                                                            Integer serviceId,
                                                            Integer staffId,
                                                            Integer withTrashed,
                                                            Integer limit,
                                                            Integer page,
                                                            Integer let);

    Observable<List<Business>> getRecommendedFavoriteBusinesses();

    Observable<List<Business>> getFavoritesByUser(String userId, String objectType,
                                                  String sort, Integer limit,
                                                  Integer page, Integer let);

    Observable<List<Business>> getFavoritesByUserAndSector(String userId, StringKeyList sectorAlias,
                                                           String objectType, String sort,
                                                           Integer limit, Integer page,
                                                           Integer let);
}
