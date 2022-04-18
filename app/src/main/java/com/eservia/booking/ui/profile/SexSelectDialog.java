package com.eservia.booking.ui.profile;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.SpeedyLinearLayoutManager;
import com.eservia.common.view.DividerItemWithoutLastChild;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class SexSelectDialog extends BottomSheetDialogFragment {

    private RecyclerView rvTypes;

    private SexAdapter mAdapter;

    private SexAdapter.OnSexClickListener mAdapterClickListener;

    private final List<SexAdapterItem> mSexAdapterItems = new ArrayList<>();

    public static SexSelectDialog newInstance() {
        SexSelectDialog f = new SexSelectDialog();
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
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_sheet_profile_sex, null);
        rvTypes = view.findViewById(R.id.rvTypes);
        initViews();
        return view;
    }

    public void setSexItemClickListener(SexAdapter.OnSexClickListener clickListener) {
        mAdapterClickListener = clickListener;
        if (mAdapter != null) {
            mAdapter.setClickListener(mAdapterClickListener);
        }
    }

    public void setAdapterItems(List<SexAdapterItem> adapterItems) {
        mSexAdapterItems.clear();
        mSexAdapterItems.addAll(adapterItems);
        if (mAdapter != null) {
            mAdapter.replaceAll(mSexAdapterItems);
        }
    }

    private void initViews() {
        mAdapter = new SexAdapter(rvTypes.getContext(),
                mAdapterClickListener, mSexAdapterItems);
        rvTypes.setAdapter(mAdapter);
        rvTypes.setHasFixedSize(true);
        rvTypes.setLayoutManager(new SpeedyLinearLayoutManager(
                getContext(), SpeedyLinearLayoutManager.VERTICAL, false));

        DividerItemWithoutLastChild decoration = new DividerItemWithoutLastChild(
                rvTypes.getContext(), DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(
                rvTypes.getContext(), R.drawable.horizontal_divider_search_sort);
        decoration.setDrawable(dividerDrawable);
        rvTypes.addItemDecoration(decoration);
    }
}
