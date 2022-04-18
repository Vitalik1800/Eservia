package com.eservia.booking.ui.splash.intro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.ui.auth.login.LoginActivity;
import com.eservia.booking.util.ViewUtil;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.prefs.WasOpened;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends BaseActivity implements IntroPagerAdapter.OnCloseListener {

    RelativeLayout rlBottomNavigation;

    ImageView mDot1;
    ImageView mDot2;
    ImageView mDot3;
    Button btnSkip;
    Button btnStart;

    private ViewPager mPager;

    private IntroPagerAdapter mAdapter;

    public static Intent openIntro(Context context) {
        Intent intent = new Intent(context, IntroActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
        rlBottomNavigation = findViewById(R.id.rlBottomNavigation);
        mDot1 = findViewById(R.id.ivDot1);
        mDot2 = findViewById(R.id.ivDot2);
        mDot3 = findViewById(R.id.ivDot3);
        btnSkip = findViewById(R.id.btnSkip);
        btnStart = findViewById(R.id.btnStart);
        btnSkip.setOnClickListener(v -> openLoginActivity());
        btnStart.setOnClickListener(v -> openLoginActivity());
        setUnbinder(ButterKnife.bind(this));
        initViews();
        setAdapterItems();
        WasOpened.setWasOpened(true);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private List<IntroAdapterItem> createAdapterItems() {
        List<IntroAdapterItem> items = new ArrayList<>();
        items.add(new IntroAdapterItem(
                getResources().getDrawable(R.drawable.page_1),
                getResources().getString(R.string.intro_find_best_message)));
        items.add(new IntroAdapterItem(
                getResources().getDrawable(R.drawable.page_2),
                getResources().getString(R.string.intro_find_all_about_message)));
        items.add(new IntroAdapterItem(
                getResources().getDrawable(R.drawable.page_3),
                getResources().getString(R.string.intro_book_visit_message)));
        items.add(new IntroAdapterItem());
        return items;
    }

    private void setAdapterItems() {
        mAdapter.replaceItems(createAdapterItems());
        int mStartPosition = 0;
        mPager.setCurrentItem(mStartPosition);
        revalidateDots();
        refreshPageButtons();
    }

    private void refreshPageButtons() {
        TransitionManager.beginDelayedTransition(rlBottomNavigation, new Fade());
        if (mPager.getCurrentItem() >= mAdapter.getCount() - 2) {
            btnSkip.setVisibility(View.GONE);
            btnStart.setVisibility(View.VISIBLE);
        } else {
            btnSkip.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        mPager = findViewById(R.id.pager);

        mAdapter = new IntroPagerAdapter(this);

        mPager.setAdapter(mAdapter);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position >= mAdapter.getItems().size() - 1) {
                    openLoginActivity();
                }
                refreshPageButtons();
                revalidateDots();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void revalidateDots() {
        int currentItem = mPager.getCurrentItem();

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        int pxInactiveSize = (int) ViewUtil.dpToPixel(this, 12);
        int pxActiveSize = (int) ViewUtil.dpToPixel(this, 15);

        switch (currentItem) {
            case 0: {
                mDot1.setColorFilter(colorsActive[0]);
                mDot2.setColorFilter(colorsInactive[1]);
                mDot3.setColorFilter(colorsInactive[2]);
                ViewUtil.setWidthHeight(mDot1, pxActiveSize, pxActiveSize);
                ViewUtil.setWidthHeight(mDot2, pxInactiveSize, pxInactiveSize);
                ViewUtil.setWidthHeight(mDot3, pxInactiveSize, pxInactiveSize);
            }
            break;
            case 1: {
                mDot1.setColorFilter(colorsInactive[0]);
                mDot2.setColorFilter(colorsActive[1]);
                mDot3.setColorFilter(colorsInactive[2]);
                ViewUtil.setWidthHeight(mDot1, pxInactiveSize, pxInactiveSize);
                ViewUtil.setWidthHeight(mDot2, pxActiveSize, pxActiveSize);
                ViewUtil.setWidthHeight(mDot3, pxInactiveSize, pxInactiveSize);
            }
            break;
            case 2: {
                mDot1.setColorFilter(colorsInactive[0]);
                mDot2.setColorFilter(colorsInactive[1]);
                mDot3.setColorFilter(colorsActive[2]);
                ViewUtil.setWidthHeight(mDot1, pxInactiveSize, pxInactiveSize);
                ViewUtil.setWidthHeight(mDot2, pxInactiveSize, pxInactiveSize);
                ViewUtil.setWidthHeight(mDot3, pxActiveSize, pxActiveSize);
            }
            break;
        }
    }

    @Override
    protected void openLoginActivity() {
        LoginActivity.start(this);
        finish();
    }
}
