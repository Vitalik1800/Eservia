package com.eservia.booking.ui.booking.resto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.RestoBookingSettings;

import org.greenrobot.eventbus.EventBus;

import moxy.presenter.InjectPresenter;

public class BookingRestoActivity extends BaseActivity implements BookingRestoView {

    @InjectPresenter
    BookingRestoPresenter mPresenter;

    public static void start(Context context, Business business, RestoBookingSettings settings) {
        EventBus.getDefault().postSticky(new BookingRestoExtra(business, settings));
        Intent starter = new Intent(context, BookingRestoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_resto);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
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
    public void openDateTimeSelectFragment() {
        FragmentUtil.showRestoBookingDateTimeFragment(getSupportFragmentManager(),
                R.id.bookingRestoContainer);
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
