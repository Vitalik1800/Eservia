package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class RestoDeliveryCompany {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("settlement")
    @Expose
    private String settlement;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private Long type;

    @SerializedName("photo")
    @Expose
    private String photo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestoDeliveryCompany that = (RestoDeliveryCompany) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(location, that.location))
            return false;
        if (!Objects.equals(settlement, that.settlement))
            return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(type, that.type)) return false;
        return Objects.equals(photo, that.photo);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (settlement != null ? settlement.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "RestoDeliveryCompany{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", settlement='" + settlement + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", photo='" + photo + '\'' +
                '}';
    }
}
