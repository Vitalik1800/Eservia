package com.eservia.model.local.sector;

import com.eservia.model.entity.BusinessSector;
import com.eservia.model.entity.BusinessSector_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class SectorRepositoryImpl implements SectorRepository {

    private final Box<BusinessSector> sectorBox;

    public SectorRepositoryImpl(BoxStore boxStore) {
        this.sectorBox = boxStore.boxFor(BusinessSector.class);
    }

    @Override
    public Observable<List<BusinessSector>> findAll() {
        return Observable.just(sectorBox.query()
                .build().find());
    }

    @Override
    public Observable<List<BusinessSector>> findSectorWithId(long id) {
        return Observable.just(sectorBox.query()
                .equal(BusinessSector_.id, id)
                .build().find());
    }

    @Override
    public Observable<BusinessSector> createOrUpdate(BusinessSector sector) {
        return Observable.fromCallable(() -> {
            sectorBox.put(sector);
            return sector;
        });
    }

    @Override
    public Observable<List<BusinessSector>> createOrUpdate(List<BusinessSector> sectors) {
        return Observable.fromCallable(() -> {
            sectorBox.put(sectors);
            return sectors;
        });
    }

    @Override
    public Observable<BusinessSector> delete(BusinessSector sector) {
        return Completable.fromAction(() -> sectorBox.remove(sector))
                .doOnComplete(() -> sector.setDbId(0))
                .andThen(Observable.just(sector));
    }

    @Override
    public Observable<List<BusinessSector>> delete(List<BusinessSector> sectors) {
        return Completable.fromAction(() -> sectorBox.remove(sectors))
                .andThen(Observable.fromIterable(sectors))
                .doOnNext(entity -> entity.setDbId(0))
                .toList()
                .toObservable();
    }

    @Override
    public Observable<List<BusinessSector>> deleteAll() {
        return Observable.fromCallable(sectorBox::getAll)
                .flatMap(this::delete);
    }
}
