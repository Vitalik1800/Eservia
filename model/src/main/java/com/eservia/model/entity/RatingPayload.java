package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

public class RatingPayload implements Validator {

    @SerializedName("convenience")
    private Float convenience;
    @SerializedName("purity")
    private Float purity;
    @SerializedName("quality")
    private Float quality;

    @Override
    public boolean isItemValid() {
        return convenience != null && purity != null && quality != null;
    }

    public Float getConvenience() {
        return convenience;
    }

    public void setConvenience(Float convenience) {
        this.convenience = convenience;
    }

    public Float getPurity() {
        return purity;
    }

    public void setPurity(Float purity) {
        this.purity = purity;
    }

    public Float getQuality() {
        return quality;
    }

    public void setQuality(Float quality) {
        this.quality = quality;
    }
}
