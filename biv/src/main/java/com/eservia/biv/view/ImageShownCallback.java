package com.eservia.biv.view;

import androidx.annotation.UiThread;

@UiThread
public interface ImageShownCallback {

    void onThumbnailShown();
    void onMainImageShown();
}
