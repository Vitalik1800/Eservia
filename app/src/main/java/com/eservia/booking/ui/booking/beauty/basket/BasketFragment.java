package com.eservia.booking.ui.booking.beauty.basket;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.common.view.CommonPlaceHolder;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.ui.booking.dialog.AlreadyBookedDialog;
import com.eservia.booking.ui.booking.dialog.BookingErrorDialog;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.SimpleTextWatcher;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.BeautyDiscount;
import com.eservia.model.entity.BeautyService;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyRequest;
import com.eservia.utils.KeyboardUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import moxy.presenter.InjectPresenter;

public class BasketFragment extends BaseHomeFragment implements BasketView {

    public static final String TAG = "basket_fragment_booking_beauty";

    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    RecyclerView rvFilledPreparations;
    RecyclerView rvNotFilledPreparations;
    ProgressBar pbProgress;
    CommonPlaceHolder mPlaceHolder;
    TextView tvToolbarSubTitle;
    Button btnCommonBookingButton;
    NestedScrollView nsvContentHolder;
    LinearLayout llContent;
    RelativeLayout rlCardHolderFilledPreparations;
    RelativeLayout rlCardHolderNotFilledPreparations;
    RelativeLayout rlCardHolderCommentEditor;
    CardView cvFilledPreparations;
    CardView cvNotFilledPreparations;
    CardView cvContainerCommentEditor;
    RelativeLayout rlFilledPreparationsTotal;
    LinearLayout llFilledPreparationsTotalPricesList;
    EditText etComment;
    ImageView ivEditComment;

    @InjectPresenter
    BasketPresenter mPresenter;

    private BaseActivity mActivity;

    private FilledPreparationsAdapter mFilledPreparationsAdapter;

    private UnFilledPreparationsAdapter mUnFilledPreparationsAdapter;

    private MenuItem mMenuItemEdit;

    private WarningUnfilledSheetDialog mWarningUnfilledDialog;

    private boolean mEditModeEnabled = false;

    public static BasketFragment newInstance() {
        return new BasketFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_beauty_basket, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setLightStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        rvFilledPreparations = view.findViewById(R.id.rvFilledPreparations);
        rvNotFilledPreparations = view.findViewById(R.id.rvNotFilledPreparations);
        pbProgress = view.findViewById(R.id.pbProgress);
        mPlaceHolder = view.findViewById(R.id.phlPlaceholder);
        tvToolbarSubTitle = view.findViewById(R.id.tvToolbarSubTitle);
        btnCommonBookingButton = view.findViewById(R.id.btnCommonBookingButton);
        nsvContentHolder = view.findViewById(R.id.nsvContentHolder);
        llContent = view.findViewById(R.id.llContent);
        rlCardHolderFilledPreparations = view.findViewById(R.id.rlCardHolderFilledPreparations);
        rlCardHolderNotFilledPreparations = view.findViewById(R.id.rlCardHolderNotFilledPreparations);
        rlCardHolderCommentEditor = view.findViewById(R.id.rlCardHolderCommentEditor);
        cvFilledPreparations = view.findViewById(R.id.cvFilledPreparations);
        cvNotFilledPreparations = view.findViewById(R.id.cvNotFilledPreparations);
        cvContainerCommentEditor = view.findViewById(R.id.cvContainerCommentEditor);
        rlFilledPreparationsTotal = view.findViewById(R.id.rlFilledPreparationsTotal);
        llFilledPreparationsTotalPricesList = view.findViewById(R.id.llFilledPreparationsTotalPricesList);
        etComment = view.findViewById(R.id.etComment);
        btnCommonBookingButton.setOnClickListener(v -> onAcceptClick());
        ivEditComment = view.findViewById(R.id.ivEditComment);
        ivEditComment.setOnClickListener(v -> onEditCommentClicked());
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onDestroy() {
        hideWarningUnfilledDialog();
        super.onDestroy();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                KeyboardUtil.hideSoftKeyboard(mActivity);
                mActivity.onBackPressed();
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

    public void onAcceptClick() {
        mPresenter.onAcceptClick();
    }

    public void onEditCommentClicked() {
        etComment.requestFocus();
        KeyboardUtil.showSoftKeyboard(mActivity);
        ViewUtil.moveCursorToEnd(etComment);
    }

    @Override
    public void showProgress() {
        mPlaceHolder.setVisibility(View.GONE);
        pbProgress.setVisibility(View.VISIBLE);
        btnCommonBookingButton.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
        mPlaceHolder.setVisibility(View.VISIBLE);
        btnCommonBookingButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showThankYouFragment() {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.popAllBackStack(fragmentManager);
        openThankYouFragment();
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
    public void onFilledPreparations(List<FilledPreparationListItem> items) {
        TransitionManager.beginDelayedTransition(rvFilledPreparations, new Slide(Gravity.LEFT));
        mFilledPreparationsAdapter.replaceAll(items);
        revalidatePlaceHolder();
        revalidateFilledTotalLayout();
        revalidateAccept();
        revalidateOptionsMenu();
    }

    @Override
    public void onUnFilledPreparations(List<UnFilledPreparationItem> items) {
        mUnFilledPreparationsAdapter.replaceAll(items);
        revalidatePlaceHolder();
    }

    @Override
    public void onEditMode(boolean enabled) {
        mEditModeEnabled = enabled;
    }

    @Override
    public void onCreateBookingSuccess(Preparation p) {
    }

    @Override
    public void onCreateBookingFailed(Throwable throwable, Preparation p) {
        openBookingErrorDialog();
    }

    @Override
    public void onCreateBookingFailedAlreadyBooked(Throwable throwable, Preparation p) {
        openAlreadyBookedDialog();
    }

    @Override
    public void onCreateMassiveBookingSuccess(List<CreateBookingBeautyRequest> requestList) {
    }

    @Override
    public void onCreateMassiveBookingFailed(Throwable throwable,
                                             List<CreateBookingBeautyRequest> requestList) {
        openBookingErrorDialog();
    }

    @Override
    public void showWarningUnfilledDialog(int filledCount, int unFilledCount) {
        openWarningUnfilledDialog(filledCount, unFilledCount);
    }

    @Override
    public void hideWarningUnfilledDialog() {
        if (mWarningUnfilledDialog != null) {
            mWarningUnfilledDialog.dismiss();
        }
    }

    @Override
    public void goBack() {
        mActivity.onBackPressed();
    }

    @Override
    public void setComment(String comment) {
        etComment.setText(comment);
    }

    private void revalidateFilledTotalLayout() {
        Map<String, Float> currencyAndPriceMap = currencyAndPriceMap(
                mFilledPreparationsAdapter.getAdapterItems());
        if (currencyAndPriceMap.size() > 0) {
            rlFilledPreparationsTotal.setVisibility(View.VISIBLE);
            llFilledPreparationsTotalPricesList.removeAllViews();
            for (Map.Entry<String, Float> entry : currencyAndPriceMap.entrySet()) {
                String currency = entry.getKey();
                Float totalPrice = entry.getValue();
                @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.item_currency_price, null);
                TextView tvCurrency = view.findViewById(R.id.tvFilledPreparationsTotalCurrency);
                TextView tvPrice = view.findViewById(R.id.tvFilledPreparationsTotalPrice);
                tvCurrency.setText(currency);
                tvPrice.setText(BookingUtil.formatPrice(totalPrice));
                llFilledPreparationsTotalPricesList.addView(view);
            }
        } else {
            rlFilledPreparationsTotal.setVisibility(View.GONE);
        }
    }

    private Map<String, Float> currencyAndPriceMap(List<FilledPreparationListItem> items) {
        Map<String, Float> result = new TreeMap<>();
        for (FilledPreparationListItem item : items) {
            if (item instanceof FilledPreparationItem) {
                BeautyService service = ((FilledPreparationItem) item).getPreparation().getService();
                Float price = service.getPrice();
                String currency = service.getCurrency();
                if (BookingUtil.servicePriceIsEmpty(price)
                        || BookingUtil.serviceCurrencyIsEmpty(currency)) {
                    continue;
                }
                BeautyDiscount discount = ((FilledPreparationItem) item).getPreparation().getDiscount();
                if (discount != null) {
                    price = discount.getPriceValue();
                }
                Float exTotalPrice = result.get(currency);
                if (exTotalPrice == null) {
                    result.put(currency, price);
                } else {
                    result.put(currency, exTotalPrice + price);
                }
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
        btnCommonBookingButton.setVisibility(mFilledPreparationsAdapter.getItemCount() > 0
                ? View.VISIBLE : View.GONE);
    }

    private void revalidatePlaceHolder() {
        boolean filledEmpty = mFilledPreparationsAdapter.getItemCount() == 0;
        boolean unFilledEmpty = mUnFilledPreparationsAdapter.getItemCount() == 0;

        if (filledEmpty) {
            rlCardHolderFilledPreparations.setVisibility(View.GONE);
            rlCardHolderCommentEditor.setVisibility(View.GONE);
        } else {
            rlCardHolderFilledPreparations.setVisibility(View.VISIBLE);
            rlCardHolderCommentEditor.setVisibility(View.VISIBLE);
        }

        if (unFilledEmpty) {
            rlCardHolderNotFilledPreparations.setVisibility(View.GONE);
        } else {
            rlCardHolderNotFilledPreparations.setVisibility(View.VISIBLE);
        }

        mPlaceHolder.setState(filledEmpty && unFilledEmpty ?
                CommonPlaceHolder.STATE_EMPTY : CommonPlaceHolder.STATE_HIDE);
    }

    private void initViews() {
        initToolbar();
        setOutlineProviders();
        initComment();
        initSwipeRefresh();
        initList();
    }

    private void initToolbar() {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");
        mActivity.getSupportActionBar().setElevation(0);
    }

    private void setOutlineProviders() {
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderFilledPreparations,
                cvFilledPreparations);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderNotFilledPreparations,
                cvNotFilledPreparations);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderCommentEditor,
                cvContainerCommentEditor);
    }

    private void initComment() {
        etComment.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                etComment, new View[]{etComment}));

        etComment.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                ValidatorUtil.MAX_BOOKING_COMMENT_LENGTH)});

        ViewUtil.setOnTouchListenerForVerticalScroll(etComment);

        etComment.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void textChanged(String s) {
                mPresenter.onCommentChanged(s);
            }
        });
    }

    private void initSwipeRefresh() {
        ColorUtil.setProgressColor(pbProgress, R.color.colorPrimary);
    }

    private void initList() {
        mFilledPreparationsAdapter = new FilledPreparationsAdapter(mActivity, mPresenter);
        rvFilledPreparations.setAdapter(mFilledPreparationsAdapter);
        rvFilledPreparations.setHasFixedSize(true);
        rvFilledPreparations.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));

        mUnFilledPreparationsAdapter = new UnFilledPreparationsAdapter(mActivity, mPresenter);
        rvNotFilledPreparations.setAdapter(mUnFilledPreparationsAdapter);
        rvNotFilledPreparations.setHasFixedSize(true);
        rvNotFilledPreparations.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));
    }

    private void openWarningUnfilledDialog(int filledCount, int unFilledCount) {
        mWarningUnfilledDialog = WarningUnfilledSheetDialog.newInstance();
        mWarningUnfilledDialog.setListener(mPresenter);
        mWarningUnfilledDialog.setPreparationsCount(filledCount, unFilledCount);
        mWarningUnfilledDialog.show(mActivity.getSupportFragmentManager(),
                WarningUnfilledSheetDialog.class.getSimpleName());
    }

    private void openThankYouFragment() {
        if (mActivity == null) return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.openBookingBeautyThankYouFragment(fragmentManager, R.id.bookingBeautyContainer);
    }

    private void openBookingErrorDialog() {
        if (mActivity == null) return;
        FragmentManager fm = mActivity.getSupportFragmentManager();
        BookingErrorDialog.newInstance().show(fm, BookingErrorDialog.class.getSimpleName());
    }

    private void openAlreadyBookedDialog() {
        if (mActivity == null) return;
        FragmentManager fm = mActivity.getSupportFragmentManager();
        AlreadyBookedDialog.newInstance().show(fm, AlreadyBookedDialog.class.getSimpleName());
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
