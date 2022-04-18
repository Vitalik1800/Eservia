package com.eservia.booking.ui.business_page.beauty.info;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.ui.business_page.gallery.BusinessGalleryExtra;
import com.eservia.booking.ui.business_page.gallery.GalleryActivity;
import com.eservia.booking.ui.business_page.marketing.BusinessMarketingsActivity;
import com.eservia.booking.ui.business_page.staff.BusinessStaffActivity;
import com.eservia.booking.ui.event_details.beauty.EventDetailsBeautyActivity;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.ui.staff.beauty.StaffBeautyActivity;
import com.eservia.booking.util.ViewUtil;
import com.eservia.butterknife.ButterKnife;
import com.eservia.expantabletextview.views.ExpandableTextView;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessPhoto;
import com.eservia.model.entity.Marketing;
import com.eservia.overscroll.OverScrollDecoratorHelper;
import com.eservia.utils.IntentUtil;
import com.eservia.utils.StringUtil;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class BusinessPageBeautyInfoFragment extends BaseHomeFragment implements
        BusinessPageBeautyInfoView {

    LinearLayout llContent;
    ConstraintLayout clGallery;
    ConstraintLayout clNews;
    RecyclerView rvGallery;
    RecyclerView rvStaff;
    RecyclerView rvNews;
    RelativeLayout rlCardHolderDescription;
    RelativeLayout rlCardHolderStaff;
    RelativeLayout rlCardHolderSocial;
    CardView cvContainerDescription;
    CardView cvContainerStaff;
    CardView cvContainerSocial;
    ExpandableTextView tvDescription;
    ImageView ivLinkWebsite;
    ImageView ivLinkFacebook;
    ImageView ivLinkInstagram;
    ImageView ivLinkTwitter;
    ImageView ivLinkGooglePlus;
    View layout_show_all_staff;

    @InjectPresenter
    BusinessPageBeautyInfoPresenter mPresenter;

    private Activity mActivity;

    private GalleryAdapter mGalleryAdapter;

    private StaffAdapter mStaffAdapter;

    private NewsAdapter mNewsAdapter;

    private boolean mCanLaunchTransition = true;

    public static BusinessPageBeautyInfoFragment newInstance() {
        return new BusinessPageBeautyInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_page_beauty_info, container, false);
        mActivity = getActivity();
        setUnbinder(ButterKnife.bind(this, view));
        llContent = view.findViewById(R.id.llContent);
        clGallery = view.findViewById(R.id.clGallery);
        clNews = view.findViewById(R.id.clNews);
        rvGallery = view.findViewById(R.id.rvGallery);
        rvStaff = view.findViewById(R.id.rvStaff);
        rvNews = view.findViewById(R.id.rvNews);
        rlCardHolderDescription = view.findViewById(R.id.rlCardHolderDescription);
        rlCardHolderStaff = view.findViewById(R.id.rlCardHolderStaff);
        rlCardHolderSocial = view.findViewById(R.id.rlCardHolderSocial);
        cvContainerDescription = view.findViewById(R.id.cvContainerDescription);
        cvContainerStaff = view.findViewById(R.id.cvContainerStaff);
        cvContainerSocial = view.findViewById(R.id.cvContainerSocial);
        tvDescription = view.findViewById(R.id.tvDescription);
        ivLinkWebsite = view.findViewById(R.id.ivLinkWebsite);
        ivLinkFacebook = view.findViewById(R.id.ivLinkFacebook);
        ivLinkInstagram = view.findViewById(R.id.ivLinkInstagram);
        ivLinkTwitter = view.findViewById(R.id.ivLinkTwitter);
        ivLinkGooglePlus = view.findViewById(R.id.ivLinkGooglePlus);
        layout_show_all_staff = view.findViewById(R.id.layout_show_all_staff);
        tvDescription.setOnClickListener(v -> onDescriptionClick());
        ivLinkWebsite.setOnClickListener(v -> onLinkWebsiteClick());
        ivLinkFacebook.setOnClickListener(v -> onLinkFacebookClick());
        ivLinkInstagram.setOnClickListener(v -> onLinkInstagramClick());
        clNews.setOnClickListener(v -> onNewsClick());
        cvContainerStaff.setOnClickListener(v -> onStaffsMoreClick());
        initViews();
        return view;
    }

    public void onDescriptionClick() {
        tvDescription.toggle();
    }

    public void onLinkWebsiteClick() {
        mPresenter.onOpenWebsiteClick();
    }

    public void onLinkFacebookClick() {
        mPresenter.onOpenFacebookClick();
    }

    public void onLinkInstagramClick() {
        mPresenter.onOpenInstagramClick();
    }

    public void onNewsClick() {
        mPresenter.onNewsMoreClick();
    }

    public void onStaffsMoreClick() {
        mPresenter.onStaffMoreClick();
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
    public void onBusiness(@Nullable Business business) {
        onContentChange();
        if (business == null) {
            llContent.setVisibility(View.INVISIBLE);
        } else {
            llContent.setVisibility(View.VISIBLE);
            bindDescription(business.getDescription());
            bindSocialLinks(business);
        }
    }

    @Override
    public void onPhotosLoadingSuccess(List<BusinessPhoto> businessPhotos) {
        mGalleryAdapter.replaceAll(businessPhotos);
        revalidateGallery();
    }

    @Override
    public void onPhotosLoadingFailed(Throwable throwable) {
        revalidateGallery();
    }

    @Override
    public void onStaffLoadingSuccess(List<BeautyStaff> beautyStaffs) {
        mStaffAdapter.replaceAll(beautyStaffs);
        revalidateStaffs();
    }

    @Override
    public void onStaffLoadingFailed(Throwable throwable) {
        revalidateStaffs();
    }

    @Override
    public void onNewsLoadingSuccess(List<Marketing> marketings) {
        mNewsAdapter.replaceAll(marketings);
        revalidateNews();
    }

    @Override
    public void onNewsLoadingFailed(Throwable throwable) {
        revalidateNews();
    }

    @Override
    public void showEventDetailBeautyPage(Marketing marketing) {
        EventDetailsBeautyActivity.start(mActivity, marketing, true);
    }

    @Override
    public void showBusinessMarketingsPage(Business business) {
        BusinessMarketingsActivity.start(mActivity, business);
    }

    @Override
    public void showBusinessStaffPage(Business business) {
        BusinessStaffActivity.start(mActivity, business);
    }

    @Override
    public void showStaffDetailBeautyPage(BeautyStaff staff) {
        StaffBeautyActivity.start(mActivity, staff);
    }

    @Override
    public void openLinkWebsite(String link) {
        IntentUtil.openLinkWebsite(mActivity, link);
    }

    @Override
    public void openLinkFacebook(String link) {
        IntentUtil.openLinkFacebook(mActivity, link);
    }

    @Override
    public void openLinkInstagram(String link) {
        IntentUtil.openLinkInstagram(mActivity, link);
    }

    @Override
    public void showGallery(BusinessGalleryExtra extra) {
        openGalleryActivity(extra);
    }

    private void bindDescription(String description) {
        if (StringUtil.isEmpty(description)) {
            rlCardHolderDescription.setVisibility(View.GONE);
        } else {
            rlCardHolderDescription.setVisibility(View.VISIBLE);
            tvDescription.setText(description);
        }
    }

    private void bindSocialLinks(Business business) {
        String website = business.getUrl();
        String facebook = business.getLinkFacebook();
        String instagram = business.getLinkInstagram();

        if (StringUtil.isEmpty(website)
                && StringUtil.isEmpty(facebook)
                && StringUtil.isEmpty(instagram)) {
            rlCardHolderSocial.setVisibility(View.GONE);
        } else {
            rlCardHolderSocial.setVisibility(View.VISIBLE);

            if (StringUtil.isEmpty(website)) {
                ivLinkWebsite.setVisibility(View.GONE);
            } else {
                ivLinkWebsite.setVisibility(View.VISIBLE);
            }

            if (StringUtil.isEmpty(facebook)) {
                ivLinkFacebook.setVisibility(View.GONE);
            } else {
                ivLinkFacebook.setVisibility(View.VISIBLE);
            }

            if (StringUtil.isEmpty(instagram)) {
                ivLinkInstagram.setVisibility(View.GONE);
            } else {
                ivLinkInstagram.setVisibility(View.VISIBLE);
            }

            ivLinkTwitter.setVisibility(View.GONE);
            ivLinkGooglePlus.setVisibility(View.GONE);
        }
    }

    private void revalidateGallery() {
        onContentChange();
        if (mGalleryAdapter.getItemCount() <= 0) {
            clGallery.setVisibility(View.GONE);
        } else {
            clGallery.setVisibility(View.VISIBLE);
        }
    }

    private void revalidateNews() {
        onContentChange();
        if (mNewsAdapter.getItemCount() <= 0) {
            clNews.setVisibility(View.GONE);
        } else {
            clNews.setVisibility(View.VISIBLE);
        }
    }

    private void revalidateStaffs() {
        onContentChange();
        if (mStaffAdapter.getItemCount() <= 0) {
            rlCardHolderStaff.setVisibility(View.GONE);
        } else {
            rlCardHolderStaff.setVisibility(View.VISIBLE);
        }

        if (mStaffAdapter.getItemCount() > 10) {
            layout_show_all_staff.setVisibility(View.VISIBLE);
        } else {
            layout_show_all_staff.setVisibility(View.GONE);
        }
    }

    private void onContentChange() {
        if (mCanLaunchTransition) {
            mCanLaunchTransition = false;
            new TransitionIntervalTimer(400, 400).start();
            TransitionManager.beginDelayedTransition(llContent);
        }
    }

    private void initViews() {
        initGalleryList();
        initNewsList();
        initStaffList();
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderDescription, cvContainerDescription);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderStaff, cvContainerStaff);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderSocial, cvContainerSocial);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initGalleryList() {
        mGalleryAdapter = new GalleryAdapter(mActivity, mPresenter, mPresenter);
        rvGallery.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.HORIZONTAL, false));
        OverScrollDecoratorHelper.setUpOverScroll(rvGallery,
                OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rvGallery.setAdapter(mGalleryAdapter);
        mGalleryAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initNewsList() {
        mNewsAdapter = new NewsAdapter(mActivity, mPresenter, mPresenter);
        rvNews.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.HORIZONTAL, false));
        OverScrollDecoratorHelper.setUpOverScroll(rvNews,
                OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rvNews.setAdapter(mNewsAdapter);
        mNewsAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initStaffList() {
        mStaffAdapter = new StaffAdapter(mActivity, mPresenter, mPresenter);
        rvStaff.setLayoutManager(new SpeedyLinearLayoutManager(
                mActivity, SpeedyLinearLayoutManager.HORIZONTAL, false));
        OverScrollDecoratorHelper.setUpOverScroll(rvStaff,
                OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        rvStaff.setAdapter(mStaffAdapter);
        mStaffAdapter.notifyDataSetChanged();
    }

    private void openGalleryActivity(BusinessGalleryExtra extra) {
        GalleryActivity.start(mActivity, extra);
    }

    private class TransitionIntervalTimer extends CountDownTimer {

        public TransitionIntervalTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {
            mCanLaunchTransition = true;
        }
    }
}
