package com.eservia.booking.ui.home.search.search.sheet;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class SearchSheetDialog extends BottomSheetDialogFragment implements
        SheetAdapter.OnSheetAdapterItemClickListener {

    public interface OnSheetDialogItemClickListener {

        void onDialogItemClick(SheetAdapterItem item, String dialogType);
    }

    private RecyclerView rvSortTypes;

    private SheetAdapter mSortAdapter;

    private OnSheetDialogItemClickListener mClickListener;

    private final List<SheetAdapterItem> mAdapterItems = new ArrayList<>();

    private TextView tvTitle;

    private String mTitle;

    private String mDialogType;

    public static SearchSheetDialog newInstance(String dialogType) {
        SearchSheetDialog f = new SearchSheetDialog();
        Bundle args = new Bundle();
        args.putString("type", dialogType);
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
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_sheet_search, null);
        rvSortTypes = view.findViewById(R.id.rvSortTypes);
        tvTitle = view.findViewById(R.id.tvTitle);
        if (getArguments() != null)
            mDialogType = getArguments().getString("type");
        initViews();
        return view;
    }

    @Override
    public void onSortTypeClick(SheetAdapterItem item) {
        if (mClickListener != null) {
            mClickListener.onDialogItemClick(item, mDialogType);
        }
    }

    public void setSortClickListener(OnSheetDialogItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setAdapterItems(List<SheetAdapterItem> adapterItems) {
        mAdapterItems.clear();
        mAdapterItems.addAll(adapterItems);
        if (mSortAdapter != null) {
            mSortAdapter.replaceAll(mAdapterItems);
        }
    }

    public void setTitle(String title) {
        mTitle = title;
        if (tvTitle != null) {
            tvTitle.setText(mTitle);
        }
    }

    private void initViews() {
        mSortAdapter = new SheetAdapter(rvSortTypes.getContext(), this, mAdapterItems);
        rvSortTypes.setAdapter(mSortAdapter);
        rvSortTypes.setHasFixedSize(true);
        rvSortTypes.setLayoutManager(new SpeedyLinearLayoutManager(
                getContext(), SpeedyLinearLayoutManager.VERTICAL, false));

        DividerItemWithoutLastChild decoration = new DividerItemWithoutLastChild(
                rvSortTypes.getContext(), DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(
                rvSortTypes.getContext(), R.drawable.horizontal_divider_search_sort);
        decoration.setDrawable(dividerDrawable);
        rvSortTypes.addItemDecoration(decoration);

        if (mTitle != null) {
            tvTitle.setText(mTitle);
        }
    }
}
