package com.eservia.booking.common.view;

import static android.content.pm.PackageManager.GET_META_DATA;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import com.eservia.booking.R;
import com.eservia.booking.ui.auth.login.LoginActivity;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.RateUsUtil;
import com.eservia.butterknife.Unbinder;
import com.eservia.model.local.ContentChangesObservable;
import com.eservia.model.local.SyncEvent;
import com.eservia.model.prefs.Profile;
import com.eservia.utils.Contract;
import com.eservia.utils.LocaleUtil;
import com.eservia.utils.LogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpAppCompatActivity;

public abstract class BaseActivity extends MvpAppCompatActivity implements
        UpdatesDialog.UpdatesDialogListener {

    private CompositeDisposable mDisposable;

    @Nullable
    private Unbinder mUnbinder;

    public void setUnbinder(Unbinder unbinder) {
        mUnbinder = unbinder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisposable = new CompositeDisposable();
        subscribeForAuthorizationErrors();
        subscribeForOutdatedVersion();
        subscribeForRateUs();
        subscribeOnProfileUpdate();
        resetTitles();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleUtil.setLocale(base));
    }

    @Override
    public void onCancelUpdateClick() {
    }

    @Override
    public void onAcceptUpdateClick() {
        RateUsUtil.openPlayMarketPage(this);
    }

    private void subscribeForAuthorizationErrors() {
        addSubscription(ContentChangesObservable.authorization()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessHandledAuthorizationError, this::onErrorHandledAuthorizationError));
    }

    private void subscribeForOutdatedVersion() {
        addSubscription(ContentChangesObservable.outdatedVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessHandledOutdatedVersion, this::onErrorHandledOutdatedVersion));
    }

    private void subscribeForRateUs() {
        addSubscription(ContentChangesObservable.rateUs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessHandledRateUs, this::onErrorHandledRateUs));
    }

    private void subscribeOnProfileUpdate() {
        addSubscription(ContentChangesObservable.profile(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessHandledProfileUpdate, this::onErrorHandledProfileUpdate));
    }

    private void onSuccessHandledAuthorizationError(SyncEvent syncEvent) {
        MessageUtil.showToast(this, R.string.error_unauthorized);
        Profile.logOut();
        openLoginActivity();
    }

    private void onErrorHandledAuthorizationError(Throwable throwable) {
        LogUtils.debug(Contract.LOG_TAG, throwable.getMessage());
    }

    private void onSuccessHandledOutdatedVersion(SyncEvent syncEvent) {
        openUpdatesDialog();
    }

    private void onErrorHandledOutdatedVersion(Throwable throwable) {
        LogUtils.debug(Contract.LOG_TAG, throwable.getMessage());
    }

    private void onSuccessHandledRateUs(SyncEvent syncEvent) {
        openRateUsDialog();
    }

    private void onErrorHandledRateUs(Throwable throwable) {
        LogUtils.debug(Contract.LOG_TAG, throwable.getMessage());
    }

    private void onSuccessHandledProfileUpdate(SyncEvent syncEvent) {
    }

    private void onErrorHandledProfileUpdate(Throwable throwable) {
        LogUtils.debug(Contract.LOG_TAG, throwable.getMessage());
    }

    private void resetTitles() {
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA);
            if (info.labelRes != 0) {
                setTitle(info.labelRes);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addSubscription(Disposable d) {
        if (mDisposable == null || mDisposable.isDisposed()) {
            LogUtils.debug(Contract.LOG_TAG, "CompositeDisposable is null or destroyed");
            return;
        }
        mDisposable.add(d);
    }

    protected void openLoginActivity() {
        LoginActivity.start(this);
        overridePendingTransition(R.anim.anim_gone, R.anim.anim_gone);
        finish();
    }

    protected void openUpdatesDialog() {
        UpdatesDialog updatesDialog = UpdatesDialog.newInstance();
        updatesDialog.setListener(this);
        updatesDialog.show(getSupportFragmentManager(), UpdatesDialog.class.getSimpleName());
    }

    protected void openRateUsDialog() {
        RateUsUtil.showRateUsDialog(this);
    }
}
