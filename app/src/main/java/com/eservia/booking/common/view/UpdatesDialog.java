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
import com.eservia.butterknife.ButterKnife;

public class UpdatesDialog extends BaseDialogFragment {

    public static UpdatesDialog newInstance() {
        UpdatesDialog f = new UpdatesDialog();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    public interface UpdatesDialogListener {
        void onCancelUpdateClick();
        void onAcceptUpdateClick();
    }
    TextView tvDialogLabel;
    TextView tvDialogMessage;
    Button btnLeft;
    Button btnRight;

    @Nullable
    private UpdatesDialogListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_two_buttons, null);
        this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.getDialog().getWindow().getDecorView().setBackgroundResource(R.color.transparent);
        setUnbinder(ButterKnife.bind(this, view));
        initViews();
        tvDialogLabel = view.findViewById(R.id.tvDialogLabel);
        tvDialogMessage = view.findViewById(R.id.tvDialogMessage);
        btnLeft = view.findViewById(R.id.btnLeft);
        btnRight = view.findViewById(R.id.btnRight);
        btnLeft.setOnClickListener(v->onClickButtonLeft());
        btnRight.setOnClickListener(v->onClickButtonRight());
        return view;
    }

    public void onClickButtonLeft() {
        dismiss();
        if (mListener != null) {
            mListener.onCancelUpdateClick();
        }
    }

    public void onClickButtonRight() {
        dismiss();
        if (mListener != null) {
            mListener.onAcceptUpdateClick();
        }
    }

    public void setListener(UpdatesDialogListener listener) {
        mListener = listener;
    }

    private void initViews() {
        String appName = getContext().getResources().getString(R.string.app_name);
        tvDialogLabel.setText(getContext().getResources().getString(R.string.updates_dialog_title));
        tvDialogMessage.setText(getContext().getResources().getString(R.string.updates_dialog_message, appName));
        btnLeft.setVisibility(View.INVISIBLE);
        btnRight.setText(getContext().getResources().getString(R.string.updates_dialog_btn_update, appName));
    }
}
