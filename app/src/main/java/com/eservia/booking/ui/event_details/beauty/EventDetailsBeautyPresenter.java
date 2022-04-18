package com.eservia.booking.ui.event_details.beauty;

import android.content.Context;

import androidx.annotation.Nullable;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.ui.booking.beauty.service.ExceededMaxSelectedServicesSheetDialog;
import com.eservia.booking.ui.gallery.GalleryExtra;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.model.entity.BeautyPromotion;
import com.eservia.model.entity.BeautyService;
import com.eservia.model.entity.Marketing;
import com.eservia.model.entity.MarketingLink;
import com.eservia.model.entity.MarketingWorkSchedule;
import com.eservia.model.interactors.business.BusinessInteractor;
import com.eservia.model.interactors.marketing.MarketingInteractor;
import com.eservia.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class EventDetailsBeautyPresenter extends BasePresenter<EventDetailsBeautyView> implements
        ServiceAdapter.OnServiceClickListener,
        ExceededMaxSelectedServicesSheetDialog.Listener {

    @Inject
    MarketingInteractor mMarketingInteractor;

    @Inject
    BusinessInteractor mBusinessInteractor;

    @Inject
    Context mContext;

    private Disposable mPromotionsDisposable;

    private final List<BeautyPromotion> mPromotions = new ArrayList<>();

    private final List<Preparation> mPreparations = new ArrayList<>();

    private Marketing mMarketing = new Marketing();

    private List<MarketingLink> mLinks = new ArrayList<>();

    private boolean mCanGoToBusiness = false;

    private boolean mIsAllPromotionsLoaded = false;

    public EventDetailsBeautyPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mMarketing = EventBus.getDefault().getStickyEvent(Marketing.class);
        if (mMarketing != null) {
            mLinks = mMarketing.getLinks();
            List<MarketingWorkSchedule> mWorkSchedule = mMarketing.getWorkSchedule();
        }

            getViewState().requiredArgs();

    }

    @Override
    public void onServiceItemClick(ServiceAdapterItem adapterItem) {
        if (!adapterItem.isSelected() && mPreparations.size() >= BookingUtil.MAX_SERVICES_FLAT_SLOT) {
            getViewState().showErrorExceedDialog();
            return;
        }
        getViewState().setSelected(!adapterItem.isSelected(), adapterItem.getService().getId());
        if (!removeService(adapterItem.getService())) {
            Preparation preparation = new Preparation(mContext);
            preparation.setService(adapterItem.getService());
            mPreparations.add(preparation);
        }
        getViewState().revalidateBookButton(!mPreparations.isEmpty());
    }

    @Override
    public void onExceededMaxSelectedServicesDoneClick() {
        getViewState().hideErrorExceedDialog();
    }

    void onArgs(boolean openedFromBusinessPage){
        mCanGoToBusiness = !openedFromBusinessPage
                && mMarketing.getBusiness() != null
                && !mMarketing.getBusiness().getAddresses().isEmpty();
        getViewState().initAcceptButton(mCanGoToBusiness, mMarketing.getBusiness().getName());
        getViewState().onMarketing(mMarketing);
        if (mMarketing.getBusiness().getAddresses().size() == 1) {
            refreshPromotions();
        }
    }

    void onAcceptClicked() {
        if (mCanGoToBusiness) {
            getViewState().showBusinessBeautyActivity(mMarketing.getBusiness());
        }
    }

    void onBookSelectedServicesClick() {
        if (mMarketing != null
                && mMarketing.getBusiness() != null
                && mMarketing.getBusiness().getAddresses() != null
                && !mMarketing.getBusiness().getAddresses().isEmpty()
                && !mPreparations.isEmpty()) {
            getViewState().goToBooking(mMarketing.getBusiness(), mPreparations);
        }
    }

    void onOpenWebsiteClick() {
        MarketingLink link = findLink(MarketingLink.TYPE_WEBSITE);
        if (link != null && !StringUtil.isEmpty(link.getUrl())) {
            getViewState().openLinkWebsite(link.getUrl());
        }
    }

    void onOpenFacebookClick() {
        MarketingLink link = findLink(MarketingLink.TYPE_FACEBOOK);
        if (link != null && !StringUtil.isEmpty(link.getUrl())) {
            getViewState().openLinkFacebook(link.getUrl());
        }
    }

    void onOpenInstagramClick() {
        MarketingLink link = findLink(MarketingLink.TYPE_INSTAGRAM);
        if (link != null && !StringUtil.isEmpty(link.getUrl())) {
            getViewState().openLinkInstagram(link.getUrl());
        }
    }

    void onOpenTwitterClick() {
        MarketingLink link = findLink(MarketingLink.TYPE_TWITTER);
        if (link != null && !StringUtil.isEmpty(link.getUrl())) {
            getViewState().openLinkTwitter(link.getUrl());
        }
    }

    void onOpenGooglePlusClick() {
        MarketingLink link = findLink(MarketingLink.TYPE_GOOGLE);
        if (link != null && !StringUtil.isEmpty(link.getUrl())) {
            getViewState().openLinkGooglePlus(link.getUrl());
        }
    }

    void onOpenImageClick() {
        if (mMarketing == null
                || mMarketing.getPathToPhoto() == null
                || mMarketing.getPathToPhoto().isEmpty()) {
            return;
        }

        String marketingImageUrl = ImageUtil.getUserPhotoPath("", mMarketing.getPathToPhoto());

        List<String> urls = new ArrayList<>();
        urls.add(marketingImageUrl);

        getViewState().showGalleryActivity(new GalleryExtra(urls));
    }

    private void loadMorePromotions() {
        if (!paginationInProgress(mPromotionsDisposable) && !mIsAllPromotionsLoaded) {
            makePromotionsPagination();
        }
    }

    private void refreshPromotions() {
        cancelPagination(mPromotionsDisposable);
        mPromotions.clear();
        mIsAllPromotionsLoaded = false;
        makePromotionsPagination();
    }

    private void makePromotionsPagination() {
        if (mMarketing == null || mMarketing.getBusinessId() == null) {
            return;
        }
        cancelPagination(mPromotionsDisposable);
        Observable<List<BeautyPromotion>> observable = mBusinessInteractor
                .getBusinessPromotions(
                        mMarketing.getBusinessId(),
                        null,
                        mMarketing.getId(),
                        null,
                        null,
                        BeautyPromotion.WITHOUT_TRASHED,
                        PART,
                        mPromotions.size() / PART + 1,
                        null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mPromotionsDisposable = observable.subscribe(this::onPromotionsLoadingSuccess,
                this::onPromotionsLoadingFailed);
        addSubscription(mPromotionsDisposable);
    }

    private void onPromotionsLoadingSuccess(List<BeautyPromotion> promotions) {
        mPromotions.addAll(promotions);
        mPromotionsDisposable = null;
        if (promotions.size() != PART) {
            getViewState().onServicesLoadingSuccess(mapToServicesItems(mPromotions));
            mIsAllPromotionsLoaded = true;
        } else {
            loadMorePromotions();
        }
    }

    private void onPromotionsLoadingFailed(Throwable throwable) {
        getViewState().onServicesLoadingFailed(throwable);
        mPromotionsDisposable = null;
    }

    @Nullable
    private MarketingLink findLink(int type) {
        if (mLinks == null || mLinks.isEmpty()) {
            return null;
        }
        for (MarketingLink link : mLinks) {
            if (link.getSocialTypeId().equals(type)) {
                return link;
            }
        }
        return null;
    }

    private boolean removeService(BeautyService serviceToRemove) {
        ListIterator<Preparation> iterator = mPreparations.listIterator();
        while (iterator.hasNext()) {
            Preparation preparation = iterator.next();
            if (preparation.getService().getId().equals(serviceToRemove.getId())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private List<ServiceAdapterItem> mapToServicesItems(List<BeautyPromotion> promotions) {
        List<ServiceAdapterItem> serviceAdapterItems = new ArrayList<>();
        for (BeautyPromotion promotion : promotions) {
            for (BeautyService service : promotion.getServices()) {
                serviceAdapterItems.add(new ServiceAdapterItem(service,
                        service.getServiceGroup(),
                        mMarketing,
                        false
                ));
            }
        }
        return serviceAdapterItems;
    }
}
