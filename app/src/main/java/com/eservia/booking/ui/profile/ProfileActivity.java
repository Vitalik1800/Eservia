package com.eservia.booking.ui.profile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.ui.auth.login.facebook.FacebookLoginManager;
import com.eservia.booking.ui.auth.login.google.GoogleLoginManager;
import com.eservia.booking.ui.profile.password.UpdatePasswordActivity;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.FileUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.ServerErrorUtil;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.glide.Glide;
import com.eservia.glide.load.DataSource;
import com.eservia.glide.load.engine.DiskCacheStrategy;
import com.eservia.glide.load.engine.GlideException;
import com.eservia.glide.request.RequestListener;
import com.eservia.glide.request.RequestOptions;
import com.eservia.glide.request.target.Target;
import com.eservia.model.entity.Gender;
import com.eservia.model.entity.PhotoSize;
import com.eservia.model.entity.Provider;
import com.eservia.model.prefs.Profile;
import com.eservia.model.remote.rest.users.codes.UsersErrorCode;
import com.eservia.utils.AccountUtil;
import com.eservia.utils.KeyboardUtil;
import com.eservia.utils.PermissionCameraUtil;
import com.eservia.utils.PermissionStorageUtil;
import com.eservia.utils.StringUtil;
import com.google.android.material.appbar.AppBarLayout;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.File;
import java.util.List;

import moxy.presenter.InjectPresenter;

public class ProfileActivity extends BaseActivity implements ProfileView,
        FacebookLoginManager.FacebookLoginListener,
        GoogleLoginManager.GoogleLoginListener {

    private static final int CODE_REQUEST_STORAGE = 99;

    private static final int CODE_REQUEST_CHOOSE_PHOTO = 98;

    private static final int CODE_REQUEST_CAMERA = 97;

    private static final int CODE_REQUEST_TAKE_PHOTO = 96;

    private static final int CODE_REQUEST_CROP_PHOTO = 95;

    private static final String SELECTED_PHOTO_NAME = "selected.jpg";

    CoordinatorLayout coordinator;
    AppBarLayout app_bar_layout;
    Toolbar toolbar;
    ProgressBar pbProgress;
    ProgressBar pbAvatar;
    ImageView ivAccountPicture;
    RelativeLayout rlAccountPicture;
    EditText etEmail;
    EditText etName;
    EditText etSurname;
    EditText etPhone;
    EditText etFacebook;
    EditText etGoogle;
    RelativeLayout rlCardHolderSettingsMain;
    CardView cvContainerSettingsMain;
    RelativeLayout rlCardHolderSettingsAdditional;
    CardView cvContainerSettingsAdditional;
    RelativeLayout rlCardHolderSocial;
    CardView cvContainerSettingsSocial;
    RelativeLayout rlCardHolderLogout;
    CardView cvContainerSettingsLogout;
    TextView tvTitleUserName;
    EditText etPassword;
    EditText etSex;
    EditText etDateOfBirth;
    ImageView ivProfileBack;
    RelativeLayout rlProfileBack;
    ImageView ivGoogleAddRemove;
    ImageView ivFacebookAddRemove;
    RelativeLayout rlPassword;
    RelativeLayout rlSex;
    RelativeLayout rlDateOfBirth;
    RelativeLayout rlGoogle;
    RelativeLayout rlFacebook;
    @InjectPresenter
    ProfilePresenter mPresenter;

    private FacebookLoginManager mFacebookLoginManager;

    private GoogleLoginManager mGoogleLoginManager;

    private MenuItem mItemSaveProfile;

    private SexSelectDialog mSexSelectSheetDialog;

    private StorageExplanationDialog mStorageExplanationDialog;

    private CameraExplanationDialog mCameraExplanationDialog;

    private PhotoSourceDialog mPhotoSourceDialog;

    private File mTempPhotoFile;

    private Uri mTempPhotoUri;

    public static void start(Context context) {
        Intent starter = new Intent(context, ProfileActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        coordinator = findViewById(R.id.coordinator);
        toolbar = findViewById(R.id.toolbar);
        app_bar_layout = findViewById(R.id.app_bar_layout);
        pbProgress = findViewById(R.id.pbProgress);
        pbAvatar = findViewById(R.id.pbAvatar);
        ivAccountPicture = findViewById(R.id.ivAccountPicture);
        rlAccountPicture = findViewById(R.id.rlAccountPicture);
        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etPhone = findViewById(R.id.etPhone);
        etFacebook = findViewById(R.id.etFacebook);
        etGoogle = findViewById(R.id.etGoogle);
        rlCardHolderSettingsMain = findViewById(R.id.rlCardHolderSettingsMain);
        cvContainerSettingsMain = findViewById(R.id.cvContainerSettingsMain);
        rlCardHolderSettingsAdditional = findViewById(R.id.rlCardHolderSettingsAdditional);
        cvContainerSettingsAdditional = findViewById(R.id.cvContainerSettingsAdditional);
        rlCardHolderSocial = findViewById(R.id.rlCardHolderSocial);
        cvContainerSettingsSocial = findViewById(R.id.cvContainerSettingsSocial);
        rlCardHolderLogout = findViewById(R.id.rlCardHolderLogout);
        cvContainerSettingsLogout = findViewById(R.id.cvContainerSettingsLogout);
        tvTitleUserName = findViewById(R.id.tvTitleUserName);
        etPassword = findViewById(R.id.etPassword);
        etSex = findViewById(R.id.etSex);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        ivProfileBack = findViewById(R.id.ivProfileBack);
        rlProfileBack = findViewById(R.id.rlProfileBack);
        ivGoogleAddRemove = findViewById(R.id.ivGoogleAddRemove);
        ivFacebookAddRemove = findViewById(R.id.ivFacebookAddRemove);
        rlPassword = findViewById(R.id.rlPassword);
        rlSex = findViewById(R.id.rlSex);
        rlDateOfBirth = findViewById(R.id.rlDateOfBirth);
        rlGoogle = findViewById(R.id.rlGoogle);
        rlFacebook = findViewById(R.id.rlFacebook);
        cvContainerSettingsLogout.setOnClickListener(v -> onLogoutClick());
        rlPassword.setOnClickListener(v -> onChangePasswordClick());
        etPassword.setOnClickListener(v -> onPasswordClick());
        rlSex.setOnClickListener(v -> onChangeSexClick());
        etSex.setOnClickListener(v -> onSexClick());
        rlDateOfBirth.setOnClickListener(v -> onChangeBirthdayClick());
        etDateOfBirth.setOnClickListener(v -> onBirthdayClick());
        rlGoogle.setOnClickListener(v -> onGoogleClick());
        rlFacebook.setOnClickListener(v -> onFacebookClick());
        etGoogle.setOnClickListener(v -> onGoogleFormClick());
        etFacebook.setOnClickListener(v -> onFacebookFormClick());
        ivAccountPicture.setOnClickListener(v -> onUserPhotoClicked());
        initViews();
    }

    @Override
    public void onDestroy() {
        if (mGoogleLoginManager != null) {
            mGoogleLoginManager.dispose();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        this.mItemSaveProfile = menu.findItem(R.id.item_profile_save);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.item_profile_save) {
            tryUpdateUserInfo();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onLogoutClick() {
        mPresenter.onLogoutClicked();
    }
    public void onChangePasswordClick() {
        mPresenter.onChangePasswordClick();
    }
    public void onPasswordClick() {
        mPresenter.onChangePasswordClick();
    }
    public void onChangeSexClick() {
        mPresenter.onChangeSexClick();
    }
    public void onSexClick() {
        mPresenter.onChangeSexClick();
    }
    public void onChangeBirthdayClick() {
        mPresenter.onChangeBirthdayClick();
    }
    public void onBirthdayClick() {
        mPresenter.onChangeBirthdayClick();
    }
    public void onGoogleClick() {
        handleGoogleClick();
    }
    public void onFacebookClick() {
        handleFacebookClick();
    }
    public void onGoogleFormClick() {
        handleGoogleClick();
    }
    public void onFacebookFormClick() {
        handleFacebookClick();
    }
    public void onUserPhotoClicked() {
        showPhotoSourceDialog();
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (mGoogleLoginManager != null) {
            mGoogleLoginManager.onActivityResult(reqCode, resultCode, data);
        }
        if (mFacebookLoginManager != null) {
            mFacebookLoginManager.setResult(reqCode, resultCode, data);
        }
        if (resultCode == RESULT_OK) {
            if (reqCode == CODE_REQUEST_CHOOSE_PHOTO) {
                onChoosePhotoResult(data);
            }
            if (reqCode == CODE_REQUEST_TAKE_PHOTO) {
                onTakePhotoResult(data);
            }
            if (reqCode == CODE_REQUEST_CROP_PHOTO) {
                onCropPhotoResult(data);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_REQUEST_STORAGE: {
                if (PermissionStorageUtil.permissionWasGranted(grantResults)) {
                    mPresenter.onStoragePermissionGranted();
                } else {
                    mPresenter.onStoragePermissionNotGranted();
                }
                break;
            }
            case CODE_REQUEST_CAMERA: {
                if (PermissionCameraUtil.permissionWasGranted(grantResults)) {
                    mPresenter.onCameraPermissionGranted();
                } else {
                    mPresenter.onCameraPermissionNotGranted();
                }
                break;
            }
        }
    }

    @Override
    public void showProgress() {
        disableMenus();
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        enableMenus();
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void onUserPhotoLoaded(String imageLink)  {
        try {
            setUserPhoto(imageLink);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUserInfoLoaded(UserInfo userInfo) {
        String firstName = userInfo.getFirstName();
        String lastName = userInfo.getLastName();
        tvTitleUserName.setText(AccountUtil.getFullName(firstName, lastName));
        etName.setText(firstName);
        etSurname.setText(lastName);
        etPhone.setText(userInfo.getPhone());
        etEmail.setText(userInfo.getEmail());
        showEmailStatus(userInfo.getEmail(), userInfo.isEmailConfirmed());
        showGoogleStatus(userInfo.isGoogleAttached());
        showFacebookStatus(userInfo.isFacebookAttached());
        showSexStatus(userInfo.getSexId());
        showBirthDate(userInfo.getBirthday());
    }

    @Override
    public void showSexStatus(long sex) {
        if (sex == Gender.MALE) {
            etSex.setText(getResources().getString(R.string.male));
            rlProfileBack.setBackground(ContextCompat.getDrawable(this,
                    R.drawable.background_profile_header_man));
        } else if (sex == Gender.FEMALE) {
            etSex.setText(getResources().getString(R.string.female));
            rlProfileBack.setBackground(ContextCompat.getDrawable(this,
                    R.drawable.background_profile_header_woman));
        } else {
            etSex.setText("");
            rlProfileBack.setBackground(ContextCompat.getDrawable(this,
                    R.drawable.background_profile_header_woman));
        }
    }

    @Override
    public void showBirthDate(long birthDate) {
        if (birthDate == 0) {
            etDateOfBirth.setText("");
        } else {
            DateTime dateTime = new DateTime(Long.valueOf(birthDate), DateTimeZone.UTC);
            etDateOfBirth.setText(dateTime.toString(BookingUtil.DAY_PATTERN));
        }
    }

    @Override
    public void showAvatarLoading() {
        pbAvatar.setVisibility(View.VISIBLE);
        rlAccountPicture.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideAvatarLoading() {
        pbAvatar.setVisibility(View.GONE);
        rlAccountPicture.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLogOutSuccess() {
        openLoginActivity();
    }

    @Override
    public void onLogOutFailed(Throwable throwable) {
        MessageUtil.showSnackbar(coordinator, throwable);
    }

    @Override
    public void openChangePasswordActivity() {
        UpdatePasswordActivity.start(this);
    }

    @Override
    public void onEmailUpdateSuccess() {
        MessageUtil.showToast(this, R.string.profile_updated);
    }

    @Override
    public void onEmailUpdateFailed(Throwable throwable) {
        MessageUtil.showSnackbar(coordinator, throwable);
    }

    @Override
    public void onUserInfoUpdated() {
        MessageUtil.showToast(this, R.string.profile_updated);
    }

    @Override
    public void onUserInfoUpdateFailed(Throwable throwable) {
        MessageUtil.showSnackbar(coordinator, throwable);
    }

    @Override
    public void onUserInfoAndEmailUpdated() {
        MessageUtil.showToast(this, R.string.profile_updated);
    }

    @Override
    public void onUserInfoAndEmailUpdateFailed(Throwable throwable) {
        MessageUtil.showSnackbar(coordinator, throwable);
    }

    @Override
    public void showSexSelectDialog(List<SexAdapterItem> adapterItemList) {
        KeyboardUtil.hideSoftKeyboard(this);
        openSexSelectDialog(adapterItemList);
    }

    @Override
    public void hideSexSelectDialog() {
        if (mSexSelectSheetDialog != null) {
            mSexSelectSheetDialog.dismiss();
        }
    }

    @Override
    public void showDatePickDialog(int year, int month, int day, long maxDate) {
        DatePickerDialog dialog = new DatePickerDialog(this, mPresenter, year, month, day);
        dialog.getDatePicker().setMaxDate(maxDate);
        dialog.show();
    }

    @Override
    public void onSuccessFacebookLogin(String token) {
        mPresenter.onRegisterProvider(Provider.FACEBOOK, token);
    }

    @Override
    public void onErrorFacebookLogin(String message) {
        MessageUtil.showSnackbar(coordinator, R.string.authorization_failed);
    }

    @Override
    public void onGoogleLoginSucceeded(String token) {
        mPresenter.onRegisterProvider(Provider.GOOGLE, token);
        if (mGoogleLoginManager != null) {
            mGoogleLoginManager.dispose();
            mGoogleLoginManager = null;
        }
    }

    @Override
    public void onGoogleLoginFailed(String message) {
        MessageUtil.showSnackbar(coordinator, R.string.authorization_failed);
        if (mGoogleLoginManager != null) {
            mGoogleLoginManager.dispose();
            mGoogleLoginManager = null;
        }
    }

    @Override
    public void onRegisterProviderSuccess(String provider) {
        MessageUtil.showToast(this, R.string.profile_register_provider_success);
        if (provider.equals(Provider.GOOGLE)) {
            showGoogleStatus(true);
        } else if (provider.equals(Provider.FACEBOOK)) {
            showFacebookStatus(true);
        }
    }

    @Override
    public void onErrorRegisterProvider(Throwable throwable, String provider) {
        if (ServerErrorUtil.getErrorCode(throwable) == UsersErrorCode.COULD_NOT_UPDATE_USER) {
            MessageUtil.showSnackbar(coordinator, R.string.could_not_attach_account);
        } else {
            MessageUtil.showSnackbar(coordinator, throwable);
        }
    }

    @Override
    public void onRemoveProviderSuccess(String provider) {
        MessageUtil.showToast(this, R.string.profile_remove_provider_success);
        if (provider.equals(Provider.GOOGLE)) {
            showGoogleStatus(false);
        } else if (provider.equals(Provider.FACEBOOK)) {
            showFacebookStatus(false);
        }
    }

    @Override
    public void onErrorRemoveProvider(Throwable throwable, String provider) {
        MessageUtil.showSnackbar(coordinator, throwable);
    }

    @Override
    public void shouldShowStorageRationale() {
        if (PermissionStorageUtil.shouldShowStorageRationale(this)) {
            mPresenter.onShouldShowStorageRationale();
        } else {
            mPresenter.onShouldNotShowStorageRationale();
        }
    }

    @Override
    public void shouldShowCameraRationale() {
        if (PermissionCameraUtil.shouldShowCameraRationale(this)) {
            mPresenter.onShouldShowCameraRationale();
        } else {
            mPresenter.onShouldNotShowCameraRationale();
        }
    }

    @Override
    public void showStorageExplanationDialog() {
        mStorageExplanationDialog = StorageExplanationDialog.newInstance();
        mStorageExplanationDialog.setListener(mPresenter);
        mStorageExplanationDialog.show(getSupportFragmentManager(),
                StorageExplanationDialog.class.getSimpleName());
    }

    @Override
    public void showCameraExplanationDialog() {
        mCameraExplanationDialog = CameraExplanationDialog.newInstance();
        mCameraExplanationDialog.setListener(mPresenter);
        mCameraExplanationDialog.show(getSupportFragmentManager(),
                CameraExplanationDialog.class.getSimpleName());
    }

    @Override
    public void hideStorageExplanationDialog() {
        if (mStorageExplanationDialog != null) {
            mStorageExplanationDialog.dismiss();
        }
    }

    @Override
    public void hideCameraExplanationDialog() {
        if (mCameraExplanationDialog != null) {
            mCameraExplanationDialog.dismiss();
        }
    }

    @Override
    public void showPhotoSourceDialog() {
        mPhotoSourceDialog = PhotoSourceDialog.newInstance();
        mPhotoSourceDialog.setListener(mPresenter);
        mPhotoSourceDialog.show(getSupportFragmentManager(),
                PhotoSourceDialog.class.getSimpleName());
    }

    @Override
    public void hidePhotoSourceDialog() {
        if (mPhotoSourceDialog != null) {
            mPhotoSourceDialog.dismiss();
        }
    }

    @Override
    public void requestStoragePermission() {
        PermissionStorageUtil.requestStoragePermission(this, CODE_REQUEST_STORAGE);
    }

    @Override
    public void requestCameraPermission() {
        PermissionCameraUtil.requestCameraPermission(this, CODE_REQUEST_CAMERA);
    }

    @Override
    public void openPickPhoto() {
        Intent imageDownload = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(imageDownload, CODE_REQUEST_CHOOSE_PHOTO);
    }

    @Override
    public void openTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mTempPhotoUri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(
                    intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, mTempPhotoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                                | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            startActivityForResult(intent, CODE_REQUEST_TAKE_PHOTO);
        }
    }

    @Override
    public void onUserPhotoUpdated(String imageLink)  {
        try {
            setUserPhoto(imageLink);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUserPhotoUpdateFailed(Throwable throwable) {
        MessageUtil.showSnackbar(coordinator, throwable);
    }

    @Override
    public void closeActivity() {
        finish();
    }

    private void onChoosePhotoResult(Intent data) {
        if (data.getData() == null) {
            return;
        }
        final Uri imageUri = data.getData();
        File file = ImageUtil.getFileFromUri(this, imageUri);
        String fileMediaType = FileUtil.getMediaType(this, imageUri);
        if (isPhotoOk(file, fileMediaType)) {
            showAvatarLoading();
            mPresenter.onUpdatePhoto(file, fileMediaType);
        }
    }

    private void onTakePhotoResult(Intent data) {
        cropImage(mTempPhotoUri, mTempPhotoUri);
    }

    private void onCropPhotoResult(Intent data) {
        mPresenter.onUpdatePhoto(mTempPhotoFile, FileUtil.getMediaType(this, mTempPhotoUri));
    }

    private boolean isPhotoOk(File file, String fileMediaType) {
        long fSize = FileUtil.getFolderSizeKb(file);
        if (fSize > FileUtil.MAX_AVATAR_SIZE_KB) {
            MessageUtil.showSnackbar(coordinator, R.string.err_big_image);
            return false;
        } else if (fSize < FileUtil.MIN_AVATAR_SIZE_KB) {
            MessageUtil.showSnackbar(coordinator, R.string.err_small_image);
            return false;
        } else if (!FileUtil.isAvatarMediaTypeOk(fileMediaType)) {
            MessageUtil.showSnackbar(coordinator, R.string.invalid_file_format);
            return false;
        } else {
            return true;
        }
    }

    public void cropImage(Uri photoUri, Uri saveIn) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP", photoUri);
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, saveIn);
        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        if (cropIntent.resolveActivity(getPackageManager()) != null) {
            List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(
                    cropIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                                Intent.FLAG_GRANT_READ_URI_PERMISSION);
                grantUriPermission(packageName, saveIn,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                                Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            startActivityForResult(cropIntent, CODE_REQUEST_CROP_PHOTO);
        } else {
            MessageUtil.showSnackbar(coordinator, R.string.error_crop_not_supported);
        }
    }

    private void tryUpdateUserInfo() {
        KeyboardUtil.hideSoftKeyboard(this);

        String email = etEmail.getText().toString();
        String firstName = etName.getText().toString();
        String lastName = etSurname.getText().toString();

        boolean emailError = hasEmailError();
        boolean userInfoError = hasUserInfoError();
        if (!emailError && !userInfoError) {
            mPresenter.onUpdateUserInfoAndEmail(firstName, lastName, email);
        }
    }

    private boolean hasEmailError() {
        if (!etEmail.getText().toString().isEmpty()) {
            etEmail.setError(ValidatorUtil.isEmailValid(this, etEmail.getText().toString()));
        } else if (!StringUtil.isEmpty(Profile.getUserEmail())) {
            etEmail.setError(ValidatorUtil.isEmailValid(this, etEmail.getText().toString()));
        } else {
            etEmail.setError(null);
        }

        boolean error = false;
        if (etEmail.getError() != null) {
            etEmail.requestFocus();
            error = true;
        }
        return error;
    }

    private boolean hasUserInfoError() {
        etName.setError(ValidatorUtil.isNameValid(this, etName.getText().toString()));

        if (!etSurname.getText().toString().isEmpty()) {
            etSurname.setError(ValidatorUtil.isNameValid(this, etSurname.getText().toString()));
        } else {
            etSurname.setError(null);
        }

        boolean error = false;
        if (etSurname.getError() != null) {
            etSurname.requestFocus();
            error = true;
        }
        if (etName.getError() != null) {
            etName.requestFocus();
            error = true;
        }
        return error;
    }

    private void showEmailStatus(String email, Boolean status) {
/*        if (email == null || email.isEmpty()) {
            btnApproveEmail.setText(resources.getString(R.string.save_email))
        } else {
            if (status) {
                btnApproveEmail.setText(resources.getString(R.string.email_approved))
            } else {
                btnApproveEmail.setText(resources.getString(R.string.approve_email))
            }
        }*/
    }

    private void showGoogleStatus(Boolean status) {
        if (status) {
            etGoogle.setText(getResources().getString(R.string.connected));
            etGoogle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            ivGoogleAddRemove.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.icon_cross_red));
        } else {
            etGoogle.setText(getResources().getString(R.string.not_connected));
            etGoogle.setTextColor(ContextCompat.getColor(this, R.color.colorInactive));
            ivGoogleAddRemove.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.cross_bold_blue));
        }
    }

    private void showFacebookStatus(Boolean status) {
        if (status) {
            etFacebook.setText(getResources().getString(R.string.connected));
            etFacebook.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            ivFacebookAddRemove.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.icon_cross_red));
        } else {
            etFacebook.setText(getResources().getString(R.string.not_connected));
            etFacebook.setTextColor(ContextCompat.getColor(this, R.color.colorInactive));
            ivFacebookAddRemove.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.cross_bold_blue));
        }
    }

    private void handleFacebookClick() {
        if (Profile.getFacebookId() != null) {
            mPresenter.onRemoveProvider(Provider.FACEBOOK);
        } else {
            getFacebookLoginManager().signIn(this, this);
        }
    }

    private void handleGoogleClick() {
        if (Profile.getGoogleId() != null) {
            mPresenter.onRemoveProvider(Provider.GOOGLE);
        } else {
            getGoogleLoginManager().startGoogleLogin();
        }
    }

    private void setUserPhoto(String imageLink) throws ClassNotFoundException {
        showAvatarLoading();
        Glide.with(this)
                .load(ImageUtil.getUserPhotoPath(PhotoSize.MIDDLE, imageLink))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        hideAvatarLoading();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        hideAvatarLoading();
                        return false;
                    }
                })
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.user_man_big))
                .apply(RequestOptions.errorOf(R.drawable.user_man_big))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(ivAccountPicture);

        Glide.with(this)
                .load(ImageUtil.getUserPhotoPath(PhotoSize.MIDDLE, imageLink))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(ivProfileBack);
    }

    private FacebookLoginManager getFacebookLoginManager() {
        if (mFacebookLoginManager == null) {
            mFacebookLoginManager = new FacebookLoginManager();
        }
        return mFacebookLoginManager;
    }

    private GoogleLoginManager getGoogleLoginManager() {
        if (mGoogleLoginManager == null) {
            mGoogleLoginManager = new GoogleLoginManager(this, this);
        }
        return mGoogleLoginManager;
    }

    private void disableMenus() {
        if (mItemSaveProfile != null) {
            mItemSaveProfile.setEnabled(false);
        }
    }

    private void enableMenus() {
        if (mItemSaveProfile != null) {
            mItemSaveProfile.setEnabled(true);
        }
    }

    private void openSexSelectDialog(List<SexAdapterItem> items) {
        mSexSelectSheetDialog = SexSelectDialog.newInstance();
        mSexSelectSheetDialog.setSexItemClickListener(mPresenter);
        mSexSelectSheetDialog.setAdapterItems(items);
        mSexSelectSheetDialog.show(getSupportFragmentManager(),
                SexSelectDialog.class.getSimpleName());
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        setOutlineProviders();
        removeKeyListeners();
        setUpOnEditorActionListeners();
        setViewTreeObservers();

        mTempPhotoFile = FileUtil.tempPhotoFile(SELECTED_PHOTO_NAME);

        mTempPhotoUri = FileUtil.provideTempPhotoUri(getApplicationContext(), mTempPhotoFile);
    }

    private void setOutlineProviders() {
        ViewUtil.setCardOutlineProvider(this, rlCardHolderSettingsMain,
                cvContainerSettingsMain);
        ViewUtil.setCardOutlineProvider(this, rlCardHolderSettingsAdditional,
                cvContainerSettingsAdditional);
        ViewUtil.setCardOutlineProvider(this, rlCardHolderSocial,
                cvContainerSettingsSocial);
        ViewUtil.setCardOutlineProvider(this, rlCardHolderLogout,
                cvContainerSettingsLogout);
    }

    private void removeKeyListeners() {
        etPhone.setKeyListener(null);
        etFacebook.setKeyListener(null);
        etGoogle.setKeyListener(null);
    }

    private void setUpOnEditorActionListeners() {
        etName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etSurname.requestFocus();
                ViewUtil.moveCursorToEnd(etSurname);
                return true;
            }
            return false;
        });

        etSurname.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etEmail.requestFocus();
                ViewUtil.moveCursorToEnd(etEmail);
                return true;
            }
            return false;
        });

        etEmail.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                KeyboardUtil.hideSoftKeyboard(this);
                return true;
            }
            return false;
        });
    }

    private void setViewTreeObservers() {
        etEmail.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                etEmail,
                new View[]{etName, etSurname, etPhone, etEmail, etPassword, etSex, etDateOfBirth,
                        etFacebook, etGoogle}));
    }
}
