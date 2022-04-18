package com.eservia.booking.ui.business_page.beauty.feedback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.SimpleTextWatcher;
import com.eservia.model.entity.Business;
import com.eservia.simpleratingbar.SimpleRatingBar;
import com.eservia.utils.KeyboardUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class BusinessFeedbackBeautyActivity extends BaseActivity implements BusinessFeedbackBeautyView {

    CoordinatorLayout coordinator;
    Toolbar toolbar;
    RelativeLayout rlCardHolderRatingSelector;
    CardView cvContainerRatingSelector;
    RelativeLayout rlCardHolderCommentEditor;
    CardView cvContainerCommentEditor;
    RecyclerView rvQuality;
    RecyclerView rvPurity;
    RecyclerView rvConvenience;
    SeekBar sbQuality;
    SeekBar sbPurity;
    SeekBar sbConvenience;
    EditText etFeedback;
    ImageView ivEdit;
    SimpleRatingBar rbTotalRating;
    TextView tvRating;
    ProgressBar pbProgress;
    View layout_accept;
    TextView tvAccept;
    ImageView ivAccept;

    @InjectPresenter
    BusinessFeedbackBeautyPresenter mPresenter;

    private StarAdapter mStarAdapterQuality;

    private StarAdapter mStarAdapterPurity;

    private StarAdapter mStarAdapterConvenience;

    public static void start(Context context, Business business) {
        EventBus.getDefault().postSticky(business);
        Intent starter = new Intent(context, BusinessFeedbackBeautyActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_feedback_beauty);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        coordinator = findViewById(R.id.coordinator);
        toolbar = findViewById(R.id.toolbar);
        rlCardHolderRatingSelector = findViewById(R.id.rlCardHolderRatingSelector);
        cvContainerRatingSelector = findViewById(R.id.cvContainerRatingSelector);
        rlCardHolderCommentEditor = findViewById(R.id.rlCardHolderCommentEditor);
        cvContainerCommentEditor = findViewById(R.id.cvContainerCommentEditor);
        rvQuality = findViewById(R.id.rvQuality);
        rvPurity = findViewById(R.id.rvPurity);
        rvConvenience = findViewById(R.id.rvConvenience);
        sbQuality = findViewById(R.id.sbQuality);
        sbPurity = findViewById(R.id.sbPurity);
        sbConvenience = findViewById(R.id.sbConvenience);
        etFeedback = findViewById(R.id.etFeedback);
        tvAccept = findViewById(R.id.tvAccept);
        ivAccept = findViewById(R.id.ivAccept);
        rbTotalRating = findViewById(R.id.rbTotalRating);
        tvRating = findViewById(R.id.tvRating);
        ivEdit = findViewById(R.id.ivEdit);
        pbProgress = findViewById(R.id.pbProgress);
        layout_accept = findViewById(R.id.layout_accept);
        layout_accept.setOnClickListener(v -> onSendFeedbackClicked());
        ivAccept.setOnClickListener(v -> onSendFeedbackClicked());
        ivEdit.setOnClickListener(v -> onEditClicked());
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

    public void onSendFeedbackClicked() {
        checkForErrorsAndSend();
    }
    public void onEditClicked() {
        etFeedback.requestFocus();
        KeyboardUtil.showSoftKeyboard(this);
        ViewUtil.moveCursorToEnd(etFeedback);
    }

    @Override
    public void showProgress() {
        pbProgress.setVisibility(View.VISIBLE);
        layout_accept.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
        layout_accept.setVisibility(View.VISIBLE);
    }

    @Override
    public void initMaxMinDefault(int maxRating, int minRating, int defaultRating) {
        sbQuality.setMax(maxRating);
        sbQuality.setProgress(defaultRating);

        sbPurity.setMax(maxRating);
        sbPurity.setProgress(defaultRating);

        sbConvenience.setMax(maxRating);
        sbConvenience.setProgress(defaultRating);
    }

    @Override
    public void setQualityStars(List<StarItem> stars) {
        mStarAdapterQuality.replaceAll(stars);
    }

    @Override
    public void setPurityStars(List<StarItem> stars) {
        mStarAdapterPurity.replaceAll(stars);
    }

    @Override
    public void setConvenienceStars(List<StarItem> stars) {
        mStarAdapterConvenience.replaceAll(stars);
    }

    @Override
    public void onTotalRating(float rating) {
        rbTotalRating.setRating(BusinessUtil.starsRating(rating));
        tvRating.setText(BusinessUtil.formatRating(rating));
    }

    @Override
    public void onCreateCommentSuccess() {
        MessageUtil.showToast(this, R.string.success_send_business_feedback);
        finish();
    }

    @Override
    public void onCreateCommentFailed(Throwable error) {
        MessageUtil.showSnackbar(coordinator, error);
    }

    private void checkForErrorsAndSend() {
        KeyboardUtil.hideSoftKeyboard(this);

        setError();

        if (!hasErrors()) {
            mPresenter.onSendFeedbackClicked();
        }
    }

    private void setError() {
        String comment = etFeedback.getText().toString();
        if (!comment.isEmpty()) {
            etFeedback.setError(ValidatorUtil.isLongMessageValid(this, comment));
        } else {
            etFeedback.setError(null);
        }
    }

    private boolean hasErrors() {
        boolean error = false;
        if (etFeedback.getError() != null) {
            etFeedback.requestFocus();
            error = true;
        }
        return error;
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        ViewUtil.setCardOutlineProvider(this, rlCardHolderRatingSelector, cvContainerRatingSelector);
        ViewUtil.setCardOutlineProvider(this, rlCardHolderCommentEditor, cvContainerCommentEditor);

        initStarLists();

        initSeekBars();

        initFeedback();

        tvAccept.setText(getResources().getString(R.string.send));
        ivAccept.setVisibility(View.GONE);
    }

    private void initFeedback() {
        etFeedback.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                etFeedback, new View[]{etFeedback}));

        etFeedback.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                ValidatorUtil.MAX_ESERVIA_FEEDBACK_LENGTH)});

        ViewUtil.setOnTouchListenerForVerticalScroll(etFeedback);

        etFeedback.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void textChanged(String s) {
                if (etFeedback.getError() != null) {
                    setError();
                    etFeedback.requestFocus();
                }
                mPresenter.onCommentChanged(s);
            }
        });
    }

    private void initStarLists() {
        mStarAdapterQuality = new StarAdapter(this, mPresenter);
        mStarAdapterPurity = new StarAdapter(this, mPresenter);
        mStarAdapterConvenience = new StarAdapter(this, mPresenter);

        rvQuality.setAdapter(mStarAdapterQuality);
        rvPurity.setAdapter(mStarAdapterPurity);
        rvConvenience.setAdapter(mStarAdapterConvenience);

        rvQuality.setHasFixedSize(true);
        rvPurity.setHasFixedSize(true);
        rvConvenience.setHasFixedSize(true);

        GridLayoutManager layoutManagerQuality = new GridLayoutManager(this, 5,
                GridLayoutManager.VERTICAL, false);
        GridLayoutManager layoutManagerPurity = new GridLayoutManager(this, 5,
                GridLayoutManager.VERTICAL, false);
        GridLayoutManager layoutManagerConvenience = new GridLayoutManager(this, 5,
                GridLayoutManager.VERTICAL, false);

        rvQuality.setLayoutManager(layoutManagerQuality);
        rvPurity.setLayoutManager(layoutManagerPurity);
        rvConvenience.setLayoutManager(layoutManagerConvenience);
    }

    private void initSeekBars() {
        SeekBarListener seekBarListener = new SeekBarListener();
        sbQuality.setOnSeekBarChangeListener(seekBarListener);
        sbPurity.setOnSeekBarChangeListener(seekBarListener);
        sbConvenience.setOnSeekBarChangeListener(seekBarListener);
    }

    private class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (seekBar.equals(sbQuality)) {
                mPresenter.onQualityChanged(i);
            } else if (seekBar.equals(sbPurity)) {
                mPresenter.onPurityChanged(i);
            } else if (seekBar.equals(sbConvenience)) {
                mPresenter.onConvenienceChanged(i);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
