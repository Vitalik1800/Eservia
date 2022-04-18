package com.eservia.booking.ui.business_page.beauty.feedback;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.RatingPayload;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business.services.comment.CreateBusinessCommentRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class BusinessFeedbackBeautyPresenter extends BasePresenter<BusinessFeedbackBeautyView>
        implements StarAdapter.StarClickListener {

    private static final int MAX_RATE_VALUE = 4;

    private static final int MIN_RATE_VALUE = 0;

    private static final int DEFAULT_RATING = 4;

    @Inject
    RestManager mRestManager;

    private Disposable mCreateCommentDisposable;

    private Business mBusiness;

    private int mCurrentQuality;

    private int mCurrentPurity;

    private int mCurrentConvenience;

    private String mComment;

    public BusinessFeedbackBeautyPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mBusiness = EventBus.getDefault().getStickyEvent(Business.class);
        getViewState().initMaxMinDefault(MAX_RATE_VALUE, MIN_RATE_VALUE, DEFAULT_RATING);
        initQuality();
        initPurity();
        initConvenience();
        recalculateTotalRating();
    }

    @Override
    public void onStarClick(StarItem star, int position) {
    }

    public void onSendFeedbackClicked() {
        if (mBusiness == null) {
            return;
        }
        if (paginationInProgress(mCreateCommentDisposable)) {
            return;
        }
        if (!mBusiness.getCan().getComment()) {
            return;
        }
        createComment();
    }

    public void onQualityChanged(int value) {
        mCurrentQuality = value;
        getViewState().setQualityStars(initStars(value));
        recalculateTotalRating();
    }

    public void onPurityChanged(int value) {
        mCurrentPurity = value;
        getViewState().setPurityStars(initStars(value));
        recalculateTotalRating();
    }

    public void onConvenienceChanged(int value) {
        mCurrentConvenience = value;
        getViewState().setConvenienceStars(initStars(value));
        recalculateTotalRating();
    }

    public void onCommentChanged(String comment) {
        mComment = comment;
    }

    private void recalculateTotalRating() {
        float totalQuality = total(mCurrentQuality);
        float totalPurity = total(mCurrentPurity);
        float totalConvenience = total(mCurrentConvenience);
        float total = BusinessUtil.average(new Float[]{totalQuality, totalPurity, totalConvenience});
        getViewState().onTotalRating(total);
    }

    private float total(int value) {
        return (float) ((++value) * 2);
    }

    private void initQuality() {
        getViewState().setQualityStars(initStars(DEFAULT_RATING));
    }

    private void initPurity() {
        getViewState().setPurityStars(initStars(DEFAULT_RATING));
    }

    private void initConvenience() {
        getViewState().setConvenienceStars(initStars(DEFAULT_RATING));
    }

    private List<StarItem> initStars(int rating) {

        List<StarItem> stars = new ArrayList<>();

        for (int i = MIN_RATE_VALUE; i <= MAX_RATE_VALUE; i++) {
            if (i < rating) {
                stars.add(new StarItem(StarItem.State.FILLED));
            } else if (i > rating) {
                stars.add(new StarItem(StarItem.State.NORMAL));
            } else {
                stars.add(new StarItem(StarItem.State.FILLED_EXPANDED));
            }
        }
        return stars;
    }

    private void createComment() {
        getViewState().showProgress();

        RatingPayload ratingPayload = new RatingPayload();
        ratingPayload.setQuality(total(mCurrentQuality));
        ratingPayload.setPurity(total(mCurrentPurity));
        ratingPayload.setConvenience(total(mCurrentConvenience));

        CreateBusinessCommentRequest request = new CreateBusinessCommentRequest(
                mBusiness.getId(),
                ratingPayload,
                mComment
        );

        mCreateCommentDisposable = mRestManager
                .createBusinessComment(request)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onCreateCommentSuccess(),
                        this::onCreateCommentFailed);
        addSubscription(mCreateCommentDisposable);
    }

    private void onCreateCommentSuccess() {
        BusinessUtil.commentAdded(mBusiness);
        getViewState().hideProgress();
        getViewState().onCreateCommentSuccess();
        mCreateCommentDisposable = null;
    }

    private void onCreateCommentFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onCreateCommentFailed(throwable);
        mCreateCommentDisposable = null;
    }
}
