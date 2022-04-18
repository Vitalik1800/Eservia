package com.eservia.booking.ui.business_page.staff;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.model.entity.Business;
import com.eservia.model.interactors.business.BusinessInteractor;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business_beauty.services.staff.BusinessBeautyStaffResponse;

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
public class BusinessStaffPresenter extends BasePresenter<BusinessStaffView> implements
        BusinessStaffAdapter.OnStaffAdapterClickListener,
        BusinessStaffAdapter.OnStaffPaginationListener {

    private final List<BeautyStaff> mStaffs = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    @Inject
    BusinessInteractor mBusinessInteractor;

    private Disposable mStaffPaginationDisposable;

    private boolean mIsAllStaffsLoaded = false;

    private Business mBusiness;

    public BusinessStaffPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mBusiness = EventBus.getDefault().getStickyEvent(Business.class);
        if (mBusiness != null) {
            mStaffs.addAll(mBusiness.getStaffs());
            getViewState().onStaffLoadingSuccess(mStaffs);
        }
        refreshStaffs();
    }

    @Override
    public void onStaffItemClick(BeautyStaff beautyStaff, int position) {
        getViewState().showStaffDetailBeautyPage(beautyStaff);
    }

    @Override
    public void loadMoreStaff() {
        if (!paginationInProgress(mStaffPaginationDisposable) && !mIsAllStaffsLoaded) {
            makeStaffPagination();
        }
    }

    public void refreshStaffs() {
        cancelPagination(mStaffPaginationDisposable);
        mStaffs.clear();
        mIsAllStaffsLoaded = false;
        makeStaffPagination();
    }

    private void makeStaffPagination() {
        if (mBusiness == null) {
            return;
        }

        if (mStaffs.isEmpty()) {
            getViewState().showProgress();
        }
        cancelPagination(mStaffPaginationDisposable);
        Observable<BusinessBeautyStaffResponse> observable = mBusinessInteractor
                .getBusinessStaffs(mBusiness.getSector(), null, null, mBusiness.getId(), null,
                        null, BeautyStaff.STATUS_ACTIVE, null, BeautyStaff.TRASHED_EXCLUDED,
                        PART, mStaffs.size() / PART + 1, null)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        mStaffPaginationDisposable = observable.subscribe(this::onStaffLoadingSuccess,
                this::onStaffLoadingFailed);
        addSubscription(mStaffPaginationDisposable);
    }

    private void onStaffLoadingSuccess(BusinessBeautyStaffResponse response) {
        mStaffs.addAll(response.getData());
        getViewState().hideProgress();
        getViewState().onStaffLoadingSuccess(mStaffs);
        mStaffPaginationDisposable = null;
        if (response.getMeta().getTotal() == mStaffs.size()) {
            mIsAllStaffsLoaded = true;
        }
    }

    private void onStaffLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onStaffLoadingFailed(throwable);
        mStaffPaginationDisposable = null;
    }
}
