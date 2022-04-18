package com.eservia.booking.common.view;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.eservia.booking.R;
import com.eservia.butterknife.ButterKnife;
import com.example.numberpicker.NumberPicker;

public class CommonNumberPickerDialog extends BaseDialogFragment {

    private static final String TITLE = "title";
    private static final String MESSAGE = "message";

    public interface CommonNumberPickerDialogListener {
        void onValuePicked(int value);
    }

    public static CommonNumberPickerDialog newInstance(String title, String message) {
        CommonNumberPickerDialog f = new CommonNumberPickerDialog();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(MESSAGE, message);
        f.setArguments(args);
        return f;
    }
    TextView tvDialogLabel;
    TextView tvDialogMessage;
    Button btnLeft;
    Button btnRight;
    NumberPicker npPicker;

    @Nullable
    private CommonNumberPickerDialogListener mListener;

    @Nullable
    private NumberPicker.Formatter mFormatter;

    private int mMinValue;

    private int mMaxValue;

    private int mValue;

    @Nullable
    private String mTitle;

    @Nullable
    private String mMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_number_picker, null);
        this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.getDialog().getWindow().getDecorView().setBackgroundResource(R.color.transparent);
        setUnbinder(ButterKnife.bind(this, view));
        tvDialogLabel = view.findViewById(R.id.tvDialogLabel);
        tvDialogMessage = view.findViewById(R.id.tvDialogMessage);
        btnLeft = view.findViewById(R.id.btnLeft);
        btnRight = view.findViewById(R.id.btnRight);
        npPicker = view.findViewById(R.id.npPicker);
        btnLeft.setOnClickListener(v -> onClickButtonLeft());
        btnRight.setOnClickListener(v -> onClickButtonRight());
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
            mMessage = getArguments().getString(MESSAGE);
        }
        initViews();
        return view;
    }

    public void onClickButtonLeft() {
        dismiss();
    }

    public void onClickButtonRight() {
        if (mListener != null) {
            mListener.onValuePicked(npPicker.getValue());
        }
        dismiss();
    }

    public void setListener(CommonNumberPickerDialogListener listener) {
        mListener = listener;
    }

    public void setPickerFormatter(NumberPicker.Formatter formatter) {
        mFormatter = formatter;
    }

    public void setMinMaxValue(int min, int max) {
        mMinValue = min;
        mMaxValue = max;
    }

    public void setSelectedItemAtPosition(int position) {
        mValue = position;
    }

    private void initViews() {
        if (mTitle != null) {
            tvDialogLabel.setText(mTitle);
            tvDialogLabel.setVisibility(View.VISIBLE);
        } else {
            tvDialogLabel.setVisibility(View.GONE);
        }

        if (mMessage != null) {
            tvDialogMessage.setText(mMessage);
            tvDialogMessage.setVisibility(View.VISIBLE);
        } else {
            tvDialogMessage.setVisibility(View.GONE);
        }

        btnLeft.setText(getContext().getResources().getString(R.string.cancel));
        btnRight.setText(getContext().getResources().getString(R.string.ok));

        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.roboto_medium);
        npPicker.setTypeface(typeface);

        npPicker.setMinValue(mMinValue);
        npPicker.setMaxValue(mMaxValue);

        npPicker.setFormatter(mFormatter);

        npPicker.setValue(mValue);
    }
}
