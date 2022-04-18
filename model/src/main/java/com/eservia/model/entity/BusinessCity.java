package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class BusinessCity {

    @SerializedName("country")
    private String country;

    @SerializedName("city")
    private String city;

    @SerializedName("distinct")
    private String distinct;

    @SerializedName("region")
    private String region;

    @SerializedName("addresses")
    private Integer addresses;

    public Integer getAddresses() {
        return addresses;
    }

    public void setAddresses(Integer addresses) {
        this.addresses = addresses;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setDistinct(String distinct) {
        this.distinct = distinct;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessCity that = (BusinessCity) o;

        if (!Objects.equals(country, that.country)) return false;
        if (!Objects.equals(city, that.city)) return false;
        if (!Objects.equals(distinct, that.distinct))
            return false;
        if (!Objects.equals(region, that.region)) return false;
        return Objects.equals(addresses, that.addresses);
    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (distinct != null ? distinct.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (addresses != null ? addresses.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "BusinessCity{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", distinct='" + distinct + '\'' +
                ", region='" + region + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
