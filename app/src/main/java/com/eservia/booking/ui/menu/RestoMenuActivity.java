package com.eservia.booking.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.ui.business_page.restaurant.menu.RestoMenuFragmentListener;
import com.eservia.booking.ui.delivery.resto.DeliveryActivity;
import com.eservia.booking.ui.dish_details.DishDetailsActivity;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.OrderRestoCategory;
import com.eservia.model.entity.OrderRestoNomenclature;
import com.google.android.material.appbar.AppBarLayout;

import org.greenrobot.eventbus.EventBus;

import moxy.presenter.InjectPresenter;

public class RestoMenuActivity extends BaseActivity implements RestoMenuView,
        FragmentManager.OnBackStackChangedListener,
        RestoMenuFragmentListener {

    CoordinatorLayout coordinator;
    AppBarLayout app_bar_layout;
    Toolbar toolbar;
    FrameLayout fragRestoMenuContainer;

    @InjectPresenter
    RestoMenuPresenter mPresenter;

    private MenuItem mMenuItemBasket;

    private int mCreatedOrderItemsCount;

    public static void start(Context context, Business business, Long categoryId) {
        EventBus.getDefault().postSticky(new RestoMenuExtra(business, categoryId));
        Intent intent = new Intent(context, RestoMenuActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_menu);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        getSupportFragmentManager().addOnBackStackChangedListener(RestoMenuActivity.this);
        coordinator = findViewById(R.id.coordinator);
        app_bar_layout = findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.toolbar);
        fragRestoMenuContainer = findViewById(R.id.fragRestoMenuContainer);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basket_counter, menu);
        mMenuItemBasket = menu.findItem(R.id.item_basket);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        revalidateBasketBadgeCount();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            mPresenter.onBackClicked();
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

    @Override
    public void onBackStackChanged() {
    }

    @Override
    public void onBackPressed() {
        mPresenter.onBackClicked();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void openFirstMenuFragment(Long categoryId) {
        openNewMenuFragment(categoryId);
    }

    @Override
    public void onCategoryClicked(OrderRestoCategory category) {
        openNewMenuFragment(category.getId());
    }

    @Override
    public void onDishClicked(OrderRestoNomenclature dish) {
        DishDetailsActivity.start(this, dish);
    }

    @Override
    public void refreshBasketState(int orderItemsCount) {
        mCreatedOrderItemsCount = orderItemsCount;
        revalidateBasketBadgeCount();
    }

    @Override
    public void openBasket(Business business) {
        DeliveryActivity.start(this, business);
    }

    private void openNewMenuFragment(Long categoryId) {
        FragmentUtil.showRestoMenuFragment(getSupportFragmentManager(),
                R.id.fragRestoMenuContainer,
                categoryId);
    }

    @Override
    public void goBack() {
        if (!popFragment()) {
            finish();
        }
    }

    private void revalidateBasketBadgeCount() {
        setBasketBadgeCount(String.valueOf(mCreatedOrderItemsCount));
    }

    private void setBasketBadgeCount(String count) {
        if (mMenuItemBasket == null) return;
        ViewUtil.setCounterDrawableCount(this, mMenuItemBasket, count);
    }

    private boolean popFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
            return true;
        }
        return false;
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);
    }
}
