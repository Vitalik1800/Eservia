package com.eservia.booking.ui.business_page.gallery;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessPhoto;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business.services.gallery.BusinessPhotosResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class GalleryPresenter extends BasePresenter<GalleryView> implements GalleryAdapter.OnGalleryPaginationListener,
        GalleryAdapter.OnGalleryItemClickListener {

    private final List<BusinessPhoto> mPhotos = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    private Disposable mPhotosPaginationDisposable;

    private boolean mIsAllBusinessPhotosLoaded = false;

    private Business mBusiness;

    public GalleryPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        BusinessGalleryExtra extra = EventBus.getDefault().getStickyEvent(BusinessGalleryExtra.class);
        if (extra == null) {
            return;
        }

        mBusiness = extra.business;
        getViewState().onStartPosition(extra.startPosition);

        mPhotos.addAll(mBusiness.getPhotos());
        if (!mPhotos.isEmpty() && !(mPhotos.size() % PART == 0)) {
            mIsAllBusinessPhotosLoaded = true;
        }

        getViewState().initWith(mPhotos);
    }

    @Override
    public void loadMorePhotos() {
        if (!paginationInProgress(mPhotosPaginationDisposable) && !mIsAllBusinessPhotosLoaded) {
            makePhotosPagination();
        }
    }

    @Override
    public void onGalleryItemClick(int position) {
        getViewState().toggleControls();
    }

    public void refreshBusinessPhotos() {
        if (mBusiness == null) {
            return;
        }
        mPhotos.clear();
        mIsAllBusinessPhotosLoaded = false;
        makePhotosPagination();
    }

    private void makePhotosPagination() {
        cancelPagination(mPhotosPaginationDisposable);
        Observable<BusinessPhotosResponse> observable = mRestManager
                .getBusinessPhotos(mBusiness.getId(), null, PART, mPhotos.size() / PART + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mPhotosPaginationDisposable = observable.subscribe(this::onPhotosLoadingSuccess, this::onPhotosLoadingFailed);
        addSubscription(mPhotosPaginationDisposable);
    }

    private void onPhotosLoadingSuccess(BusinessPhotosResponse response) {
        mPhotos.addAll(response.getData());
        mBusiness.getPhotos().clear();
        mBusiness.getPhotos().addAll(mPhotos);
        getViewState().onPhotosLoadingSuccess(mPhotos);
        mPhotosPaginationDisposable = null;
        if (mPhotos.size() == response.getMeta().getTotal()) {
            mIsAllBusinessPhotosLoaded = true;
        }
    }

    private void onPhotosLoadingFailed(Throwable throwable) {
        getViewState().onPhotosLoadingFailed(throwable);
        mPhotosPaginationDisposable = null;
    }
}
