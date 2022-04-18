package com.eservia.booking.common.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.eservia.booking.R;
import com.eservia.booking.util.RateUsUtil;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.prefs.RateUs;

public class RateUsDialog extends BaseDialogFragment {

    public static RateUsDialog newInstance() {
        RateUsDialog f = new RateUsDialog();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }
    TextView tvDialogLabel;
    TextView tvDialogMessage;
    Button btnNeutral;
    Button btnNegative;
    Button btnPositive;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_three_buttons, null);
        this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.getDialog().getWindow().getDecorView().setBackgroundResource(R.color.transparent);
        setUnbinder(ButterKnife.bind(this, view));
        tvDialogLabel = view.findViewById(R.id.tvDialogLabel);
        tvDialogMessage = view.findViewById(R.id.tvDialogMessage);
        btnNeutral = view.findViewById(R.id.btnNeutral);
        btnNegative = view.findViewById(R.id.btnNegative);
        btnPositive = view.findViewById(R.id.btnPositive);
        btnNeutral.setOnClickListener(v -> onClickButtonNeutral());
        btnNegative.setOnClickListener(v -> onClickButtonNegative());
        btnPositive.setOnClickListener(v -> onClickButtonPositive());
        initViews();
        return view;
    }
    public void onClickButtonNeutral() {
        dismiss();
    }
    public void onClickButtonNegative() {
        RateUs.setDontShowAgain(true);
        dismiss();
    }
    public void onClickButtonPositive() {
        if (getActivity() != null) {
            RateUsUtil.openPlayMarketPage(getActivity());
        }
        RateUs.setDontShowAgain(true);
        dismiss();
    }

    private void initViews() {
        String appName = getContext().getResources().getString(R.string.app_name);
        tvDialogLabel.setText(getContext().getResources().getString(R.string.rate_us_dialog_title, appName));
        tvDialogMessage.setText(getContext().getResources().getString(R.string.rate_us_dialog_message, appName));
        btnNeutral.setText(getContext().getResources().getString(R.string.rate_us_dialog_btn_neutral));
        btnNegative.setText(getContext().getResources().getString(R.string.rate_us_dialog_btn_negative));
        btnPositive.setText(getContext().getResources().getString(R.string.rate_us_dialog_btn_positive));
    }
}
