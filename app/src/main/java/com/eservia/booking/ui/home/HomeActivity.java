package com.eservia.booking.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.eservia.ahbottomnavigation.AHBottomNavigation;
import com.eservia.ahbottomnavigation.AHBottomNavigationAdapter;
import com.eservia.ahbottomnavigation.AHBottomNavigationViewPager;
import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.ui.home.bookings.BookingsManagerFragment;
import com.eservia.booking.ui.home.favorite.FavoriteManagerFragment;
import com.eservia.booking.ui.home.menu.MenuManagerFragment;
import com.eservia.booking.ui.home.news.NewsManagerFragment;
import com.eservia.booking.ui.home.search.SearchManagerFragment;
import com.eservia.booking.ui.suggest_business.SuggestBusinessActivity;
import com.eservia.booking.util.LocationUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import moxy.presenter.InjectPresenter;

public class HomeActivity extends BaseActivity implements HomeView {

    public static final int CODE_REQUEST_FINE_LOCATION = 99;

    public static final int CODE_REQUEST_CHECK_LOCATION_SETTINGS = 98;

    public static final int KEY_TAB_BOOKINGS = 111;

    public static final int KEY_TAB_MARKETING = 112;

    private static final int START_TAB_POSITION = 0;

    private static final String KEY_START_TAB = "key_start_tab";
    AHBottomNavigationViewPager mViewPager;
    AHBottomNavigation mBottomNavigation;

    @InjectPresenter
    HomePresenter mPresenter;

    private final Handler mHandler = new Handler();

    private final List<BaseHomeFragment> mFragments = new ArrayList<>();

    private BaseHomeFragment mCurrentFragment;

    private HomeAdapter mAdapter;

    private LocationAccessExplanationSheetDialog mLocationExplanationDialog;

    public static Intent init(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    public static Intent init(Context context, int startTab) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(KEY_START_TAB, startTab);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    public static void start(Context context) {
        Intent starter = init(context);
        context.startActivity(starter);
    }

    public static void start(Context context, int startTab) {
        Intent starter = init(context, startTab);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        WindowUtils.setFullScreenWithStatusBar(this);
        WindowUtils.setNormalStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        mViewPager = findViewById(R.id.view_pager);
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        initViews();
    }

    @Override
    protected void onPause() {
        mPresenter.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!popChildFragment()) {
            finish();
        }
    }

    @Override
    public void showSuggestBusinessModal() {
        SuggestBusinessActivity.start(this, true);
    }

    @Override
    public void shouldShowFineLocationRationale() {
        if (LocationUtil.shouldShowFineLocationRationale(this)) {
            mPresenter.onShouldShowFineLocationRationale();
        } else {
            mPresenter.onShouldNotShowFineLocationRationale();
        }
    }

    @Override
    public void requestFineLocationPermission() {
        LocationUtil.requestFineLocationPermission(this, CODE_REQUEST_FINE_LOCATION);
    }

    @Override
    public void showFineLocationExplanationDialog() {
        openLocationExplanationDialog();
    }

    @Override
    public void hideFineLocationExplanationDialog() {
        if (mLocationExplanationDialog != null) {
            mLocationExplanationDialog.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_REQUEST_FINE_LOCATION) {
            if (LocationUtil.permissionWasGranted(grantResults)) {
                mPresenter.onFineLocationPermissionGranted();
            } else {
                mPresenter.onFineLocationPermissionNotGranted();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REQUEST_CHECK_LOCATION_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    mPresenter.onLocationSettingsResolved();
                    break;
                case Activity.RESULT_CANCELED:
                    mPresenter.onLocationSettingsNotResolved();
                    break;
            }
        }
    }

    @Override
    public void checkLocationSettings(LocationSettingsRequest.Builder builder) {
        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, locationSettingsResponse -> mPresenter.onLocationSettingsSatisfied());

        task.addOnFailureListener(this, e -> {
            if (e instanceof ResolvableApiException) {
                ResolvableApiException resolvable = (ResolvableApiException) e;
                mPresenter.onLocationSettingsNotSatisfied(resolvable);
            } else {
                mPresenter.onLocationSettingsNotSatisfied(null);
            }
        });
    }

    @Override
    public void showLocationSettingsResolutionDialog(ResolvableApiException resolvable) {

        try {
            resolvable.startResolutionForResult(HomeActivity.this,
                    CODE_REQUEST_CHECK_LOCATION_SETTINGS);

        } catch (IntentSender.SendIntentException sendEx) {
            // ignore the error
        }
    }

    @Override
    public void prepareExtra() {
        int keyStartTab = getIntent().getIntExtra(KEY_START_TAB, 0);
        if (keyStartTab == KEY_TAB_BOOKINGS) {
            mPresenter.onStartTabBookings();
        } else if (keyStartTab == KEY_TAB_MARKETING) {
            mPresenter.onStartTabMarketing();
        }
    }

    @Override
    public void showBookingsTab() {
        mBottomNavigation.setCurrentItem(bookingsFragmentIndex());
        WindowUtils.setLightStatusBar(this);
    }

    @Override
    public void showMarketingTab() {
        mBottomNavigation.setCurrentItem(newsFragmentIndex());
        WindowUtils.setLightStatusBar(this);
    }

    public void reload() {
        start(this);
        finish();
    }

    public void showBottomNavigation(boolean show, boolean withAnimation) {
        if (show) {
            mBottomNavigation.restoreBottomNavigation(withAnimation);
        } else {
            mBottomNavigation.hideBottomNavigation(withAnimation);
        }
    }

    private boolean popChildFragment() {
        if (mCurrentFragment != null) {
            FragmentManager childFm = mCurrentFragment.getChildFragmentManager();
            if (childFm.getBackStackEntryCount() > 1) {
                childFm.popBackStack();
                return true;
            }
        }
        return false;
    }

    private void initFragments() {
        mFragments.add(SearchManagerFragment.newInstance());
        mFragments.add(FavoriteManagerFragment.newInstance());
        mFragments.add(BookingsManagerFragment.newInstance());
        mFragments.add(NewsManagerFragment.newInstance());
        mFragments.add(MenuManagerFragment.newInstance());
    }

    private void initNavigationAdapter() {
        int[] tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
        AHBottomNavigationAdapter mNavigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu);
        mNavigationAdapter.setupWithBottomNavigation(mBottomNavigation, tabColors);
        mAdapter = new HomeAdapter(getSupportFragmentManager());
        for (BaseHomeFragment fragment : mFragments) {
            mAdapter.addFragment(fragment);
        }
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setCurrentItem(START_TAB_POSITION, false);
        mCurrentFragment = mAdapter.getItem(START_TAB_POSITION);
    }

    private void initBottomNavigation() {
        mBottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        mBottomNavigation.setItemDisableColor(getResources().getColor(R.color.gray_300));
        mBottomNavigation.setInactiveColor(getResources().getColor(R.color.gray_300));
        mBottomNavigation.setBehaviorTranslationEnabled(false);
        mBottomNavigation.setTranslucentNavigationEnabled(false);
        mBottomNavigation.setColored(false);
        mBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        mBottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        mBottomNavigation.setUseElevation(true, ViewUtil.dpToPixel(this, 16));
        mBottomNavigation.setOnTabSelectedListener(new NavigationTabSelectedListener());
        mBottomNavigation.setOnNavigationPositionListener(new NavigationPositionListener());
    }

    private void initViews() {
        initBottomNavigation();
        initFragments();
        initNavigationAdapter();
    }

    private void openLocationExplanationDialog() {
        mLocationExplanationDialog = LocationAccessExplanationSheetDialog.newInstance();
        mLocationExplanationDialog.setListener(mPresenter);
        mLocationExplanationDialog.show(getSupportFragmentManager(),
                LocationAccessExplanationSheetDialog.class.getSimpleName());
    }

    private int bookingsFragmentIndex() {
        for (int i = 0; i < mFragments.size(); i++) {
            if (mFragments.get(i) instanceof BookingsManagerFragment) {
                return i;
            }
        }
        return 0;
    }

    private int newsFragmentIndex() {
        for (int i = 0; i < mFragments.size(); i++) {
            if (mFragments.get(i) instanceof NewsManagerFragment) {
                return i;
            }
        }
        return 0;
    }

    private class NavigationTabSelectedListener implements AHBottomNavigation.OnTabSelectedListener {

        @Override
        public boolean onTabSelected(int position, boolean wasSelected) {

            if (mCurrentFragment == null) {
                mCurrentFragment = mAdapter.getCurrentFragment();
            }
            if (wasSelected) {
                mCurrentFragment.refresh();
                return true;
            }
            if (mCurrentFragment != null) {
                mCurrentFragment.willBeHidden();
            }
            mViewPager.setCurrentItem(position, false);
            if (mCurrentFragment == null) {
                return true;
            }
            mCurrentFragment = mAdapter.getCurrentFragment();

            if (mCurrentFragment != null) {
                mCurrentFragment.willBeDisplayed();
            }

            return true;
        }
    }

    private static class NavigationPositionListener implements AHBottomNavigation.OnNavigationPositionListener {

        @Override
        public void onPositionChange(int y) {

        }
    }
}
