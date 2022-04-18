package com.eservia.booking.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.eservia.booking.R;

public class CommonPlaceHolder extends RelativeLayout {

    public static final int STATE_HIDE = 0;
    public static final int STATE_EMPTY = 1;
    public static final int STATE_NO_CONNECTION = 2;
    public static final int STATE_UNKNOWN_ERROR = 3;

    public static final int STATE_BUTTON_GONE = 0;
    public static final int STATE_BUTTON_INVISIBLE = 1;
    public static final int STATE_BUTTON_VISIBLE = 2;

    private RelativeLayout mPlaceHolder;

    private ImageView mImageView;

    private TextView mTextView;

    private Button mButton;

    private int mIconEmptyId;

    private int mTextEmptyId;

    private int mIconNoConnectionId;

    private int mTextNoConnectionId;

    private int mIconUnknownErrorId;

    private int mTextUnknownErrorId;

    private int mState;

    private int mTextButtonId;

    private int mStateButton;

    public CommonPlaceHolder(Context context) {
        this(context, null);
    }

    public CommonPlaceHolder(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonPlaceHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void setState(int state) {
        mState = state;
        revalidate();
    }

    public int getState() {
        return mState;
    }

    public void setStateButton(int state) {
        mStateButton = state;
        revalidate();
    }

    public int getStateButton() {
        return mStateButton;
    }

    public void setTextEmpty(int stringId) {
        mTextEmptyId = stringId;
        revalidate();
    }

    public void setButtonClickListener(OnClickListener listener) {
        mButton.setOnClickListener(listener);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initViews();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonPlaceHolder,
                defStyleAttr, 0);

        try {
            mIconEmptyId = a.getResourceId(R.styleable.CommonPlaceHolder_iconEmpty,
                    R.drawable.search_failed);
            mTextEmptyId = a.getResourceId(R.styleable.CommonPlaceHolder_textEmpty,
                    R.string.placeholder_message_not_found);

            mIconNoConnectionId = a.getResourceId(R.styleable.CommonPlaceHolder_iconNoConnection,
                    R.drawable.connection);
            mTextNoConnectionId = a.getResourceId(R.styleable.CommonPlaceHolder_textNoConnection,
                    R.string.place_holder_connection);

            mIconUnknownErrorId = a.getResourceId(R.styleable.CommonPlaceHolder_iconUnknownError,
                    0);
            mTextUnknownErrorId = a.getResourceId(R.styleable.CommonPlaceHolder_textUnknownError,
                    0);

            mState = a.getInteger(R.styleable.CommonPlaceHolder_state, STATE_HIDE);

            mTextButtonId = a.getResourceId(R.styleable.CommonPlaceHolder_textButton,
                    0);

            mStateButton = a.getInteger(R.styleable.CommonPlaceHolder_stateButton, STATE_BUTTON_GONE);

        } finally {
            if (a != null) {
                a.recycle();
            }
        }
        revalidate();
    }

    private void initViews() {
        inflate(getContext(), R.layout.view_common_place_holder, this);
        mPlaceHolder = findViewById(R.id.rlPlaceHolder);
        mImageView = mPlaceHolder.findViewById(R.id.ivPlaceHolderLogo);
        mTextView = mPlaceHolder.findViewById(R.id.tvPlaceHolderLabel);
        mButton = mPlaceHolder.findViewById(R.id.btnPlaceHolderButton);
    }

    private void revalidate() {
        switch (mState) {
            case STATE_EMPTY: {
                mPlaceHolder.setVisibility(VISIBLE);
                mImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), mIconEmptyId));
                mTextView.setText(getResources().getString(mTextEmptyId));
                break;
            }
            case STATE_NO_CONNECTION: {
                mPlaceHolder.setVisibility(VISIBLE);
                mImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), mIconNoConnectionId));
                mTextView.setText(getResources().getString(mTextNoConnectionId));
                break;
            }
            case STATE_UNKNOWN_ERROR: {
                mPlaceHolder.setVisibility(VISIBLE);
                mImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), mIconUnknownErrorId));
                mTextView.setText(getResources().getString(mTextUnknownErrorId));
                break;
            }
            default: {
                mPlaceHolder.setVisibility(GONE);
                break;
            }
        }

        if (mTextButtonId != 0) {
            mButton.setText(mTextButtonId);
        }

        switch (mStateButton) {
            case STATE_BUTTON_INVISIBLE: {
                mButton.setVisibility(INVISIBLE);
                break;
            }
            case STATE_BUTTON_VISIBLE: {
                mButton.setVisibility(VISIBLE);
                break;
            }
            default: {
                mButton.setVisibility(GONE);
                break;
            }
        }
    }
}
