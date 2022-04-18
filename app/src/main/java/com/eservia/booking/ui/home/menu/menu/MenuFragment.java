package com.eservia.booking.ui.home.menu.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.eservia.booking.R;
import com.eservia.booking.ui.about_app.AboutAppActivity;
import com.eservia.booking.ui.contacts.ContactsActivity;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.ui.home.HomeActivity;
import com.eservia.booking.ui.profile.ProfileActivity;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.glide.Glide;
import com.eservia.glide.load.engine.DiskCacheStrategy;
import com.eservia.glide.load.resource.drawable.DrawableTransitionOptions;
import com.eservia.glide.request.RequestOptions;
import com.eservia.model.entity.PhotoSize;

import moxy.presenter.InjectPresenter;

public class MenuFragment extends BaseHomeFragment implements MenuView {

    public static final String TAG = "menu_fragment";

    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    RelativeLayout rlCardHolderProfile;
    CardView cvContainerProfile;
    RelativeLayout rlCardHolderWriteUs;
    CardView cvContainerWriteUs;
    RelativeLayout rlCardHolderPrivacy;
    CardView cvContainerPrivacy;
    RelativeLayout rlCardHolderTerms;
    CardView cvContainerTerms;
    RelativeLayout rlCardHolderAboutApp;
    CardView cvContainerAboutApp;
    ImageView ivUserImage;
    TextView tvUserPhone;
    TextView tvUserName;

    @InjectPresenter
    MenuPresenter mPresenter;

    private HomeActivity mActivity;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        mActivity = (HomeActivity) getActivity();
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        rlCardHolderProfile = view.findViewById(R.id.rlCardHolderProfile);
        cvContainerProfile = view.findViewById(R.id.cvContainerProfile);
        rlCardHolderWriteUs = view.findViewById(R.id.rlCardHolderWriteUs);
        cvContainerWriteUs = view.findViewById(R.id.cvContainerWriteUs);
        rlCardHolderPrivacy = view.findViewById(R.id.rlCardHolderPrivacy);
        cvContainerPrivacy = view.findViewById(R.id.cvContainerPrivacy);
        rlCardHolderTerms = view.findViewById(R.id.rlCardHolderTerms);
        cvContainerTerms = view.findViewById(R.id.cvContainerTerms);
        rlCardHolderAboutApp = view.findViewById(R.id.rlCardHolderAboutApp);
        cvContainerAboutApp = view.findViewById(R.id.cvContainerAboutApp);
        ivUserImage = view.findViewById(R.id.ivUserImage);
        tvUserPhone = view.findViewById(R.id.tvUserPhone);
        tvUserName = view.findViewById(R.id.tvUserName);
        rlCardHolderProfile.setOnClickListener(v -> onProfileSettingsClicked());
        rlCardHolderWriteUs.setOnClickListener(v -> onWriteToUsClicked());
        rlCardHolderPrivacy.setOnClickListener(v -> onPrivacyClicked());
        rlCardHolderTerms.setOnClickListener(v -> onTermsClicked());
        rlCardHolderAboutApp.setOnClickListener(v -> onAboutAppClicked());
        initViews();
        return view;
    }

    public void onProfileSettingsClicked() {
        mPresenter.onProfileSettingsClicked();
    }
    public void onWriteToUsClicked() {
        mPresenter.onWriteToUsClicked();
    }
    public void onPrivacyClicked() {
        mPresenter.onPrivacyClicked();
    }
    public void onTermsClicked() {
        mPresenter.onTermsClicked();
    }
    public void onAboutAppClicked() {
        mPresenter.onAboutAppClicked();
    }

    @Override
    public void refresh() {
    }

    @Override
    public void willBeDisplayed() {
        WindowUtils.setLightStatusBar(mActivity);
        FragmentUtil.startFragmentTabSelectAnimation(getActivity(), fragmentContainer);
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
    public void onUserPhotoLoaded(String photoId) {
        try {
            Glide.with(mActivity)
                    .load(ImageUtil.getUserPhotoPath(PhotoSize.MIDDLE, photoId))
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.placeholderOf(R.drawable.user_man_big))
                    .apply(RequestOptions.errorOf(R.drawable.user_man_big))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivUserImage);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUserNameSurnameLoaded(String nameSurname) {
        tvUserName.setText(nameSurname);
    }

    @Override
    public void onUserPhoneLoaded(String phone) {
        tvUserPhone.setText(phone);
    }

    @Override
    public void openProfileSettings() {
        ProfileActivity.start(mActivity);
    }

    @Override
    public void openWriteToUs() {
        ContactsActivity.start(mActivity);
    }

    @Override
    public void openPrivacy(String privacyUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(privacyUrl));
        startActivity(Intent.createChooser(intent, ""));
    }

    @Override
    public void openTerms(String termsUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(termsUrl));
        startActivity(Intent.createChooser(intent, ""));
    }

    @Override
    public void openAboutApp() {
        AboutAppActivity.start(mActivity);
    }

    private void initViews() {

        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderProfile, cvContainerProfile);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderWriteUs, cvContainerWriteUs);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderPrivacy, cvContainerPrivacy);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderTerms, cvContainerTerms);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderAboutApp, cvContainerAboutApp);
    }
}
