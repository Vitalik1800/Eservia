package com.eservia.booking.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.butterknife.ButterKnife;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class GalleryActivity extends BaseActivity implements GalleryView,
        GalleryAdapter.OnGalleryItemClickListener {

    ViewPager pager;
    RelativeLayout rlHeader;
    ImageButton ibClose;
    RelativeLayout rlBottomNavigation;
    TextView tvPageNumber;
    RelativeLayout rlPrevPage;
    RelativeLayout rlNextPage;
    ImageView ivPrev;
    ImageView ivNext;

    @InjectPresenter
    GalleryPresenter mPresenter;

    private Handler mHandler;

    private GalleryAdapter mAdapter;

    private int mCurrentPage = 0;

    private boolean isControlsHidden = false;

    public static void start(Context context, GalleryExtra extra) {
        EventBus.getDefault().postSticky(extra);
        Intent starter = new Intent(context, GalleryActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery);
        setUnbinder(ButterKnife.bind(this));
        pager = findViewById(R.id.pager);
        rlHeader = findViewById(R.id.rlHeader);
        ibClose = findViewById(R.id.ibClose);
        rlPrevPage = findViewById(R.id.rlPrevPage);
        rlNextPage = findViewById(R.id.rlNextPage);
        ivPrev = findViewById(R.id.ivPrev);
        ivNext = findViewById(R.id.ivNext);
        tvPageNumber = findViewById(R.id.tvPageNumber);
        rlBottomNavigation = findViewById(R.id.rlBottomNavigation);
        ibClose.setOnClickListener(v -> onCloseClick());
        rlPrevPage.setOnClickListener(v -> onLeftClick());
        rlNextPage.setOnClickListener(v -> onRightClick());
        initViews();
    }

    public void onCloseClick() {
        finish();
    }

    public void onLeftClick() {
        mHandler.post(showPrevPage);
    }

    public void onRightClick() {
        mHandler.post(showNextPage);
    }

    @Override
    public void onGalleryItemClick(int position) {
        toggleControls();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void onStartPosition(int startPosition) {
        mCurrentPage = startPosition;
    }

    @Override
    public void initWith(List<String> urls) {
        mAdapter.replacePhotos(urls);
        setCurrentPage();
    }

    private void refreshPageTitle(int position) {
    }

    private void toggleControls() {
        if (!isControlsHidden) {
            hideHeaderBar();
            hideBottomBar();
            isControlsHidden = true;
        } else {
            showHeaderBar();
            showBottomBar();
            isControlsHidden = false;
        }
    }

    private void hideHeaderBar() {
        rlHeader.animate().translationY(0.0f - rlHeader.getHeight());
    }

    private void showHeaderBar() {
        rlHeader.animate().translationY(0);
    }

    private void hideBottomBar() {
        rlBottomNavigation.animate().translationY(rlBottomNavigation.getHeight());
    }

    private void showBottomBar() {
        rlBottomNavigation.animate().translationY(0);
    }

    private final Runnable showPrevPage = () -> {
        if (mCurrentPage > 0) {
            mCurrentPage--;
        }
        setCurrentPage();
    };

    private final Runnable showNextPage = () -> {
        if (mCurrentPage < mAdapter.getCount() - 1) {
            mCurrentPage++;
        }
        setCurrentPage();
    };

    private void setCurrentPage() {
        pager.setCurrentItem(mCurrentPage, true);
        refreshControls();
    }

    private void refreshControls() {
        if (mCurrentPage <= 0) {
            rlPrevPage.setAlpha(0.5f);
        } else {
            rlPrevPage.setAlpha(1.0f);
        }

        if (mCurrentPage >= mAdapter.getCount() - 1) {
            rlNextPage.setAlpha(0.5f);
        } else {
            rlNextPage.setAlpha(1.0f);
        }
    }

    private void initViews() {
        mHandler = new Handler();

        mAdapter = new GalleryAdapter(this, this);

        pager.setAdapter(mAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                refreshPageTitle(position);
                refreshControls();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
