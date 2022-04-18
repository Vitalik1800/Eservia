package com.eservia.booking.ui.business_page.marketing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.CommonPlaceHolder;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.ui.event_details.beauty.EventDetailsBeautyActivity;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.Marketing;
import com.google.android.material.appbar.AppBarLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class BusinessMarketingsActivity extends BaseActivity implements BusinessMarketingsView {

    CoordinatorLayout coordinator;
    AppBarLayout app_bar_layout;
    Toolbar toolbar;
    SwipeRefreshLayout swipeContainer;
    RecyclerView rvNews;
    CommonPlaceHolder mPlaceHolderLayout;

    @InjectPresenter
    BusinessMarketingsPresenter mPresenter;

    private BusinessMarketingsAdapter mAdapter;

    public static void start(Context context, Business business) {
        EventBus.getDefault().postSticky(business);
        Intent starter = new Intent(context, BusinessMarketingsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_marketings);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        coordinator = findViewById(R.id.coordinator);
        app_bar_layout = findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.toolbar);
        swipeContainer = findViewById(R.id.swipeContainer);
        rvNews = findViewById(R.id.rvNews);
        mPlaceHolderLayout = findViewById(R.id.phlPlaceholder);
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
        EventDetailsBeautyActivity.start(this, marketing, true);
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

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
        swipeContainer.setColorSchemeColors(ColorUtil.swipeRefreshColors(this));
    }

    private void initList() {
        mAdapter = new BusinessMarketingsAdapter(this, mPresenter, mPresenter);
        rvNews.setHasFixedSize(true);
        rvNews.setLayoutManager(new SpeedyLinearLayoutManager(
                this, SpeedyLinearLayoutManager.VERTICAL, false));
        rvNews.setAdapter(mAdapter);
    }
}
