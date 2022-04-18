package com.eservia.model.interactors.marketing;

import com.eservia.model.entity.Marketing;
import com.eservia.model.remote.rest.request.KeyList;

import java.util.List;

import io.reactivex.Observable;

public interface MarketingInteractor {

    Observable<List<Marketing>> getMarketings(KeyList businessId,
                                              String title,
                                              String status,
                                              String city,
                                              String from,
                                              String to,
                                              Integer pageSize,
                                              Integer pageIndex);
}
