package com.eservia.model.local.menu;

import com.eservia.model.entity.OrderRestoMenuData;
import com.eservia.model.entity.OrderRestoNomenclature;

import java.util.List;

import io.reactivex.Observable;

public interface MenuRepository {

    Observable<Boolean> saveMenu(OrderRestoMenuData menuData, long businessId, long addressId);

    Observable<Long> getMenuVersion(long businessId);

    Observable<List<OrderRestoNomenclature>> findNomenclaturesWithId(long id, long businessId);

    Observable<List<OrderRestoNomenclature>> findNomenclaturesWithId(long[] ids, long businessId);

}
