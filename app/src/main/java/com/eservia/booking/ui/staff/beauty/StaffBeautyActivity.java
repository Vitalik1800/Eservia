package com.eservia.booking.ui.staff.beauty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.ui.staff.beauty.info.StaffInfoBeautyFragment;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.utils.KeyboardUtil;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;

import moxy.presenter.InjectPresenter;

public class StaffBeautyActivity extends BaseActivity implements StaffBeautyView {
    CoordinatorLayout coordinator;
    Toolbar toolbar;
    ViewPager view_pager;
    TabLayout tab_layout;
    TextView tvToolbarTitle;

    @InjectPresenter
    StaffBeautyPresenter mPresenter;

    public static void start(Context context, BeautyStaff staff) {
        EventBus.getDefault().postSticky(staff);
        Intent starter = new Intent(context, StaffBeautyActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_beauty);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        coordinator = findViewById(R.id.coordinator);
        toolbar = findViewById(R.id.toolbar);
        view_pager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            mPresenter.onCloseClick();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void onStaff(@Nullable BeautyStaff staff) {
        if (staff == null) {
            return;
        }
        tvToolbarTitle.setText(staff.getFirstName());
    }

    @Override
    public void closeActivity() {
        finish();
    }

    private void setupViewPagerAdapter(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(StaffInfoBeautyFragment.newInstance(), getResources().getString(R.string.information));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount() > 0 ? adapter.getCount() : 1);
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        setupViewPagerAdapter(view_pager);
        tab_layout.setupWithViewPager(view_pager);
        tab_layout.addOnTabSelectedListener(new TabListener());

        tab_layout.setVisibility(View.GONE);
    }

    private class TabListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            KeyboardUtil.hideSoftKeyboard(StaffBeautyActivity.this);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    }
}
