package com.eservia.common.view;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;

import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eservia.common.R;

public class CommonAlert extends RelativeLayout {

    public enum Type {
        INFO,
        EXPLANATION,
        WARNING,
        ERROR
    }

    private RelativeLayout mLayout;
    private TextView mMessageTextView;

    private String mMessage = "";
    private Type mType = Type.INFO;

    private int mColorInfo;
    private int mColorExplanation;
    private int mColorWarning;
    private int mColorError;

    private boolean mCanLaunchTransition = true;

    public CommonAlert(Context context) {
        this(context, null);
    }

    public CommonAlert(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonAlert(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void setMessageText(String text) {
        mMessage = text;
        revalidate();
    }

    public String getMessageText() {
        return mMessage;
    }

    public void show() {
        if (mLayout.getVisibility() == VISIBLE) {
            return;
        }
        runTransition();
        mLayout.setVisibility(VISIBLE);
        long mVisibleAlertMillis = 4000;
        new AlertVisibleTimer(mVisibleAlertMillis).start();
    }

    public void setType(Type type) {
        mType = type;
        revalidate();
    }

    public Type getType() {
        return mType;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initColors();
        initViews();
        revalidate();
    }

    private void initColors() {
        mColorInfo = Color.parseColor("#66cc99");
        mColorExplanation = Color.parseColor("#86b6df");
        mColorWarning = Color.parseColor("#f9d354");
        mColorError = Color.parseColor("#ff5c5c");
    }

    private void initViews() {
        inflate(getContext(), R.layout.view_common_alert, this);
        mLayout = findViewById(R.id.ca_layout);
        mMessageTextView = mLayout.findViewById(R.id.ca_message);
    }

    private void revalidate() {
        revalidateMessage();
        revalidateBackground();
    }

    private void revalidateMessage() {
        if (mMessage != null) {
            mMessageTextView.setText(mMessage);
        }
    }

    private void revalidateBackground() {
        if (mType != null) {
            switch (mType) {
                case EXPLANATION: {
                    mLayout.setBackgroundColor(mColorExplanation);
                    break;
                }
                case WARNING: {
                    mLayout.setBackgroundColor(mColorWarning);
                    break;
                }
                case ERROR: {
                    mLayout.setBackgroundColor(mColorError);
                    break;
                }
                default: {
                    mLayout.setBackgroundColor(mColorInfo);
                    break;
                }
            }
        }
    }

    private void runTransition() {
        if (mCanLaunchTransition) {
            mCanLaunchTransition = false;
            long mTransitionIntervalMillis = 500;
            new TransitionIntervalTimer(mTransitionIntervalMillis).start();
            Slide slide = new Slide(Gravity.TOP);
            slide.setDuration(300);
            TransitionManager.beginDelayedTransition(mLayout, slide);
        }
    }

    private class AlertVisibleTimer extends CountDownTimer {

        private AlertVisibleTimer(long millisInFuture) {
            super(millisInFuture, millisInFuture);
        }

        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {
            runTransition();
            mLayout.setVisibility(INVISIBLE);
        }
    }

    private class TransitionIntervalTimer extends CountDownTimer {

        private TransitionIntervalTimer(long millisInFuture) {
            super(millisInFuture, millisInFuture);
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
