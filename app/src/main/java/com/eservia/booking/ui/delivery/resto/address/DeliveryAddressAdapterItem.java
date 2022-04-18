package com.eservia.booking.ui.delivery.resto.address;

import androidx.annotation.NonNull;

import java.util.Objects;

public class DeliveryAddressAdapterItem {

    private Long id;

    private String title;

    public DeliveryAddressAdapterItem(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryAddressAdapterItem that = (DeliveryAddressAdapterItem) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "DeliveryAddressAdapterItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
