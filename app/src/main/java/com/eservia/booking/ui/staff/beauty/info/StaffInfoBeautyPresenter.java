package com.eservia.booking.ui.staff.beauty.info;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.ui.gallery.GalleryExtra;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import moxy.InjectViewState;

@InjectViewState
public class StaffInfoBeautyPresenter extends BasePresenter<StaffInfoBeautyView> {

    @Inject
    RestManager mRestManager;

    private BeautyStaff mStaff;

    public StaffInfoBeautyPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mStaff = EventBus.getDefault().getStickyEvent(BeautyStaff.class);
        getViewState().onStaff(mStaff);

    }

    void onStaffImageClick() {
        if (mStaff == null) {
            return;
        }
        String photo = mStaff.getPhoto();
        if (StringUtil.isEmpty(photo)) {
            return;
        }
        getViewState().showGalleryActivity(new GalleryExtra(new ArrayList<>(
                Collections.singletonList(photo))));
    }
}
