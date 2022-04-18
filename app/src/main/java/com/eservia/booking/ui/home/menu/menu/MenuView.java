package com.eservia.booking.ui.home.menu.menu;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MenuView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onUserPhotoLoaded(String photoId);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onUserNameSurnameLoaded(String nameSurname);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onUserPhoneLoaded(String phone);

    @StateStrategyType(value = SkipStrategy.class)
    void openProfileSettings();

    @StateStrategyType(value = SkipStrategy.class)
    void openWriteToUs();

    @StateStrategyType(value = SkipStrategy.class)
    void openPrivacy(String privacyUrl);

    @StateStrategyType(value = SkipStrategy.class)
    void openTerms(String termsUrl);

    @StateStrategyType(value = SkipStrategy.class)
    void openAboutApp();
}
