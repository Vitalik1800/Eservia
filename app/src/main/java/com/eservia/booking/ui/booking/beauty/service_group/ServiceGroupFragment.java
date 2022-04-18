package com.eservia.booking.ui.booking.beauty.service_group;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.common.view.CommonPlaceHolder;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.ui.booking.beauty.basket.BasketFragment;
import com.eservia.booking.ui.booking.beauty.service.ServiceFragment;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.BeautyServiceGroup;
import com.eservia.utils.KeyboardUtil;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class ServiceGroupFragment extends BaseHomeFragment implements
        ServiceGroupView {

    public static final String TAG = "service_group_fragment_booking_beauty";

    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    RecyclerView rvServiceGroups;
    ProgressBar pbProgress;
    CommonPlaceHolder mPlaceHolder;
    EditText etSearch;
    TextView tvToolbarSubTitle;

    @InjectPresenter
    ServiceGroupPresenter mPresenter;

    private BaseActivity mActivity;

    private ServiceGroupAdapter mAdapter;

    private int mCreatedPreparationsCount;

    private MenuItem mMenuItemBasket;

    public static ServiceGroupFragment newInstance() {
        return new ServiceGroupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_beauty_service_group, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setLightStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        rvServiceGroups = view.findViewById(R.id.rvServiceGroups);
        pbProgress = view.findViewById(R.id.pbProgress);
        mPlaceHolder = view.findViewById(R.id.phlPlaceholder);
        etSearch = view.findViewById(R.id.etSearch);
        tvToolbarSubTitle = view.findViewById(R.id.tvToolbarSubTitle);
        initViews();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_basket_counter, menu);
        mMenuItemBasket = menu.findItem(R.id.item_basket);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        revalidateBasketBadgeCount();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                KeyboardUtil.hideSoftKeyboard(mActivity);
                mActivity.onBackPressed();
                return true;
            }
            case R.id.item_basket: {
                openBasketFragment();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void refresh() {
        if (rvServiceGroups != null) {
            rvServiceGroups.smoothScrollToPosition(0);
        }
    }

    @Override
    public void willBeDisplayed() {
        WindowUtils.setLightStatusBar(mActivity);
    }

    @Override
    public void willBeHidden() {
    }

    @Override
    public void showProgress() {
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void showServiceFragment(boolean startFromSearch) {
        openServiceFragment(startFromSearch);
    }

    @Override
    public void showSelectedAddress(Address address) {
        String street = address.getStreet();
        String number = address.getNumber();
        String addressName = BusinessUtil.getFullAddress(street, number);
        tvToolbarSubTitle.setVisibility(View.VISIBLE);
        tvToolbarSubTitle.setText(addressName);
    }

    @Override
    public void onServiceGroupsLoadingSuccess(List<BeautyServiceGroup> serviceGroupList) {
        mAdapter.replaceAll(serviceGroupList);
        revalidatePlaceHolder();
    }

    @Override
    public void onServiceGroupsLoadingFailed(Throwable throwable) {
        revalidatePlaceHolder();
    }

    @Override
    public void refreshBasketState(int preparationsCount) {
        mCreatedPreparationsCount = preparationsCount;
        revalidateBasketBadgeCount();
    }

    private void revalidatePlaceHolder() {
        boolean isEmpty = mAdapter.getItemCount() == 0;
        mPlaceHolder.setState(isEmpty ? CommonPlaceHolder.STATE_EMPTY : CommonPlaceHolder.STATE_HIDE);
    }

    private void revalidateBasketBadgeCount() {
        setBasketBadgeCount(String.valueOf(mCreatedPreparationsCount));
    }

    private void setBasketBadgeCount(String count) {
        if (mMenuItemBasket == null) return;
        ViewUtil.setCounterDrawableCount(mActivity, mMenuItemBasket, count);
    }

    private void initViews() {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");
        mActivity.getSupportActionBar().setElevation(0);
        initSwipeRefresh();
        initList();
        initSearch();
    }

    private void initSwipeRefresh() {
        ColorUtil.setProgressColor(pbProgress, R.color.colorPrimary);
    }

    private void initList() {
        mAdapter = new ServiceGroupAdapter(mActivity, mPresenter, mPresenter);
        rvServiceGroups.setAdapter(mAdapter);
        rvServiceGroups.setHasFixedSize(true);
        rvServiceGroups.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));
        rvServiceGroups.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    KeyboardUtil.hideSoftKeyboard(mActivity);
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void initSearch() {
        etSearch.setOnFocusChangeListener((view, b) -> {
            if (b) {
                mPresenter.onSearchFocus();
            }
        });
        etSearch.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                etSearch, new View[]{etSearch}));
    }

    private void openServiceFragment(boolean startFromSearch) {
        if (mActivity == null) return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentUtil.applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(ServiceFragment.TAG);
        if (fragment == null) {
            fragment = ServiceFragment.newInstance(startFromSearch);
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(R.id.bookingBeautyContainer, fragment, ServiceFragment.TAG);
        fragmentTransaction.commit();
    }

    private void openBasketFragment() {
        if (mActivity == null) return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentUtil.applyCommonFragmentAnimations(fragmentTransaction);
        Fragment fragment = fragmentManager.findFragmentByTag(BasketFragment.TAG);
        if (fragment == null) {
            fragment = BasketFragment.newInstance();
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(R.id.bookingBeautyContainer, fragment, BasketFragment.TAG);
        fragmentTransaction.commit();
    }
}
