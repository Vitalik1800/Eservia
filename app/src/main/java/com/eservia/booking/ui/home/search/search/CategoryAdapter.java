package com.eservia.booking.ui.home.search.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.util.SectorUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.model.entity.BusinessCategory;

public class CategoryAdapter extends BaseRecyclerAdapter<CategoryAdapterItem> {

    private final OnCategoryItemClickListener mListener;

    private final CategoryPaginationListener mPaginationListener;

    private final Context mContext;

    public CategoryAdapter(Context context, OnCategoryItemClickListener listener,
                           CategoryPaginationListener paginationListener) {
        mContext = context;
        mListener = listener;
        mPaginationListener = paginationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_search_category_item, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position >= getItemCount() - THRESHHOLD) {
            if (mPaginationListener != null) {
                mPaginationListener.loadMoreCategories();
            }
        }

        CategoryAdapterItem item = getListItems().get(position);
        CategoryViewHolder holder = (CategoryViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onCategoryItemClick(getItem(position));
            }
        });

        BusinessCategory category = item.getCategory();
        holder.tvCategory.setText(SectorUtil.businessCategoryName(mContext, category));

        if (item.isSelected()) {
            holder.view.setBackground(ContextCompat.getDrawable(
                    mContext, R.drawable.background_service_prepared_checked));

            holder.view.getBackground().mutate().setColorFilter(
                    ContextCompat.getColor(mContext, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

            ((GradientDrawable) holder.point.getBackground()).setStroke(
                    Math.round(ViewUtil.dpToPixel(mContext, 1)),
                    ContextCompat.getColor(mContext, R.color.white));

            holder.tvCategory.setTextColor(ContextCompat.getColor(mContext, R.color.white));

        } else {
            holder.view.setBackground(ContextCompat.getDrawable(
                    mContext, R.drawable.background_service_prepared_checked));

            ((GradientDrawable) holder.view.getBackground()).setColor(
                    ContextCompat.getColor(mContext, R.color.green_light_2));

            ((GradientDrawable) holder.view.getBackground()).setStroke(
                    Math.round(ViewUtil.dpToPixel(mContext, 1)),
                    ContextCompat.getColor(mContext, R.color.colorPrimary));

            ((GradientDrawable) holder.point.getBackground()).setStroke(
                    Math.round(ViewUtil.dpToPixel(mContext, 1)),
                    ContextCompat.getColor(mContext, R.color.colorPrimary));

            holder.tvCategory.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelected(boolean selected, Integer id) {
        for (CategoryAdapterItem item : getListItems()) {
            if (item.getCategory().getId().equals(id)) {
                item.setSelected(selected);
            }
        }
        notifyDataSetChanged();
    }

    public interface OnCategoryItemClickListener {

        void onCategoryItemClick(CategoryAdapterItem adapterItem);
    }

    public interface CategoryPaginationListener {

        void loadMoreCategories();
    }

    private static class CategoryViewHolder extends RecyclerView.ViewHolder {

        View view;
        View point;
        TextView tvCategory;

        CategoryViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            point = itemView.findViewById(R.id.point);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }
    }
}
