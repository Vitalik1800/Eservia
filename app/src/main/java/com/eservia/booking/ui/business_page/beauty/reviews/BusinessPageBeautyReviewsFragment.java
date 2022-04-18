package com.eservia.booking.ui.business_page.beauty.reviews;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eservia.booking.R;
import com.eservia.booking.common.view.CommonPlaceHolder;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.ui.business_page.beauty.feedback.BusinessFeedbackBeautyActivity;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.ColorUtil;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.Business;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class BusinessPageBeautyReviewsFragment extends BaseHomeFragment implements
        BusinessPageBeautyReviewsView {

    SwipeRefreshLayout swipeContainer;
    RecyclerView rvReviews;
    CommonPlaceHolder mPlaceHolder;

    @InjectPresenter
    BusinessPageBeautyReviewsPresenter mPresenter;

    private Activity mActivity;

    private ReviewsAdapter mReviewsAdapter;

    public static BusinessPageBeautyReviewsFragment newInstance() {
        return new BusinessPageBeautyReviewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_page_beauty_reviews, container, false);
        mActivity = getActivity();
        setUnbinder(ButterKnife.bind(this, view));
        swipeContainer = view.findViewById(R.id.swipeContainer);
        rvReviews = view.findViewById(R.id.rvReviews);
        mPlaceHolder = view.findViewById(R.id.phlPlaceholder);
        initViews();
        return view;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void willBeDisplayed() {
    }

    @Override
    public void willBeHidden() {
    }

    @Override
    public void showProgress() {
        swipeContainer.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onCommentsLoadingSuccess(List<ReviewsAdapterItem> comments) {
        mReviewsAdapter.replaceAll(comments);
        revalidatePlaceHolder();
    }

    @Override
    public void onCommentsLoadingFailed(Throwable throwable) {
        revalidatePlaceHolder();
    }

    @Override
    public void openBeautyFeedback(Business business) {
        BusinessFeedbackBeautyActivity.start(mActivity, business);
    }

    private void revalidatePlaceHolder() {
        boolean isEmpty = mReviewsAdapter.getItemCount() == 0;
        boolean containsOnlyCreateReviewItem = mReviewsAdapter.getItemCount() == 1 &&
                mReviewsAdapter.getItem(0) instanceof ItemCreateReview;

        mPlaceHolder.setState(isEmpty || containsOnlyCreateReviewItem ?
                CommonPlaceHolder.STATE_EMPTY : CommonPlaceHolder.STATE_HIDE);
    }

    private void initViews() {
        initSwipeRefresh();
        initCommentList();
    }

    private void initSwipeRefresh() {
        swipeContainer.setOnRefreshListener(mPresenter::refreshBusinessComments);
        swipeContainer.setColorSchemeColors(ColorUtil.swipeRefreshColors(mActivity));
    }

    private void initCommentList() {
        mReviewsAdapter = new ReviewsAdapter(mActivity, mPresenter, mPresenter);
        rvReviews.setHasFixedSize(true);
        rvReviews.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));
        rvReviews.setAdapter(mReviewsAdapter);
    }
}
