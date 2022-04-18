package com.eservia.common.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eservia.common.R;

public class CommonCounter extends RelativeLayout {

    public interface CommonCounterListener {

        void onCounterValueIncreased(int count);

        void onCounterValueDecreased(int count);

        void onUnableToIncrease();

        void onUnableToDecrease();
    }

    private TextView mIncrement;

    private TextView mDecrement;

    private TextView mCounter;

    private int mMaxValue = 15;

    private int mMinValue = 1;

    private int mCount;

    private boolean mIsEditable = true;

    private CommonCounterListener mListener;

    public CommonCounter(Context context) {
        this(context, null);
    }

    public CommonCounter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonCounter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void setListener(CommonCounterListener listener) {
        mListener = listener;
    }

    public void setValue(int value) {
        if (value < mMinValue || value > mMaxValue) {
            return;
        }
        mCount = value;
        revalidate();
    }

    public void setEditable(boolean isEditable) {
        mIsEditable = isEditable;
        revalidate();
    }

    public void setMaxValue(int maxValue) {
        this.mMaxValue = maxValue;
        revalidate();
    }

    public void setMinValue(int minValue) {
        this.mMinValue = minValue;
        revalidate();
    }

    public int getCount() {
        return mCount;
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public int getMinValue() {
        return mMinValue;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mCount = mMinValue;
        initViews();
        revalidate();
    }

    private void initViews() {
        inflate(getContext(), R.layout.view_common_counter, this);
        RelativeLayout mLayout = findViewById(R.id.common_counter);
        mDecrement = mLayout.findViewById(R.id.cv_decrement);
        mIncrement = mLayout.findViewById(R.id.cv_increment);
        mCounter = mLayout.findViewById(R.id.cv_count);

        mIncrement.setOnClickListener(v -> {
            if (!canIncrement()) {
                onUnableToIncreaseEvent();
                return;
            }
            mCount++;
            revalidate();
            onCounterValueIncreasedEvent();
        });

        mDecrement.setOnClickListener(v -> {
            if (!canDecrement()) {
                onUnableToDecreaseEvent();
                return;
            }
            mCount--;
            revalidate();
            onCounterValueDecreasedEvent();
        });
    }

    private boolean canIncrement() {
        return mCount < mMaxValue && mIsEditable;
    }

    private boolean canDecrement() {
        return mCount > mMinValue && mIsEditable;
    }

    private void revalidate() {
        mCounter.setText(String.valueOf(mCount));

        if (mCount == mMinValue) {
            mDecrement.setTextColor(Color.parseColor("#bdbdbd"));
        } else {
            mDecrement.setTextColor(Color.parseColor("#fb877b"));
        }
        if (mCount == mMaxValue) {
            mIncrement.setTextColor(Color.parseColor("#bdbdbd"));
        } else {
            mIncrement.setTextColor(Color.parseColor("#fb877b"));
        }
        if (!mIsEditable) {
            mIncrement.setTextColor(Color.parseColor("#bdbdbd"));
            mDecrement.setTextColor(Color.parseColor("#bdbdbd"));
        }
    }

    private void onCounterValueIncreasedEvent() {
        if (mListener != null) {
            mListener.onCounterValueIncreased(mCount);
        }
    }

    private void onCounterValueDecreasedEvent() {
        if (mListener != null) {
            mListener.onCounterValueDecreased(mCount);
        }
    }

    private void onUnableToIncreaseEvent() {
        if (mListener != null) {
            mListener.onUnableToIncrease();
        }
    }

    private void onUnableToDecreaseEvent() {
        if (mListener != null) {
            mListener.onUnableToDecrease();
        }
    }
}
