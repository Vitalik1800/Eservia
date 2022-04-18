package com.eservia.booking.ui.profile;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eservia.booking.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CameraExplanationDialog extends BottomSheetDialogFragment {

    public interface Listener {

        void onCameraExplanationDialogOkClick();

        void onCameraExplanationDialogDismissed();
    }

    private Listener mListener;

    public static CameraExplanationDialog newInstance() {
        CameraExplanationDialog f = new CameraExplanationDialog();
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
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_sheet_camera_explanation, null);
        initViews(view);
        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (mListener != null) {
            mListener.onCameraExplanationDialogDismissed();
        }
        super.onDismiss(dialog);
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    private void initViews(View view) {

        Button btnOk = view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onCameraExplanationDialogOkClick();
            }
        });
    }
}
