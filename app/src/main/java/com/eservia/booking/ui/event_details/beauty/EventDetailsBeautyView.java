package com.eservia.booking.ui.event_details.beauty;

import androidx.annotation.Nullable;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.ui.gallery.GalleryExtra;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.Marketing;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface EventDetailsBeautyView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onMarketing(@Nullable Marketing marketing);

    @StateStrategyType(value = SkipStrategy.class)
    void openLinkWebsite(String link);

    @StateStrategyType(value = SkipStrategy.class)
    void openLinkFacebook(String link);

    @StateStrategyType(value = SkipStrategy.class)
    void openLinkInstagram(String link);

    @StateStrategyType(value = SkipStrategy.class)
    void openLinkGooglePlus(String link);

    @StateStrategyType(value = SkipStrategy.class)
    void openLinkTwitter(String link);

    @StateStrategyType(value = SkipStrategy.class)
    void requiredArgs();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void initAcceptButton(boolean show, String businessTitle);

    @StateStrategyType(value = SkipStrategy.class)
    void showBusinessBeautyActivity(Business business);

    @StateStrategyType(value = SkipStrategy.class)
    void showGalleryActivity(GalleryExtra extra);

    @StateStrategyType(value = AddToEndStrategy.class)
    void onServicesLoadingSuccess(List<ServiceAdapterItem> serviceList);

    @StateStrategyType(value = SkipStrategy.class)
    void onServicesLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void showErrorExceedDialog();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideErrorExceedDialog();

    @StateStrategyType(value = AddToEndStrategy.class)
    void setSelected(boolean selected, Integer serviceId);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void revalidateBookButton(boolean isActive);

    @StateStrategyType(value = SkipStrategy.class)
    void goToBooking(Business business, List<Preparation> preparations);
}
