package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PromoterCustomerBusiness {

    @SerializedName("id")
    private Long id;

    @SerializedName("connectingDate")
    private String connectingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConnectingDate() {
        return connectingDate;
    }

    public void setConnectingDate(String connectingDate) {
        this.connectingDate = connectingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PromoterCustomerBusiness that = (PromoterCustomerBusiness) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(connectingDate, that.connectingDate);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (connectingDate != null ? connectingDate.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "PromoterCustomerBusiness{" +
                "id=" + id +
                ", connectingDate='" + connectingDate + '\'' +
                '}';
    }
}
