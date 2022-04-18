package com.eservia.model.interactors.sector;

import androidx.annotation.NonNull;

import com.eservia.model.entity.BusinessSector;

import java.util.Objects;

public class SectorModel {

    private BusinessSector sector;

    private boolean hasBusinesses;

    public SectorModel(BusinessSector sector, boolean hasBusinesses) {
        this.sector = sector;
        this.hasBusinesses = hasBusinesses;
    }

    public BusinessSector getSector() {
        return sector;
    }

    public void setSector(BusinessSector sector) {
        this.sector = sector;
    }

    public boolean hasBusinesses() {
        return hasBusinesses;
    }

    public void setHasBusinesses(boolean hasBusinesses) {
        this.hasBusinesses = hasBusinesses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SectorModel that = (SectorModel) o;

        if (hasBusinesses != that.hasBusinesses) return false;
        return Objects.equals(sector, that.sector);
    }

    @Override
    public int hashCode() {
        int result = sector != null ? sector.hashCode() : 0;
        result = 31 * result + (hasBusinesses ? 1 : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "SectorModel{" +
                "sector=" + sector +
                ", hasBusinesses=" + hasBusinesses +
                '}';
    }
}
