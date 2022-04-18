package com.eservia.booking.ui.business_page.beauty.info;

import androidx.annotation.Nullable;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.booking.ui.business_page.gallery.BusinessGalleryExtra;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessPhoto;
import com.eservia.model.entity.Marketing;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BusinessPageBeautyInfoView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onBusiness(@Nullable Business business);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onPhotosLoadingSuccess(List<BusinessPhoto> businessPhotos);

    @StateStrategyType(value = SkipStrategy.class)
    void onPhotosLoadingFailed(Throwable throwable);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onStaffLoadingSuccess(List<BeautyStaff> beautyStaffs);

    @StateStrategyType(value = SkipStrategy.class)
    void onStaffLoadingFailed(Throwable throwable);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onNewsLoadingSuccess(List<Marketing> marketings);

    @StateStrategyType(value = SkipStrategy.class)
    void onNewsLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void openLinkWebsite(String link);

    @StateStrategyType(value = SkipStrategy.class)
    void openLinkFacebook(String link);

    @StateStrategyType(value = SkipStrategy.class)
    void openLinkInstagram(String link);

    @StateStrategyType(value = SkipStrategy.class)
    void showGallery(BusinessGalleryExtra extra);

    @StateStrategyType(value = SkipStrategy.class)
    void showEventDetailBeautyPage(Marketing marketing);

    @StateStrategyType(value = SkipStrategy.class)
    void showBusinessMarketingsPage(Business business);

    @StateStrategyType(value = SkipStrategy.class)
    void showBusinessStaffPage(Business business);

    @StateStrategyType(value = SkipStrategy.class)
    void showStaffDetailBeautyPage(BeautyStaff staff);
}
