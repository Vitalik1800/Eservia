package com.eservia.booking.ui.business_page.beauty.departments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.ui.booking.beauty.BookingBeautyActivity;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class BusinessPageBeautyDepartmentsFragment extends BaseHomeFragment implements
        BusinessPageBeautyDepartmentsView {

    RecyclerView rvDepartments;

    @InjectPresenter
    BusinessPageBeautyDepartmentsPresenter mPresenter;

    private Activity mActivity;

    private DepartmentsAdapter mAdapter;

    public static BusinessPageBeautyDepartmentsFragment newInstance() {
        return new BusinessPageBeautyDepartmentsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_page_beauty_departments, container, false);
        mActivity = getActivity();
        setUnbinder(ButterKnife.bind(this, view));
        rvDepartments = view.findViewById(R.id.rvDepartments);
        initViews();
        return view;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void willBeDisplayed() {
    }

    @Override
    public void willBeHidden() {
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void onAddressesLoadingSuccess(List<Address> addressList) {
        mAdapter.replaceAll(addressList);
    }

    @Override
    public void openBooking(Business business, Address address) {
        BookingBeautyActivity.start(mActivity, business, address, null);
    }

    @Override
    public void onAddressesLoadingFailed(Throwable throwable) {
    }

    private void initViews() {
        initList();
    }

    private void initList() {
        mAdapter = new DepartmentsAdapter(mActivity, mPresenter);
        rvDepartments.setHasFixedSize(true);
        rvDepartments.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));
        rvDepartments.setAdapter(mAdapter);
    }
}
