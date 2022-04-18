package com.eservia.model.entity;

import com.google.gson.annotations.SerializedName;

public class MarketingLink {

    public static final int TYPE_FACEBOOK = 1;
    public static final int TYPE_TWITTER = 2;
    public static final int TYPE_INSTAGRAM = 3;
    public static final int TYPE_GOOGLE = 4;
    public static final int TYPE_WEBSITE = 5;

    @SerializedName("id")
    private Integer id;
    @SerializedName("socialTypeId")
    private Integer socialTypeId;
    @SerializedName("url")
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSocialTypeId() {
        return socialTypeId;
    }

    public void setSocialTypeId(Integer socialTypeId) {
        this.socialTypeId = socialTypeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
