package com.eservia.booking.ui.home.favorite.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.eservia.booking.R;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.ui.home.HomeActivity;
import com.eservia.booking.ui.home.favorite.favorite.favorites_sector.FavoritesSectorFragment;
import com.eservia.booking.ui.suggest_business.SuggestBusinessActivity;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.utils.KeyboardUtil;
import com.google.android.material.tabs.TabLayout;

import moxy.presenter.InjectPresenter;

public class FavoriteFragment extends BaseHomeFragment implements FavoriteView {

    public static final String TAG = "favorite_fragment";

    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    ViewPager view_pager;
    TabLayout tab_layout;

    @InjectPresenter
    FavoritePresenter mPresenter;

    private HomeActivity mActivity;

    private PagerAdapter mAdapter;

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
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
    public void showBusinessSuggestion() {
        SuggestBusinessActivity.start(mActivity, false);
    }

    private void setupViewPagerAdapter(ViewPager viewPager) {
        mAdapter = new PagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(FavoritesSectorFragment.newInstance(), "");
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(mAdapter.getCount() > 0 ? mAdapter.getCount() : 1);
    }

    private void initViews() {

        toolbar.inflateMenu(R.menu.menu_suggest_business);
        toolbar.setOnMenuItemClickListener(new ToolbarMenuItemListener());

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

    private class ToolbarMenuItemListener implements Toolbar.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            if (item.getItemId() == R.id.item_suggest) {
                mPresenter.onSuggestClicked();
                return true;
            }
            return false;
        }
    }
}
