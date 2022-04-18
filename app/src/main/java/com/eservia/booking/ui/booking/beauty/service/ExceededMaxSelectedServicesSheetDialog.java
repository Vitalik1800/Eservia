package com.eservia.booking.ui.booking.beauty.service;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eservia.booking.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ExceededMaxSelectedServicesSheetDialog extends BottomSheetDialogFragment {

    public interface Listener {

        void onExceededMaxSelectedServicesDoneClick();
    }

    private Listener mListener;

    public static ExceededMaxSelectedServicesSheetDialog newInstance() {
        ExceededMaxSelectedServicesSheetDialog f = new ExceededMaxSelectedServicesSheetDialog();
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
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_sheet_exceeded_max_services, null);
        initViews(view);
        return view;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    private void initViews(View view) {
        RelativeLayout rlAccept = view.findViewById(R.id.rlAccept);
        rlAccept.setOnClickListener(accept -> {
            if (mListener != null) {
                mListener.onExceededMaxSelectedServicesDoneClick();
            }
        });
    }
}
