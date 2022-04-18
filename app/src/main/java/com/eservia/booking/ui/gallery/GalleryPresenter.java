package com.eservia.booking.ui.gallery;

import com.eservia.booking.common.presenter.BasePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import moxy.InjectViewState;

@InjectViewState
public class GalleryPresenter extends BasePresenter<GalleryView> {

    private final List<String> mPhotos = new ArrayList<>();

    public GalleryPresenter() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        GalleryExtra extra = EventBus.getDefault().getStickyEvent(GalleryExtra.class);
        if (extra == null) {
            return;
        }
        getViewState().onStartPosition(extra.startPosition);

        mPhotos.addAll(extra.urls);

        getViewState().initWith(mPhotos);
    }
}
