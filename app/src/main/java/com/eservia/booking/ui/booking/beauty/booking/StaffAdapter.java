package com.eservia.booking.ui.booking.beauty.booking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.model.entity.BeautyStaff;

public class StaffAdapter extends BaseRecyclerAdapter<StaffAdapterItem> {

    private static final int THRESHHOLD = 25;

    private final OnStaffAdapterClickListener mListener;

    private final OnStaffPaginationListener mPaginationListener;

    private final Context mContext;

    @ColorInt
    private int mItemColorId;

    public StaffAdapter(Context context,
                        OnStaffAdapterClickListener listener,
                        OnStaffPaginationListener paginationListener,
                        @ColorInt int itemColorId) {
        mContext = context;
        mListener = listener;
        mPaginationListener = paginationListener;
        mItemColorId = itemColorId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == StaffAdapterItem.TYPE_NOT_SELECTED) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_booking_beauty_staff_unselected,
                    parent, false);
            return new StaffViewHolder(itemView);
        } else if (viewType == StaffAdapterItem.TYPE_SELECTED) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_booking_beauty_staff_selected,
                    parent, false);
            return new SelectedStaffViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position >= getItemCount() - THRESHHOLD) {
            if (mPaginationListener != null) {
                mPaginationListener.loadMoreStaff();
            }
        }
        if (viewHolder instanceof StaffViewHolder) {
            StaffAdapterItem item = getListItems().get(position);
            BeautyStaff staff = item.getStaff();
            StaffViewHolder holder = (StaffViewHolder) viewHolder;

            holder.view.setOnClickListener(view -> {
                if (mListener != null) {
                    mListener.onStaffAdapterItemClick(getItem(position));
                }
            });

            if (staff.getFirstName() != null && !staff.getFirstName().isEmpty()) {
                holder.tvTitle.setText(staff.getFirstName());
            } else {
                holder.tvTitle.setText("");
            }

            try {
                ImageUtil.displayStaffImageRound(mContext, holder.ivImage,
                        staff.getPhoto(), R.drawable.user_man_big);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else if (viewHolder instanceof SelectedStaffViewHolder) {

            StaffAdapterItem item = getListItems().get(position);
            BeautyStaff staff = item.getStaff();

            SelectedStaffViewHolder holder = (SelectedStaffViewHolder) viewHolder;

            holder.view.setOnClickListener(view -> {
            });

            try {
                ImageUtil.displayStaffImageRound(mContext, holder.ivImage,
                        staff.getPhoto(), R.drawable.user_man_big);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            holder.tvBottomTriangle.setTextColor(mItemColorId);
            ((GradientDrawable) holder.clImageContainer.getBackground()).setStroke(
                    Math.round(ViewUtil.dpToPixel(mContext, 4)), mItemColorId);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getListItems().get(position).getItemType();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelected(Integer staffId) {
        for (StaffAdapterItem item : getListItems()) {
            item.setSelected(item.getStaff().getId().equals(staffId));
        }
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItemColorId(@ColorInt int colorId) {
        mItemColorId = colorId;
        notifyDataSetChanged();
    }

    public interface OnStaffAdapterClickListener {

        void onStaffAdapterItemClick(StaffAdapterItem staffAdapterItem);
    }

    public interface OnStaffPaginationListener {

        void loadMoreStaff();
    }

    private static class StaffViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvTitle;
        ImageView ivImage;

        StaffViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }

    private static class SelectedStaffViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView ivImage;
        ConstraintLayout clImageContainer;
        TextView tvBottomTriangle;

        SelectedStaffViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivImage = itemView.findViewById(R.id.ivImage);
            tvBottomTriangle = itemView.findViewById(R.id.tvBottomTriangle);
            clImageContainer = itemView.findViewById(R.id.clImageContainer);
        }
    }
}
