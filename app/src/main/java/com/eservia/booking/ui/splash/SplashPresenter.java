package com.eservia.booking.ui.splash;

import android.content.Context;

import com.eservia.booking.App;
import com.eservia.booking.R;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.entity.OrderResto;
import com.eservia.model.local.order_resto.OrderRestoRepository;
import com.eservia.model.prefs.AccessToken;
import com.eservia.model.prefs.WasOpened;
import com.eservia.model.remote.rest.RestManager;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class SplashPresenter extends BasePresenter<SplashView> {

    @Inject
    RestManager mRestManager;

    @Inject
    OrderRestoRepository mOrderRestoRepository;

    @Inject
    Context mContext;

    private Timer mTimer = new Timer();

    public SplashPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        clearAllBasketOrders();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        mTimer = null;
    }

    private void clearAllBasketOrders() {
        Single<List<OrderResto>> observable = mOrderRestoRepository
                .deleteAll()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        addSubscription(observable.subscribe(success -> start(), error -> start()));
    }

    private void start() {
        if (WasOpened.wasOpened()) {
            if (AccessToken.isValidRefreshToken()) {
                requestUser();
            } else {
                getViewState().openLoginView();
            }
        } else {
            getViewState().openIntro();
        }
    }

    private void requestUser() {
        addSubscription(mRestManager
                .loadProfile()
                .flatMap(success -> {
                    String token = FirebaseInstanceId.getInstance().getToken();
                    if (token != null) {
                        return mRestManager.postDeviceToken(token);
                    } else {
                        return Observable.just(success);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onSuccessProfileLoaded(), this::onProfileLoadingFailed));
    }

    private void onSuccessProfileLoaded() {
        getViewState().openHomeView();
    }

    private void onProfileLoadingFailed(Throwable throwable) {
        getViewState().showError(mContext.getResources().getString(R.string.error_no_internet_detailed));
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                requestUser();
            }
        }, 4000);
    }
}
