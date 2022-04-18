package com.eservia.booking.ui.delivery.resto.basket;

import androidx.annotation.NonNull;

import com.eservia.model.entity.OrderRestoItem;
import com.eservia.model.entity.OrderRestoNomenclature;

import java.util.Objects;

public class DeliveryOrderAdapterItem {

    private OrderRestoItem orderRestoItem;

    private OrderRestoNomenclature dish;

    private boolean isEditMode;

    private int maxPortionsCount;

    public DeliveryOrderAdapterItem(OrderRestoItem orderRestoItem, OrderRestoNomenclature dish,
                                    boolean isEditMode, int maxPortionsCount) {
        this.orderRestoItem = orderRestoItem;
        this.dish = dish;
        this.isEditMode = isEditMode;
        this.maxPortionsCount = maxPortionsCount;
    }

    public int getMaxPortionsCount() {
        return maxPortionsCount;
    }

    public void setMaxPortionsCount(int maxPortionsCount) {
        this.maxPortionsCount = maxPortionsCount;
    }

    public OrderRestoItem getOrderRestoItem() {
        return orderRestoItem;
    }

    public void setOrderRestoItem(OrderRestoItem orderRestoItem) {
        this.orderRestoItem = orderRestoItem;
    }

    public OrderRestoNomenclature getDish() {
        return dish;
    }

    public void setDish(OrderRestoNomenclature dish) {
        this.dish = dish;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryOrderAdapterItem that = (DeliveryOrderAdapterItem) o;

        if (isEditMode != that.isEditMode) return false;
        if (maxPortionsCount != that.maxPortionsCount) return false;
        if (!Objects.equals(orderRestoItem, that.orderRestoItem))
            return false;
        return Objects.equals(dish, that.dish);
    }

    @Override
    public int hashCode() {
        int result = orderRestoItem != null ? orderRestoItem.hashCode() : 0;
        result = 31 * result + (dish != null ? dish.hashCode() : 0);
        result = 31 * result + (isEditMode ? 1 : 0);
        result = 31 * result + maxPortionsCount;
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "DeliveryOrderAdapterItem{" +
                "orderRestoItem=" + orderRestoItem +
                ", dish=" + dish +
                ", isEditMode=" + isEditMode +
                ", maxPortionsCount=" + maxPortionsCount +
                '}';
    }
}
