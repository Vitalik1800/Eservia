package com.eservia.booking.ui.delivery.resto.basket;

import android.annotation.SuppressLint;
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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.common.view.CommonPlaceHolder;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.ui.delivery.resto.address.DeliveryAddressMode;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.DishUtil;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.CommonAlert;
import com.eservia.utils.KeyboardUtil;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class DeliveryBasketFragment extends BaseHomeFragment implements DeliveryBasketView {

    public static final String TAG = "delivery_resto_basket_fragment";

    CoordinatorLayout fragmentContainer;
    RelativeLayout rlCity, rlAddress;
    Toolbar toolbar;
    RecyclerView rvDeliveryOrders;
    ProgressBar pbProgress;
    CommonPlaceHolder phlPlaceholder;
    RelativeLayout rlDeliveryBasketOrder;
    RelativeLayout rlDeliveryBasketClientInfo;
    RelativeLayout rlCardHolderCommentEditor;
    CardView cvDeliveryBasketOrder;
    CardView cvDeliveryBasketClientInfo;
    CardView cvContainerCommentEditor;
    LinearLayout llTotalPricesList;
    EditText etName;
    EditText etPhone;
    EditText etCity;
    EditText etAddress;
    EditText etHouse;
    EditText etApartment;
    EditText etDoorPhone;
    TextView tvToolbarSubTitle;
    EditText etComment;
    TextView tvApartmentLabel;
    TextView tvDoorPhoneLabel;
    CommonAlert alert;
    Button btnCommonBookingNextButton;
    ImageView ivEditComment;

    @InjectPresenter
    DeliveryBasketPresenter mPresenter;

    private DeliveryOrderAdapter mOrdersAdapter;

    private MenuItem mMenuItemEdit;

    private boolean mEditModeEnabled = false;

    private BaseActivity mActivity;

    public static DeliveryBasketFragment newInstance() {
        return new DeliveryBasketFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_resto_basket, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setLightStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        rvDeliveryOrders = view.findViewById(R.id.rvDeliveryOrders);
        pbProgress = view.findViewById(R.id.pbProgress);
        phlPlaceholder = view.findViewById(R.id.phlPlaceholder);
        rlDeliveryBasketOrder = view.findViewById(R.id.rlDeliveryBasketOrder);
        rlDeliveryBasketClientInfo = view.findViewById(R.id.rlDeliveryBasketClientInfo);
        rlCardHolderCommentEditor = view.findViewById(R.id.rlCardHolderCommentEditor);
        cvDeliveryBasketOrder = view.findViewById(R.id.cvDeliveryBasketOrder);
        cvDeliveryBasketClientInfo = view.findViewById(R.id.cvDeliveryBasketClientInfo);
        cvContainerCommentEditor = view.findViewById(R.id.cvContainerCommentEditor);
        llTotalPricesList = view.findViewById(R.id.llTotalPricesList);
        etName = view.findViewById(R.id.etName);
        etPhone = view.findViewById(R.id.etPhone);
        etCity = view.findViewById(R.id.etCity);
        etAddress = view.findViewById(R.id.etAddress);
        etHouse = view.findViewById(R.id.etHouse);
        etApartment = view.findViewById(R.id.etApartment);
        etDoorPhone = view.findViewById(R.id.etDoorPhone);
        tvToolbarSubTitle = view.findViewById(R.id.tvToolbarSubTitle);
        etComment = view.findViewById(R.id.etComment);
        tvApartmentLabel = view.findViewById(R.id.tvApartmentLabel);
        tvDoorPhoneLabel = view.findViewById(R.id.tvDoorPhoneLabel);
        alert = view.findViewById(R.id.alert);
        btnCommonBookingNextButton = view.findViewById(R.id.btnCommonBookingNextButton);
        btnCommonBookingNextButton.setOnClickListener(v -> onAcceptClick());
        rlCity = view.findViewById(R.id.rlCity);
        rlCity.setOnClickListener(v -> onCityClick());
        rlAddress = view.findViewById(R.id.rlAddress);
        rlAddress.setOnClickListener(v -> onAddressClick());
        etCity.setOnClickListener(v -> onCityFormClick());
        etAddress.setOnClickListener(v -> onAddresssFormClick());
        ivEditComment = view.findViewById(R.id.ivEditComment);
        ivEditComment.setOnClickListener(v -> onEditCommentClicked());
        initViews();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_change_dark, menu);
        mMenuItemEdit = menu.findItem(R.id.item_change);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        revalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            mPresenter.onBackClicked();
            return true;
        } else if (id == R.id.item_change) {
            mMenuItemEdit.setEnabled(false);
            mPresenter.editModeEnabled(!mEditModeEnabled);
            new EditDisabledTimer(500, 500).start();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void showProgress() {
        phlPlaceholder.setVisibility(View.GONE);
        pbProgress.setVisibility(View.VISIBLE);
        btnCommonBookingNextButton.setClickable(false);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
        phlPlaceholder.setVisibility(View.VISIBLE);
        btnCommonBookingNextButton.setClickable(true);
    }

    public void onAcceptClick() {
        mPresenter.onAcceptClick(getText(etName), getText(etPhone), getText(etCity), getText(etAddress),
                getText(etHouse), getText(etApartment), getText(etDoorPhone), getText(etComment));
    }

    public void onCityClick() {
        mPresenter.onCityClick();
    }

    public void onAddressClick() {
        mPresenter.onAddressClick();
    }

    public void onCityFormClick() {
        mPresenter.onCityClick();
    }

    public void onAddresssFormClick() {
        mPresenter.onAddressClick();
    }

    public void onEditCommentClicked() {
        etComment.requestFocus();
        KeyboardUtil.showSoftKeyboard(mActivity);
        ViewUtil.moveCursorToEnd(etComment);
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onOrderItemsLoaded(List<DeliveryOrderAdapterItem> adapterItems) {
        TransitionManager.beginDelayedTransition(rvDeliveryOrders, new Slide(Gravity.LEFT));
        mOrdersAdapter.replaceAll(adapterItems);
        revalidatePlaceHolder();
        revalidateTotalPrice();
        revalidateAccept();
        revalidateOptionsMenu();
    }

    @Override
    public void onOrderItemsLoadingFailed(Throwable error) {
        MessageUtil.showSnackbar(fragmentContainer, error);
    }

    @Override
    public void onDeletingBasketItemFailed(Throwable error) {
        MessageUtil.showSnackbar(fragmentContainer, error);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onOrderItemsUpdated() {
        mOrdersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditMode(boolean enabled) {
        mEditModeEnabled = enabled;
    }

    @Override
    public void bindMinDeliveryTime(int minutes) {
        tvToolbarSubTitle.setVisibility(View.VISIBLE);
        tvToolbarSubTitle.setText(getResources().getString(
                R.string.min_delivery_time_min, minutes));
    }

    @Override
    public void goBack() {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        mActivity.onBackPressed();
    }

    @Override
    public void setUserName(String name) {
        etName.setText(name);
    }

    @Override
    public void setUserPhone(String phone) {
        etPhone.setText(phone);
    }

    @Override
    public void setUserCity(String city) {
        etCity.setText(city);
        if (!city.isEmpty()) {
            etCity.setError(null);
        }
    }

    @Override
    public void setUserAddress(String address) {
        etAddress.setText(address);
        if (!address.isEmpty()) {
            etAddress.setError(null);
        }
    }

    @Override
    public void setUserHouse(String house) {
        etHouse.setText(house);
    }

    @Override
    public void setUserApartment(String apartment) {
        etApartment.setText(apartment);
    }

    @Override
    public void setUserDoorPhoneCode(String doorPhoneCode) {
        etDoorPhone.setText(doorPhoneCode);
    }

    @Override
    public void setUserComment(String comment) {
        etComment.setText(comment);
    }

    @Override
    public void revalidateTotalPrice() {
        revalidateTotalLayout(mOrdersAdapter.getAdapterItems());
    }

    @Override
    public void showCitySelectPage() {
        if (mActivity == null) return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.showRestoDeliveryAddressFragment(fragmentManager, R.id.deliveryRestoContainer,
                DeliveryAddressMode.SETTLEMENT);
    }

    @Override
    public void showStreetSelectPage() {
        if (mActivity == null) return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.showRestoDeliveryAddressFragment(fragmentManager, R.id.deliveryRestoContainer,
                DeliveryAddressMode.STREET);
    }

    @Override
    public void showNotFilledUserInfoError() {
        alert.setMessageText(mActivity.getResources().getString(R.string.fill_in_the_fields_with_your_data));
        alert.setType(CommonAlert.Type.WARNING);
        alert.show();
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
    public void openDeliveryTimePage() {
        if (mActivity == null) return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.showRestoDeliveryDateFragment(fragmentManager, R.id.deliveryRestoContainer);
    }

    @Override
    public void showEmptyHouseError() {
        etHouse.setError(emptyError());
        etHouse.requestFocus();
    }

    @Override
    public void showEmptyPhoneError() {
        etPhone.setError(emptyError());
        etPhone.requestFocus();
    }

    @Override
    public void showEmptyCityError() {
        etCity.setError(emptyError());
        etCity.requestFocus();
    }

    @Override
    public void showEmptyAddressError() {
        etAddress.setError(emptyError());
        etAddress.requestFocus();
    }

    @Override
    public void showEmptyNameError() {
        etName.setError(emptyError());
        etName.requestFocus();
    }

    private String emptyError() {
        return mActivity.getResources().getString(R.string.empty_field_error);
    }

    private void revalidateTotalLayout(List<DeliveryOrderAdapterItem> orderItems) {
        llTotalPricesList.removeAllViews();
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mActivity).inflate(R.layout.item_currency_price, null);
        TextView tvPrice = view.findViewById(R.id.tvFilledPreparationsTotalCurrency);
        tvPrice.setText(getFormattedTotalPrice(orderItems));
        llTotalPricesList.addView(view);
    }

    private String getFormattedTotalPrice(List<DeliveryOrderAdapterItem> orderItems) {
        double totalPrice = 0.0;
        for (DeliveryOrderAdapterItem item : orderItems) {
            totalPrice += item.getOrderRestoItem().getPriceByPortion()
                    * item.getOrderRestoItem().getAmount();
        }
        return DishUtil.INSTANCE.getFormattedPrice(mActivity, totalPrice);
    }

    private void revalidateOptionsMenu() {
        if (mMenuItemEdit == null) return;

        if (mEditModeEnabled) {
            mMenuItemEdit.setTitle(getResources().getString(R.string.complete));
        } else {
            mMenuItemEdit.setTitle(getResources().getString(R.string.change));
        }
        mMenuItemEdit.setVisible(mEditModeEnabled || mOrdersAdapter.getItemCount() > 0);
    }

    private void revalidateAccept() {
        btnCommonBookingNextButton.setVisibility(mOrdersAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
    }

    private void revalidatePlaceHolder() {
        boolean ordersEmpty = mOrdersAdapter.getItemCount() == 0;
        if (ordersEmpty) {
            rlDeliveryBasketOrder.setVisibility(View.GONE);
            rlDeliveryBasketClientInfo.setVisibility(View.GONE);
            rlCardHolderCommentEditor.setVisibility(View.GONE);
        } else {
            rlDeliveryBasketOrder.setVisibility(View.VISIBLE);
            rlDeliveryBasketClientInfo.setVisibility(View.VISIBLE);
            rlCardHolderCommentEditor.setVisibility(View.VISIBLE);
        }
        phlPlaceholder.setState(ordersEmpty ? CommonPlaceHolder.STATE_EMPTY
                : CommonPlaceHolder.STATE_HIDE);
    }

    private void initViews() {
        initToolbar();
        setOutlineProviders();
        initEditText();
        initProgress();
        initList();
        initAccept();
        initComment();
        formatTitleAsOptional(tvApartmentLabel);
        formatTitleAsOptional(tvDoorPhoneLabel);
    }

    private void formatTitleAsOptional(TextView textView) {
        String optional = mActivity.getResources().getString(R.string.optional);
        textView.setText(String.format("%s (%s)", textView.getText().toString(), optional));
    }

    private void initAccept() {
        btnCommonBookingNextButton.setBackground(ContextCompat.getDrawable(mActivity,
                R.drawable.background_common_button_red_light));
    }

    private void initToolbar() {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");
        mActivity.getSupportActionBar().setElevation(0);
    }

    private void setOutlineProviders() {
        ViewUtil.setCardOutlineProvider(mActivity, rlDeliveryBasketOrder,
                cvDeliveryBasketOrder);
        ViewUtil.setCardOutlineProvider(mActivity, rlDeliveryBasketClientInfo,
                cvDeliveryBasketClientInfo);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderCommentEditor,
                cvContainerCommentEditor);
    }

    private void initComment() {
        etComment.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                etComment, new View[]{etComment}));

        etComment.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                ValidatorUtil.MAX_BOOKING_COMMENT_LENGTH)});

        ViewUtil.setOnTouchListenerForVerticalScroll(etComment);
    }

    private void initEditText() {
        etApartment.getViewTreeObserver().addOnGlobalLayoutListener(
                new ClearFocusLayoutListener(etApartment,
                        new View[]{etName, etPhone, etCity, etAddress, etHouse, etApartment, etDoorPhone}));

        etName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etPhone.requestFocus();
                ViewUtil.moveCursorToEnd(etPhone);
                return true;
            }
            return false;
        });
        etPhone.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etHouse.requestFocus();
                ViewUtil.moveCursorToEnd(etHouse);
                return true;
            }
            return false;
        });
        etHouse.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etApartment.requestFocus();
                ViewUtil.moveCursorToEnd(etApartment);
                return true;
            }
            return false;
        });
        etApartment.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etDoorPhone.requestFocus();
                ViewUtil.moveCursorToEnd(etDoorPhone);
                return true;
            }
            return false;
        });
        etDoorPhone.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                KeyboardUtil.hideSoftKeyboard(mActivity);
                return true;
            }
            return false;
        });
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    private void initList() {
        mOrdersAdapter = new DeliveryOrderAdapter(mActivity, mPresenter);
        rvDeliveryOrders.setAdapter(mOrdersAdapter);
        rvDeliveryOrders.setHasFixedSize(true);
        rvDeliveryOrders.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));
    }

    private void initProgress() {
        ColorUtil.setProgressColor(pbProgress, R.color.colorPrimary);
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
