package com.eservia.booking.ui.home.news.news.news_and_promo;

import android.content.Context;
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
import com.eservia.booking.ui.event_details.beauty.EventDetailsBeautyActivity;
import com.eservia.booking.ui.home.HomeActivity;
import com.eservia.booking.ui.home.news.news.BaseNewsListFragment;
import com.eservia.booking.ui.home.search.search.sheet.SearchSheetDialog;
import com.eservia.booking.ui.home.search.search.sheet.SheetAdapterItem;
import com.eservia.booking.util.ColorUtil;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.Marketing;
import com.eservia.utils.KeyboardUtil;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class NewsAndPromoFragment extends BaseNewsListFragment implements NewsAndPromoView,
        SearchSheetDialog.OnSheetDialogItemClickListener {

    public interface OnFilterSelectedListener {
        void onFilteringItemSelected(@Nullable String filter);
    }

    SwipeRefreshLayout swipeContainer;
    RecyclerView rvNews;
    CommonPlaceHolder mPlaceHolderLayout;

    @InjectPresenter
    NewsAndPromoPresenter mPresenter;

    private HomeActivity mActivity;

    private NewsAndPromoAdapter mAdapter;

    private SearchSheetDialog mSheetDialog;

    private OnFilterSelectedListener mFilterListener;

    public static NewsAndPromoFragment newInstance() {
        return new NewsAndPromoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_and_promo, container, false);
        mActivity = (HomeActivity) getActivity();
        setUnbinder(ButterKnife.bind(this, view));
        swipeContainer = view.findViewById(R.id.swipeContainer);
        rvNews = view.findViewById(R.id.rvNews);
        mPlaceHolderLayout = view.findViewById(R.id.phlPlaceholder);
        initViews();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof OnFilterSelectedListener) {
            mFilterListener = (OnFilterSelectedListener) getParentFragment();
        } else {
            throw new RuntimeException("The parent fragment must implement OnFilterSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFilterListener = null;
    }

    @Override
    public void refresh() {
        if (rvNews != null) {
            rvNews.post(() -> rvNews.smoothScrollToPosition(0));
        }
    }

    @Override
    public void willBeDisplayed() {
        mPresenter.willBeDisplayed();
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
    public void onFilterClick() {
        mPresenter.onShowCitySelection();
    }

    @Override
    public void onNewsLoadingSuccess(List<Marketing> marketingList) {
        mAdapter.replaceAll(marketingList);
        revalidatePlaceHolder();
    }

    @Override
    public void onNewsLoadingFailed(Throwable throwable) {
        revalidatePlaceHolder();
    }

    @Override
    public void showEventDetailBeautyPage(Marketing marketing) {
        EventDetailsBeautyActivity.start(mActivity, marketing, false);
    }

    @Override
    public void showSelectedCity(@Nullable String city) {
        if (mFilterListener != null) {
            mFilterListener.onFilteringItemSelected(city);
        }
    }

    @Override
    public void showCitiesDialog(List<SheetAdapterItem> items) {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        openCitiesSheetDialog(items);
    }

    @Override
    public void hideSheetDialog() {
        if (mSheetDialog != null) {
            mSheetDialog.dismiss();
        }
    }

    @Override
    public void onDialogItemClick(SheetAdapterItem item, String dialogType) {
        mPresenter.onDialogCitySelected(item);
    }

    private void initViews() {
        initSwipeRefresh();
        initList();
    }

    private void revalidatePlaceHolder() {
        boolean isEmpty = mAdapter.getItemCount() == 0;
        mPlaceHolderLayout.setState(isEmpty ?
                CommonPlaceHolder.STATE_EMPTY : CommonPlaceHolder.STATE_HIDE);
    }

    private void initSwipeRefresh() {
        swipeContainer.setOnRefreshListener(mPresenter::refreshNews);
        swipeContainer.setColorSchemeColors(ColorUtil.swipeRefreshColors(mActivity));
    }

    private void initList() {
        mAdapter = new NewsAndPromoAdapter(mActivity, mPresenter, mPresenter);
        rvNews.setHasFixedSize(true);
        rvNews.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));
        rvNews.setAdapter(mAdapter);
    }

    private void openCitiesSheetDialog(List<SheetAdapterItem> items) {
        mSheetDialog = SearchSheetDialog.newInstance(null);
        mSheetDialog.setSortClickListener(this);
        mSheetDialog.setAdapterItems(items);
        mSheetDialog.setTitle(mActivity.getResources().getString(R.string.choose_city));
        mSheetDialog.show(mActivity.getSupportFragmentManager(),
                SearchSheetDialog.class.getSimpleName());
    }
}
