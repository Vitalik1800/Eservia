package com.eservia.booking.ui.business_page.beauty.contacts;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import moxy.InjectViewState;

@InjectViewState
public class BusinessPageBeautyContactsPresenter extends BasePresenter<BusinessPageBeautyContactsView> implements
        AddressAdapter.OnAddressClickListener,
        AddressAdapter.OnAddressPaginationListener {

    private final List<Address> mAddresses = new ArrayList<>();

    private final List<AddressAdapterItem> mAddressesItems = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    private Business mBusiness;

    public BusinessPageBeautyContactsPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mBusiness = EventBus.getDefault().getStickyEvent(Business.class);
        if (mBusiness != null && !mBusiness.getAddresses().isEmpty()) {
            mAddresses.addAll(mBusiness.getAddresses());
            getViewState().onAddressesLoadingSuccess(mapToAddressAdapterItems(mAddresses));
        }
    }

    @Override
    public void onAddressClick(ItemAddress itemAddress, int position) {
        if (mBusiness == null) {
            return;
        }
        String title = mBusiness.getName();
        Address address = itemAddress.getAddress();
        String lat = address.getLat();
        String lng = address.getLng();
        if (title == null || lat == null || lng == null) {
            return;
        }
        getViewState().openMap(title, Double.valueOf(lat), Double.valueOf(lng));
    }

    @Override
    public void onAddressPhoneClick(ItemAddress itemAddress, int position) {
        String phone = itemAddress.getAddress().getPhone();
        if (!StringUtil.isEmpty(phone)) {
            getViewState().openPhone(phone);
        }
    }

    @Override
    public void loadMoreAddresses() {
    }

    private List<AddressAdapterItem> mapToAddressAdapterItems(List<Address> addressEntities) {
        List<AddressAdapterItem> result = new ArrayList<>();
        result.add(new ItemHeader());
        for (Address entity : addressEntities) {
            result.add(new ItemAddress(entity));
        }
        mAddressesItems.clear();
        mAddressesItems.addAll(result);
        return result;
    }
}
