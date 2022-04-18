package com.eservia.booking.ui.business_page.beauty.info;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.ui.business_page.gallery.BusinessGalleryExtra;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessPhoto;
import com.eservia.model.entity.Marketing;
import com.eservia.model.interactors.business.BusinessInteractor;
import com.eservia.model.interactors.marketing.MarketingInteractor;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business.services.gallery.BusinessPhotosResponse;
import com.eservia.model.remote.rest.business_beauty.services.staff.BusinessBeautyStaffResponse;
import com.eservia.model.remote.rest.request.KeyList;
import com.eservia.utils.StringUtil;

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
public class BusinessPageBeautyInfoPresenter extends BasePresenter<BusinessPageBeautyInfoView> implements
        GalleryAdapter.GalleryPhotoPaginationListener,
        GalleryAdapter.GalleryPhotoClickListener,
        StaffAdapter.OnStaffPaginationListener,
        StaffAdapter.OnStaffAdapterClickListener,
        NewsAdapter.NewsClickListener,
        NewsAdapter.OnNewsPaginationListener {

    private final List<BusinessPhoto> mPhotos = new ArrayList<>();

    private final List<BeautyStaff> mStaffs = new ArrayList<>();

    private final List<Marketing> mMarketings = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    @Inject
    MarketingInteractor mMarketingInteractor;

    @Inject
    BusinessInteractor mBusinessInteractor;

    private Disposable mPhotosPaginationDisposable;

    private Disposable mStaffsPaginationDisposable;

    private Disposable mNewsPaginationDisposable;

    private boolean mIsAllBusinessPhotosLoaded = false;

    private boolean mIsAllStaffsLoaded = false;

    private boolean mIsAllNewsLoaded = false;

    private Business mBusiness;

    public BusinessPageBeautyInfoPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mBusiness = EventBus.getDefault().getStickyEvent(Business.class);
        getViewState().onBusiness(mBusiness);
        refreshBusinessPhotos();
        refreshBusinessStaffs();
        refreshNews();
    }

    @Override
    public void onGalleryPhotoClick(BusinessPhoto photo, int position) {
        BusinessGalleryExtra extra = new BusinessGalleryExtra(mBusiness, position);
        getViewState().showGallery(extra);
    }

    @Override
    public void loadMoreGalleryPhoto() {
        if (!paginationInProgress(mPhotosPaginationDisposable) && !mIsAllBusinessPhotosLoaded) {
            makePhotosPagination();
        }
    }

    @Override
    public void onStaffItemClick(BeautyStaff beautyStaff) {
        getViewState().showStaffDetailBeautyPage(beautyStaff);
    }

    @Override
    public void loadMoreStaff() {
        if (!paginationInProgress(mStaffsPaginationDisposable) && !mIsAllStaffsLoaded) {
            makeStaffPagination();
        }
    }

    @Override
    public void onNewsClick(Marketing item, int position) {
        if (item.getBusiness() != null) {
            getViewState().showEventDetailBeautyPage(item);
        }
    }

    @Override
    public void loadMoreNews() {
        if (!paginationInProgress(mNewsPaginationDisposable) && !mIsAllNewsLoaded) {
            makeNewsPagination();
        }
    }

    public void onOpenWebsiteClick() {
        if (mBusiness != null && !StringUtil.isEmpty(mBusiness.getUrl())) {
            getViewState().openLinkWebsite(mBusiness.getUrl());
        }
    }

    public void onOpenFacebookClick() {
        if (mBusiness != null && !StringUtil.isEmpty(mBusiness.getLinkFacebook())) {
            getViewState().openLinkFacebook(mBusiness.getLinkFacebook());
        }
    }

    public void onOpenInstagramClick() {
        if (mBusiness != null && !StringUtil.isEmpty(mBusiness.getLinkInstagram())) {
            getViewState().openLinkInstagram(mBusiness.getLinkInstagram());
        }
    }

    public void onNewsMoreClick() {
        if (mBusiness != null) {
            getViewState().showBusinessMarketingsPage(mBusiness);
        }
    }

    public void onStaffMoreClick() {
        if (mBusiness != null) {
            getViewState().showBusinessStaffPage(mBusiness);
        }
    }

    public void refreshBusinessPhotos() {
        if (mBusiness == null) {
            return;
        }
        mPhotos.clear();
        mIsAllBusinessPhotosLoaded = false;
        makePhotosPagination();
    }

    public void refreshBusinessStaffs() {
        if (mBusiness == null) {
            return;
        }
        mStaffs.clear();
        mIsAllStaffsLoaded = false;
        makeStaffPagination();
    }

    public void refreshNews() {
        if (mBusiness == null) {
            return;
        }
        cancelPagination(mNewsPaginationDisposable);
        mMarketings.clear();
        mIsAllNewsLoaded = false;
        makeNewsPagination();
    }

    private void makePhotosPagination() {
        cancelPagination(mPhotosPaginationDisposable);
        Observable<BusinessPhotosResponse> observable = mRestManager
                .getBusinessPhotos(mBusiness.getId(), null,
                        PART, mPhotos.size() / PART + 1)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        mPhotosPaginationDisposable = observable.subscribe(this::onPhotosLoadingSuccess,
                this::onPhotosLoadingFailed);
        addSubscription(mPhotosPaginationDisposable);
    }

    private void makeStaffPagination() {
        cancelPagination(mStaffsPaginationDisposable);
        Observable<BusinessBeautyStaffResponse> observable = mBusinessInteractor
                .getBusinessStaffs(mBusiness.getSector(), null, null, mBusiness.getId(), null,
                        null, BeautyStaff.STATUS_ACTIVE, null, BeautyStaff.TRASHED_EXCLUDED,
                        PART, mStaffs.size() / PART + 1, null)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        mStaffsPaginationDisposable = observable.subscribe(this::onStaffLoadingSuccess,
                this::onStaffLoadingFailed);
        addSubscription(mStaffsPaginationDisposable);
    }

    private void makeNewsPagination() {
        cancelPagination(mNewsPaginationDisposable);
        Observable<List<Marketing>> observable = mMarketingInteractor
                .getMarketings(new KeyList().add(mBusiness.getId()), null, Marketing.STATUS_ACTIVE,
                        null, null, null, PART, mMarketings.size() / PART)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mNewsPaginationDisposable = observable.subscribe(this::onNewsLoadingSuccess,
                this::onNewsLoadingFailed);
        addSubscription(mNewsPaginationDisposable);
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

    private void onStaffLoadingSuccess(BusinessBeautyStaffResponse response) {
        mStaffs.addAll(response.getData());
        mBusiness.getStaffs().clear();
        mBusiness.getStaffs().addAll(mStaffs);
        getViewState().onStaffLoadingSuccess(mStaffs);
        mStaffsPaginationDisposable = null;
        if (mStaffs.size() == response.getMeta().getTotal()) {
            mIsAllStaffsLoaded = true;
        }
    }

    private void onStaffLoadingFailed(Throwable throwable) {
        getViewState().onStaffLoadingFailed(throwable);
        mStaffsPaginationDisposable = null;
    }

    private void onNewsLoadingSuccess(List<Marketing> marketings) {
        mMarketings.addAll(marketings);
        mBusiness.getMarketings().clear();
        mBusiness.getMarketings().addAll(mMarketings);
        getViewState().onNewsLoadingSuccess(mMarketings);
        mNewsPaginationDisposable = null;
        if (marketings.size() != PART) {
            mIsAllNewsLoaded = true;
        }
    }

    private void onNewsLoadingFailed(Throwable throwable) {
        getViewState().onNewsLoadingFailed(throwable);
        mNewsPaginationDisposable = null;
    }
}
