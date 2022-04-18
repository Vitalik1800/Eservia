package com.eservia.booking.ui.profile;

import com.eservia.booking.common.view.LoadingView;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface ProfileView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onUserPhotoLoaded(String imageLink);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onUserInfoLoaded(UserInfo userInfo);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showAvatarLoading();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideAvatarLoading();

    @StateStrategyType(value = SkipStrategy.class)
    void onLogOutSuccess();

    @StateStrategyType(value = SkipStrategy.class)
    void onLogOutFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void openChangePasswordActivity();

    @StateStrategyType(value = SkipStrategy.class)
    void onEmailUpdateSuccess();

    @StateStrategyType(value = SkipStrategy.class)
    void onEmailUpdateFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void onUserInfoUpdated();

    @StateStrategyType(value = SkipStrategy.class)
    void onUserInfoUpdateFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void onUserInfoAndEmailUpdated();

    @StateStrategyType(value = SkipStrategy.class)
    void onUserInfoAndEmailUpdateFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void showSexSelectDialog(List<SexAdapterItem> adapterItemList);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideSexSelectDialog();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showSexStatus(long sex);

    @StateStrategyType(value = SkipStrategy.class)
    void showDatePickDialog(int year, int month, int day, long maxDate);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showBirthDate(long birthDate);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onRegisterProviderSuccess(String provider);

    @StateStrategyType(value = SkipStrategy.class)
    void onErrorRegisterProvider(Throwable throwable, String provider);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onRemoveProviderSuccess(String provider);

    @StateStrategyType(value = SkipStrategy.class)
    void onErrorRemoveProvider(Throwable throwable, String provider);

    @StateStrategyType(value = SkipStrategy.class)
    void shouldShowStorageRationale();

    @StateStrategyType(value = SkipStrategy.class)
    void showStorageExplanationDialog();

    @StateStrategyType(value = SkipStrategy.class)
    void hideStorageExplanationDialog();

    @StateStrategyType(value = SkipStrategy.class)
    void requestStoragePermission();

    @StateStrategyType(value = SkipStrategy.class)
    void openPickPhoto();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onUserPhotoUpdated(String imageLink);

    @StateStrategyType(value = SkipStrategy.class)
    void onUserPhotoUpdateFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void closeActivity();

    @StateStrategyType(value = SkipStrategy.class)
    void shouldShowCameraRationale();

    @StateStrategyType(value = SkipStrategy.class)
    void showCameraExplanationDialog();

    @StateStrategyType(value = SkipStrategy.class)
    void hideCameraExplanationDialog();

    @StateStrategyType(value = SkipStrategy.class)
    void requestCameraPermission();

    @StateStrategyType(value = SkipStrategy.class)
    void openTakePhoto();

    @StateStrategyType(value = SkipStrategy.class)
    void showPhotoSourceDialog();

    @StateStrategyType(value = SkipStrategy.class)
    void hidePhotoSourceDialog();
}
