package com.eservia.booking.model.city;

import androidx.annotation.Nullable;

import com.eservia.model.entity.BusinessCity;

import java.util.ArrayList;
import java.util.List;

public class CityStatus {

    private final List<BusinessCity> cities = new ArrayList<>();

    @Nullable
    private BusinessCity selectedCity;

    @Nullable
    public BusinessCity getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(@Nullable BusinessCity selectedCity) {
        this.selectedCity = selectedCity;
    }

    public List<BusinessCity> getCities() {
        return cities;
    }
}
