package com.eservia.model.interactors.business;

import com.eservia.model.entity.BeautyPromotion;
import com.eservia.model.entity.BeautyService;
import com.eservia.model.entity.BeautyServiceGroup;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.entity.PromoterCustomerBusiness;
import com.eservia.model.interactors.sector.SectorInteractor;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business.services.favorite.UserFavoritesResponse;
import com.eservia.model.remote.rest.business.services.favorite.UserFavoritesResponseData;
import com.eservia.model.remote.rest.business_beauty.services.service.BeautyServiceResponse;
import com.eservia.model.remote.rest.business_beauty.services.service_group.BeautyServiceGroupResponse;
import com.eservia.model.remote.rest.business_beauty.services.staff.BusinessBeautyStaffResponse;
import com.eservia.model.remote.rest.business_beauty.services.working_day.BeautyWorkingDayResponse;
import com.eservia.model.remote.rest.request.KeyList;
import com.eservia.model.remote.rest.request.StringKeyList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;

public class BusinessInteractorImpl extends BaseBusinessInteractor {

    private final int PART = 25;

    public BusinessInteractorImpl(RestManager restManager, SectorInteractor sectorInteractor) {
        super(restManager, sectorInteractor);
    }

    @Override
    public Observable<BeautyServiceResponse> getServiceGroupServices(BusinessSector sector,
                                                                     Integer id,
                                                                     String query,
                                                                     Integer businessId,
                                                                     Integer serviceGroupId,
                                                                     String sort,
                                                                     Integer status,
                                                                     Integer withTrashed,
                                                                     Integer limit,
                                                                     Integer page,
                                                                     Integer let) {
        return isHealthBooking(sector) ?
                restManager.getServiceGroupHealthServices(id, query, businessId, serviceGroupId,
                        sort, status, withTrashed, limit, page, let) :
                restManager.getServiceGroupBeautyServices(id, query, businessId, serviceGroupId,
                        sort, status, withTrashed, limit, page, let);
    }

    @Override
    public Observable<BeautyServiceResponse> getAddressServices(BusinessSector sector,
                                                                Integer id,
                                                                String query,
                                                                Integer businessId,
                                                                Integer serviceGroupId,
                                                                String sort,
                                                                Integer status,
                                                                Integer withTrashed,
                                                                Integer limit,
                                                                Integer page,
                                                                Integer let) {
        return isHealthBooking(sector) ?
                restManager.getAddressHealthServices(id, query, businessId, serviceGroupId, sort,
                        status, withTrashed, limit, page, let) :
                restManager.getAddressBeautyServices(id, query, businessId, serviceGroupId, sort,
                        status, withTrashed, limit, page, let);
    }

    @Override
    public Observable<BeautyServiceResponse> getServices(BusinessSector sector,
                                                         String query,
                                                         KeyList ids,
                                                         Integer businessId,
                                                         Integer serviceGroupId,
                                                         String sort, Integer status,
                                                         Integer withTrashed,
                                                         Integer limit,
                                                         Integer page,
                                                         Integer let) {
        return isHealthBooking(sector) ?
                restManager.getHealthServices(query, ids, businessId, serviceGroupId, sort, status,
                        withTrashed, limit, page, let) :
                restManager.getBeautyServices(query, ids, businessId, serviceGroupId, sort, status,
                        withTrashed, limit, page, let);
    }

    @Override
    public Observable<BeautyServiceGroupResponse> getServiceGroups(BusinessSector sector,
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
                                                                   Integer let) {
        return isHealthBooking(sector) ?
                restManager.getHealthServiceGroups(query, id, services, addresses, staffs, businessId,
                        sort, withTrashed, limit, page, let) :
                restManager.getBeautyServiceGroups(query, id, services, addresses, staffs, businessId,
                        sort, withTrashed, limit, page, let);
    }

    @Override
    public Observable<BusinessBeautyStaffResponse> getBusinessStaffs(BusinessSector sector,
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
                                                                     Integer let) {
        return isHealthBooking(sector) ?
                restManager.getBusinessHealthStaffs(query, ids, businessId, addressId, services,
                        status, sort, withTrashed, limit, page, let) :
                restManager.getBusinessBeautyStaffs(query, ids, businessId, addressId, services,
                        status, sort, withTrashed, limit, page, let);
    }

    @Override
    public Observable<BeautyWorkingDayResponse> getStaffWorkingDays(BusinessSector sector,
                                                                    Integer staffId) {
        return isHealthBooking(sector) ?
                restManager.getStaffHealthWorkingDays(staffId) :
                restManager.getStaffBeautyWorkingDays(staffId);
    }

    @Override
    public Observable<List<BeautyPromotion>> getBusinessPromotions(Integer businessId,
                                                                   KeyList ids,
                                                                   Integer marketingId,
                                                                   Integer serviceId,
                                                                   Integer staffId,
                                                                   Integer withTrashed,
                                                                   Integer limit,
                                                                   Integer page,
                                                                   Integer let) {
        return restManager.getBeautyBusinessPromotions(businessId, ids, marketingId, serviceId,
                staffId, withTrashed, limit, page, let)
                .flatMap(promotions -> {
                    if (promotions.isEmpty()) {
                        return Observable.just(new ArrayList<>());
                    }
                    return bindBeautyServices(promotions);
                });
    }

    @Override
    public Observable<List<Business>> getRecommendedFavoriteBusinesses() {
        return restManager.getPromoterCustomerBusinesses()
                .flatMap(recommended -> {
                    if (recommended.isEmpty()) {
                        return Observable.just(new ArrayList<Business>());
                    }
                    KeyList recommendedIds = new KeyList();
                    for (PromoterCustomerBusiness business : recommended) {
                        recommendedIds.add((int) business.getId().longValue());
                    }
                    return restManager.getBusinessesWithAddresses(null,
                            recommendedIds,
                            null,
                            String.valueOf(Business.STATUS_ACTIVE),
                            null,
                            null,
                            null,
                            Business.WITHOUT_TRASHED,
                            null,
                            null,
                            null,
                            null);
                })
                .flatMap(this::bindFavoritesSectors);
    }

    @Override
    public Observable<List<Business>> getFavoritesByUser(String userId, String objectType,
                                                         String sort, Integer limit,
                                                         Integer page, Integer let) {
        return restManager.getFavoritesByUser(userId, objectType, sort, limit, page, let)
                .flatMap(this::onFavoritesResponse);
    }

    @Override
    public Observable<List<Business>> getFavoritesByUserAndSector(String userId, StringKeyList sectorAlias,
                                                                  String objectType, String sort,
                                                                  Integer limit, Integer page,
                                                                  Integer let) {
        return restManager.getSectors(sectorAlias, BusinessSector.STATUS_ACTIVE, null, PART, 1)
                .flatMap(sectors -> {
                    if (sectors.getData().isEmpty()) {
                        return Observable.just(new ArrayList<>());
                    } else {
                        Integer sectorId = sectors.getData().get(0).getId();
                        return restManager.getFavoritesByUserAndSector(userId,
                                sectorId,
                                objectType,
                                sort,
                                limit,
                                page,
                                let)
                                .flatMap(this::onFavoritesResponse);
                    }
                });
    }

    private Observable<List<Business>> onFavoritesResponse(UserFavoritesResponse response) {
        if (response.getData().isEmpty()) {
            return Observable.just(new ArrayList<>());
        }

        List<KeyList> businessesIds = new ArrayList<>();
        businessesIds.add(new KeyList());

        for (UserFavoritesResponseData data : response.getData()) {
            KeyList last = businessesIds.get(businessesIds.size() - 1);
            if (last.getKeys().size() >= PART) {
                businessesIds.add(new KeyList());
            }
            businessesIds.get(businessesIds.size() - 1).add(data.getObjectId());
        }

        return Observable.range(1, businessesIds.size())
                .concatMap(integer -> restManager.getBusinessesWithAddresses(null,
                        businessesIds.get(--integer), null,
                        String.valueOf(Business.STATUS_ACTIVE), null, null, null,
                        Business.WITHOUT_TRASHED, null,
                        PART, integer, null))
                .scan((businesses1, businesses2) -> {
                    List<Business> list = new ArrayList<>();
                    list.addAll(businesses1);
                    list.addAll(businesses2);
                    return list;
                })
                .last(new ArrayList<>())
                .toObservable()
                .flatMap(this::bindFavoritesSectors);
    }

    private Observable<List<Business>> bindFavoritesSectors(List<Business> businesses) {
        List<BusinessSector> loadedSectors = new ArrayList<>();
        return Observable.fromIterable(businesses)
                .flatMap(business -> {
                    int sectorIndx = -1;
                    for (int i = 0; i < loadedSectors.size(); i++) {
                        if (loadedSectors.get(i).getId().equals(business.getSectorId())) {
                            sectorIndx = i;
                            break;
                        }
                    }
                    if (sectorIndx == -1) {
                        return sectorInteractor.getSector(business.getSectorId())
                                .flatMap(sectors -> {
                                    loadedSectors.add(sectors.get(0));
                                    business.setSector(sectors.get(0));
                                    return Observable.just(business);
                                });
                    } else {
                        business.setSector(loadedSectors.get(sectorIndx));
                        return Observable.just(business);
                    }
                })
                .toList()
                .toObservable();
    }

    private Observable<List<BeautyPromotion>> bindBeautyServices(List<BeautyPromotion> promotions) {

        return Observable.fromIterable(promotions)
                .flatMap(promotion -> getAllBeautyPromotionServices((int) promotion.getId().longValue())
                        .flatMap(allServices -> {
                            if (allServices.isEmpty()) {
                                promotion.setServices(new ArrayList<>());
                                return Observable.just(promotion);
                            }
                            HashSet<Integer> serviseGroupsIds = new HashSet<>();
                            for (BeautyService service : allServices) {
                                serviseGroupsIds.add(service.getServiceGroupId());
                            }
                            return bindBeautyServiceGroups(allServices, serviseGroupsIds)
                                    .flatMap(servicesWithServiceGroups -> {
                                        promotion.setServices(servicesWithServiceGroups);
                                        return Observable.just(promotion);
                                    });
                        }))
                .toList()
                .toObservable();
    }

    private Observable<List<BeautyService>> bindBeautyServiceGroups(List<BeautyService> services,
                                                                    HashSet<Integer> serviceGroupsIds) {
        return restManager.getBeautyServiceGroups(null, new KeyList().addAll(serviceGroupsIds), null, null, null, null, null, null, null, null, null)
                .flatMap(serviceGroupResponse -> {
                    for (BeautyService service : services) {
                        for (BeautyServiceGroup serviceGroup : serviceGroupResponse.getData()) {
                            if (service.getServiceGroupId().equals(serviceGroup.getId())) {
                                service.setServiceGroup(serviceGroup);
                            }
                        }
                    }
                    return Observable.just(services);
                });
    }

    private Observable<List<BeautyService>> getAllBeautyPromotionServices(Integer promotionId) {
        int limit = 50;
        return restManager.getBeautyPromotionServices(promotionId, limit, 1, null)
                .flatMap(servicesResponse -> {
                    int totalPages = servicesResponse.getMeta().getPages();
                    if (totalPages == 1) {
                        return Observable.just(servicesResponse.getData());
                    } else {
                        return Observable.range(2, --totalPages)
                                .concatMap(page -> restManager.getBeautyPromotionServices(promotionId,
                                        limit, page, null))
                                .map(BeautyServiceResponse::getData)
                                .flatMap(Observable::just)
                                .scan((services1, services2) -> {
                                    List<BeautyService> list = new ArrayList<>();
                                    list.addAll(services1);
                                    list.addAll(services2);
                                    return list;
                                })
                                .last(new ArrayList<>())
                                .map(list -> {
                                    list.addAll(servicesResponse.getData());
                                    return list;
                                })
                                .toObservable();
                    }
                });
    }

    private boolean isHealthBooking(BusinessSector sector) {
        return sector.getSector().equals(BusinessSector.HEALTH);
    }
}
