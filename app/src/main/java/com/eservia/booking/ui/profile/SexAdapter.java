package com.eservia.booking.ui.profile;

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

public class SexAdapter extends BaseRecyclerAdapter<SexAdapterItem> {

    private OnSexClickListener mClickListener;

    private final Context mContext;

    public SexAdapter(Context context, OnSexClickListener clickListener,
                      List<SexAdapterItem> itemList) {
        mContext = context;
        mClickListener = clickListener;
        getListItems().addAll(itemList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_profile_sex, parent, false);
        return new SexViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        SexAdapterItem item = getListItems().get(position);
        SexViewHolder holder = (SexViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mClickListener != null) {
                mClickListener.onSexItemClick(item);
            }
        });

        holder.tvType.setText(mContext.getResources().getString(item.getTitleStringId()));

        if (item.isChecked()) {
            holder.ivChecked.setVisibility(View.VISIBLE);
        } else {
            holder.ivChecked.setVisibility(View.INVISIBLE);
        }
    }

    public void setClickListener(OnSexClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface OnSexClickListener {

        void onSexItemClick(SexAdapterItem item);
    }

    private static class SexViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvType;
        ImageView ivChecked;

        SexViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvType = itemView.findViewById(R.id.tvType);
            ivChecked = itemView.findViewById(R.id.ivChecked);
        }
    }
}
