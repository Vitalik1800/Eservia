package com.eservia.booking.ui.auth.registration.dialog;

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

public class RegistrationFailedDialog extends BaseDialogFragment {

    private static final String MESSAGE = "message";

    public static RegistrationFailedDialog newInstance(String message) {
        RegistrationFailedDialog f = new RegistrationFailedDialog();
        Bundle args = new Bundle();
        args.putString(MESSAGE, message);
        f.setArguments(args);
        return f;
    }

    private String mMessage;
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
        mMessage = getArguments().getString(MESSAGE);
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
        tvDialogLabel.setText(getContext().getResources().getString(R.string.register_failed));
        if (mMessage == null) {
            tvDialogMessage.setText(getContext().getResources().getString(
                    R.string.wrong_login_password));
        } else {
            tvDialogMessage.setText(mMessage);
        }
        btnLeft.setVisibility(View.INVISIBLE);
        btnRight.setText(getContext().getResources().getString(R.string.ok));
    }
}
