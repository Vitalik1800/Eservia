package com.eservia.booking.ui.business_page.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.eservia.booking.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class PrepaymentSheetDialog extends BottomSheetDialogFragment {

    public interface Listener {

        void onPaymentAcceptClick();

        void onPaymentLaterClick();
    }

    private Listener mListener;

    public static PrepaymentSheetDialog newInstance() {
        PrepaymentSheetDialog f = new PrepaymentSheetDialog();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_sheet_two_buttons, null);
        initViews(view);
        return view;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    private void initViews(View view) {
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvSubTitle = view.findViewById(R.id.tvSubTitle);
        RelativeLayout rlCancel = view.findViewById(R.id.rlCancel);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        Button btnAccept = view.findViewById(R.id.btnAccept);

        rlCancel.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onPaymentLaterClick();
            }
        });
        btnAccept.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onPaymentAcceptClick();
            }
        });

        tvTitle.setText(tvTitle.getContext().getResources().getString(R.string.make_prepayment_title));
        tvSubTitle.setText(tvTitle.getContext().getResources().getString(R.string.make_prepayment_sub_title));
        tvCancel.setText(tvTitle.getContext().getResources().getString(R.string.not_now));
        btnAccept.setText(tvTitle.getContext().getResources().getString(R.string.pay));
        btnAccept.setBackground(ContextCompat.getDrawable(btnAccept.getContext(), R.drawable.background_common_payment_button));
    }
}
