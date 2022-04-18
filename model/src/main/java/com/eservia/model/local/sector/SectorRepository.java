package com.eservia.model.local.sector;

import com.eservia.model.entity.BusinessSector;

import java.util.List;

import io.reactivex.Observable;

public interface SectorRepository {

    Observable<List<BusinessSector>> findAll();

    Observable<List<BusinessSector>> findSectorWithId(long id);

    Observable<BusinessSector> createOrUpdate(BusinessSector sector);

    Observable<List<BusinessSector>> createOrUpdate(List<BusinessSector> sectors);

    Observable<BusinessSector> delete(BusinessSector sector);

    Observable<List<BusinessSector>> delete(List<BusinessSector> sectors);

    Observable<List<BusinessSector>> deleteAll();
}
