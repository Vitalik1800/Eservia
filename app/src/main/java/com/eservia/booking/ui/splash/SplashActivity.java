package com.eservia.booking.ui.splash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.ui.auth.login.LoginActivity;
import com.eservia.booking.ui.home.HomeActivity;
import com.eservia.booking.ui.splash.intro.IntroActivity;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;

import moxy.presenter.InjectPresenter;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity implements SplashView {

    @InjectPresenter
    SplashPresenter mSplashPresenter;

    ConstraintLayout mContent;

    public static void start(Context context) {
        Intent starter = new Intent(context, SplashActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContent = findViewById(R.id.rlSplashContent);
        WindowUtils.setFullScreenWithStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
    }

    private Animation getFinishAnimation() {
        return AnimationUtils.loadAnimation(this, R.anim.splash_anim);
    }

    @Override
    public void showError(String message) {
        MessageUtil.showSnackbar(mContent, message);
    }

    @Override
    public void openHomeView() {
        startAnimation(() -> {
            HomeActivity.start(this);
            finish();
        });
    }

    @Override
    public void openLoginView() {
        new Handler().postDelayed(() -> {
            LoginActivity.start(SplashActivity.this);
            finish();
        }, 1500);
    }

    @Override
    public void openIntro() {
        new Handler().postDelayed(this::openIntroActivity, 1500);
    }

    private void startAnimation(Runnable action) {
        Animation animation = getFinishAnimation();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                action.run();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mContent.startAnimation(animation);
    }

    private void openIntroActivity() {
        startActivity(IntroActivity.openIntro(this));
        finish();
    }
}
