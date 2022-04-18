package com.eservia.booking.ui.booking.dialog;


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
import com.eservia.booking.common.view.BaseDialogFragment;
import com.eservia.butterknife.ButterKnife;

public class AlreadyBookedDialog extends BaseDialogFragment {

    public static AlreadyBookedDialog newInstance() {
        AlreadyBookedDialog f = new AlreadyBookedDialog();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    TextView tvDialogLabel;
    TextView tvDialogMessage;
    Button btnLeft;
    Button btnRight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_two_buttons, null);
        this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.getDialog().getWindow().getDecorView().setBackgroundResource(R.color.transparent);
        setUnbinder(ButterKnife.bind(this, view));
        tvDialogLabel = view.findViewById(R.id.tvDialogLabel);
        tvDialogMessage = view.findViewById(R.id.tvDialogMessage);
        btnLeft = view.findViewById(R.id.btnLeft);
        btnRight = view.findViewById(R.id.btnRight);
        btnLeft.setOnClickListener(v -> onClickButtonLeft());
        btnRight.setOnClickListener(v -> onClickButtonRight());
        initViews();
        return view;
    }

    public void onClickButtonLeft() {
        dismiss();
    }

    public void onClickButtonRight() {
        dismiss();
    }

    private void initViews() {
        tvDialogLabel.setText(getContext().getResources().getString(R.string.already_booked));
        tvDialogMessage.setText(getContext().getResources().getString(R.string.do_another_selection));
        btnLeft.setVisibility(View.INVISIBLE);
        btnRight.setText(getContext().getResources().getString(R.string.do_another_attempt));
    }
}
