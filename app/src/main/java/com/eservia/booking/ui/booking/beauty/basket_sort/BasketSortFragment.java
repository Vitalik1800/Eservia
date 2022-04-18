package com.eservia.booking.ui.booking.beauty.basket_sort;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.CommonPlaceHolder;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.item_touch_helper.ItemTouchHelperCallback;
import com.eservia.common.view.item_touch_helper.OnStartDragListener;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.BeautyService;
import com.eservia.utils.KeyboardUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import moxy.presenter.InjectPresenter;

public class BasketSortFragment extends BaseHomeFragment implements BasketSortView,
        OnStartDragListener {

    public static final String TAG = "basket_sort_fragment_booking_beauty";

    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    RecyclerView rvSelectedPreparations;
    ProgressBar pbProgress;
    CommonPlaceHolder mPlaceHolder;
    TextView tvToolbarSubTitle;
    Button btnCommonBookingNextButton;
    NestedScrollView nsvContentHolder;
    LinearLayout llContent;
    RelativeLayout rlCardHolderSelectedPreparations;
    CardView cvSelectedPreparations;
    RelativeLayout rlSelectedPreparationsTotal;
    LinearLayout llSelectedPreparationsTotalPricesList;

    @InjectPresenter
    BasketSortPresenter mPresenter;

    private BaseActivity mActivity;

    private BasketSortAdapter mAdapter;

    private MenuItem mMenuItemEdit;

    private boolean mEditModeEnabled = false;

    private ItemTouchHelper mItemTouchHelper;

    public static BasketSortFragment newInstance() {
        return new BasketSortFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_beauty_basket_sort, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setLightStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        rvSelectedPreparations = view.findViewById(R.id.rvSelectedPreparations);
        pbProgress = view.findViewById(R.id.pbProgress);
        mPlaceHolder = view.findViewById(R.id.phlPlaceholder);
        tvToolbarSubTitle = view.findViewById(R.id.tvToolbarSubTitle);
        btnCommonBookingNextButton = view.findViewById(R.id.btnCommonBookingNextButton);
        nsvContentHolder = view.findViewById(R.id.nsvContentHolder);
        llContent = view.findViewById(R.id.llContent);
        rlCardHolderSelectedPreparations = view.findViewById(R.id.rlCardHolderSelectedPreparations);
        cvSelectedPreparations = view.findViewById(R.id.cvSelectedPreparations);
        rlSelectedPreparationsTotal = view.findViewById(R.id.rlSelectedPreparationsTotal);
        llSelectedPreparationsTotalPricesList = view.findViewById(R.id.llSelectedPreparationsTotalPricesList);
        btnCommonBookingNextButton.setOnClickListener(v -> onAcceptClick());
        initViews();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_change_dark, menu);
        mMenuItemEdit = menu.findItem(R.id.item_change);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        revalidateOptionsMenu();
        super.onPrepareOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                mPresenter.onGoBackClick(mAdapter.getAdapterItems());
                return true;
            }
            case R.id.item_change: {
                mMenuItemEdit.setEnabled(false);
                mPresenter.editModeEnabled(!mEditModeEnabled);
                new EditDisabledTimer(500, 500).start();
                return true;
            }
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

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public void onAcceptClick() {
        mPresenter.onAcceptClick(mAdapter.getAdapterItems());
    }

    @Override
    public void showProgress() {
        mPlaceHolder.setVisibility(View.GONE);
        pbProgress.setVisibility(View.VISIBLE);
        btnCommonBookingNextButton.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
        mPlaceHolder.setVisibility(View.VISIBLE);
        btnCommonBookingNextButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBookingFragment() {
        openBookingFragment();
    }

    @Override
    public void showSelectedAddress(Address address) {
        String street = address.getStreet();
        String number = address.getNumber();
        String addressName = BusinessUtil.getFullAddress(street, number);
        tvToolbarSubTitle.setVisibility(View.VISIBLE);
        tvToolbarSubTitle.setText(addressName);
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onPreparations(List<BasketSortAdapterItem> items) {
        TransitionManager.beginDelayedTransition(rvSelectedPreparations, new Slide(Gravity.LEFT));
        mAdapter.replaceAll(items);
        revalidatePlaceHolder();
        revalidateFilledTotalLayout();
        revalidateAccept();
        revalidateOptionsMenu();
    }

    @Override
    public void onEditMode(boolean enabled) {
        mEditModeEnabled = enabled;
    }

    @Override
    public void goBack() {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        mActivity.onBackPressed();
    }

    private void revalidateFilledTotalLayout() {
        Map<String, Float> currencyAndPriceMap = currencyAndPriceMap(mAdapter.getAdapterItems());
        if (currencyAndPriceMap.size() > 0) {
            rlSelectedPreparationsTotal.setVisibility(View.VISIBLE);
            llSelectedPreparationsTotalPricesList.removeAllViews();
            for (Map.Entry<String, Float> entry : currencyAndPriceMap.entrySet()) {
                String currency = entry.getKey();
                Float totalPrice = entry.getValue();
                @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.item_currency_price, null);
                TextView tvCurrency = view.findViewById(R.id.tvFilledPreparationsTotalCurrency);
                TextView tvPrice = view.findViewById(R.id.tvFilledPreparationsTotalPrice);
                tvCurrency.setText(currency);
                tvPrice.setText(BookingUtil.formatPrice(totalPrice));
                llSelectedPreparationsTotalPricesList.addView(view);
            }
        } else {
            rlSelectedPreparationsTotal.setVisibility(View.GONE);
        }
    }

    private Map<String, Float> currencyAndPriceMap(List<BasketSortAdapterItem> items) {
        Map<String, Float> result = new TreeMap<>();
        for (BasketSortAdapterItem item : items) {

            BeautyService service = item.getPreparation().getService();
            Float price = service.getPrice();
            String currency = service.getCurrency();
            if (BookingUtil.servicePriceIsEmpty(price)
                    || BookingUtil.serviceCurrencyIsEmpty(currency)) {
                continue;
            }
            Float exTotalPrice = result.get(currency);
            if (exTotalPrice == null) {
                result.put(currency, price);
            } else {
                result.put(currency, exTotalPrice + price);
            }
        }
        return result;
    }

    private void revalidateOptionsMenu() {
        if (mMenuItemEdit == null) return;

        // TODO: for now edit mode is unavailable, uncomment then
        mMenuItemEdit.setVisible(false);

    }

    private void revalidateAccept() {
        boolean isActive = mAdapter.getItemCount() > 0;
        if (isActive) {
            btnCommonBookingNextButton.setVisibility(View.VISIBLE);
        } else {
            btnCommonBookingNextButton.setVisibility(View.INVISIBLE);
        }
    }

    private void revalidatePlaceHolder() {
        boolean isEmpty = mAdapter.getItemCount() == 0;

        if (isEmpty) {
            rlCardHolderSelectedPreparations.setVisibility(View.GONE);
        } else {
            rlCardHolderSelectedPreparations.setVisibility(View.VISIBLE);
        }

        mPlaceHolder.setState(isEmpty ? CommonPlaceHolder.STATE_EMPTY : CommonPlaceHolder.STATE_HIDE);
    }

    private void initViews() {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");
        mActivity.getSupportActionBar().setElevation(0);

        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderSelectedPreparations, cvSelectedPreparations);

        initSwipeRefresh();
        initList();
    }

    private void initSwipeRefresh() {
        ColorUtil.setProgressColor(pbProgress, R.color.colorPrimary);
    }

    private void initList() {
        mAdapter = new BasketSortAdapter(mActivity, mPresenter, this);
        rvSelectedPreparations.setAdapter(mAdapter);
        rvSelectedPreparations.setHasFixedSize(true);
        rvSelectedPreparations.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rvSelectedPreparations);
    }

    private void openBookingFragment() {
        if (mActivity == null) return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.openBookingBeautyFragment(fragmentManager, R.id.bookingBeautyContainer);
    }

    private class EditDisabledTimer extends CountDownTimer {

        public EditDisabledTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {
            mMenuItemEdit.setEnabled(true);
        }
    }
}
