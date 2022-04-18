package com.eservia.booking.ui.booking.beauty.basket;

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

public class WarningUnfilledSheetDialog extends BottomSheetDialogFragment {

    public interface Listener {

        void onWarningUnfilledFinishClick();

        void onWarningUnfilledFillClick();
    }

    private Listener mListener;

    private int mFilledCount;

    private int mUnFilledCount;

    private TextView tvTitle;

    public static WarningUnfilledSheetDialog newInstance() {
        WarningUnfilledSheetDialog f = new WarningUnfilledSheetDialog();
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

    public void setPreparationsCount(int filledCount, int unFilledCount) {
        mFilledCount = filledCount;
        mUnFilledCount = unFilledCount;
        setTitle();
    }

    private void initViews(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvSubTitle = view.findViewById(R.id.tvSubTitle);
        RelativeLayout rlCancel = view.findViewById(R.id.rlCancel);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        Button btnAccept = view.findViewById(R.id.btnAccept);

        rlCancel.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onWarningUnfilledFillClick();
            }
        });
        btnAccept.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onWarningUnfilledFinishClick();
            }
        });

        setTitle();
        tvSubTitle.setText(tvTitle.getContext().getResources().getString(R.string.unfilled_services_booking_warning_message));
        tvCancel.setText(tvTitle.getContext().getResources().getString(R.string.fill_up));
        btnAccept.setText(tvTitle.getContext().getResources().getString(R.string.finish));
        btnAccept.setBackground(ContextCompat.getDrawable(btnAccept.getContext(), R.drawable.background_common_booking_button));
    }

    private void setTitle() {
        if (tvTitle != null && getContext() != null) {
            tvTitle.setText(getContext().getResources().getString(R.string.unfilled_services_booking_warning_title,
                    mFilledCount, mFilledCount + mUnFilledCount));
        }
    }
}
