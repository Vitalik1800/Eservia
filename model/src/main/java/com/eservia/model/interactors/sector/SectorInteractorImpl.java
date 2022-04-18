package com.eservia.model.interactors.sector;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessCity;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.interactors.business.BusinessSearchableHelper;
import com.eservia.model.local.sector.SectorRepository;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.request.StringKeyList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;

public class SectorInteractorImpl implements SectorInteractor {

    private final RestManager restManager;

    private final SectorRepository sectorRepository;

    public SectorInteractorImpl(RestManager restManager, SectorRepository repository) {
        this.restManager = restManager;
        this.sectorRepository = repository;
    }

    @Override
    public Observable<List<BusinessSector>> getSector(int sectorId) {
        return sectorRepository.findSectorWithId(sectorId)
                .flatMap(sectors -> sectors.isEmpty() ? restManager.getSector(sectorId)
                        : Observable.just(sectors));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Observable<List<SectorModel>> getSectors(StringKeyList sectors, String city) {
        return restManager.getSectors(sectors, null, null, null, null)
                .flatMap(response -> {
                    List<BusinessSector> sectorList = response.getData();
                    if (sectorList.isEmpty()) {
                        return Observable.just(new ArrayList<>());
                    }
                    Collections.sort(sectorList, Comparator.comparingInt(BusinessSector::getId));
                    return sectorRepository.deleteAll()
                            .flatMap(success -> sectorRepository.createOrUpdate(sectorList))
                            .flatMap(success -> getSectorModels(sectorList, city));
                });
    }

    @Override
    public Observable<List<BusinessCity>> getBusinessCities(String sort) {
        return restManager.getBusinessCities(sort)
                .flatMap(citiesResponse -> Observable.fromIterable(citiesResponse.getData()))
                .distinct(BusinessCity::getCity)
                .toList()
                .toObservable();
    }

    private Observable<List<SectorModel>> getSectorModels(List<BusinessSector> sectors, String city) {
        return Observable.fromIterable(sectors)
                .concatMapEager(sector -> restManager.getBusinesses(null, null, String.valueOf(sector.getId()), String.valueOf(Business.STATUS_ACTIVE), null, null, null, Business.WITHOUT_TRASHED, city, null, null, BusinessSearchableHelper.getSearchableQuery()))
                .toList()
                .toObservable()
                .flatMap(sectorsBusinesses -> {
                    List<SectorModel> sectorModels = new ArrayList<>();
                    for (int i = 0; i < sectors.size(); i++) {
                        List<Business> businesses = sectorsBusinesses.get(i);
                        BusinessSector sector = sectors.get(i);
                        sectorModels.add(new SectorModel(sector, !businesses.isEmpty()));
                    }
                    return Observable.just(sectorModels);
                });
    }

    private Observable<List<BusinessCity>> filteredBusinessCitiesObservable(List<BusinessCity> businessCities) {
        return Observable.fromIterable(businessCities)
                .concatMapEager(city -> restManager.getBusinesses(null, null, null, String.valueOf(Business.STATUS_ACTIVE), null, null, null, Business.WITHOUT_TRASHED, city.getCity(), null, null, BusinessSearchableHelper.getSearchableQuery()))
                .toList()
                .toObservable()
                .flatMap(businessesForEachCity -> {
                    List<BusinessCity> filteredCities = new ArrayList<>();
                    for (int i = 0; i < businessCities.size(); i++) {
                        BusinessCity city = businessCities.get(i);
                        if (!businessesForEachCity.get(i).isEmpty()) {
                            filteredCities.add(city);
                        }
                    }
                    return Observable.just(filteredCities);
                });
    }
}
