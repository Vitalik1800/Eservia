package com.eservia.booking.ui.home.search.search.sheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;

import java.util.List;

public class SheetAdapter extends BaseRecyclerAdapter<SheetAdapterItem> {

    private OnSheetAdapterItemClickListener mClickListener;

    private final Context mContext;

    public SheetAdapter(Context context, OnSheetAdapterItemClickListener clickListener,
                        List<SheetAdapterItem> sortAdapterItems) {
        mContext = context;
        mClickListener = clickListener;
        getListItems().addAll(sortAdapterItems);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_search_sort, parent, false);
        return new SortTypeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        SheetAdapterItem item = getListItems().get(position);
        SortTypeViewHolder holder = (SortTypeViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mClickListener != null) {
                mClickListener.onSortTypeClick(item);
            }
        });

        if (item.getTitle() != null) {
            holder.tvSortType.setText(item.getTitle());
        }
        if (item.getTitleStringId() != null) {
            holder.tvSortType.setText(mContext.getResources().getString(item.getTitleStringId()));
        }

        if (item.isChecked()) {
            holder.ivChecked.setVisibility(View.VISIBLE);
        } else {
            holder.ivChecked.setVisibility(View.INVISIBLE);
        }
    }

    public void setClickListener(OnSheetAdapterItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface OnSheetAdapterItemClickListener {

        void onSortTypeClick(SheetAdapterItem item);
    }

    private static class SortTypeViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvSortType;
        ImageView ivChecked;

        SortTypeViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvSortType = itemView.findViewById(R.id.tvSortType);
            ivChecked = itemView.findViewById(R.id.ivChecked);
        }
    }
}
