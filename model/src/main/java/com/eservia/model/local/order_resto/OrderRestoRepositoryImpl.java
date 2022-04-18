package com.eservia.model.local.order_resto;

import com.eservia.model.entity.OrderResto;
import com.eservia.model.entity.OrderRestoItem;
import com.eservia.model.entity.OrderResto_;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class OrderRestoRepositoryImpl implements OrderRestoRepository {

    private final BoxStore boxStore;

    private final Box<OrderResto> orderRestoBox;

    private final Box<OrderRestoItem> orderRestoItemBox;

    public OrderRestoRepositoryImpl(BoxStore boxStore) {
        this.boxStore = boxStore;
        this.orderRestoBox = boxStore.boxFor(OrderResto.class);
        this.orderRestoItemBox = boxStore.boxFor(OrderRestoItem.class);
    }

    @Override
    public Observable<List<OrderResto>> findAll() {
        return Observable.just(orderRestoBox.query()
                .build().find());
    }

    @Override
    public Observable<List<OrderResto>> findAllWithBusinessId(long businessId) {
        return Observable.just(orderRestoBox.query()
                .equal(OrderResto_.businessId, businessId)
                .build().find());
    }

    @Override
    public Observable<List<OrderRestoItem>> findAllOrderItemsForBusinessId(long businessId) {
        OrderResto order = orderRestoBox.query()
                .equal(OrderResto_.businessId, businessId)
                .build().findFirst();
        return Observable.fromCallable(() -> order != null ? order.getOrderItems() : new ArrayList<>());
    }

    @Override
    public Observable<Integer> orderItemsForBusinessIdCount(long businessId) {
        return Observable.fromCallable(() -> {
            OrderResto order = orderRestoBox.query()
                    .equal(OrderResto_.businessId, businessId)
                    .build().findFirst();
            return order != null ? order.getOrderItems().size() : 0;
        });
    }

    @Override
    public Observable<OrderResto> createOrUpdate(OrderResto orderResto) {
        return Observable.fromCallable(() -> {
            boxStore.runInTx(() -> {
                orderRestoBox.put(orderResto);
                orderRestoItemBox.put(orderResto.getOrderItems());
            });
            return orderResto;
        });
    }

    @Override
    public Single<List<OrderResto>> createOrUpdate(List<OrderResto> orderRestoList) {
        return Single.fromCallable(() -> {
            boxStore.runInTx(() -> {
                orderRestoBox.put(orderRestoList);
                for (OrderResto orderResto : orderRestoList) {
                    orderRestoItemBox.put(orderResto.getOrderItems());
                }
            });
            return orderRestoList;
        });
    }

    @Override
    public Observable<List<OrderRestoItem>> createOrUpdateItems(List<OrderRestoItem> orderRestoItems) {
        return Observable.fromCallable(() -> {
            orderRestoItemBox.put(orderRestoItems);
            return orderRestoItems;
        });
    }

    @Override
    public Single<OrderResto> delete(OrderResto orderResto) {
        return Completable.fromAction(() -> orderRestoBox.remove(orderResto))
                .doOnComplete(() -> orderResto.setDbId(0))
                .andThen(Single.just(orderResto));
    }

    @Override
    public Single<OrderRestoItem> delete(OrderRestoItem orderRestoItem) {
        return Completable.fromAction(() -> orderRestoItemBox.remove(orderRestoItem))
                .doOnComplete(() -> orderRestoItem.setDbId(0))
                .andThen(Single.just(orderRestoItem));
    }

    @Override
    public Single<List<OrderResto>> delete(List<OrderResto> orderRestoList) {
        return Completable.fromAction(() -> orderRestoBox.remove(orderRestoList))
                .andThen(Observable.fromIterable(orderRestoList))
                .doOnNext(entity -> entity.setDbId(0))
                .toList();
    }

    @Override
    public Observable<List<OrderResto>> deleteAllWithBusinessId(long businessId) {
        return findAllWithBusinessId(businessId)
                .flatMap(list -> delete(list).toObservable());
    }

    @Override
    public Single<List<OrderResto>> deleteAll() {
        return Single.fromCallable(orderRestoBox::getAll)
                .flatMap(this::delete);
    }
}
