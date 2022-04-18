package com.eservia.model.interactors.marketing;

import com.eservia.model.entity.Business;
import com.eservia.model.entity.Marketing;
import com.eservia.model.interactors.business.BusinessSearchableHelper;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.marketing.services.Marketing.MarketingResponse;
import com.eservia.model.remote.rest.request.KeyList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;

public class MarketingInteractorImpl implements MarketingInteractor {

    private final RestManager restManager;

    public MarketingInteractorImpl(RestManager restManager) {
        this.restManager = restManager;
    }

    @Override
    public Observable<List<Marketing>> getMarketings(KeyList businessId,
                                                     String title,
                                                     String status,
                                                     String city,
                                                     String from,
                                                     String to,
                                                     Integer pageSize,
                                                     Integer pageIndex) {
        return restManager.getMarketings(businessId, title, status, city, from, to, pageSize, pageIndex)
                .flatMap(marketingResponse -> {
                    if (marketingResponse.getData().isEmpty()) {
                        return Observable.just(new ArrayList<>());
                    }
                    HashSet<Integer> businessesIdsSet = new HashSet<>();
                    for (Marketing marketing : marketingResponse.getData()) {
                        businessesIdsSet.add(marketing.getBusinessId());
                    }
                    return bindBusinesses(marketingResponse, businessesIdsSet);
                });
    }

    private Observable<List<Marketing>> bindBusinesses(MarketingResponse response,
                                                       HashSet<Integer> businessesIdsSet) {
        return restManager.getBusinessesWithAddresses(null,
                new KeyList().addAll(businessesIdsSet),
                null,
                String.valueOf(Business.STATUS_ACTIVE),
                null,
                null,
                null,
                Business.WITHOUT_TRASHED,
                null,
                null,
                null,
                BusinessSearchableHelper.getSearchableQuery()
        )
                .flatMap(businessEntities -> {
                    for (Marketing marketing : response.getData()) {
                        for (Business business : businessEntities) {
                            if (marketing.getBusinessId().equals(business.getId())) {
                                marketing.setBusiness(business);
                            }
                        }
                    }
                    return Observable.just(response.getData());
                });
    }
}
