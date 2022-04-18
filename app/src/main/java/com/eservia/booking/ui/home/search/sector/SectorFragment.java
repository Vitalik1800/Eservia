package com.eservia.booking.ui.home.search.sector;

import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eservia.booking.R;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.ui.home.HomeActivity;
import com.eservia.booking.ui.home.search.search.sheet.SearchSheetDialog;
import com.eservia.booking.ui.home.search.search.sheet.SheetAdapterItem;
import com.eservia.booking.ui.payment.PaymentActivity;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.BusinessSector;
import com.eservia.utils.KeyboardUtil;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import moxy.presenter.InjectPresenter;

public class SectorFragment extends BaseHomeFragment implements SectorView,
        SearchSheetDialog.OnSheetDialogItemClickListener {

    public static final String TAG = "sector_fragment";
    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    RecyclerView rvSectors;
    SwipeRefreshLayout swipeContainer;
    LinearLayout llCommonFakeSpinner;
    TextView tvCommonFakeSpinnerTitle;
    RelativeLayout rlPaymentBar;
    CardView cvPaymentBar;
    AppBarLayout app_bar_layout;
    ImageView ivPoints, ivDiscounts, ivWallet;

    @InjectPresenter
    SectorPresenter mPresenter;

    private HomeActivity mActivity;

    private SectorAdapter mAdapter;

    private SearchSheetDialog mSheetDialog;

    public static SectorFragment newInstance() {
        return new SectorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sector, container, false);
        mActivity = (HomeActivity) getActivity();
        mPresenter = new SectorPresenter();
        //WindowUtils.setNormalStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        rvSectors = view.findViewById(R.id.rvSectors);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        llCommonFakeSpinner = view.findViewById(R.id.llCommonFakeSpinner);
        tvCommonFakeSpinnerTitle = view.findViewById(R.id.tvCommonFakeSpinnerTitle);
        rlPaymentBar = view.findViewById(R.id.rlPaymentBar);
        cvPaymentBar = view.findViewById(R.id.cvPaymentBar);
        app_bar_layout = view.findViewById(R.id.app_bar_layout);
        llCommonFakeSpinner.setOnClickListener(v -> onCityClick());
        ivPoints = view.findViewById(R.id.ivPoints);
        ivDiscounts = view.findViewById(R.id.ivDiscounts);
        ivWallet = view.findViewById(R.id.ivWallet);
        ivPoints.setOnClickListener(v -> onPointsClick());
        ivDiscounts.setOnClickListener(v -> onDiscountsClick());
        ivWallet.setOnClickListener(v -> onWalletClick());
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void refresh() {
        ViewUtil.scrollTop(app_bar_layout, rvSectors);
    }

    @Override
    public void willBeDisplayed() {
        WindowUtils.setNormalStatusBar(mActivity);
        FragmentUtil.startFragmentTabSelectAnimation(getActivity(), fragmentContainer);
    }

    @Override
    public void willBeHidden() {
    }

    @Override
    public void showProgress() {
        swipeContainer.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeContainer.setRefreshing(false);
    }

    public void onCityClick() {
        mPresenter.onShowCitySelection();
    }
    public void onPointsClick() {
        mPresenter.onPointsClick();
    }
    public void onDiscountsClick() {
        mPresenter.onDiscountsClick();
    }
    public void onWalletClick() {
        mPresenter.onWalletClick();
    }

    @Override
    public void onSectorsLoadingSuccess(List<BusinessSector> sectors, boolean isMale) {
        mAdapter.replaceAll(mapToListItems(sectors, isMale));
        revalidateSectorsList();
    }

    @Override
    public void onSectorsLoadingFailed(Throwable throwable) {
        if (sectorsListIsFullyEmpty()) {
            mAdapter.addItem(new NotFoundSectorsItem());
        }
        revalidateSectorsList();
    }

    @Override
    public void goToSearch(BusinessSector sector) {
        showSearchFragment(sector, false);
    }

    @Override
    public void goToPoints() {
        showPointsFragment();
    }

    @Override
    public void goToDiscounts() {
        showDiscountsFragment();
    }

    @Override
    public void goToWallet() {
        showWalletFragment();
    }

    @Override
    public void showSelectedCity(@Nullable String city) {
        if (city != null) {
            llCommonFakeSpinner.setVisibility(View.VISIBLE);
            tvCommonFakeSpinnerTitle.setText(city);
        } else {
            llCommonFakeSpinner.setVisibility(View.INVISIBLE);
            tvCommonFakeSpinnerTitle.setText("");
        }
    }

    @Override
    public void showCitiesDialog(List<SheetAdapterItem> items) {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        openCitiesSheetDialog(items);
    }

    @Override
    public void hideSheetDialog() {
        if (mSheetDialog != null) {
            mSheetDialog.dismiss();
        }
    }

    @Override
    public void onDialogItemClick(SheetAdapterItem item, String dialogType) {
        mPresenter.onDialogCitySelected(item);
    }

    private void revalidateSectorsList() {
        TransitionManager.beginDelayedTransition(rvSectors, new Fade());
    }

    private List<ListItem> mapToListItems(List<BusinessSector> sectors, boolean isMale) {
        List<ListItem> listItems = new ArrayList<>();

        for (BusinessSector sector : sectors) {
            listItems.add(new SectorItem(sector, isMale));
        }
        if (listItems.size() > 0) {
            listItems.add(new ChooseSectorItem());
        } else {
            listItems.add(new NotFoundSectorsItem());
        }
        return listItems;
    }

    private boolean sectorsListIsFullyEmpty() {
        return mAdapter.getItemCount() == 0;
    }

    private void showSearchFragment(BusinessSector sector, boolean startFromSearch) {
        if (getParentFragment() == null) return;
        FragmentManager fragmentManager = getParentFragment().getChildFragmentManager();
        FragmentUtil.showSearchFragment(fragmentManager, R.id.fragSearchContainer, sector, startFromSearch);
    }

    private void showPointsFragment() {
        if (getParentFragment() == null) return;
        FragmentManager fragmentManager = getParentFragment().getChildFragmentManager();
        FragmentUtil.showPointsFragment(fragmentManager, R.id.fragSearchContainer);
    }

    private void showDiscountsFragment() {
        if (getParentFragment() == null) return;
        FragmentManager fragmentManager = getParentFragment().getChildFragmentManager();
        FragmentUtil.showDiscountsFragment(fragmentManager, R.id.fragSearchContainer);
    }

    private void showWalletFragment() {
        showPaymentFragment();
    }

    private void showPaymentFragment() {
        PaymentActivity.start(mActivity);
    }

    private void initViews() {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");
        mActivity.getSupportActionBar().setElevation(0);
        initSwipeRefresh();
        initList();
        initOutlineProviders();
    }

    private void initOutlineProviders() {
        ViewUtil.setCardOutlineProviderStraightCorners(mActivity, rlPaymentBar, cvPaymentBar);
    }

    private void initSwipeRefresh() {
        swipeContainer.setOnRefreshListener(mPresenter::refresh);
        swipeContainer.setColorSchemeColors(ColorUtil.swipeRefreshColors(mActivity));
    }

    private void initList() {
        mAdapter = new SectorAdapter(mActivity, mPresenter, mPresenter);
        rvSectors.setAdapter(mAdapter);
        rvSectors.setHasFixedSize(true);
        rvSectors.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));

    }

    private void openCitiesSheetDialog(List<SheetAdapterItem> items) {
        mSheetDialog = SearchSheetDialog.newInstance(null);
        mSheetDialog.setSortClickListener(this);
        mSheetDialog.setAdapterItems(items);
        mSheetDialog.setTitle(mActivity.getResources().getString(R.string.choose_city));
        mSheetDialog.show(mActivity.getSupportFragmentManager(),
                SearchSheetDialog.class.getSimpleName());
    }
}
