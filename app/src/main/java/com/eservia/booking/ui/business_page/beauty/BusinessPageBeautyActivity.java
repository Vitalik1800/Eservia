package com.eservia.booking.ui.business_page.beauty;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.eservia.booking.R;
import com.eservia.booking.common.view.AppBarStateChangeListener;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.ui.booking.beauty.BookingBeautyActivity;
import com.eservia.booking.ui.booking.resto.BookingRestoActivity;
import com.eservia.booking.ui.business_page.beauty.contacts.BusinessPageBeautyContactsFragment;
import com.eservia.booking.ui.business_page.beauty.departments.BusinessPageBeautyDepartmentsFragment;
import com.eservia.booking.ui.business_page.beauty.info.BusinessPageBeautyInfoFragment;
import com.eservia.booking.ui.business_page.beauty.reviews.BusinessPageBeautyReviewsFragment;
import com.eservia.booking.ui.business_page.dialog.PrepaymentSheetDialog;
import com.eservia.booking.ui.business_page.restaurant.menu.MenuFragment;
import com.eservia.booking.ui.business_page.restaurant.menu.RestoMenuFragmentListener;
import com.eservia.booking.ui.delivery.resto.DeliveryActivity;
import com.eservia.booking.ui.dish_details.DishDetailsActivity;
import com.eservia.booking.ui.menu.RestoMenuActivity;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.glide.Glide;
import com.eservia.glide.load.engine.DiskCacheStrategy;
import com.eservia.glide.load.resource.drawable.DrawableTransitionOptions;
import com.eservia.glide.request.RequestOptions;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessPhoto;
import com.eservia.model.entity.OrderRestoCategory;
import com.eservia.model.entity.OrderRestoNomenclature;
import com.eservia.model.entity.RestoBookingSettings;
import com.eservia.simpleratingbar.SimpleRatingBar;
import com.eservia.utils.KeyboardUtil;
import com.expandablelayout.ExpandableRelativeLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class BusinessPageBeautyActivity extends BaseActivity implements BusinessPageBeautyView,
        RestoMenuFragmentListener {

    CoordinatorLayout coordinator;
    AppBarLayout app_bar_layout;
    Toolbar toolbar;
    ViewPager view_pager;
    TabLayout tab_layout;
    ExpandableRelativeLayout erlHeader;
    CardView cvHeader;
    ImageView ivBusinessImage;
    TextView tvBusinessName;
    TextView tvAddress;
    TextView tvDistance;
    SimpleRatingBar rbRating;
    TextView tvRating;
    TextView tvToolbarTitle;
    RelativeLayout rlBusinessInfoContainer;
    ImageView ivToolbarBackground;
    Button btnCommonBookingButton;
    ConstraintLayout clRestoMenuDeliveryBootTable;
    Button btnMenuBookTable;
    View toolbar_background;
    Button btnMenuDelivery;

    @InjectPresenter
    BusinessPageBeautyPresenter mPresenter;

    private PrepaymentSheetDialog mPrepaymentDialog;

    private PagerAdapter mPagerAdapter;

    private MenuItem mMenuItemFavorite;

    private MenuItem mMenuItemBasket;

    private int mCreatedOrderItemsCount;

    private boolean mFavorited = false;

    public static void start(Context context, Business business) {
        EventBus.getDefault().postSticky(business);
        Intent starter = new Intent(context, BusinessPageBeautyActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_page_beauty);
        WindowUtils.setLightStatusBar(this);
        WindowUtils.setStatusBarColor(this, R.color.transparent);
        setUnbinder(ButterKnife.bind(this));
        mPresenter = new BusinessPageBeautyPresenter();
        coordinator = findViewById(R.id.coordinator);
        app_bar_layout = findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.toolbar);
        view_pager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);
        erlHeader = findViewById(R.id.erlHeader);
        cvHeader = findViewById(R.id.cvHeader);
        toolbar_background = findViewById(R.id.toolbar_background);
        ivBusinessImage = findViewById(R.id.ivBusinessImage);
        tvBusinessName = findViewById(R.id.tvBusinessName);
        tvAddress = findViewById(R.id.tvAddress);
        tvDistance = findViewById(R.id.tvDistance);
        rbRating = findViewById(R.id.rbRating);
        tvRating = findViewById(R.id.tvRating);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        rlBusinessInfoContainer = findViewById(R.id.rlBusinessInfoContainer);
        ivToolbarBackground = findViewById(R.id.ivToolbarBackground);
        btnCommonBookingButton = findViewById(R.id.btnCommonBookingButton);
        clRestoMenuDeliveryBootTable = findViewById(R.id.clRestoMenuDeliveryBootTable);
        btnMenuBookTable = findViewById(R.id.btnMenuBookTable);
        btnCommonBookingButton.setOnClickListener(v -> onGoBookingClicked());
        btnMenuDelivery = findViewById(R.id.btnMenuDelivery);
        btnMenuDelivery.setOnClickListener(v -> onRestoDeliveryBasketClick());
        btnMenuBookTable.setOnClickListener(v -> onRestoBookTableClick());
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_business_page_beauty, menu);
        mMenuItemFavorite = menu.findItem(R.id.item_favorite);
        mMenuItemBasket = menu.findItem(R.id.item_basket);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        revalidateFavoriteMenu();
        revalidateBasketBadgeCount();
        mPresenter.onPreparedOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.item_favorite) {
            mPresenter.onFavoriteClicked();
        } else if (id == R.id.item_basket) {
            mPresenter.onBasketClicked();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    public void onGoBookingClicked() {
        mPresenter.onGoBookingClicked();
    }
    public void onRestoDeliveryBasketClick() {
        mPresenter.onRestoDeliveryBasketClicked();
    }
    public void onRestoBookTableClick() {
        mPresenter.onRestoBookTableClicked();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void onBusiness(@Nullable Business business) {
        if (business == null) {
            rlBusinessInfoContainer.setVisibility(View.INVISIBLE);
        } else {
            rlBusinessInfoContainer.setVisibility(View.VISIBLE);

            tvToolbarTitle.setText(business.getName());

            try {
                ImageUtil.displayBusinessImageTransform(this, ivBusinessImage,
                        business.getLogo(), R.drawable.icon_business_photo_placeholder_beauty,
                        R.drawable.mask_business_image);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            tvBusinessName.setText(business.getName());

            rbRating.setRating(BusinessUtil.starsRating(business.getRating()));
            tvRating.setText(BusinessUtil.formatRating(business.getRating()));

            tvAddress.setText(BusinessUtil.businessAddressesTitle(this, business));

            String distance = BusinessUtil.formatBusinessDistanceTitle(this, business);
            if (distance.isEmpty()) {
                tvDistance.setVisibility(View.GONE);
            } else {
                tvDistance.setVisibility(View.VISIBLE);
                tvDistance.setText(distance);
            }

            mFavorited = business.getIs().getFavorited();
            revalidateFavoriteMenu();
        }
    }

    @Override
    public void onPhotosLoadingSuccess(Business business, List<BusinessPhoto> businessPhotos) {
        if (businessPhotos.isEmpty()) {
            try {
                setToolbarBackground(business.getLogo());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            setToolbarBackground(businessPhotos.get(0).getPath());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPhotosLoadingFailed(Throwable throwable) {
    }

    @Override
    public void enableFavorite() {
        if (mMenuItemFavorite != null) {
            mMenuItemFavorite.setEnabled(true);
        }
    }

    @Override
    public void disableFavorite() {
        if (mMenuItemFavorite != null) {
            mMenuItemFavorite.setEnabled(false);
        }
    }

    @Override
    public void onAddBusinessToFavoriteSuccess() {
        mFavorited = true;
        revalidateFavoriteMenu();
        MessageUtil.showToast(this, R.string.added_to_favorite);
    }

    @Override
    public void onAddBusinessToFavoriteFailed(Throwable throwable) {
        MessageUtil.showSnackbar(coordinator, throwable);
    }

    @Override
    public void onDeleteFavoriteSuccess() {
        mFavorited = false;
        revalidateFavoriteMenu();
        MessageUtil.showToast(this, R.string.deleted_from_favorite);
    }

    @Override
    public void onDeleteFavoriteBusinessFailed(Throwable throwable) {
        MessageUtil.showSnackbar(coordinator, throwable);
    }

    @Override
    public void showBookingBeautyPage(Business business) {
        BookingBeautyActivity.start(this, business, null, null);
    }

    @Override
    public void onCategoryClicked(OrderRestoCategory category) {
        mPresenter.onMenuCategorySelected(category);
    }

    @Override
    public void openMenuActivity(Business business, Long categoryId) {
        RestoMenuActivity.start(this, business, categoryId);
    }

    @Override
    public void onDishClicked(OrderRestoNomenclature dish) {
        DishDetailsActivity.start(this, dish);
    }

    @Override
    public void openRestoDeliveryBasket(Business business) {
        DeliveryActivity.start(this, business);
    }

    @Override
    public void openRestoTableBooking(Business business, RestoBookingSettings bookingSettings) {
        BookingRestoActivity.start(this, business, bookingSettings);
    }

    @Override
    public void createBeautyFragments(boolean showDepartments) {
        if (showDepartments) {
            mPagerAdapter.addFragment(BusinessPageBeautyDepartmentsFragment.newInstance(), getResources().getString(R.string.departments));
        }
        mPagerAdapter.addFragment(BusinessPageBeautyInfoFragment.newInstance(), getResources().getString(R.string.information));
        mPagerAdapter.addFragment(BusinessPageBeautyReviewsFragment.newInstance(), getResources().getString(R.string.reviews));
        mPagerAdapter.addFragment(BusinessPageBeautyContactsFragment.newInstance(), getResources().getString(R.string.contacts));
        setUpOffscreenPageLimit();
    }

    @Override
    public void createRestoFragments() {
        mPagerAdapter.addFragment(BusinessPageBeautyInfoFragment.newInstance(), getResources().getString(R.string.information));
        mPagerAdapter.addFragment(MenuFragment.newInstance(null), getResources().getString(R.string.menu));
        mPagerAdapter.addFragment(BusinessPageBeautyReviewsFragment.newInstance(), getResources().getString(R.string.reviews));
        mPagerAdapter.addFragment(BusinessPageBeautyContactsFragment.newInstance(), getResources().getString(R.string.contacts));
        setUpOffscreenPageLimit();
    }

    @Override
    public void createOtherDeliveryFragments() {
        mPagerAdapter.addFragment(BusinessPageBeautyInfoFragment.newInstance(), getResources().getString(R.string.information));
        mPagerAdapter.addFragment(BusinessPageBeautyReviewsFragment.newInstance(), getResources().getString(R.string.reviews));
        mPagerAdapter.addFragment(BusinessPageBeautyContactsFragment.newInstance(), getResources().getString(R.string.contacts));
        setUpOffscreenPageLimit();
    }

    @Override
    public void showBeautyButtons() {
        beginBeautyBookingButtonTransition();
        clRestoMenuDeliveryBootTable.setVisibility(View.INVISIBLE);
        btnCommonBookingButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRestoButtons() {
        beginRestoDeliveryAndBookingButtonsTransition();
        btnCommonBookingButton.setVisibility(View.INVISIBLE);
        clRestoMenuDeliveryBootTable.setVisibility(View.VISIBLE);
    }

    @Override
    public void showOtherDeliveryButtons() {
        beginRestoDeliveryAndBookingButtonsTransition();
        btnCommonBookingButton.setVisibility(View.INVISIBLE);
        clRestoMenuDeliveryBootTable.setVisibility(View.VISIBLE);
        hideRestoBookingButton();
    }

    @Override
    public void hideAllButtons() {
        btnCommonBookingButton.setVisibility(View.INVISIBLE);
        clRestoMenuDeliveryBootTable.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideRestoBookingButton() {
        btnMenuBookTable.setVisibility(View.GONE);
    }

    @Override
    public void refreshBasketState(int orderItemsCount) {
        mCreatedOrderItemsCount = orderItemsCount;
        revalidateBasketBadgeCount();
    }

    @Override
    public void showBasketMenu() {
        if (mMenuItemBasket != null) {
            mMenuItemBasket.setVisible(true);
        }
    }

    @Override
    public void hideBasketMenu() {
        if (mMenuItemBasket != null) {
            mMenuItemBasket.setVisible(false);
        }
    }

    @Override
    public void showPrepaymentDialog() {
        mPrepaymentDialog = PrepaymentSheetDialog.newInstance();
        mPrepaymentDialog.setListener(mPresenter);
        mPrepaymentDialog.show(getSupportFragmentManager(), PrepaymentSheetDialog.class.getSimpleName());
    }

    @Override
    public void hidePrepaymentDialog() {
        if (mPrepaymentDialog != null) {
            mPrepaymentDialog.dismiss();
        }
    }

    private void setUpOffscreenPageLimit() {
        view_pager.setOffscreenPageLimit(mPagerAdapter.getCount() > 0 ? mPagerAdapter.getCount() : 1);
    }

    private void setIconFavoriteOn() {
        if (mMenuItemFavorite != null) {
            mMenuItemFavorite.setIcon(R.drawable.ic_favorite_on_red);
        }
    }

    private void setIconFavoriteOff() {
        if (mMenuItemFavorite != null) {
            mMenuItemFavorite.setIcon(R.drawable.icon_favourites_add_beauty);
        }
    }

    private void revalidateFavoriteMenu() {
        if (mFavorited) {
            setIconFavoriteOn();
        } else {
            setIconFavoriteOff();
        }
    }

    private void revalidateBasketBadgeCount() {
        setBasketBadgeCount(String.valueOf(mCreatedOrderItemsCount));
    }

    private void setBasketBadgeCount(String count) {
        if (mMenuItemBasket == null) return;
        ViewUtil.setCounterDrawableCount(this, mMenuItemBasket, count);
    }

    private void beginRestoDeliveryAndBookingButtonsTransition() {
        Fade fade = new Fade();
        fade.setDuration(300);
        TransitionManager.beginDelayedTransition(clRestoMenuDeliveryBootTable, fade);
    }

    private void beginBeautyBookingButtonTransition() {
        Fade fade = new Fade();
        fade.setDuration(300);
        TransitionManager.beginDelayedTransition(coordinator, fade);
    }

    private void setToolbarBackground(String path) throws ClassNotFoundException {
        Glide.with(this)
                .load(path)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_background_default))
                .apply(RequestOptions.errorOf(R.drawable.ic_background_default))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivToolbarBackground);
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        view_pager.setAdapter(mPagerAdapter);

        tab_layout.setupWithViewPager(view_pager);
        tab_layout.addOnTabSelectedListener(new TabListener());

        app_bar_layout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {

                if (state.equals(State.EXPANDED)) {
                    erlHeader.expand();

                } else if (state.equals(State.COLLAPSED)) {
                    erlHeader.collapse();

                } else if (state.equals(State.IDLE)) {
                    erlHeader.collapse();
                }
            }
        });

        ViewUtil.setCardOutlineProvider(this, erlHeader, cvHeader);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            toolbar_background.setBackground(ContextCompat.getDrawable(this,
                    R.drawable.business_page_beauty_toolbar_back_dark));
        } else {
            toolbar_background.setBackground(ContextCompat.getDrawable(this,
                    R.drawable.business_page_beauty_toolbar_back));
        }
    }

    private class TabListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            KeyboardUtil.hideSoftKeyboard(BusinessPageBeautyActivity.this);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    }
}
