package com.eservia.booking.ui.booking.beauty.service;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.SimpleTextWatcher;
import com.eservia.model.entity.Address;
import com.eservia.utils.KeyboardUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import moxy.presenter.InjectPresenter;

public class ServiceFragment extends BaseHomeFragment implements ServiceView {

    private static final String KEY_START_FROM_SEARCH = "start_from_search";

    public static final String TAG = "service_fragment_booking_beauty";

    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    RecyclerView rvServices;
    ProgressBar pbProgress;
    CommonPlaceHolder mPlaceHolder;
    EditText etSearch;
    TextView tvToolbarSubTitle;
    ImageView ivSearchIcon;
    Button btnCommonBookingNextButton;

    @InjectPresenter
    ServicePresenter mPresenter;

    private BaseActivity mActivity;

    private ServiceAdapter mAdapter;

    private int mCreatedPreparationsCount;

    private MenuItem mMenuItemBasket;

    private ExceededMaxSelectedServicesSheetDialog mErrorExceedDialog;

    public static ServiceFragment newInstance(boolean startFromSearch) {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        args.putBoolean(KEY_START_FROM_SEARCH, startFromSearch);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_beauty_service, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setLightStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        rvServices = view.findViewById(R.id.rvServices);
        pbProgress = view.findViewById(R.id.pbProgress);
        mPlaceHolder = view.findViewById(R.id.phlPlaceholder);
        etSearch = view.findViewById(R.id.etSearch);
        tvToolbarSubTitle = view.findViewById(R.id.tvToolbarSubTitle);
        ivSearchIcon = view.findViewById(R.id.ivSearchIcon);
        btnCommonBookingNextButton = view.findViewById(R.id.btnCommonBookingNextButton);
        btnCommonBookingNextButton.setOnClickListener(v -> onAcceptClick());
        initViews();
        return view;
    }

    @Override
    public void onDestroy() {
        hideErrorExceedDialog();
        super.onDestroy();
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
    public void refresh() {
        if (rvServices != null) {
            rvServices.smoothScrollToPosition(0);
        }
    }

    @Override
    public void willBeDisplayed() {
        WindowUtils.setLightStatusBar(mActivity);
    }

    @Override
    public void willBeHidden() {
    }

    public void onAcceptClick() {
        mPresenter.onAcceptClick();
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
    public void showSelectedAddress(Address address) {
        String street = address.getStreet();
        String number = address.getNumber();
        String addressName = BusinessUtil.getFullAddress(street, number);
        tvToolbarSubTitle.setVisibility(View.VISIBLE);
        tvToolbarSubTitle.setText(addressName);
    }

    @Override
    public void requiredArgs() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mPresenter.onArgs(bundle.getBoolean(KEY_START_FROM_SEARCH, false));
        } else {
            mPresenter.onArgs(false);
        }
    }

    @Override
    public void requestFocus() {
        etSearch.requestFocus();
    }

    @Override
    public void hideErrorExceedDialog() {
        if (mErrorExceedDialog != null) {
            mErrorExceedDialog.dismiss();
        }
    }

    @Override
    public void showErrorExceedDialog() {
        openErrorExceedServicesDialog();
    }

    @Override
    public void onServicesLoadingSuccess(List<ServiceAdapterItem> serviceList, boolean replaceAll) {
        if (replaceAll) {
            mAdapter.replaceAll(serviceList);
        } else {
            mAdapter.addAll(serviceList);
        }
        revalidatePlaceHolder();
    }

    @Override
    public void onServicesLoadingFailed(Throwable throwable) {
        revalidatePlaceHolder();
    }

    @Override
    public void showBookingOneServiceFragment() {
        openBookingOneServiceFragment();
    }

    @Override
    public void showBasketSortFragment() {
        openBasketSortFragment();
    }

    @Override
    public void showBookingFewServicesFragment() {
        openBookingFewServicesFragment();
    }

    @Override
    public void setSelected(boolean selected, Integer serviceId) {
        mAdapter.setSelected(selected, serviceId);
    }

    @Override
    public void refreshAcceptState(boolean isActive) {
        if (isActive) {
            btnCommonBookingNextButton.setVisibility(View.VISIBLE);
        } else {
            btnCommonBookingNextButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void refreshBasketState(int preparationsCount) {
        mCreatedPreparationsCount = preparationsCount;
        revalidateBasketBadgeCount();
    }

    private void search(String query) {
        mPresenter.onQuery(query);
    }

    private void revalidateBasketBadgeCount() {
        setBasketBadgeCount(String.valueOf(mCreatedPreparationsCount));
    }

    private void setBasketBadgeCount(String count) {
        if (mMenuItemBasket == null) return;
        ViewUtil.setCounterDrawableCount(mActivity, mMenuItemBasket, count);
    }

    private void revalidatePlaceHolder() {
        boolean isEmpty = mAdapter.getItemCount() == 0;
        mPlaceHolder.setState(isEmpty ? CommonPlaceHolder.STATE_EMPTY : CommonPlaceHolder.STATE_HIDE);
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
        mAdapter = new ServiceAdapter(mActivity, mPresenter, mPresenter);
        rvServices.setAdapter(mAdapter);
        rvServices.setHasFixedSize(true);
        rvServices.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));
        rvServices.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(etSearch.getText().toString());
                return true;
            }
            return false;
        });
        ivSearchIcon.setOnClickListener(view -> search(etSearch.getText().toString()));
        etSearch.addTextChangedListener(new SimpleTextWatcher() {
            private Timer timer = new Timer();
            private int changeCount = 0;

            @Override
            public void textChanged(String s) {
                if (changeCount < 1) {
                    changeCount++;
                    return;
                }
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(() -> search(s));
                    }
                }, 500);
            }
        });
        etSearch.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                etSearch, new View[]{etSearch}));
    }

    private void openErrorExceedServicesDialog() {
        mErrorExceedDialog = ExceededMaxSelectedServicesSheetDialog.newInstance();
        mErrorExceedDialog.setListener(mPresenter);
        mErrorExceedDialog.show(mActivity.getSupportFragmentManager(),
                ExceededMaxSelectedServicesSheetDialog.class.getSimpleName());
    }

    private void openBookingFewServicesFragment() {
    }

    private void openBasketSortFragment() {
        if (mActivity == null) return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.openBookingBeautyBasketSortFragment(fragmentManager, R.id.bookingBeautyContainer);
    }

    private void openBookingOneServiceFragment() {
        if (mActivity == null) return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.openBookingBeautyFragment(fragmentManager, R.id.bookingBeautyContainer);
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
