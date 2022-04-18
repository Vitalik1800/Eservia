package com.eservia.booking.ui.business_page.beauty.departments;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import moxy.InjectViewState;

@InjectViewState
public class BusinessPageBeautyDepartmentsPresenter extends BasePresenter<BusinessPageBeautyDepartmentsView>
        implements DepartmentsAdapter.OnDepartmentClickListener {

    private final List<Address> mAddresses = new ArrayList<>();

    private Business mBusiness;

    public BusinessPageBeautyDepartmentsPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mBusiness = EventBus.getDefault().getStickyEvent(Business.class);
        if (mBusiness != null && !mBusiness.getAddresses().isEmpty()) {
            mAddresses.addAll(mBusiness.getAddresses());
            getViewState().onAddressesLoadingSuccess(mAddresses);
        }
    }

    @Override
    public void onDepartmentClick(Address address, int position) {
        onGoToBooking(address);
    }

    @Override
    public void onDepartmentBookingClick(Address address, int position) {
        onGoToBooking(address);
    }

    private void onGoToBooking(Address address) {
        if (mBusiness == null || address == null) {
            return;
        }
        getViewState().openBooking(mBusiness, address);
    }
}
