package com.eservia.booking.common.presenter;

import com.eservia.utils.Contract;
import com.eservia.utils.LogUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import moxy.MvpPresenter;
import moxy.MvpView;


public class BasePresenter<T extends MvpView> extends MvpPresenter<T> {

    public static final int PART = 25;

    private final CompositeDisposable mDisposable;

    public BasePresenter() {
        mDisposable = new CompositeDisposable();
    }

    public void addSubscription(Disposable d) {
        if (mDisposable.isDisposed()) {
            LogUtils.debug(Contract.LOG_TAG, "CompositeDisposable is null or destroyed");
            return;
        }
        mDisposable.add(d);
    }

    @Override
    public void onDestroy() {
        mDisposable.dispose();
        super.onDestroy();
    }

    public boolean paginationInProgress(Disposable paginationDisposable) {
        return paginationDisposable != null && !paginationDisposable.isDisposed();
    }

    public void cancelPagination(Disposable paginationDisposable) {
        if (paginationDisposable != null) {
            paginationDisposable.dispose();
        }
    }
}
