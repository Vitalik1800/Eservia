package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestoGuestOrder {

    @SerializedName("guestId")
    @Expose
    private Long guestId;

    @SerializedName("orderItems")
    @Expose
    private List<OrderRestoItem> orderItems = new ArrayList<>();

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public List<OrderRestoItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderRestoItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestoGuestOrder that = (RestoGuestOrder) o;

        if (!Objects.equals(guestId, that.guestId)) return false;
        return Objects.equals(orderItems, that.orderItems);
    }

    @Override
    public int hashCode() {
        int result = guestId != null ? guestId.hashCode() : 0;
        result = 31 * result + (orderItems != null ? orderItems.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "RestoGuestOrder{" +
                "guestId=" + guestId +
                ", orderItems=" + orderItems +
                '}';
    }
}
