package com.eservia.booking.ui.staff.beauty;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.model.remote.rest.RestManager;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import moxy.InjectViewState;

@InjectViewState
public class StaffBeautyPresenter extends BasePresenter<StaffBeautyView> {

    @Inject
    RestManager mRestManager;

    public StaffBeautyPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        BeautyStaff mStaff = EventBus.getDefault().getStickyEvent(BeautyStaff.class);
        getViewState().onStaff(mStaff);
    }

    void onCloseClick() {
        getViewState().closeActivity();
    }
}
