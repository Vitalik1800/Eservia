package com.eservia.model.remote.rest.business_beauty.services;

import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("from")
    private Integer from;
    @SerializedName("path")
    private String path;
    @SerializedName("to")
    private Integer to;
    @SerializedName("total")
    private Integer total;
    @SerializedName("pages")
    private Integer pages;
    @SerializedName("page")
    private Integer page;
    @SerializedName("let")
    private Integer let;
    @SerializedName("limit")
    private Integer limit;

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLet() {
        return let;
    }

    public void setLet(Integer let) {
        this.let = let;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
