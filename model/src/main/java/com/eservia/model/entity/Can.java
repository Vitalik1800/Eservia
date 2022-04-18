package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

public class Can {

    @SerializedName("favorite")
    private Boolean favorite;
    @SerializedName("comment")
    private Boolean comment;

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getComment() {
        return comment;
    }

    public void setComment(Boolean comment) {
        this.comment = comment;
    }
}
