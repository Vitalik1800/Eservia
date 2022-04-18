package com.eservia.booking.ui.home.bookings.archive_bookings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.eservia.booking.R;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.ui.home.HomeActivity;
import com.eservia.booking.ui.home.bookings.active_bookings.OnBookingsRestoListener;
import com.eservia.booking.ui.home.bookings.archive_bookings.general_archive_bookings.GeneralArchiveBookingsFragment;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.BindView;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.RestoDelivery;
import com.eservia.utils.KeyboardUtil;
import com.google.android.material.tabs.TabLayout;

import moxy.presenter.InjectPresenter;

public class ArchiveBookingsFragment extends BaseHomeFragment implements ArchiveBookingsView, OnBookingsRestoListener {

    public static final String TAG = "archive_bookings_fragment";

    @BindView(R.id.fragment_container)
    CoordinatorLayout fragmentContainer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;

    @InjectPresenter
    ArchiveBookingsPresenter mPresenter;

    private HomeActivity mActivity;

    private PagerAdapter mAdapter;

    public static ArchiveBookingsFragment newInstance() {
        return new ArchiveBookingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings_archive, container, false);
        mActivity = (HomeActivity) getActivity();
        setUnbinder(ButterKnife.bind(this, view));
        initViews();
        return view;
    }

    @Override
    public void refresh() {
        if (mAdapter != null && tab_layout != null) {
            Fragment fragment = mAdapter.getItem(tab_layout.getSelectedTabPosition());
            if (fragment instanceof BaseHomeFragment) {
                ((BaseHomeFragment) fragment).refresh();
            }
        }
    }

    @Override
    public void willBeDisplayed() {
        WindowUtils.setLightStatusBar(mActivity);
        FragmentUtil.startFragmentTabSelectAnimation(getActivity(), fragmentContainer);
    }

    @Override
    public void willBeHidden() {
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void onOpenDeliveryInfo(@NonNull RestoDelivery delivery) {
        if (getParentFragment() == null) return;
        FragmentManager fragmentManager = getParentFragment().getChildFragmentManager();
        FragmentUtil.showRestoDeliveryInfoFragment(fragmentManager, R.id.fragBookingsContainer, delivery);
    }

    private void setupViewPagerAdapter(ViewPager viewPager) {
        mAdapter = new PagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(GeneralArchiveBookingsFragment.newInstance(), "");
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(mAdapter.getCount() > 0 ? mAdapter.getCount() : 1);
    }

    private void initViews() {
/*        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");
        mActivity.getSupportActionBar().setElevation(0);*/

        //toolbar.inflateMenu(R.menu.menu_archive);
        //toolbar.setOnMenuItemClickListener(new ToolbarMenuItemListener());

        toolbar.setNavigationOnClickListener(view -> {
            if (getParentFragment() != null) {
                KeyboardUtil.hideSoftKeyboard(mActivity);
                mActivity.onBackPressed();
            }
        });

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
            KeyboardUtil.hideSoftKeyboard(mActivity);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    }
}
