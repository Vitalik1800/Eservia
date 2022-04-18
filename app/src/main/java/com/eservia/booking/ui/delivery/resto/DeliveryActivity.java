package com.eservia.booking.ui.delivery.resto;

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

import org.greenrobot.eventbus.EventBus;

import moxy.presenter.InjectPresenter;

public class DeliveryActivity extends BaseActivity implements DeliveryView {

    @InjectPresenter
    public DeliveryPresenter mPresenter;

    public static void start(Context context, Business business) {
        EventBus.getDefault().postSticky(new DeliveryRestoExtra(business));
        Intent starter = new Intent(context, DeliveryActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_resto);
        mPresenter = new DeliveryPresenter();
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
    public void openBasketFragment() {
        FragmentUtil.showRestoDeliveryBasketFragment(getSupportFragmentManager(),
                R.id.deliveryRestoContainer);
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
