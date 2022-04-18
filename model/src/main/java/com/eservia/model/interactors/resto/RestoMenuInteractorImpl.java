package com.eservia.model.interactors.resto;

import com.eservia.model.local.menu.MenuRepository;
import com.eservia.model.remote.rest.RestManager;

import io.reactivex.Observable;

public class RestoMenuInteractorImpl implements RestoMenuInteractor {

    private final RestManager restManager;

    private final MenuRepository menuRepository;

    public RestoMenuInteractorImpl(RestManager restManager, MenuRepository menuRepository) {
        this.restManager = restManager;
        this.menuRepository = menuRepository;
    }

    @Override
    public Observable<Boolean> loadMenu(Long businessId, Long addressId, Long departmentId) {
        Observable<Long> menuVersionRemoteObservable = restManager
                .getMenuVersion(addressId, departmentId);

        Observable<Long> menuVersionFromCacheObservable = menuRepository
                .getMenuVersion(businessId);

        Observable<Boolean> isSameVersionObservable = Observable.zip(menuVersionRemoteObservable,
                menuVersionFromCacheObservable, Long::equals);

        Observable<Boolean> loadAndSaveMenuObservable = restManager
                .getMenu(addressId, departmentId)
                .flatMap(menu -> menuRepository.saveMenu(menu.getData(), businessId, addressId));

        Observable<Boolean> successObservable = Observable.just(Boolean.TRUE);

        return isSameVersionObservable
                .flatMap(isSame -> isSame ? successObservable : loadAndSaveMenuObservable);
    }
}
