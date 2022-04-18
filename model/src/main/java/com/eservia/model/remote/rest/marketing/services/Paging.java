package com.eservia.model.remote.rest.marketing.services;

import com.google.gson.annotations.SerializedName;

public class Paging {

    @SerializedName("pageIndex")
    private Integer pageIndex;
    @SerializedName("numberOfPages")
    private Integer numberOfPages;
    @SerializedName("numberOfRecords")
    private Integer numberOfRecords;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Integer getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(Integer numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }
}
