package com.eservia.booking.ui.home.news.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.eservia.booking.R;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.ui.home.HomeActivity;
import com.eservia.booking.ui.home.news.news.news_and_promo.NewsAndPromoFragment;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.utils.KeyboardUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import moxy.presenter.InjectPresenter;

public class NewsFragment extends BaseHomeFragment implements NewsView, NewsAndPromoFragment.OnFilterSelectedListener {

    public static final String TAG = "news_fragment";

    CoordinatorLayout fragmentContainer;
    AppBarLayout app_bar_layout;
    Toolbar toolbar;
    ViewPager view_pager;
    TabLayout tab_layout;
    TextView tvFilteringInput;

    @InjectPresenter
    NewsPresenter mPresenter;

    private HomeActivity mActivity;

    private PagerAdapter mAdapter;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_news, container, false);
        mActivity = (HomeActivity) getActivity();
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        app_bar_layout = view.findViewById(R.id.app_bar_layout);
        toolbar = view.findViewById(R.id.toolbar);
        view_pager = view.findViewById(R.id.view_pager);
        tab_layout = view.findViewById(R.id.tab_layout);
        tvFilteringInput = view.findViewById(R.id.tvFilteringInput);
        tvFilteringInput.setOnClickListener(v -> onFilterClick());
        initViews();
        return view;
    }

    @Override
    public void refresh() {
        BaseNewsListFragment currentFragment = currentFragment();
        if (currentFragment != null) {
            currentFragment.refresh();
            ViewUtil.scrollTop(app_bar_layout);
        }
    }

    @Override
    public void willBeDisplayed() {
        WindowUtils.setNormalStatusBar(mActivity);
        FragmentUtil.startFragmentTabSelectAnimation(getActivity(), fragmentContainer);
        BaseNewsListFragment currentFragment = currentFragment();
        if (currentFragment != null) {
            currentFragment.willBeDisplayed();
        }
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

    public void onFilterClick() {
        BaseNewsListFragment currentFragment = currentFragment();
        if (currentFragment != null) {
            currentFragment.onFilterClick();
        }
    }

    @Override
    public void onFilteringItemSelected(@Nullable String filter) {
        tvFilteringInput.setText(filter != null ? filter : mActivity.getResources().getString(R.string.all_cities));
    }

    @Nullable
    private BaseNewsListFragment currentFragment() {
        if (mAdapter == null || tab_layout == null) {
            return null;
        }
        Fragment current = mAdapter.getItem(tab_layout.getSelectedTabPosition());
        return current instanceof BaseNewsListFragment ? (BaseNewsListFragment) current : null;
    }

    private void setupViewPagerAdapter(ViewPager viewPager) {
        mAdapter = new PagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(NewsAndPromoFragment.newInstance(), getResources().getString(R.string.news_and_promotions));
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(mAdapter.getCount() > 0 ? mAdapter.getCount() : 1);
    }

    private void initViews() {

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
