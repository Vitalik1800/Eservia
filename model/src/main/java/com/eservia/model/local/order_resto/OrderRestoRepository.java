package com.eservia.model.local.order_resto;

import com.eservia.model.entity.OrderResto;
import com.eservia.model.entity.OrderRestoItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface OrderRestoRepository {

    Observable<List<OrderResto>> findAll();

    Observable<List<OrderResto>> findAllWithBusinessId(long businessId);

    Observable<List<OrderRestoItem>> findAllOrderItemsForBusinessId(long businessId);

    Observable<Integer> orderItemsForBusinessIdCount(long businessId);

    Observable<OrderResto> createOrUpdate(OrderResto orderResto);

    Single<List<OrderResto>> createOrUpdate(List<OrderResto> orderRestoList);

    Observable<List<OrderRestoItem>> createOrUpdateItems(List<OrderRestoItem> orderRestoItems);

    Single<OrderResto> delete(OrderResto orderResto);

    Single<OrderRestoItem> delete(OrderRestoItem orderRestoItem);

    Single<List<OrderResto>> delete(List<OrderResto> orderRestoList);

    Observable<List<OrderResto>> deleteAllWithBusinessId(long businessId);

    Single<List<OrderResto>> deleteAll();
}
