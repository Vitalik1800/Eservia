package com.eservia.booking.ui.booking.beauty.booking;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.PlaceHolderLayout;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.ui.booking.beauty.basket.BasketFragment;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.ServerErrorUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.BeautyDiscount;
import com.eservia.model.entity.BeautyService;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.overscroll.OverScrollDecoratorHelper;
import com.eservia.simplecalendar.Appearance;
import com.eservia.simplecalendar.SimpleCalendar;
import com.eservia.utils.KeyboardUtil;
import com.eservia.utils.StringUtil;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import moxy.presenter.InjectPresenter;

public class BookingFragment extends BaseHomeFragment implements BookingView {

    public static final String TAG = "booking_beauty_fragment";

    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    RecyclerView rvPreparedServices;
    ProgressBar pbProgress;
    PlaceHolderLayout mPlaceHolderLayout;
    TextView tvToolbarSubTitle;
    ConstraintLayout clHasStaffContainer;
    RelativeLayout rlAutoSelectedStaffs;
    RelativeLayout rlStaffLoadingPlaceHolder;
    RelativeLayout rlStaffLoadingErrorPlaceHolder;
    RelativeLayout rlHasNotAnyStaffPlaceHolder;
    TextView tvStaffLoadingErrorPlaceHolderMessage;
    RelativeLayout rlStaffDescription;
    TextView tvServiceStaffCurrency;
    TextView tvServiceStaffPrice;
    View serviceStaffPriceSeparator;
    TextView tvSelectedStaffName;
    TextView tvSelectedStaffPosition;
    TextView tvSelectedStaffDescription;
    RecyclerView rvStaff;
    RecyclerView rvAutoSelectedStaffs;
    RecyclerView rvTimeSlot;
    SwitchCompat swPriority;
    TextView tvPriorityMaster;
    TextView tvPriorityDateTime;
    LinearLayout llBookingContentPage;
    CardView cvChooseStaffContainer;
    CardView cvChooseDateTimeContainer;
    RelativeLayout rlCardHolderChooseStaff;
    RelativeLayout rlCardHolderChooseDateTime;
    RelativeLayout rlDayAndTimeSelectorContainer;
    RelativeLayout rlDateTimeLoadingPlaceHolder;
    RelativeLayout rlDateTimeLoadingErrorPlaceHolder;
    TextView tvDateTimeLoadingErrorPlaceHolderMessage;
    RelativeLayout rlTimeSelectorContainer;
    RelativeLayout rlTimeSlotLoadingPlaceHolder;
    Button btnTimeSlotLoadingErrorPlaceHolderButton;
    TextView tvTimeSlotLoadingErrorPlaceHolderMessage;
    RelativeLayout rlTimeSlotLoadingErrorPlaceHolder;
    TextView tvHasNotAnyTimeSlotPlaceHolderMessage;
    RelativeLayout rlHasNotAnyTimeSlotPlaceHolder;
    Button btnCommonBookingNextButton;
    NestedScrollView nsvContentHolder;
    SimpleCalendar scCalendar;
    TextView tvExpandCalendar;
    RelativeLayout rlMark;
    ImageView ivMark;
    TextView tvMark;
    TextView tvServiceStaffCurrencyCrossed;
    TextView tvServiceStaffPriceCrossed;
    Button btnStaffLoadingErrorPlaceHolderButton;
    Button btnDateTimeLoadingErrorPlaceHolderButton;


    @InjectPresenter
    BookingPresenter mPresenter;

    private BaseActivity mActivity;

    private PreparedServicesAdapter mPreparedServicesAdapter;

    private StaffAdapter mStaffAdapter;

    private AutoSelectedStaffAdapter mAutoSelectedStaffAdapter;

    private TimeSlotAdapter mTimeSlotAdapter;

    private Priority mPriority = Priority.STAFF;

    @ColorInt
    private int mCurrentColorId;

    private MenuItem mMenuItemBasket;

    public static BookingFragment newInstance() {
        return new BookingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_beauty, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setWhiteStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        rvPreparedServices = view.findViewById(R.id.rvPreparedServices);
        pbProgress = view.findViewById(R.id.pbProgress);
        mPlaceHolderLayout = view.findViewById(R.id.phlPlaceholder);
        tvToolbarSubTitle = view.findViewById(R.id.tvToolbarSubTitle);
        clHasStaffContainer = view.findViewById(R.id.clHasStaffContainer);
        rlAutoSelectedStaffs = view.findViewById(R.id.rlAutoSelectedStaffs);
        rlStaffLoadingPlaceHolder = view.findViewById(R.id.rlStaffLoadingPlaceHolder);
        rlStaffLoadingErrorPlaceHolder = view.findViewById(R.id.rlStaffLoadingErrorPlaceHolder);
        rlHasNotAnyStaffPlaceHolder = view.findViewById(R.id.rlHasNotAnyStaffPlaceHolder);
        tvStaffLoadingErrorPlaceHolderMessage = view.findViewById(R.id.tvStaffLoadingErrorPlaceHolderMessage);
        rlStaffDescription = view.findViewById(R.id.rlStaffDescription);
        tvServiceStaffCurrency = view.findViewById(R.id.tvServiceStaffCurrency);
        tvServiceStaffPrice = view.findViewById(R.id.tvServiceStaffPrice);
        serviceStaffPriceSeparator = view.findViewById(R.id.serviceStaffPriceSeparator);
        tvSelectedStaffName = view.findViewById(R.id.tvSelectedStaffName);
        tvSelectedStaffPosition = view.findViewById(R.id.tvSelectedStaffPosition);
        tvSelectedStaffDescription = view.findViewById(R.id.tvSelectedStaffDescription);
        rvStaff = view.findViewById(R.id.rvStaff);
        rvAutoSelectedStaffs = view.findViewById(R.id.rvAutoSelectedStaffs);
        rvTimeSlot = view.findViewById(R.id.rvTimeSlot);
        swPriority = view.findViewById(R.id.swPriority);
        tvPriorityMaster = view.findViewById(R.id.tvPriorityMaster);
        tvPriorityDateTime = view.findViewById(R.id.tvPriorityDateTime);
        llBookingContentPage = view.findViewById(R.id.llBookingContentPage);
        cvChooseStaffContainer = view.findViewById(R.id.cvChooseStaffContainer);
        cvChooseDateTimeContainer = view.findViewById(R.id.cvChooseDateTimeContainer);
        rlCardHolderChooseStaff = view.findViewById(R.id.rlCardHolderChooseStaff);
        rlCardHolderChooseDateTime = view.findViewById(R.id.rlCardHolderChooseDateTime);
        rlDayAndTimeSelectorContainer = view.findViewById(R.id.rlDayAndTimeSelectorContainer);
        rlDateTimeLoadingPlaceHolder = view.findViewById(R.id.rlDateTimeLoadingPlaceHolder);
        rlDateTimeLoadingErrorPlaceHolder = view.findViewById(R.id.rlDateTimeLoadingErrorPlaceHolder);
        tvDateTimeLoadingErrorPlaceHolderMessage = view.findViewById(R.id.tvDateTimeLoadingErrorPlaceHolderMessage);
        rlTimeSelectorContainer = view.findViewById(R.id.rlTimeSelectorContainer);
        rlTimeSlotLoadingPlaceHolder = view.findViewById(R.id.rlTimeSlotLoadingPlaceHolder);
        btnTimeSlotLoadingErrorPlaceHolderButton = view.findViewById(R.id.btnTimeSlotLoadingErrorPlaceHolderButton);
        tvTimeSlotLoadingErrorPlaceHolderMessage = view.findViewById(R.id.tvTimeSlotLoadingErrorPlaceHolderMessage);
        rlTimeSlotLoadingErrorPlaceHolder = view.findViewById(R.id.rlTimeSlotLoadingErrorPlaceHolder);
        tvHasNotAnyTimeSlotPlaceHolderMessage = view.findViewById(R.id.tvHasNotAnyTimeSlotPlaceHolderMessage);
        rlHasNotAnyTimeSlotPlaceHolder = view.findViewById(R.id.rlHasNotAnyTimeSlotPlaceHolder);
        btnCommonBookingNextButton = view.findViewById(R.id.btnCommonBookingNextButton);
        nsvContentHolder = view.findViewById(R.id.nsvContentHolder);
        scCalendar = view.findViewById(R.id.scCalendar);
        tvExpandCalendar = view.findViewById(R.id.tvExpandCalendar);
        rlMark = view.findViewById(R.id.rlMark);
        ivMark = view.findViewById(R.id.ivMark);
        tvMark = view.findViewById(R.id.tvMark);
        tvServiceStaffCurrencyCrossed = view.findViewById(R.id.tvServiceStaffCurrencyCrossed);
        tvServiceStaffPriceCrossed = view.findViewById(R.id.tvServiceStaffPriceCrossed);
        btnCommonBookingNextButton.setOnClickListener(v -> onAcceptClick());
        tvPriorityDateTime.setOnClickListener(v -> onPriorityDateTimeClick());
        tvPriorityMaster.setOnClickListener(v -> onPriorityMasterClick());
        btnStaffLoadingErrorPlaceHolderButton = view.findViewById(R.id.btnStaffLoadingErrorPlaceHolderButton);
        btnStaffLoadingErrorPlaceHolderButton.setOnClickListener(v -> onRefreshStaffClick());
        btnDateTimeLoadingErrorPlaceHolderButton.setOnClickListener(v -> onRefreshDateTimeClick());
        btnTimeSlotLoadingErrorPlaceHolderButton.setOnClickListener(v -> onRefreshTimeSlotClick());
        tvTimeSlotLoadingErrorPlaceHolderMessage.setOnClickListener(v -> onRefreshTimeSlotMessageClick());
        tvExpandCalendar.setOnClickListener(v -> onExpandCalendarClick());
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
                showBasketFragment();
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
        WindowUtils.setWhiteStatusBar(mActivity);
    }

    @Override
    public void willBeHidden() {
    }

    public void onAcceptClick() {
        mPresenter.onAcceptClick();
    }

    public void onPriorityDateTimeClick() {
        if (mPriority.equals(Priority.STAFF)) {
            swPriority.setChecked(true);
        }
    }

    public void onPriorityMasterClick() {
        if (mPriority.equals(Priority.DATE)) {
            swPriority.setChecked(false);
        }
    }

    public void onRefreshStaffClick() {
        mPresenter.refreshStaffList();
    }

    public void onRefreshDateTimeClick() {
        mPresenter.refreshDateTime();
    }

    public void onRefreshTimeSlotClick() {
        mPresenter.refreshTimeSlots();
    }

    public void onRefreshTimeSlotMessageClick() {
        mPresenter.refreshTimeSlots();
    }

    public void onExpandCalendarClick() {
        Appearance newAppearance = scCalendar.getAppearance().equals(Appearance.WEEK) ?
                Appearance.MONTH : Appearance.WEEK;
        if (scCalendar.setAppearance(newAppearance)) {
            if (newAppearance.equals(Appearance.WEEK)) {
                mPresenter.onCalendarCollapsed();
            } else if (newAppearance.equals(Appearance.MONTH)) {
                mPresenter.onCalendarExpanded();
            }
        }
    }

    @Override
    public void showProgress() {
        mPlaceHolderLayout.setVisibility(View.GONE);
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
        mPlaceHolderLayout.setVisibility(View.VISIBLE);
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
    public void showBasketFragment() {
        openBasketFragment();
    }

    @Override
    public void performStaffPriority() {
        mPriority = Priority.STAFF;
        refreshPriorityTextColor();
        swapStaffAndDateCards();
        revalidateStaffSelectCard(false);
    }

    @Override
    public void performDatePriority(boolean multiServices) {
        mPriority = Priority.DATE;
        refreshPriorityTextColor();
        swapStaffAndDateCards();
        revalidateStaffSelectCard(multiServices);
    }

    @Override
    public void onPreparedServicesLoadingSuccess(List<Preparation> preparations) {
        List<PreparedServicesAdapterItem> adapterItems = mapToServiceAdapterItems(preparations);
        mPreparedServicesAdapter.replaceAll(adapterItems);
        revalidatePlaceHolder();
        revalidateBasketBadgeCount();
    }

    @Override
    public void onPreparedServicesLoadingFailed(Throwable throwable) {
        if (mPreparedServicesAdapter.getItemCount() == 0
                && ServerErrorUtil.isConnectionError(throwable)) {
            mPlaceHolderLayout.showConnectionError();
        } else {
            revalidatePlaceHolder();
        }
        //MessageUtil.showSnackbar(fragmentContainer, throwable);
    }

    @Override
    public void selectFirstPreparedServiceItem() {
        if (mPreparedServicesAdapter.getAdapterItems().size() == 0) return;
        mPresenter.onPreparedServiceItemClick(mPreparedServicesAdapter.getAdapterItems().get(0));
    }

    @Override
    public void selectPreparationWithId(String id) {
        for (int i = 0; i < mPreparedServicesAdapter.getItemCount(); i++) {
            PreparedServicesAdapterItem item = mPreparedServicesAdapter.getItem(i);
            if (item.getPreparation().getId().equals(id)) {
                mPresenter.onPreparedServiceItemClick(item);
                final int pos = i;
                rvPreparedServices.post(() -> rvPreparedServices.smoothScrollToPosition(pos));
                break;
            }
        }
    }

    @Override
    public void setSelectedPreparation(Integer serviceId) {
        mPreparedServicesAdapter.setSelected(serviceId);
    }

    @Override
    public void setAllPreparationsSelected() {
        mPreparedServicesAdapter.setAllSelected();
    }

    @Override
    public void revalidatePreparationsFinishedStates() {
        mPreparedServicesAdapter.revalidateFinishedStates();
    }

    @Override
    public void revalidateAcceptLayout(boolean isCurrentPreparationFinished,
                                       boolean isAllPreparationsFinished) {

        boolean shouldScrollDown = btnCommonBookingNextButton.getVisibility() == View.GONE;

        if (isCurrentPreparationFinished && isAllPreparationsFinished) {
            btnCommonBookingNextButton.setVisibility(View.VISIBLE);
            btnCommonBookingNextButton.setText(mActivity.getResources().getString(R.string.all_next));

            if (shouldScrollDown) {
                nsvContentHolder.post(() -> nsvContentHolder.fullScroll(View.FOCUS_DOWN));
            }
        } else if (isCurrentPreparationFinished) {
            btnCommonBookingNextButton.setVisibility(View.VISIBLE);
            btnCommonBookingNextButton.setText(mActivity.getResources().getString(R.string.next_service));

            if (shouldScrollDown) {
                nsvContentHolder.post(() -> nsvContentHolder.fullScroll(View.FOCUS_DOWN));
            }
        } else {
            btnCommonBookingNextButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNewColor(int colorId) {
        mCurrentColorId = colorId;
        tvSelectedStaffName.setTextColor(mCurrentColorId);
        tvSelectedStaffPosition.setTextColor(mCurrentColorId);
        tvServiceStaffPrice.setTextColor(mCurrentColorId);
        tvServiceStaffCurrency.setTextColor(mCurrentColorId);
        refreshPriorityColors();
        mStaffAdapter.setItemColorId(mCurrentColorId);
        mTimeSlotAdapter.setItemColorId(mCurrentColorId);
        scCalendar.setSelectedColor(colorId);
    }

    @Override
    public void refreshSelectedServiceInfo(BeautyService service, @Nullable BeautyDiscount discount) {
        rlStaffDescription.setVisibility(View.VISIBLE);

        Float price = service.getPrice();
        String currency = service.getCurrency();

        if (BookingUtil.servicePriceIsEmpty(price)) {
            tvServiceStaffPrice.setVisibility(View.INVISIBLE);
            tvServiceStaffCurrency.setVisibility(View.INVISIBLE);
            tvServiceStaffCurrencyCrossed.setVisibility(View.INVISIBLE);
            tvServiceStaffPriceCrossed.setVisibility(View.INVISIBLE);
            serviceStaffPriceSeparator.setVisibility(View.INVISIBLE);
            rlMark.setVisibility(View.INVISIBLE);
            return;
        }

        serviceStaffPriceSeparator.setVisibility(View.VISIBLE);
        tvServiceStaffPrice.setVisibility(View.VISIBLE);

        boolean isCurrencyNotEmpty = currency != null && !currency.isEmpty();
        boolean isDiscountNotEmpty = discount != null && discount.getDiscountType() != null;
        boolean isDiscountTypePercent = isDiscountNotEmpty
                && discount.getDiscountType().equals(BeautyDiscount.TYPE_PERCENT);

        if (isDiscountNotEmpty) {
            tvServiceStaffPrice.setText(BookingUtil.formatPrice(discount.getPriceValue()));
        } else {
            tvServiceStaffPrice.setText(BookingUtil.formatPrice(price));
        }

        if (isCurrencyNotEmpty) {
            tvServiceStaffCurrency.setVisibility(View.VISIBLE);
            tvServiceStaffCurrency.setText(currency.toUpperCase());
        } else {
            tvServiceStaffCurrency.setVisibility(View.INVISIBLE);
        }

        if (isDiscountNotEmpty) {
            tvServiceStaffPriceCrossed.setVisibility(View.VISIBLE);
            tvServiceStaffPriceCrossed.setText(BookingUtil.formatPrice(price));
        } else {
            tvServiceStaffPriceCrossed.setVisibility(View.INVISIBLE);
        }

        if (isDiscountNotEmpty && isCurrencyNotEmpty) {
            tvServiceStaffCurrencyCrossed.setVisibility(View.VISIBLE);
            tvServiceStaffCurrencyCrossed.setText(currency.toUpperCase());
        } else {
            tvServiceStaffCurrencyCrossed.setVisibility(View.INVISIBLE);
        }

        if (isDiscountTypePercent) {
            rlMark.setVisibility(View.VISIBLE);
            ivMark.setBackground(ContextCompat.getDrawable(mActivity,
                    R.drawable.background_sale));
            tvMark.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources()
                    .getDimension(R.dimen.mark_promo_text_size));
            tvMark.setText(mActivity.getResources().getString(R.string.sale_mark,
                    discount.getDiscountValue().intValue()));
        } else {
            rlMark.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStaffLoadingSuccess(List<StaffAdapterItem> items, boolean replaceAll,
                                      @ColorInt int currentColor) {
        if (replaceAll) {
            mStaffAdapter.replaceAll(items);
        } else {
            mStaffAdapter.addAll(items);
        }
        mStaffAdapter.setItemColorId(currentColor);
        revalidateStaffPlaceHolder(false, false);
    }

    @Override
    public void onStaffLoadingFailed(Throwable throwable) {
        revalidateStaffPlaceHolder(true, mStaffAdapter.getItemCount() == 0 && ServerErrorUtil.isConnectionError(throwable));
        //MessageUtil.showSnackbar(fragmentContainer, throwable);
    }

    @Override
    public void onAutoSelectedStaffLoadingSuccess(List<AutoSelectedStaffAdapterItem> items) {
        mAutoSelectedStaffAdapter.replaceAll(items);
    }

    @Override
    public void onAutoSelectedStaffLoadingFailed(Throwable throwable) {
        revalidateStaffPlaceHolder(true, mAutoSelectedStaffAdapter.getItemCount() == 0 && ServerErrorUtil.isConnectionError(throwable));
    }

    @Override
    public void showStaffProgress() {
        clHasStaffContainer.setVisibility(View.GONE);
        rlAutoSelectedStaffs.setVisibility(View.GONE);
        rlHasNotAnyStaffPlaceHolder.setVisibility(View.GONE);
        rlStaffLoadingErrorPlaceHolder.setVisibility(View.GONE);
        rlStaffLoadingPlaceHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideStaffProgress() {
        rlStaffLoadingPlaceHolder.setVisibility(View.GONE);
        clHasStaffContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAutoSelectedStaffProgress() {
        clHasStaffContainer.setVisibility(View.GONE);
        rlAutoSelectedStaffs.setVisibility(View.GONE);
        rlHasNotAnyStaffPlaceHolder.setVisibility(View.GONE);
        rlStaffLoadingErrorPlaceHolder.setVisibility(View.GONE);
        rlStaffLoadingPlaceHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAutoSelectedStaffProgress() {
        rlStaffLoadingPlaceHolder.setVisibility(View.GONE);
        rlAutoSelectedStaffs.setVisibility(View.VISIBLE);
    }

    @Override
    public void selectFirstStaffItem() {
        if (mStaffAdapter.getAdapterItems().size() == 0) return;
        mPresenter.onStaffAdapterItemClick(mStaffAdapter.getAdapterItems().get(0));
    }

    @Override
    public void setSelectedStaffItem(Integer staffIf) {
        mStaffAdapter.setSelected(staffIf);
    }

    @Override
    public void refreshSelectedStaffInfo(@Nullable BeautyStaff staff) {
        if (staff != null) {
            rlStaffDescription.setVisibility(View.VISIBLE);
            tvSelectedStaffName.setText(BusinessUtil.getStaffFullName(
                    staff.getFirstName(), staff.getLastName()));
            if (!StringUtil.isEmpty(staff.getPosition())) {
                tvSelectedStaffPosition.setText(staff.getPosition());
            } else {
                tvSelectedStaffPosition.setText("");
            }
            if (!StringUtil.isEmpty(staff.getDescription())) {
                tvSelectedStaffDescription.setText(staff.getDescription());
            } else {
                tvSelectedStaffDescription.setText("");
            }
        } else {
            rlStaffDescription.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideTimeSlotLayout(boolean gone) {
        rlTimeSelectorContainer.setVisibility(gone ? View.GONE : View.INVISIBLE);
    }

    @Override
    public void showDateTimeProgress() {
        rlDayAndTimeSelectorContainer.setVisibility(View.INVISIBLE);
        rlDateTimeLoadingErrorPlaceHolder.setVisibility(View.GONE);
        rlDateTimeLoadingPlaceHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDateTimeProgress() {
        rlDateTimeLoadingPlaceHolder.setVisibility(View.GONE);
        rlDateTimeLoadingErrorPlaceHolder.setVisibility(View.GONE);
        rlDayAndTimeSelectorContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void setSelectedDay(DateTime selectedDay) {
        scCalendar.selectDay(selectedDay);
    }

    @Override
    public void workingDaysLoadingError(Throwable t) {
        revalidateDateTimePlaceHolder(true, ServerErrorUtil.isConnectionError(t));
    }

    @Override
    public void initCalendar(List<DateTime> days) {
        scCalendar.initCalendar(days);
    }

    @Override
    public void setVisibleDay(DateTime day) {
        scCalendar.setVisibleDay(day);
    }

    @Override
    public void showTimeSlotsProgress() {
        rlTimeSelectorContainer.setVisibility(View.VISIBLE);
        rvTimeSlot.setVisibility(View.GONE);
        rlHasNotAnyTimeSlotPlaceHolder.setVisibility(View.GONE);
        rlTimeSlotLoadingErrorPlaceHolder.setVisibility(View.GONE);
        rlTimeSlotLoadingPlaceHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTimeSlotsProgress() {
        rvTimeSlot.setVisibility(View.VISIBLE);
        rlHasNotAnyTimeSlotPlaceHolder.setVisibility(View.GONE);
        rlTimeSlotLoadingErrorPlaceHolder.setVisibility(View.GONE);
        rlTimeSlotLoadingPlaceHolder.setVisibility(View.GONE);
    }

    @Override
    public void onTimeSlotsLoadingSuccess(List<TimeSlotAdapterItem> timeSlotAdapterItems) {
        mTimeSlotAdapter.setItemColorId(mCurrentColorId);
        mTimeSlotAdapter.replaceAll(timeSlotAdapterItems);
        revalidateTimeSlotPlaceHolder(false, false);
    }

    @Override
    public void onTimeSlotsLoadingError(Throwable throwable) {
        revalidateTimeSlotPlaceHolder(true, ServerErrorUtil.isConnectionError(throwable));
    }

    @Override
    public void selectFirstTimeSlotItem() {
        if (mTimeSlotAdapter.getAdapterItems().size() == 0) return;
        mPresenter.onTimeSlotItemClick(mTimeSlotAdapter.getAdapterItems().get(0));
    }

    @Override
    public void setSelectedTimeSlotItem(DateTime time) {
        mTimeSlotAdapter.setSelected(time);
    }

    @Override
    public void setCollapseCalendarTitle() {
        tvExpandCalendar.setText(mActivity.getResources().getString(R.string.collapse_calendar));
    }

    @Override
    public void setExpandCalendarTitle() {
        tvExpandCalendar.setText(mActivity.getResources().getString(R.string.expand_calendar));
    }

    private void revalidatePlaceHolder() {
        mPlaceHolderLayout.setEmpty(mPreparedServicesAdapter.getItemCount() == 0);
    }

    private void revalidateStaffSelectCard(boolean autoSelect) {
        if (autoSelect) {
            clHasStaffContainer.setVisibility(View.GONE);
            rlAutoSelectedStaffs.setVisibility(View.VISIBLE);
        } else {
            rlAutoSelectedStaffs.setVisibility(View.GONE);
            clHasStaffContainer.setVisibility(View.VISIBLE);
        }
    }

    private void revalidateStaffPlaceHolder(boolean error, boolean connectionError) {
        if (mStaffAdapter.getItemCount() == 0 && error && connectionError) {
            showStaffListConnectionErrorPlaceHolder();
        } else if (mStaffAdapter.getItemCount() == 0 && error) {
            showStaffListErrorPlaceHolder();
        } else if (mStaffAdapter.getItemCount() == 0) {
            showEmptyStaffListPlaceHolder();
        } else {
            showStaffList();
        }
    }

    private void revalidateDateTimePlaceHolder(boolean error, boolean connectionError) {
        if (error && connectionError) {
            showDateTimeConnectionErrorPlaceHolder();
        } else if (error) {
            showDateTimeErrorPlaceHolder();
        } else {
            showDateTime();
        }
    }

    private void revalidateTimeSlotPlaceHolder(boolean error, boolean connectionError) {
        if (error && connectionError) {
            showTimeSlotConnectionErrorPlaceHolder();
        } else if (error) {
            showTimeSlotErrorPlaceHolder();
        } else if (mTimeSlotAdapter.getItemCount() == 0) {
            showEmptyTimeSlotListPlaceHolder();
        } else {
            showTimeSlotList();
        }
    }

    private void showDateTime() {
        rlDateTimeLoadingErrorPlaceHolder.setVisibility(View.GONE);
        rlDateTimeLoadingPlaceHolder.setVisibility(View.GONE);
        rlDayAndTimeSelectorContainer.setVisibility(View.VISIBLE);
    }

    private void showDateTimeConnectionErrorPlaceHolder() {
        rlDayAndTimeSelectorContainer.setVisibility(View.INVISIBLE);
        rlDateTimeLoadingErrorPlaceHolder.setVisibility(View.VISIBLE);
        rlDateTimeLoadingPlaceHolder.setVisibility(View.GONE);
        tvDateTimeLoadingErrorPlaceHolderMessage.setText(
                mActivity.getResources().getString(R.string.error_no_internet));
    }

    private void showDateTimeErrorPlaceHolder() {
        rlDayAndTimeSelectorContainer.setVisibility(View.INVISIBLE);
        rlDateTimeLoadingErrorPlaceHolder.setVisibility(View.VISIBLE);
        rlDateTimeLoadingPlaceHolder.setVisibility(View.GONE);
        tvDateTimeLoadingErrorPlaceHolderMessage.setText(
                mActivity.getResources().getString(R.string.unknown_error));
    }

    private void showStaffList() {
        rlHasNotAnyStaffPlaceHolder.setVisibility(View.GONE);
        rlStaffLoadingErrorPlaceHolder.setVisibility(View.GONE);
        rlStaffLoadingPlaceHolder.setVisibility(View.GONE);
        clHasStaffContainer.setVisibility(View.VISIBLE);
    }

    private void showEmptyStaffListPlaceHolder() {
        rlHasNotAnyStaffPlaceHolder.setVisibility(View.VISIBLE);
        rlStaffLoadingErrorPlaceHolder.setVisibility(View.GONE);
        rlStaffLoadingPlaceHolder.setVisibility(View.GONE);
        clHasStaffContainer.setVisibility(View.GONE);
    }

    private void showStaffListErrorPlaceHolder() {
        rlHasNotAnyStaffPlaceHolder.setVisibility(View.GONE);
        rlStaffLoadingErrorPlaceHolder.setVisibility(View.VISIBLE);
        rlStaffLoadingPlaceHolder.setVisibility(View.GONE);
        clHasStaffContainer.setVisibility(View.GONE);
        tvStaffLoadingErrorPlaceHolderMessage.setText(
                mActivity.getResources().getString(R.string.unknown_error));
    }

    private void showStaffListConnectionErrorPlaceHolder() {
        rlHasNotAnyStaffPlaceHolder.setVisibility(View.GONE);
        rlStaffLoadingErrorPlaceHolder.setVisibility(View.VISIBLE);
        rlStaffLoadingPlaceHolder.setVisibility(View.GONE);
        clHasStaffContainer.setVisibility(View.GONE);
        tvStaffLoadingErrorPlaceHolderMessage.setText(
                mActivity.getResources().getString(R.string.error_no_internet));
    }

    private void showTimeSlotConnectionErrorPlaceHolder() {
        rvTimeSlot.setVisibility(View.GONE);
        rlHasNotAnyTimeSlotPlaceHolder.setVisibility(View.GONE);
        rlTimeSlotLoadingErrorPlaceHolder.setVisibility(View.VISIBLE);
        rlTimeSlotLoadingPlaceHolder.setVisibility(View.GONE);
        tvTimeSlotLoadingErrorPlaceHolderMessage.setText(
                mActivity.getResources().getString(R.string.error_no_internet));
    }

    private void showTimeSlotErrorPlaceHolder() {
        rvTimeSlot.setVisibility(View.GONE);
        rlHasNotAnyTimeSlotPlaceHolder.setVisibility(View.GONE);
        rlTimeSlotLoadingErrorPlaceHolder.setVisibility(View.VISIBLE);
        rlTimeSlotLoadingPlaceHolder.setVisibility(View.GONE);
        tvTimeSlotLoadingErrorPlaceHolderMessage.setText(
                mActivity.getResources().getString(R.string.unknown_error));
    }

    private void showEmptyTimeSlotListPlaceHolder() {
        rvTimeSlot.setVisibility(View.GONE);
        rlHasNotAnyTimeSlotPlaceHolder.setVisibility(View.VISIBLE);
        rlTimeSlotLoadingErrorPlaceHolder.setVisibility(View.GONE);
        rlTimeSlotLoadingPlaceHolder.setVisibility(View.GONE);
    }

    private void showTimeSlotList() {
        rvTimeSlot.setVisibility(View.VISIBLE);
        rlHasNotAnyTimeSlotPlaceHolder.setVisibility(View.GONE);
        rlTimeSlotLoadingErrorPlaceHolder.setVisibility(View.GONE);
        rlTimeSlotLoadingPlaceHolder.setVisibility(View.GONE);
    }

    private List<PreparedServicesAdapterItem> mapToServiceAdapterItems(
            List<Preparation> preparations) {

        List<PreparedServicesAdapterItem> adapterItems = new ArrayList<>();
        for (Preparation p : preparations) {
            PreparedServicesAdapterItem item = new PreparedServicesAdapterItem(p);
            item.setFinished(p.isFull());
            adapterItems.add(item);
        }
        return adapterItems;
    }

    private void refreshPriorityColors() {
        refreshPrioritySwitchCompatColor();
        refreshPriorityTextColor();
    }

    private void initPrioritySwitchCompat() {
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked},
        };
        int[] trackColors = getResources().getIntArray(R.array.prepared_booking_switch_track_colors);
        DrawableCompat.setTintList(DrawableCompat.wrap(swPriority.getTrackDrawable()),
                new ColorStateList(states, trackColors));
        swPriority.setOnCheckedChangeListener(new PriorityCheckedListener());
    }

    private void refreshPrioritySwitchCompatColor() {
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked},
        };
        int[] thumbColors = new int[]{mCurrentColorId, mCurrentColorId};
        DrawableCompat.setTintList(DrawableCompat.wrap(swPriority.getThumbDrawable()),
                new ColorStateList(states, thumbColors));
    }

    private void refreshPriorityTextColor() {
        if (mPriority.equals(Priority.DATE)) {
            tvPriorityDateTime.setTextColor(mCurrentColorId);
            tvPriorityMaster.setTextColor(ContextCompat.getColor(mActivity, R.color.colorInactive));
        } else if (mPriority.equals(Priority.STAFF)) {
            tvPriorityDateTime.setTextColor(ContextCompat.getColor(mActivity, R.color.colorInactive));
            tvPriorityMaster.setTextColor(mCurrentColorId);
        }
    }

    private void swapStaffAndDateCards() {
        if (llBookingContentPage.getChildCount() <= 1) return;
        View view = llBookingContentPage.getChildAt(1);
        TransitionManager.beginDelayedTransition(llBookingContentPage, new ChangeBounds());
        llBookingContentPage.removeView(view);
        llBookingContentPage.addView(view, 0);
    }

    private void revalidateBasketBadgeCount() {
        if (mPreparedServicesAdapter == null) return;
        setBasketBadgeCount(String.valueOf(mPreparedServicesAdapter.getItemCount()));
    }

    private void setBasketBadgeCount(String count) {
        if (mMenuItemBasket == null) return;
        ViewUtil.setCounterDrawableCount(mActivity, mMenuItemBasket, count);
    }

    private void initCalendar() {
        scCalendar.setCalendarListener(mPresenter);
        scCalendar.setVisibleMonthAfterNow(BookingUtil.MAX_BOOKING_MONTH_COUNT);
        scCalendar.setVisibleMonthBeforeNow(2);
    }

    private void initViews() {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");

        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderChooseDateTime, cvChooseDateTimeContainer);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderChooseStaff, cvChooseStaffContainer);

        initSwipeRefresh();
        initServicesList();
        initStaffList();
        initAutoSelectedStaffList();
        initTimeSlotList();
        initPrioritySwitchCompat();
        refreshPriorityTextColor();
        initCalendar();

        ViewUtil.applyCrossedTextStyle(tvServiceStaffPriceCrossed);
        ViewUtil.applyCrossedTextStyle(tvServiceStaffCurrencyCrossed);
    }

    private void initSwipeRefresh() {
        mPlaceHolderLayout.setOnButtonClickListener(() -> {
        });
        ColorUtil.setProgressColor(pbProgress, R.color.colorPrimary);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initServicesList() {
        mPreparedServicesAdapter = new PreparedServicesAdapter(mActivity, mPresenter);
        rvPreparedServices.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.HORIZONTAL, false));
        OverScrollDecoratorHelper.setUpOverScroll(rvPreparedServices,
                OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rvPreparedServices.setAdapter(mPreparedServicesAdapter);
        mPreparedServicesAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initStaffList() {
        mStaffAdapter = new StaffAdapter(mActivity, mPresenter, mPresenter,
                ContextCompat.getColor(mActivity, R.color.gold));
        rvStaff.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.HORIZONTAL, false));
        OverScrollDecoratorHelper.setUpOverScroll(rvStaff,
                OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rvStaff.setAdapter(mStaffAdapter);
        mStaffAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initAutoSelectedStaffList() {
        mAutoSelectedStaffAdapter = new AutoSelectedStaffAdapter(mActivity, mPresenter);
        rvAutoSelectedStaffs.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));
        rvAutoSelectedStaffs.setAdapter(mAutoSelectedStaffAdapter);
        mAutoSelectedStaffAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initTimeSlotList() {
        mTimeSlotAdapter = new TimeSlotAdapter(mActivity, mPresenter);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mActivity);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.FLEX_START);
        rvTimeSlot.setLayoutManager(layoutManager);
        rvTimeSlot.setAdapter(mTimeSlotAdapter);
        mTimeSlotAdapter.notifyDataSetChanged();
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

    private class PriorityCheckedListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                mPresenter.onPriorityDateClicked();
            } else {
                mPresenter.onPriorityStaffClicked();
            }
        }
    }
}
