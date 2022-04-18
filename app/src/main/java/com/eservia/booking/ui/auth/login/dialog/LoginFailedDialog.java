package com.eservia.booking.ui.auth.login.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseDialogFragment;
import com.eservia.butterknife.ButterKnife;


public class LoginFailedDialog extends BaseDialogFragment {

    private static final String MESSAGE = "message";

    public interface OnButtonClickListener {
        void onForgotPassClick();
    }

    @Nullable
    private OnButtonClickListener mListener;

    public static LoginFailedDialog newInstance(String message) {
        LoginFailedDialog f = new LoginFailedDialog();
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
        tvDialogLabel = view.findViewById(R.id.tvDialogLabel);
        tvDialogMessage = view.findViewById(R.id.tvDialogMessage);
        btnLeft = view.findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(v -> onClickButtonLeft());
        btnRight = view.findViewById(R.id.btnRight);
        btnRight.setOnClickListener(v -> onClickButtonRight());
        mMessage = getArguments().getString(MESSAGE);
        initViews();
        return view;
    }

    public void onClickButtonLeft() {
        dismiss();
        if (mListener != null) {
            mListener.onForgotPassClick();
        }
    }

    public void onClickButtonRight() {
        dismiss();
    }

    public void setListener(@NonNull OnButtonClickListener listener) {
        mListener = listener;
    }

    private void initViews() {
        tvDialogLabel.setText(getContext().getResources().getString(R.string.login_failed));
        if (mMessage == null) {
            tvDialogMessage.setText(getContext().getResources().getString(
                    R.string.wrong_login_password));
        } else {
            tvDialogMessage.setText(mMessage);
        }
        btnLeft.setText(getContext().getResources().getString(R.string.recover_password));
        btnRight.setText(getContext().getResources().getString(R.string.ok));
    }
}
