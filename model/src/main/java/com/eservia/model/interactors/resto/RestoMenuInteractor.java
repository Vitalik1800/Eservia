package com.eservia.model.interactors.resto;

import io.reactivex.Observable;

public interface RestoMenuInteractor {

    Observable<Boolean> loadMenu(Long businessId, Long addressId, Long departmentId);
}
