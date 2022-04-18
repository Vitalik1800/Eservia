package com.eservia.booking.ui.business_page.beauty.reviews;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessComment;
import com.eservia.model.local.ContentChangesObservable;
import com.eservia.model.local.SyncEvent;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business.services.comment.BusinessCommentsResponse;
import com.eservia.utils.Contract;
import com.eservia.utils.LogUtils;
import com.eservia.utils.SortUtil;

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
public class BusinessPageBeautyReviewsPresenter extends BasePresenter<BusinessPageBeautyReviewsView>
        implements ReviewsAdapter.CommentClickListener,
        ReviewsAdapter.CommentPaginationListener {

    private final List<BusinessComment> mComments = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    private Disposable mCommentsPaginationDisposable;

    private boolean mIsAllBusinessCommentsLoaded = false;

    private Business mBusiness;

    public BusinessPageBeautyReviewsPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mBusiness = EventBus.getDefault().getStickyEvent(Business.class);
        refreshBusinessComments();
        subscribeOnCommentsAdd();
    }

    @Override
    public void loadMoreComments() {
        if (!paginationInProgress(mCommentsPaginationDisposable) && !mIsAllBusinessCommentsLoaded) {
            makeCommentsPagination();
        }
    }

    @Override
    public void onCommentClick(BusinessComment comment, int position) {
    }

    @Override
    public void onWriteCommentClick() {
        getViewState().openBeautyFeedback(mBusiness);
    }

    public void refreshBusinessComments() {
        if (mBusiness == null) {
            return;
        }
        mComments.clear();
        mIsAllBusinessCommentsLoaded = false;
        makeCommentsPagination();
    }

    private void makeCommentsPagination() {
        if (mComments.isEmpty()) {
            getViewState().showProgress();
        }
        cancelPagination(mCommentsPaginationDisposable);
        Observable<BusinessCommentsResponse> observable = mRestManager
                .getBusinessComments(mBusiness.getId(), SortUtil.createdAtDesc(),
                        PART, mComments.size() / PART + 1, null)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        mCommentsPaginationDisposable = observable.subscribe(this::onCommentsLoadingSuccess,
                this::onCommentsLoadingFailed);
        addSubscription(mCommentsPaginationDisposable);
    }

    private void onCommentsLoadingSuccess(BusinessCommentsResponse response) {
        mComments.addAll(response.getData());
        mBusiness.getCommentsItems().clear();
        mBusiness.getCommentsItems().addAll(mComments);
        getViewState().hideProgress();
        getViewState().onCommentsLoadingSuccess(mapToReviewsAdapterItems(mComments));
        mCommentsPaginationDisposable = null;
        if (mComments.size() == response.getMeta().getTotal()) {
            mIsAllBusinessCommentsLoaded = true;
        }
    }

    private void onCommentsLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onCommentsLoadingFailed(throwable);
        mCommentsPaginationDisposable = null;
    }

    private List<ReviewsAdapterItem> mapToReviewsAdapterItems(List<BusinessComment> comments) {
        List<ReviewsAdapterItem> result = new ArrayList<>();

        if (mBusiness.getCan().getComment()) {
            result.add(new ItemCreateReview());
        }

        for (BusinessComment comment : comments) {
            result.add(new ItemReview(comment));
        }

        return result;
    }

    private void subscribeOnCommentsAdd() {
        addSubscription(ContentChangesObservable.addedComment(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessHandledCommentsAdd, this::onErrorHandledCommentsAdd));
    }

    private void onSuccessHandledCommentsAdd(SyncEvent syncEvent) {
        BusinessUtil.commentAdded(mBusiness);
        getViewState().onCommentsLoadingSuccess(mapToReviewsAdapterItems(mComments));
        refreshBusinessComments();
    }

    private void onErrorHandledCommentsAdd(Throwable throwable) {
        LogUtils.debug(Contract.LOG_TAG, throwable.getMessage());
    }
}
