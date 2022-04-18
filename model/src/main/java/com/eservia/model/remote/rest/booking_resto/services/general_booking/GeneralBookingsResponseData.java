package com.eservia.model.remote.rest.booking_resto.services.general_booking;

import androidx.annotation.NonNull;

import com.eservia.model.entity.GeneralBooking;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class GeneralBookingsResponseData {

    @SerializedName("kind")
    private Integer kind;

    @SerializedName("body")
    private GeneralBooking body;

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public GeneralBooking getBody() {
        return body;
    }

    public void setBody(GeneralBooking body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneralBookingsResponseData that = (GeneralBookingsResponseData) o;

        if (!Objects.equals(kind, that.kind)) return false;
        return Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        int result = kind != null ? kind.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "GeneralBookingsResponseData{" +
                "kind=" + kind +
                ", body=" + body +
                '}';
    }
}
