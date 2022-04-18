package com.eservia.booking.ui.gallery;

import java.util.ArrayList;
import java.util.List;

public class GalleryExtra {

    public final List<String> urls = new ArrayList<>();

    public int startPosition;

    public GalleryExtra(List<String> urls) {
        this.urls.addAll(urls);
        this.startPosition = 0;
    }

    public GalleryExtra(List<String> urls, int startPosition) {
        this.urls.addAll(urls);
        this.startPosition = startPosition;
    }
}
