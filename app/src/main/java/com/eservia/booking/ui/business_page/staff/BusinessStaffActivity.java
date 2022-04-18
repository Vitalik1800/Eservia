package com.eservia.booking.ui.business_page.staff;

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
import com.eservia.booking.ui.staff.beauty.StaffBeautyActivity;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.model.entity.Business;
import com.google.android.material.appbar.AppBarLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class BusinessStaffActivity extends BaseActivity implements BusinessStaffView {

    CoordinatorLayout coordinator;
    AppBarLayout app_bar_layout;
    Toolbar toolbar;
    SwipeRefreshLayout swipeContainer;
    RecyclerView rvStaff;
    CommonPlaceHolder mPlaceHolderLayout;

    @InjectPresenter
    BusinessStaffPresenter mPresenter;

    private BusinessStaffAdapter mAdapter;

    public static void start(Context context, Business business) {
        EventBus.getDefault().postSticky(business);
        Intent starter = new Intent(context, BusinessStaffActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_staff);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        coordinator = findViewById(R.id.coordinator);
        toolbar = findViewById(R.id.toolbar);
        app_bar_layout = findViewById(R.id.app_bar_layout);
        swipeContainer = findViewById(R.id.swipeContainer);
        rvStaff = findViewById(R.id.rvStaff);
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
    public void onStaffLoadingSuccess(List<BeautyStaff> staffList) {
        mAdapter.replaceAll(staffList);
        revalidatePlaceHolder();
    }

    @Override
    public void onStaffLoadingFailed(Throwable throwable) {
        revalidatePlaceHolder();
    }

    @Override
    public void showStaffDetailBeautyPage(BeautyStaff staff) {
        StaffBeautyActivity.start(this, staff);
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
        swipeContainer.setOnRefreshListener(mPresenter::refreshStaffs);
        swipeContainer.setColorSchemeColors(ColorUtil.swipeRefreshColors(this));
    }

    private void initList() {
        mAdapter = new BusinessStaffAdapter(this, mPresenter, mPresenter);
        rvStaff.setHasFixedSize(true);
        rvStaff.setLayoutManager(new SpeedyLinearLayoutManager(
                this, SpeedyLinearLayoutManager.VERTICAL, false));
        rvStaff.setAdapter(mAdapter);
    }
}
