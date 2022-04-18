package com.eservia.booking.ui.business_page.beauty.contacts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.ui.business_page.map.BusinessMapActivity;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.butterknife.ButterKnife;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class BusinessPageBeautyContactsFragment extends BaseHomeFragment implements
        BusinessPageBeautyContactsView {

    RecyclerView rvAddresses;

    @InjectPresenter
    BusinessPageBeautyContactsPresenter mPresenter;

    private Activity mActivity;

    private AddressAdapter mAddressAdapter;

    public static BusinessPageBeautyContactsFragment newInstance() {
        return new BusinessPageBeautyContactsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_page_beauty_contacts, container, false);
        mActivity = getActivity();
        setUnbinder(ButterKnife.bind(this, view));
        rvAddresses = view.findViewById(R.id.rvAddresses);
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
    public void onAddressesLoadingSuccess(List<AddressAdapterItem> adapterItems) {
        mAddressAdapter.replaceAll(adapterItems);
    }

    @Override
    public void onAddressesLoadingFailed(Throwable throwable) {
    }

    @Override
    public void openMap(String title, Double lat, Double lng) {
        BusinessMapActivity.start(mActivity, title, lat, lng);
    }

    @Override
    public void openPhone(String number) {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                "tel", number, null));
        startActivity(phoneIntent);
    }

    private void initViews() {
        initAddressesList();
    }

    private void initAddressesList() {
        mAddressAdapter = new AddressAdapter(mActivity, mPresenter, mPresenter);
        rvAddresses.setHasFixedSize(true);
        rvAddresses.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.VERTICAL, false));
        rvAddresses.setAdapter(mAddressAdapter);
    }
}
