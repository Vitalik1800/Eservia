package com.eservia.booking.ui.home.bookings.active_bookings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.RestoDelivery;
import com.eservia.utils.KeyboardUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.Map;

import moxy.presenter.InjectPresenter;

public class ActiveBookingsFragment extends BaseHomeFragment implements ActiveBookingsView, OnBookingsRestoListener {

    public static final String TAG = "active_bookings_fragment";

    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    ViewPager view_pager;
    TabLayout tab_layout;

    @InjectPresenter
    ActiveBookingsPresenter mPresenter;

    private HomeActivity mActivity;

    private PagerAdapter mAdapter;

    public static ActiveBookingsFragment newInstance() {
        return new ActiveBookingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings_active, container, false);
        mActivity = (HomeActivity) getActivity();
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        view_pager = view.findViewById(R.id.view_pager);
        tab_layout = view.findViewById(R.id.tab_layout);
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
        if (getParentFragment() != null) {
            FragmentManager fragmentManager = getParentFragment().getChildFragmentManager();
            FragmentUtil.showRestoDeliveryInfoFragment(fragmentManager, R.id.fragBookingsContainer, delivery);
        }
    }

    @Override
    public void showArchiveBookings() {
        if (getParentFragment() == null) return;
        FragmentManager fragmentManager = getParentFragment().getChildFragmentManager();
        FragmentUtil.openArchiveBookingsFragment(fragmentManager, R.id.fragBookingsContainer);
    }

    @Override
    public void setFragments(Map<String, Fragment> fragments) {
        mAdapter = new PagerAdapter(getChildFragmentManager());
        for (Map.Entry<String, Fragment> entry : fragments.entrySet()) {
            mAdapter.addFragment(entry.getValue(), entry.getKey());
        }
        view_pager.setAdapter(mAdapter);
        view_pager.setOffscreenPageLimit(mAdapter.getCount() > 0 ? mAdapter.getCount() : 1);
    }

    private void initViews() {

        toolbar.inflateMenu(R.menu.menu_archive);
        toolbar.setOnMenuItemClickListener(new ToolbarMenuItemListener());

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

    private class ToolbarMenuItemListener implements Toolbar.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            if (item.getItemId() == R.id.item_archive) {
                mPresenter.onArchiveClick();
                return true;
            }
            return false;
        }
    }
}
