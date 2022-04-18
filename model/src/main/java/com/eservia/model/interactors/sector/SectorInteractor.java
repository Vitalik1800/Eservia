package com.eservia.model.interactors.sector;

import com.eservia.model.entity.BusinessCity;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.remote.rest.request.StringKeyList;

import java.util.List;

import io.reactivex.Observable;

public interface SectorInteractor {

    Observable<List<SectorModel>> getSectors(StringKeyList sectors, String city);

    Observable<List<BusinessSector>> getSector(int sectorId);

    Observable<List<BusinessCity>> getBusinessCities(String sort);
}
