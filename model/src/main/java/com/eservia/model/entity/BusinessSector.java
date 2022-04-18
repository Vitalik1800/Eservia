package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;

@Entity
public class BusinessSector implements Validator {

    public static final String BEAUTY_CLUB = "beauty-club";
    public static final String HEALTH = "health";
    public static final String SPORT = "sport";
    public static final String RESTAURANTS = "restaurants";
    public static final String ENTERTAINMENT = "entertainment";
    public static final String SHOPPING = "shopping";
    public static final String AUTO_BUSINESS = "auto-business";
    public static final String REAL_ESTATE = "real-estate";
    public static final String OTHER = "other";

    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_INACTIVE = 0;

    @Id
    private long dbId;

    @Index
    @Unique
    @SerializedName("id")
    private Integer id;

    @SerializedName("sector")
    private String sector;

    @SerializedName("name_en")
    private String nameEn;

    @SerializedName("name_ru")
    private String nameRu;

    @SerializedName("name_uk")
    private String nameUk;

    @SerializedName("status")
    private Integer status;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("deleted_at")
    private String deletedAt;

    @Override
    public boolean isItemValid() {
        return id != null && sector != null
                && nameEn != null
                && nameRu != null
                && nameUk != null
                && status != null;
    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameUk() {
        return nameUk;
    }

    public void setNameUk(String nameUk) {
        this.nameUk = nameUk;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessSector sector1 = (BusinessSector) o;

        if (dbId != sector1.dbId) return false;
        if (!Objects.equals(id, sector1.id)) return false;
        if (!Objects.equals(sector, sector1.sector)) return false;
        if (!Objects.equals(nameEn, sector1.nameEn)) return false;
        if (!Objects.equals(nameRu, sector1.nameRu)) return false;
        if (!Objects.equals(nameUk, sector1.nameUk)) return false;
        if (!Objects.equals(status, sector1.status)) return false;
        if (!Objects.equals(createdAt, sector1.createdAt))
            return false;
        if (!Objects.equals(updatedAt, sector1.updatedAt))
            return false;
        return Objects.equals(deletedAt, sector1.deletedAt);
    }

    @Override
    public int hashCode() {
        int result = (int) (dbId ^ (dbId >>> 32));
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (sector != null ? sector.hashCode() : 0);
        result = 31 * result + (nameEn != null ? nameEn.hashCode() : 0);
        result = 31 * result + (nameRu != null ? nameRu.hashCode() : 0);
        result = 31 * result + (nameUk != null ? nameUk.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "BusinessSector{" +
                "dbId=" + dbId +
                ", id=" + id +
                ", sector='" + sector + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", nameRu='" + nameRu + '\'' +
                ", nameUk='" + nameUk + '\'' +
                ", status=" + status +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}
