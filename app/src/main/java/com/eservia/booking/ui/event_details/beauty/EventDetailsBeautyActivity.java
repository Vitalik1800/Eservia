package com.eservia.booking.ui.event_details.beauty;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.ui.booking.beauty.BookingBeautyActivity;
import com.eservia.booking.ui.booking.beauty.service.ExceededMaxSelectedServicesSheetDialog;
import com.eservia.booking.ui.business_page.beauty.BusinessPageBeautyActivity;
import com.eservia.booking.ui.gallery.GalleryActivity;
import com.eservia.booking.ui.gallery.GalleryExtra;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.TimeUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.glide.Glide;
import com.eservia.glide.load.engine.DiskCacheStrategy;
import com.eservia.glide.load.resource.drawable.DrawableTransitionOptions;
import com.eservia.glide.request.RequestOptions;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.Marketing;
import com.eservia.model.entity.MarketingLink;
import com.eservia.model.entity.MarketingWorkSchedule;
import com.eservia.utils.IntentUtil;
import com.eservia.utils.StringUtil;
import com.expandablelayout.ExpandableRelativeLayout;
import com.google.android.material.appbar.AppBarLayout;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import moxy.presenter.InjectPresenter;

public class EventDetailsBeautyActivity extends BaseActivity implements EventDetailsBeautyView,
        ScheduleAdapter.Listener {

    private static final String KEY_OPENED_FROM_BUSINESS_PAGE = "opened_from_business_page";

    CoordinatorLayout coordinator;
    AppBarLayout app_bar_layout;
    Toolbar toolbar;
    NestedScrollView nsvContentHolder;
    TextView tvToolbarTitle;
    RelativeLayout rlAccept;
    TextView tvAccept;
    LinearLayout llContentPage;
    ProgressBar pbProgress;
    RelativeLayout rlCardHolderEventDetails;
    CardView cvContainerEventDetails;
    RelativeLayout rlCardHolderSocial;
    CardView cvContainerSocial;
    ImageView ivEventImage;
    TextView tvTitle;
    TextView tvDescription;
    RelativeLayout rlMark;
    ImageView ivMark;
    TextView tvMark;
    TableLayout tlTimes;
    TableRow trPeriod;
    TableRow trTime;
    TextView tvTime;
    TextView tvPeriod;
    ImageView ivLinkWebsite;
    ImageView ivLinkFacebook;
    ImageView ivLinkInstagram;
    ImageView ivLinkTwitter;
    ImageView ivLinkGooglePlus;
    RecyclerView rvSchedules;
    ExpandableRelativeLayout erlSchedules;
    ImageView ivSchedulesExpand;
    TextView tvSocialTitle;
    RecyclerView rvServices;
    Button btnCommonBookingButton;

    @InjectPresenter
    EventDetailsBeautyPresenter mPresenter;

    private ScheduleAdapter mScheduleAdapter;

    private ServiceAdapter mServicesAdapter;

    private ExceededMaxSelectedServicesSheetDialog mErrorExceedDialog;

    public static void start(Context context, Marketing marketing, boolean openedFromBusinessPage) {
        EventBus.getDefault().postSticky(marketing);
        Intent starter = new Intent(context, EventDetailsBeautyActivity.class);
        starter.putExtra(KEY_OPENED_FROM_BUSINESS_PAGE, openedFromBusinessPage);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details_beauty);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        mPresenter = new EventDetailsBeautyPresenter();
        coordinator = findViewById(R.id.coordinator);
        toolbar = findViewById(R.id.toolbar);
        app_bar_layout = findViewById(R.id.app_bar_layout);
        nsvContentHolder = findViewById(R.id.nsvContentHolder);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        rlAccept = findViewById(R.id.rlAccept);
        tvAccept = findViewById(R.id.tvAccept);
        llContentPage = findViewById(R.id.llContentPage);
        pbProgress = findViewById(R.id.pbProgress);
        rlCardHolderEventDetails = findViewById(R.id.rlCardHolderEventDetails);
        cvContainerEventDetails = findViewById(R.id.cvContainerEventDetails);
        rlCardHolderSocial = findViewById(R.id.rlCardHolderSocial);
        cvContainerSocial = findViewById(R.id.cvContainerSocial);
        ivEventImage = findViewById(R.id.ivEventImage);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        rlMark = findViewById(R.id.rlMark);
        ivMark = findViewById(R.id.ivMark);
        tvMark = findViewById(R.id.tvMark);
        tlTimes = findViewById(R.id.tlTimes);
        tvPeriod = findViewById(R.id.tvPeriod);
        trTime = findViewById(R.id.trTime);
        tvTime = findViewById(R.id.tvTime);
        tvPeriod = findViewById(R.id.tvPeriod);
        ivLinkWebsite = findViewById(R.id.ivLinkWebsite);
        ivLinkFacebook = findViewById(R.id.ivLinkFacebook);
        ivLinkInstagram = findViewById(R.id.ivLinkInstagram);
        ivLinkTwitter = findViewById(R.id.ivLinkTwitter);
        ivLinkGooglePlus = findViewById(R.id.ivLinkGooglePlus);
        rvSchedules = findViewById(R.id.rvSchedules);
        erlSchedules = findViewById(R.id.erlSchedules);
        ivSchedulesExpand = findViewById(R.id.ivSchedulesExpand);
        tvSocialTitle = findViewById(R.id.tvSocialTitle);
        rvServices = findViewById(R.id.rvServices);
        btnCommonBookingButton = findViewById(R.id.btnCommonBookingButton);
        rlAccept.setOnClickListener(v -> onAcceptClicked());
        btnCommonBookingButton.setOnClickListener(v -> onBookSelectedServicesClick());
        tlTimes.setOnClickListener(v -> onTimeTableClicked());
        ivLinkWebsite.setOnClickListener(v -> onLinkWebsiteClick());
        ivLinkFacebook.setOnClickListener(v -> onLinkFacebookClick());
        ivLinkInstagram.setOnClickListener(v -> onLinkInstagramClick());
        ivLinkTwitter.setOnClickListener(v -> onLinkTwitterClick());
        ivLinkGooglePlus.setOnClickListener(v -> onLinkGooglePlusClick());
        ivEventImage.setOnClickListener(v -> onEventImageClick());
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAcceptClicked() {
        mPresenter.onAcceptClicked();
    }
    public void onBookSelectedServicesClick() {
        mPresenter.onBookSelectedServicesClick();
    }
    public void onTimeTableClicked() {
        if (erlSchedules.isExpanded()) {
            ivSchedulesExpand.animate().rotation(270).start();
            erlSchedules.collapse();
        } else {
            ivSchedulesExpand.animate().rotation(90).start();
            erlSchedules.expand();
        }
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
    public void onLinkTwitterClick() {
        mPresenter.onOpenTwitterClick();
    }
    public void onLinkGooglePlusClick() {
        mPresenter.onOpenGooglePlusClick();
    }
    public void onEventImageClick() {
        mPresenter.onOpenImageClick();
    }

    @Override
    public void onScheduleItemClick(MarketingWorkSchedule schedule, int position) {
        onTimeTableClicked();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void requiredArgs(){
        mPresenter.onArgs(getIntent().getBooleanExtra(KEY_OPENED_FROM_BUSINESS_PAGE, true));
    }

    @Override
    public void initAcceptButton(boolean show, String businessTitle) {
        if (show) {
            rlAccept.setVisibility(View.VISIBLE);
            tvAccept.setText(getResources().getString(R.string.go_to_, businessTitle));
        } else {
            rlAccept.setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMarketing(@Nullable Marketing marketing) {
        if (marketing == null) {
            llContentPage.setVisibility(View.GONE);
        } else {
            llContentPage.setVisibility(View.VISIBLE);

            try {
                bindMarketing(marketing);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void openLinkWebsite(String link) {
        IntentUtil.openLinkWebsite(this, link);
    }

    @Override
    public void openLinkFacebook(String link) {
        IntentUtil.openLinkFacebook(this, link);
    }

    @Override
    public void openLinkInstagram(String link) {
        IntentUtil.openLinkInstagram(this, link);
    }

    @Override
    public void openLinkGooglePlus(String link) {
        IntentUtil.openLinkWebsite(this, link);
    }

    @Override
    public void openLinkTwitter(String link) {
        IntentUtil.openLinkWebsite(this, link);
    }

    @Override
    public void showBusinessBeautyActivity(Business business) {
        BusinessPageBeautyActivity.start(this, business);
    }

    @Override
    public void showGalleryActivity(GalleryExtra extra) {
        GalleryActivity.start(this, extra);
    }

    @Override
    public void onServicesLoadingSuccess(List<ServiceAdapterItem> serviceList) {
        mServicesAdapter.replaceAll(serviceList);
        revalidateServicesList();
    }

    @Override
    public void onServicesLoadingFailed(Throwable throwable) {
        revalidateServicesList();
    }

    @Override
    public void setSelected(boolean selected, Integer serviceId) {
        mServicesAdapter.setSelected(selected, serviceId);
    }

    @Override
    public void showErrorExceedDialog() {
        openErrorExceedServicesDialog();
    }

    @Override
    public void hideErrorExceedDialog() {
        if (mErrorExceedDialog != null) {
            mErrorExceedDialog.dismiss();
        }
    }

    @Override
    public void revalidateBookButton(boolean isActive) {
        btnCommonBookingButton.setVisibility(isActive ? View.VISIBLE : View.GONE);
    }

    @Override
    public void goToBooking(Business business, List<Preparation> preparations) {
        BookingBeautyActivity.start(this, business, null, preparations);
    }

    private void revalidateServicesList() {
        TransitionManager.beginDelayedTransition(llContentPage, new Fade());
        if (mServicesAdapter.getItemCount() > 0) {
            rvServices.setVisibility(View.VISIBLE);
        } else {
            rvServices.setVisibility(View.GONE);
        }
    }

    private void openErrorExceedServicesDialog() {
        mErrorExceedDialog = ExceededMaxSelectedServicesSheetDialog.newInstance();
        mErrorExceedDialog.setListener(mPresenter);
        mErrorExceedDialog.show(getSupportFragmentManager(),
                ExceededMaxSelectedServicesSheetDialog.class.getSimpleName());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bindMarketing(Marketing marketing) throws ClassNotFoundException {
        bindBusiness(marketing.getBusiness());

        bindImage(marketing);

        bindDescription(marketing);

        bindType(marketing);

        bindSocialLinks(marketing.getLinks());
    }

    private void bindBusiness(@Nullable Business business) {
        if (business != null) {
            tvToolbarTitle.setText(business.getName());
        } else {
            tvToolbarTitle.setText("");
        }
    }

    private void bindImage(Marketing marketing) throws ClassNotFoundException {
        Glide.with(this)
                .load(ImageUtil.getUserPhotoPath("", marketing.getPathToPhoto()))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_background_default))
                .apply(RequestOptions.errorOf(R.drawable.ic_background_default))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivEventImage);
    }

    private void bindDescription(Marketing marketing) {
        if (marketing.getTitle() != null) {
            tvTitle.setText(marketing.getTitle());
        } else {
            tvTitle.setText("");
        }

        if (marketing.getDescription() != null) {
            tvDescription.setText(marketing.getDescription());
        } else {
            tvDescription.setText("");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bindType(Marketing marketing) {
        Integer type = marketing.getMarketingTypeId();
        if (type != null) {

            if (type.equals(Marketing.TYPE_NEWS)) {
                bindNewsTime(marketing);
                bindNewsMark(marketing);

            } else if (type.equals(Marketing.TYPE_EVENT)) {

                bindEventTime(marketing);
                bindEventMark(marketing);

            } else if (type.equals(Marketing.TYPE_PROMOTION)) {
                bindPromoTime(marketing);
                bindPromoMark(marketing);
            }
        } else {
            bindNewsTime(marketing);
            bindNewsMark(marketing);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bindNewsTime(Marketing marketing) {
        bindPeriod(marketing);
        bindTime(marketing);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bindEventTime(Marketing marketing) {
        bindPeriod(marketing);
        bindTime(marketing);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bindPromoTime(Marketing marketing) {
        bindPeriod(marketing);
        bindTime(marketing);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bindTime(Marketing marketing) {
        List<MarketingWorkSchedule> schedules = marketing.getWorkSchedule();

        if (schedules == null || schedules.isEmpty()) {
            trTime.setVisibility(View.GONE);
        } else {
            trTime.setVisibility(View.VISIBLE);

            if (schedules.size() == 1) {

                MarketingWorkSchedule sh = schedules.get(0);

                DateTime start = TimeUtil.dateTimeFromMillisOfDay(sh.getStartTime());
                DateTime end = TimeUtil.dateTimeFromMillisOfDay(sh.getEndTime());

                tvTime.setText(BusinessUtil.formatWeekDaysHoursPeriod(this,
                        sh.getDay(), start, end));

                rvSchedules.setVisibility(View.GONE);
                ivSchedulesExpand.setVisibility(View.GONE);

            } else {

                Collections.sort(schedules, Comparator.comparing(MarketingWorkSchedule::getDay));

                tvTime.setText(getResources().getString(R.string.schedule_of_holding));

                rvSchedules.setVisibility(View.VISIBLE);
                ivSchedulesExpand.setVisibility(View.VISIBLE);

                erlSchedules.collapse();

                mScheduleAdapter.replaceAll(schedules);
            }
        }
    }

    private void bindPeriod(Marketing marketing) {
        String dateStart = marketing.getBeginTime();
        String dateEnd = marketing.getEndTime();

        DateTime start = dateStart != null ? DateTime.parse(dateStart) : null;
        DateTime end = dateEnd != null ? DateTime.parse(dateEnd) : null;

        if (start == null && end == null) {
            trPeriod.setVisibility(View.GONE);
        } else {
            trPeriod.setVisibility(View.VISIBLE);
            tvPeriod.setText(BusinessUtil.formatEventStartEndTime(start, end));
        }
    }

    private void bindNewsMark(Marketing marketing) {
        rlMark.setVisibility(View.GONE);
    }

    private void bindEventMark(Marketing marketing) {
        rlMark.setVisibility(View.VISIBLE);
        ivMark.setBackground(ContextCompat.getDrawable(this, R.drawable.background_attention));
        tvMark.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.mark_event_text_size));
        tvMark.setText(getResources().getString(R.string.mark_event_sign));
    }

    private void bindPromoMark(Marketing marketing) {
        Integer discountType = marketing.getDiscountTypeId();
        Float discount = marketing.getDiscount();
        if (discount != null && discountType != null && discountType.equals(Marketing.DISCOUNT_PERCENT)) {
            rlMark.setVisibility(View.VISIBLE);
            ivMark.setBackground(ContextCompat.getDrawable(this, R.drawable.background_sale));
            tvMark.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.mark_promo_text_size));
            tvMark.setText(getResources().getString(R.string.sale_mark, marketing.getDiscount().intValue()));
        } else {
            rlMark.setVisibility(View.GONE);
        }
    }

    private void bindSocialLinks(List<MarketingLink> marketingLinks) {
        MarketingLink linkWebsite = findLink(marketingLinks, MarketingLink.TYPE_WEBSITE);
        MarketingLink linkFacebook = findLink(marketingLinks, MarketingLink.TYPE_FACEBOOK);
        MarketingLink linkInstagram = findLink(marketingLinks, MarketingLink.TYPE_INSTAGRAM);
        MarketingLink linkTwitter = findLink(marketingLinks, MarketingLink.TYPE_TWITTER);
        MarketingLink linkGooglePlus = findLink(marketingLinks, MarketingLink.TYPE_GOOGLE);

        String website = linkWebsite != null ? linkWebsite.getUrl() : null;
        String facebook = linkFacebook != null ? linkFacebook.getUrl() : null;
        String instagram = linkInstagram != null ? linkInstagram.getUrl() : null;
        String twitter = linkTwitter != null ? linkTwitter.getUrl() : null;
        String googlePlus = linkGooglePlus != null ? linkGooglePlus.getUrl() : null;

        if (StringUtil.isEmpty(website)
                && StringUtil.isEmpty(facebook)
                && StringUtil.isEmpty(instagram)
                && StringUtil.isEmpty(twitter)
                && StringUtil.isEmpty(googlePlus)) {
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

            if (StringUtil.isEmpty(twitter)) {
                ivLinkTwitter.setVisibility(View.GONE);
            } else {
                ivLinkTwitter.setVisibility(View.VISIBLE);
            }

            if (StringUtil.isEmpty(googlePlus)) {
                ivLinkGooglePlus.setVisibility(View.GONE);
            } else {
                ivLinkGooglePlus.setVisibility(View.VISIBLE);
            }
        }
    }

    private MarketingLink findLink(List<MarketingLink> links, int type) {
        if (links == null || links.isEmpty()) {
            return null;
        }
        for (MarketingLink link : links) {
            if (link.getSocialTypeId().equals(type)) {
                return link;
            }
        }
        return null;
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        ViewUtil.setCardOutlineProvider(this, rlCardHolderEventDetails, cvContainerEventDetails);
        ViewUtil.setCardOutlineProvider(this, rlCardHolderSocial, cvContainerSocial);

        initScheduleList();
        initServicesList();

        tvSocialTitle.setText(getResources().getString(R.string.follow_us_on_social_networks));
    }

    private void initScheduleList() {
        mScheduleAdapter = new ScheduleAdapter(this, this);
        rvSchedules.setHasFixedSize(true);
        rvSchedules.setLayoutManager(new SpeedyLinearLayoutManager(
                this, SpeedyLinearLayoutManager.VERTICAL, false));
        rvSchedules.setAdapter(mScheduleAdapter);
    }

    private void initServicesList() {
        mServicesAdapter = new ServiceAdapter(this, mPresenter);
        rvServices.setAdapter(mServicesAdapter);
        rvServices.setHasFixedSize(true);
        rvServices.setLayoutManager(new SpeedyLinearLayoutManager(
                this, SpeedyLinearLayoutManager.VERTICAL, false));
    }
}
