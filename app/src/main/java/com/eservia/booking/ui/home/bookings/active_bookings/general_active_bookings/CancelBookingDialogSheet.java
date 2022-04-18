package com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings;

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

public class CancelBookingDialogSheet extends BottomSheetDialogFragment {

    public interface OnCancelBookingListener {

        void onDialogKeepBookingClick();

        void onDialogCancelBookingClick();
    }

    private OnCancelBookingListener mListener;

    public static CancelBookingDialogSheet newInstance() {
        CancelBookingDialogSheet f = new CancelBookingDialogSheet();
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

    public void setListener(OnCancelBookingListener listener) {
        mListener = listener;
    }

    private void initViews(View view) {
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvSubTitle = view.findViewById(R.id.tvSubTitle);
        RelativeLayout rlCancel = view.findViewById(R.id.rlCancel);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        Button btnAccept = view.findViewById(R.id.btnAccept);

        rlCancel.setOnClickListener(accept -> {
            if (mListener != null) {
                mListener.onDialogKeepBookingClick();
            }
        });
        btnAccept.setOnClickListener(accept -> {
            if (mListener != null) {
                mListener.onDialogCancelBookingClick();
            }
        });

        tvTitle.setText(tvTitle.getContext().getResources()
                .getString(R.string.cancel_reservation_question));
        tvSubTitle.setText("");
        tvCancel.setText(tvTitle.getContext().getResources()
                .getString(R.string.keep_on));
        btnAccept.setText(tvTitle.getContext().getResources()
                .getString(R.string.cancel));
        btnAccept.setBackground(ContextCompat.getDrawable(btnAccept.getContext(),
                R.drawable.background_button_try_again));
    }
}
