package com.eservia.common.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.eservia.common.R;

public class CommonSegmentedBar extends LinearLayout {

    public interface CommonSegmentedBarListener {

        void onLeftSegmentSelected();

        void onRightSegmentSelected();
    }

    private RelativeLayout mLayoutLeft;

    private RelativeLayout mLayoutRight;

    private TextView mTextViewLeft;

    private TextView mTextViewRight;

    private boolean mIsLeftSelected = true;

    private int mColorAccent;
    private int mColorWhite;

    @Nullable
    private CommonSegmentedBarListener mListener;

    @Nullable
    private String mLeftSegmentText;

    @Nullable
    private String mRightSegmentText;

    public CommonSegmentedBar(Context context) {
        this(context, null);
    }

    public CommonSegmentedBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonSegmentedBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void setListener(CommonSegmentedBarListener listener) {
        mListener = listener;
    }

    public void selectLeft() {
        mIsLeftSelected = true;
        revalidate();
    }

    public void selectRight() {
        mIsLeftSelected = false;
        revalidate();
    }

    public void setLeftSegmentText(String text) {
        mLeftSegmentText = text;
        revalidate();
    }

    public void setRightSegmentText(String text) {
        mRightSegmentText = text;
        revalidate();
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initViews();
        initColors();
        revalidate();
    }

    private void initViews() {
        inflate(getContext(), R.layout.view_common_segmented_bar, this);
        LinearLayout mLayout = findViewById(R.id.csb_layout);
        mLayoutLeft = mLayout.findViewById(R.id.csb_rlLeft);
        mLayoutRight = mLayout.findViewById(R.id.csb_rlRight);
        mTextViewLeft = mLayout.findViewById(R.id.csb_tvLeft);
        mTextViewRight = mLayout.findViewById(R.id.csb_tvRight);

        mLayoutLeft.setOnClickListener(v -> {
            if (mIsLeftSelected) {
                return;
            }
            mIsLeftSelected = true;
            revalidate();
            if (mListener != null) {
                mListener.onLeftSegmentSelected();
            }
        });

        mLayoutRight.setOnClickListener(v -> {
            if (!mIsLeftSelected) {
                return;
            }
            mIsLeftSelected = false;
            revalidate();
            if (mListener != null) {
                mListener.onRightSegmentSelected();
            }
        });
    }

    private void initColors() {
        mColorAccent = Color.parseColor("#fb877b");
        mColorWhite = Color.parseColor("#ffffff");
    }

    private void revalidate() {
        mTextViewLeft.setText(mLeftSegmentText != null ? mLeftSegmentText : "");
        mTextViewRight.setText(mRightSegmentText != null ? mRightSegmentText : "");

        if (mIsLeftSelected) {
            mTextViewLeft.setTextColor(mColorWhite);
            mTextViewRight.setTextColor(mColorAccent);

            mLayoutLeft.setBackground(ContextCompat.getDrawable(getContext(),
                    R.drawable.background_segmented_bar_left_selected));
            mLayoutRight.setBackground(ContextCompat.getDrawable(getContext(),
                    R.drawable.background_segmented_bar_right));
        } else {
            mTextViewLeft.setTextColor(mColorAccent);
            mTextViewRight.setTextColor(mColorWhite);

            mLayoutLeft.setBackground(ContextCompat.getDrawable(getContext(),
                    R.drawable.background_segmented_bar_left));
            mLayoutRight.setBackground(ContextCompat.getDrawable(getContext(),
                    R.drawable.background_segmented_bar_right_selected));
        }
    }
}
