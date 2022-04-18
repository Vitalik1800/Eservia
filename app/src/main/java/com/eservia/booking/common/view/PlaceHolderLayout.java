package com.eservia.booking.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eservia.booking.R;

@Deprecated
public class PlaceHolderLayout extends RelativeLayout {

    private PlaceHolderButtonClickListener mButtonClickListener;

    private RelativeLayout mContent;
    private RelativeLayout mPlaceHolder;

    private ImageView mImageView;
    private TextView mTextView;
    private Button mButton;

    private boolean mEmpty = false;

    private int textId;
    private int buttonTextId;
    private int logoId;

    public PlaceHolderLayout(Context context) {
        this(context, null);
    }

    public PlaceHolderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlaceHolderLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void initViews() {
        inflate(getContext(), R.layout.view_placeholder, this);
        mContent = findViewById(R.id.rlContent);
        mPlaceHolder = findViewById(R.id.rlPlaceHolder);
        mImageView = mPlaceHolder.findViewById(R.id.ivPlaceHolderLogo);
        mTextView = mPlaceHolder.findViewById(R.id.tvPlaceHolderLabel);
        mButton = mPlaceHolder.findViewById(R.id.btnPlaceHolderButton);
        mButton.setOnClickListener(view -> {
            if (mButtonClickListener != null) {
                mButtonClickListener.onPlaceHolderButtonClick();
            }
        });
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initViews();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PlaceHolderLayout, defStyleAttr, 0);
        try {
            textId = a.getResourceId(R.styleable.PlaceHolderLayout_text, 0);
            buttonTextId = a.getResourceId(R.styleable.PlaceHolderLayout_buttonText, 0);
            logoId = a.getResourceId(R.styleable.PlaceHolderLayout_icon, 0);
            mEmpty = a.getBoolean(R.styleable.PlaceHolderLayout_empty, false);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
        revalidateViews();
        revalidateEmpty();
    }

    private void revalidateViews() {
        if (textId == 0) {
            mTextView.setText("");
        } else {
            mTextView.setText(textId);
        }
        if (buttonTextId == 0) {
            mButton.setText("");
        } else {
            mButton.setText(buttonTextId);
        }
        if (logoId == 0) {
            ColorDrawable drawable = new ColorDrawable();
            drawable.setColor(Color.WHITE);
            drawable.setAlpha(123);
            mImageView.setImageDrawable(drawable);
        } else {
            mImageView.setImageResource(logoId);
        }
    }

    private void revalidateEmpty() {
        mPlaceHolder.setVisibility(mEmpty ? VISIBLE : GONE);
        mContent.setVisibility(mEmpty ? GONE : VISIBLE);
    }

    private void showButton(boolean showButton) {
        mButton.setVisibility(showButton ? VISIBLE : GONE);
    }

    @Override
    public void addView(View child) {
        if (isContent(child)) {
            mContent.addView(child);
        } else {
            super.addView(child);
        }
    }

    @Override
    public void addView(View child, int index) {
        mContent.addView(child, index);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (isContent(child)) {
            mContent.addView(child, width, height);
        } else {
            super.addView(child, width, height);
        }
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isContent(child)) {
            mContent.addView(child, params);
        } else {
            super.addView(child, params);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (isContent(child)) {
            mContent.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }

    @Override
    public void removeView(View view) {
        if (isContent(view)) {
            mContent.removeView(view);
        } else {
            super.removeView(view);
        }
    }

    @Override
    public void removeViewInLayout(View view) {
        if (isContent(view)) {
            mContent.removeViewInLayout(view);
        } else {
            super.removeViewInLayout(view);
        }
    }

    private boolean isContent(View view) {
        return view.getId() != R.id.rlContent && view.getId() != R.id.rlPlaceHolder;
    }

    public boolean isEmpty() {
        return mEmpty;
    }

    public void setEmpty(boolean empty) {
        mEmpty = empty;
        revalidateViews();
        revalidateEmpty();
        showButton(false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void showConnectionError() {
        setImageDrawable(getResources().getDrawable(R.drawable.ic_no_connection));
        setMessageText(getResources().getString(R.string.error_no_internet_detailed));
        showButton(true);
        mEmpty = true;
        revalidateEmpty();
    }

    public void setMessageText(String text) {
        mTextView.setText(text);
    }

    public void setImageDrawable(Drawable image) {
        mImageView.setImageDrawable(image);
    }

    public void setOnButtonClickListener(PlaceHolderButtonClickListener listener) {
        mButtonClickListener = listener;
    }

    public interface PlaceHolderButtonClickListener {
        void onPlaceHolderButtonClick();
    }
}
