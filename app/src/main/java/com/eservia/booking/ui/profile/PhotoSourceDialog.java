package com.eservia.booking.ui.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.eservia.booking.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class PhotoSourceDialog extends BottomSheetDialogFragment {

    public interface Listener {

        void onChoosePhotoClick();

        void onTakePhotoClick();
    }

    private Listener mListener;

    public static PhotoSourceDialog newInstance() {
        PhotoSourceDialog f = new PhotoSourceDialog();
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
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_sheet_profile_photo_source, null);
        initViews(view);
        return view;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    private void initViews(View view) {

        RelativeLayout rlTakePhoto = view.findViewById(R.id.rlTakePhoto);

        RelativeLayout rlChoosePhoto = view.findViewById(R.id.rlChoosePhoto);

        rlTakePhoto.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onTakePhotoClick();
            }
        });

        rlChoosePhoto.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onChoosePhotoClick();
            }
        });
    }
}
