package com.eservia.booking.ui.profile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import com.eservia.booking.App;
import com.eservia.booking.R;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.model.entity.Gender;
import com.eservia.model.entity.ProfileUserData;
import com.eservia.model.prefs.AccessToken;
import com.eservia.model.prefs.Profile;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.users.services.auth.LogoutRequest;
import com.eservia.model.remote.rest.users.services.auth.SocialSignInRequest;
import com.eservia.model.remote.rest.users.services.profile.EditProfileRequest;
import com.eservia.model.remote.rest.users.services.profile.EmailRequest;
import com.eservia.utils.PermissionCameraUtil;
import com.eservia.utils.PermissionStorageUtil;
import com.eservia.utils.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> implements
        SexAdapter.OnSexClickListener,
        DatePickerDialog.OnDateSetListener,
        StorageExplanationDialog.Listener,
        CameraExplanationDialog.Listener,
        PhotoSourceDialog.Listener {

    private final List<SexAdapterItem> mSexAdapterItems = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    @Inject
    Context mContext;

    private Disposable mLogoutDisposable;

    private Disposable mProfileDisposable;

    private Disposable mSocialDisposable;

    private long mSelectedSex = Profile.getGender();

    private long mBirthDay = Profile.getBirthDate();

    private boolean mPhotoUpdate = false;

    public ProfilePresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadUserInfo();
        try {
            loadUserPhoto();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        fillSexAdapterItems();
    }

    @Override
    public void onSexItemClick(SexAdapterItem item) {
        checkSexItem(item.getSex());
        getViewState().hideSexSelectDialog();
        getViewState().showSexStatus(mSelectedSex);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.set(year, month, dayOfMonth);
        mBirthDay = calendar.getTimeInMillis();
        getViewState().showBirthDate(mBirthDay);
    }

    @Override
    public void onChoosePhotoClick() {
        getViewState().hidePhotoSourceDialog();
        onChoosePhoto();
    }

    @Override
    public void onTakePhotoClick() {
        getViewState().hidePhotoSourceDialog();
        onTakePhoto();
    }

    void onLogoutClicked() {
        if (paginationInProgress(mLogoutDisposable)) {
            return;
        }
        logoutUser();
    }

    void onRegisterProvider(String provider, String token) {
        if (paginationInProgress(mSocialDisposable)) {
            return;
        }
        registerProvider(provider, token);
    }

    void onRemoveProvider(String provider) {
        if (paginationInProgress(mSocialDisposable)) {
            return;
        }
        removeProvider(provider);
    }

    void onChangePasswordClick() {
        getViewState().openChangePasswordActivity();
    }

    void onChangeSexClick() {
        getViewState().showSexSelectDialog(mSexAdapterItems);
    }

    void onChangeBirthdayClick() {
        if (mBirthDay == 0) {
            Calendar calendar = getMaxBirthday();
            getViewState().showDatePickDialog(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.getTimeInMillis());
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mBirthDay);
            getViewState().showDatePickDialog(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    getMaxBirthday().getTimeInMillis());
        }
    }

    void onChoosePhoto() {
        if (PermissionStorageUtil.isStoragePermissionGranted(mContext)) {
            onStoragePermissionGranted();
        } else {
            getViewState().shouldShowStorageRationale();
        }
    }

    void onTakePhoto() {
        if (PermissionCameraUtil.isCameraAndStoragePermissionGranted(mContext)) {
            onCameraPermissionGranted();
        } else {
            getViewState().shouldShowCameraRationale();
        }
    }

    void onShouldShowStorageRationale() {
        getViewState().showStorageExplanationDialog();
    }

    void onShouldNotShowStorageRationale() {
        getViewState().showStorageExplanationDialog();
    }

    void onShouldShowCameraRationale() {
        getViewState().showCameraExplanationDialog();
    }

    void onShouldNotShowCameraRationale() {
        getViewState().showCameraExplanationDialog();
    }

    @Override
    public void onStorageExplanationDialogOkClick() {
        getViewState().hideStorageExplanationDialog();
    }

    @Override
    public void onStorageExplanationDialogDismissed() {
        getViewState().requestStoragePermission();
    }

    @Override
    public void onCameraExplanationDialogOkClick() {
        getViewState().hideCameraExplanationDialog();
    }

    @Override
    public void onCameraExplanationDialogDismissed() {
        getViewState().requestCameraPermission();
    }

    void onStoragePermissionGranted() {
        getViewState().openPickPhoto();
    }

    void onStoragePermissionNotGranted() {
    }

    void onCameraPermissionGranted() {
        getViewState().openTakePhoto();
    }

    void onCameraPermissionNotGranted() {
    }

    void onUpdatePhoto(File file, String mediaType) {
        if (paginationInProgress(mProfileDisposable)) {
            return;
        }
        updateUserPhoto(file, mediaType);
    }

    void onUpdateEmail(String email) {
        if (paginationInProgress(mProfileDisposable)) {
            return;
        }
        updateEmail(email);
    }

    void onUpdateUserInfo(String firstName, String lastName) {
        if (paginationInProgress(mProfileDisposable)) {
            return;
        }
        updateUserInfo(firstName, lastName, Profile.getPhotoId(), mSelectedSex, mBirthDay);
    }

    void onUpdateUserInfoAndEmail(String firstName, String lastName, String email) {
        if (paginationInProgress(mProfileDisposable)) {
            return;
        }
        if (StringUtil.isEmpty(email)) {
            updateUserInfo(firstName, lastName, Profile.getPhotoId(), mSelectedSex, mBirthDay);
        } else {
            updateUserInfoAndEmail(firstName, lastName, Profile.getPhotoId(), mSelectedSex,
                    mBirthDay, email);
        }
    }

    private void loadUserInfo() {
        UserInfo info = new UserInfo();
        info.setEmail(Profile.getUserEmail());
        info.setFirstName(Profile.getUserFirstName());
        info.setLastName(Profile.getUserLastName());
        info.setPhone(Profile.getUserPhoneNumber());
        info.setGoogleAttached(Profile.getGoogleId() != null && !Profile.getGoogleId().isEmpty());
        info.setFacebookAttached(Profile.getFacebookId() != null && !Profile.getFacebookId().isEmpty());
        info.setEmailConfirmed(Profile.isEmailConfirmed());
        info.setSexId(Profile.getGender());
        info.setBirthday(Profile.getBirthDate());
        getViewState().onUserInfoLoaded(info);
    }

    private void loadUserPhoto() throws ClassNotFoundException {
        getViewState().onUserPhotoLoaded(Profile.getPhotoId());
    }

    private void logoutUser() {
        getViewState().showProgress();
        mLogoutDisposable = mRestManager
                .logout(new LogoutRequest(AccessToken.getSessionId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onLogoutSuccess(), this::onLogoutFailed);
        addSubscription(mLogoutDisposable);
    }

    private void registerProvider(String provider, String token) {
        getViewState().showProgress();
        mSocialDisposable = mRestManager
                .attachSocialAccount(new SocialSignInRequest(provider, token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> onRegisterProviderSuccess(r, provider),
                        e -> onErrorRegisterProvider(e, provider));
        addSubscription(mSocialDisposable);
    }

    private void removeProvider(String provider) {
        getViewState().showProgress();
        mSocialDisposable = mRestManager
                .detachSocialAccount(provider)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> onRemoveProviderSuccess(r, provider),
                        e -> onErrorRemoveProvider(e, provider));
        addSubscription(mSocialDisposable);
    }

    private void updateUserPhoto(File file, String mediaType) {
        getViewState().showAvatarLoading();
        mProfileDisposable = mRestManager
                .uploadPhoto(ImageUtil.createImageRequestBody(file, mediaType))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onUserPhotoUpdated(), this::onUserPhotoUpdateFailed);
        addSubscription(mProfileDisposable);
    }

    private void updateEmail(String email) {
        getViewState().showProgress();
        mProfileDisposable = mRestManager
                .sendEmail(new EmailRequest(email))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onEmailUpdateSuccess(), this::onEmailUpdateFailed);
        addSubscription(mProfileDisposable);
    }

    private void updateUserInfo(String firstName, String lastName, String photoPath,
                                Long sexId, Long birthday) {
        getViewState().showProgress();

        EditProfileRequest request = new EditProfileRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setBirthday(birthday);
        request.setGender(sexId);
        request.setPhotoPath(photoPath);

        mProfileDisposable = mRestManager
                .updateProfile(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onUserInfoUpdated(), this::onUserInfoUpdateFailed);
        addSubscription(mProfileDisposable);
    }

    private void updateUserInfoAndEmail(String firstName, String lastName, String photoPath,
                                        Long sexId, Long birthday, String email) {
        getViewState().showProgress();

        EditProfileRequest request = new EditProfileRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setBirthday(birthday);
        request.setGender(sexId);
        request.setPhotoPath(photoPath);

        mProfileDisposable = mRestManager
                .updateProfile(request)
                .flatMap(r -> mRestManager.sendEmail(new EmailRequest(email)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onUserInfoAndEmailUpdated(),
                        this::onUserInfoAndEmailUpdateFailed);
        addSubscription(mProfileDisposable);
    }

    private void onLogoutSuccess() {
        getViewState().hideProgress();
        getViewState().onLogOutSuccess();
        mLogoutDisposable = null;
    }

    private void onLogoutFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onLogOutFailed(throwable);
        mLogoutDisposable = null;
    }

    private void onRegisterProviderSuccess(ProfileUserData data, String provider) {
        getViewState().hideProgress();
        getViewState().onRegisterProviderSuccess(provider);
        mSocialDisposable = null;
    }

    private void onErrorRegisterProvider(Throwable throwable, String provider) {
        getViewState().hideProgress();
        getViewState().onErrorRegisterProvider(throwable, provider);
        mSocialDisposable = null;
    }

    private void onRemoveProviderSuccess(ProfileUserData data, String provider) {
        getViewState().hideProgress();
        getViewState().onRemoveProviderSuccess(provider);
        mSocialDisposable = null;
    }

    private void onErrorRemoveProvider(Throwable throwable, String provider) {
        getViewState().hideProgress();
        getViewState().onErrorRemoveProvider(throwable, provider);
        mSocialDisposable = null;
    }

    private void onUserPhotoUpdated() {
        getViewState().hideAvatarLoading();
        getViewState().onUserPhotoUpdated(Profile.getPhotoId());
        mProfileDisposable = null;

        mPhotoUpdate = true;
        updateUserInfo(Profile.getUserFirstName(),
                Profile.getUserLastName(),
                Profile.getPhotoId(),
                Profile.getGender(),
                Profile.getBirthDate());
    }

    private void onUserPhotoUpdateFailed(Throwable throwable) {
        getViewState().hideAvatarLoading();
        getViewState().onUserPhotoUpdateFailed(throwable);
        mProfileDisposable = null;
    }

    private void onEmailUpdateSuccess() {
        getViewState().hideProgress();
        getViewState().onEmailUpdateSuccess();
        mProfileDisposable = null;
        loadUserInfo();
    }

    private void onEmailUpdateFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onEmailUpdateFailed(throwable);
        mProfileDisposable = null;
    }

    private void onUserInfoUpdated() {
        getViewState().hideProgress();
        getViewState().onUserInfoUpdated();
        mProfileDisposable = null;

        if (mPhotoUpdate) {
            mPhotoUpdate = false;
            loadUserInfo();
        } else {
            getViewState().closeActivity();
        }
    }

    private void onUserInfoUpdateFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onUserInfoUpdateFailed(throwable);
        mProfileDisposable = null;
    }

    private void onUserInfoAndEmailUpdated() {
        getViewState().hideProgress();
        getViewState().onUserInfoAndEmailUpdated();
        mProfileDisposable = null;

        if (mPhotoUpdate) {
            mPhotoUpdate = false;
            loadUserInfo();
        } else {
            getViewState().closeActivity();
        }
    }

    private void onUserInfoAndEmailUpdateFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onUserInfoAndEmailUpdateFailed(throwable);
        mProfileDisposable = null;
    }

    private void checkSexItem(long sex) {
        for (SexAdapterItem i : mSexAdapterItems) {
            i.setChecked(i.getSex() == sex);
        }
        mSelectedSex = sex;
    }

    private void fillSexAdapterItems() {
        mSexAdapterItems.clear();

        mSexAdapterItems.add(new SexAdapterItem(Gender.MALE, R.string.male,
                mSelectedSex == Gender.MALE));
        mSexAdapterItems.add(new SexAdapterItem(Gender.FEMALE, R.string.female,
                mSelectedSex == Gender.FEMALE));
    }

    @NonNull
    private Calendar getMaxBirthday() {
        Calendar maxDate = Calendar.getInstance();
        maxDate.set(Calendar.HOUR_OF_DAY, 23);
        maxDate.set(Calendar.MINUTE, 59);
        maxDate.set(Calendar.SECOND, 59);
        maxDate.add(Calendar.YEAR, -ValidatorUtil.MIN_PROFILE_YEARS);
        return maxDate;
    }
}
