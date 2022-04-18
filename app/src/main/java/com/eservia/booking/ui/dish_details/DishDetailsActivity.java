package com.eservia.booking.ui.dish_details;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.util.DishUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.CommonAlert;
import com.eservia.common.view.CommonCounter;
import com.eservia.common.view.recycle_selector.CommonRecyclerSelector;
import com.eservia.common.view.recycle_selector.CommonRecyclerSelectorAdapterItem;
import com.eservia.glide.Glide;
import com.eservia.glide.load.engine.DiskCacheStrategy;
import com.eservia.glide.load.resource.drawable.DrawableTransitionOptions;
import com.eservia.glide.request.RequestOptions;
import com.eservia.model.entity.OrderRestoNomenclature;
import com.eservia.model.entity.OrderRestoSizePrice;
import com.eservia.utils.StringUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import moxy.presenter.InjectPresenter;

public class DishDetailsActivity extends BaseActivity implements DishDetailsView {

    CoordinatorLayout coordinator;
    Toolbar toolbar;
    CardView bottomSheet;
    ImageView ivBackImage;
    TextView tvTitle;
    TextView tvPrice;
    TextView tvWeight;
    TextView tvTime;
    TextView tvDescription;
    CommonRecyclerSelector sizesRecycler;
    CommonCounter portionsCounter;
    CommonAlert alert;
    Button btnAddToBasket;

    @InjectPresenter
    DishDetailsPresenter mPresenter;

    BottomSheetBehavior mSheetBehavior;

    public static void start(Context context, OrderRestoNomenclature dish) {
        EventBus.getDefault().postSticky(new DishDetailsExtra(dish));
        Intent intent = new Intent(context, DishDetailsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_details);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        coordinator = findViewById(R.id.coordinator);
        toolbar = findViewById(R.id.toolbar);
        bottomSheet = findViewById(R.id.bottom_sheet);
        ivBackImage = findViewById(R.id.ivBackImage);
        tvTitle = findViewById(R.id.tvTitle);
        tvPrice = findViewById(R.id.tvPrice);
        tvWeight = findViewById(R.id.tvWeight);
        tvTime = findViewById(R.id.tvTime);
        tvDescription = findViewById(R.id.tvDescription);
        sizesRecycler = findViewById(R.id.sizes_list);
        portionsCounter = findViewById(R.id.portions_selector);
        alert = findViewById(R.id.alert);
        btnAddToBasket = findViewById(R.id.btnAddToBasket);
        btnAddToBasket.setOnClickListener(v -> onAddToBasketClick());
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    public void onAddToBasketClick() {
        mPresenter.onAddToBasket();
    }

    @Override
    public void bindDishImage(String photoPath){
        try {
            Glide.with(this)
                    .load(ImageUtil.getUserPhotoPath("", photoPath))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivBackImage);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bindDishName(String name) {
        tvTitle.setText(name);
    }

    @Override
    public void bindDishMinWeight(OrderRestoNomenclature dish) {
        tvWeight.setText(DishUtil.INSTANCE.getFormattedMinWeightLong(this, dish));
    }

    @Override
    public void bindDishWeight(OrderRestoNomenclature dish, Double size) {
        tvWeight.setText(DishUtil.INSTANCE.getFormattedWeightLong(this, dish, size));
    }

    @Override
    public void bindDishCookingTime(OrderRestoNomenclature dish) {
        tvTime.setText(DishUtil.INSTANCE.getFormattedCookingTimeLong(tvTime.getContext(), dish));
    }

    @Override
    public void bindDishDescription(String description) {
        tvDescription.setText(!StringUtil.isEmpty(description) ? description : "");
    }

    @Override
    public void bindNumberOfPortions(int numberOfPortions) {
        portionsCounter.setValue(numberOfPortions);
    }

    @Override
    public void bindDishSizePrices(OrderRestoNomenclature dish, int selectedDishPosition) {
        List<CommonRecyclerSelectorAdapterItem> selectorAdapterItems = new ArrayList<>();
        for (int i = 0; i < dish.getSizePrices().size(); i++) {
            OrderRestoSizePrice sizePrice = dish.getSizePrices().get(i);

            boolean isDrink = DishUtil.INSTANCE.isDrink(dish);
            Drawable imageSelected = isDrink ?
                    ContextCompat.getDrawable(this, R.drawable.drink_size_large_selected) :
                    ContextCompat.getDrawable(this, R.drawable.dish_size_large_selected);
            Drawable imageNormal = isDrink ?
                    ContextCompat.getDrawable(this, R.drawable.drink_size_large) :
                    ContextCompat.getDrawable(this, R.drawable.dish_size_large);

            CommonRecyclerSelectorAdapterItem item = new CommonRecyclerSelectorAdapterItem(
                    imageSelected,
                    imageNormal,
                    sizePrice.getPresentationName() != null
                            ? sizePrice.getPresentationName()
                            : DishUtil.INSTANCE.getFormattedWeight(dish, sizePrice.getSize()),
                    ContextCompat.getColor(this, R.color.resto),
                    ContextCompat.getColor(this, R.color.colorInactive),
                    i == selectedDishPosition);
            selectorAdapterItems.add(item);
        }
        sizesRecycler.setItems(selectorAdapterItems);
    }

    @Override
    public void bindTotalPrice(double totalPrice) {
        tvPrice.setText(DishUtil.INSTANCE.getFormattedPrice(this, totalPrice));
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void showAddedToCartMessage() {
        MessageUtil.showToast(this, R.string.added_to_cart);
    }

    @Override
    public void showAddedToCartErrorMessage() {
        MessageUtil.showSnackbar(coordinator, R.string.unknown_error);
    }

    @Override
    public void setMaxPortionsCount(int maxPortionsCount) {
        portionsCounter.setMaxValue(maxPortionsCount);
    }

    @Override
    public void showExceededMaxAmountOfPortionsError(int maxAmount) {
        alert.setMessageText(getResources().getString(
                R.string.error_exceeded_max_amount_of_portions, maxAmount));
        alert.setType(CommonAlert.Type.WARNING);
        alert.show();
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        sizesRecycler.setItemsSizeIncreasing(true);
        sizesRecycler.setListener(new SizePricesListener());

        portionsCounter.setListener(new PortionsCounterListener());

        mSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mSheetBehavior.setBottomSheetCallback(new SheetListener());
    }

    private class SizePricesListener implements CommonRecyclerSelector.CommonRecyclerSelectorListener {

        @Override
        public void onCommonRecyclerItemSelected(CommonRecyclerSelectorAdapterItem item,
                                                 int position) {
            mPresenter.onSelectedSizePriceAtPosition(position);
        }
    }

    private class PortionsCounterListener implements CommonCounter.CommonCounterListener {

        @Override
        public void onCounterValueIncreased(int count) {
            mPresenter.onPortionsChanged(count);
        }

        @Override
        public void onCounterValueDecreased(int count) {
            mPresenter.onPortionsChanged(count);
        }

        @Override
        public void onUnableToIncrease() {
        }

        @Override
        public void onUnableToDecrease() {
        }
    }

    private static class SheetListener extends BottomSheetBehavior.BottomSheetCallback {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_HIDDEN:
                case BottomSheetBehavior.STATE_EXPANDED:
                case BottomSheetBehavior.STATE_DRAGGING:
                case BottomSheetBehavior.STATE_SETTLING:
                case BottomSheetBehavior.STATE_COLLAPSED: {
                }
                break;
                case BottomSheetBehavior.STATE_HALF_EXPANDED:
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    }
}
