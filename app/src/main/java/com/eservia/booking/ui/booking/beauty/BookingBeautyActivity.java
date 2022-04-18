package com.eservia.booking.ui.booking.beauty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class BookingBeautyActivity extends BaseActivity implements BookingBeautyView,
        FragmentManager.OnBackStackChangedListener {

    FrameLayout bookingBeautyContainer;

    @InjectPresenter
    BookingBeautyPresenter mPresenter;

    public static void start(Context context, Business business, @Nullable Address address,
                             @Nullable List<Preparation> preparations) {
        EventBus.getDefault().postSticky(new BookingBeautyExtra(business, address, preparations));
        Intent starter = new Intent(context, BookingBeautyActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_beauty);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        bookingBeautyContainer = findViewById(R.id.bookingBeautyContainer);
        getSupportFragmentManager().addOnBackStackChangedListener(BookingBeautyActivity.this);
    }

    @Override
    public void onBackStackChanged() {
    }

    @Override
    public void onBackPressed() {
        if (!popFragment()) {
            finish();
        }
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void openServiceGroups() {
        FragmentUtil.showServiceGroupBeautyFragment(getSupportFragmentManager(),
                R.id.bookingBeautyContainer);
    }

    @Override
    public void openBookingFragment() {
        FragmentUtil.openBookingBeautyFragment(getSupportFragmentManager(),
                R.id.bookingBeautyContainer);
    }

    @Override
    public void openBasketSortFragment() {
        FragmentUtil.openBookingBeautyBasketSortFragment(getSupportFragmentManager(),
                R.id.bookingBeautyContainer);
    }

    private boolean popFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
            return true;
        }
        return false;
    }
}
