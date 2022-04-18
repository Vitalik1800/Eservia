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

public class StorageExplanationDialog extends BottomSheetDialogFragment {

    public interface Listener {

        void onStorageExplanationDialogOkClick();

        void onStorageExplanationDialogDismissed();
    }

    private Listener mListener;

    public static StorageExplanationDialog newInstance() {
        StorageExplanationDialog f = new StorageExplanationDialog();
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
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_sheet_storage_explanation, null);
        initViews(view);
        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (mListener != null) {
            mListener.onStorageExplanationDialogDismissed();
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
                mListener.onStorageExplanationDialogOkClick();
            }
        });
    }
}
