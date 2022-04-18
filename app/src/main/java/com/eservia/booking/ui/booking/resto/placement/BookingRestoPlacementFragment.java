package com.eservia.booking.ui.booking.resto.placement;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.CommonPlaceHolder;
import com.eservia.booking.ui.booking.dialog.BookingErrorDialog;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.CommonAlert;
import com.eservia.model.entity.Address;
import com.eservia.utils.KeyboardUtil;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class BookingRestoPlacementFragment extends BaseHomeFragment implements
        BookingRestoPlacementView {

    public static final String TAG = "placement_fragment_booking_resto";

    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    ProgressBar pbProgress;
    CommonPlaceHolder phlPlaceholder;
    RelativeLayout rlPage;
    RelativeLayout rlCardHolderTablePlace;
    CardView cvTablePlaceContainer;
    Button btnCommonBookingButton;
    TextView tvToolbarSubTitle;
    NestedScrollView nsvContentHolder;
    RecyclerView rvTablePlace;
    CommonAlert alert;

    @InjectPresenter
    BookingRestoPlacementPresenter mPresenter;

    private BaseActivity mActivity;

    private DepartmentsAdapter mDepartmentsAdapter;

    public static BookingRestoPlacementFragment newInstance() {
        return new BookingRestoPlacementFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_resto_table_place, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setLightStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        pbProgress = view.findViewById(R.id.pbProgress);
        phlPlaceholder = view.findViewById(R.id.phlPlaceholder);
        rlPage = view.findViewById(R.id.rlPage);
        rlCardHolderTablePlace = view.findViewById(R.id.rlCardHolderTablePlace);
        cvTablePlaceContainer = view.findViewById(R.id.cvTablePlaceContainer);
        btnCommonBookingButton = view.findViewById(R.id.btnCommonBookingButton);
        tvToolbarSubTitle = view.findViewById(R.id.tvToolbarSubTitle);
        nsvContentHolder = view.findViewById(R.id.nsvContentHolder);
        alert = view.findViewById(R.id.alert);
        rvTablePlace = view.findViewById(R.id.rvTablePlace);
        btnCommonBookingButton.setOnClickListener(v -> onAcceptClick());
        initViews();
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            KeyboardUtil.hideSoftKeyboard(mActivity);
            mActivity.onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void refresh() {
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
        rlPage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
        rlPage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSelectedAddressAndTime(Address address, Pair<Integer, Integer> hourMinStart,
                                           Pair<Integer, Integer> hourMinEnd) {
        String street = address.getStreet();
        String number = address.getNumber();
        String addressName = BusinessUtil.getFullAddress(street, number);
        String time = BookingUtil.formatTimeStartEnd(hourMinStart.first, hourMinStart.second,
                hourMinEnd.first, hourMinEnd.second);
        tvToolbarSubTitle.setVisibility(View.VISIBLE);
        tvToolbarSubTitle.setText(String.format("%s\n%s", addressName, time));
    }

    @Override
    public void showPageLoadingError() {
        btnCommonBookingButton.setVisibility(View.INVISIBLE);
        nsvContentHolder.setVisibility(View.INVISIBLE);
        phlPlaceholder.setState(CommonPlaceHolder.STATE_EMPTY);
    }

    @Override
    public void refreshAcceptState(boolean isActive) {
        btnCommonBookingButton.setVisibility(isActive ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setDepartments(List<DepartmentAdapterItem> departments) {
        mDepartmentsAdapter.replaceAll(departments);
    }

    @Override
    public void matchSelectedDepartment(DepartmentAdapterItem item) {
        mDepartmentsAdapter.setSelected(item);
    }

    @Override
    public void onCreateBookingFailed(Throwable throwable) {
        openBookingErrorDialog();
    }

    @Override
    public void showThankYouFragment() {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.popAllBackStack(fragmentManager);
        openThankYouFragment();
    }

    private void openThankYouFragment() {
        if (mActivity == null) return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.openBookingRestoThankYouFragment(fragmentManager, R.id.bookingRestoContainer);
    }

    private void openBookingErrorDialog() {
        if (mActivity == null) return;
        FragmentManager fm = mActivity.getSupportFragmentManager();
        BookingErrorDialog.newInstance().show(fm, BookingErrorDialog.class.getSimpleName());
    }

    private void revalidatePlaceHolder() {
        phlPlaceholder.setState(CommonPlaceHolder.STATE_HIDE);
    }

    private void initViews() {
        initToolbar();
        initSpinner();
        setOutlineProviders();
        initAcceptLayout();
        initDepartmentsList();
    }

    private void initToolbar() {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");
    }

    private void setOutlineProviders() {
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderTablePlace, cvTablePlaceContainer);
    }

    private void initAcceptLayout() {
        btnCommonBookingButton.setBackground(ContextCompat.getDrawable(mActivity,
                R.drawable.background_common_button_red_light));
    }

    private void initDepartmentsList() {
        mDepartmentsAdapter = new DepartmentsAdapter(mActivity, mPresenter);
        rvTablePlace.setHasFixedSize(true);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mActivity);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.FLEX_START);
        rvTablePlace.setLayoutManager(layoutManager);
        rvTablePlace.setAdapter(mDepartmentsAdapter);
    }

    private void initSpinner() {
        ColorUtil.setProgressColor(pbProgress, R.color.colorPrimary);
    }
}
