package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

public class Is {

    @SerializedName("favorited")
    private Boolean favorited;
    @SerializedName("commented")
    private Boolean commented;

    public Boolean getFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    public Boolean getCommented() {
        return commented;
    }

    public void setCommented(Boolean commented) {
        this.commented = commented;
    }
}
