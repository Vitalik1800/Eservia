package com.eservia.booking.ui.business_page.beauty.info;

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
import com.eservia.booking.util.ImageUtil;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.utils.StringUtil;

public class StaffAdapter extends BaseRecyclerAdapter<BeautyStaff> {

    private static final int THRESHHOLD = 25;

    private final OnStaffAdapterClickListener mListener;

    private final OnStaffPaginationListener mPaginationListener;

    private final Context mContext;

    public StaffAdapter(Context context,
                        OnStaffAdapterClickListener listener,
                        OnStaffPaginationListener paginationListener) {
        mContext = context;
        mListener = listener;
        mPaginationListener = paginationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_business_page_beauty_staff_single,
                parent, false);
        return new StaffViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position >= getItemCount() - THRESHHOLD) {
            if (mPaginationListener != null) {
                mPaginationListener.loadMoreStaff();
            }
        }
        BeautyStaff item = getListItems().get(position);
        StaffViewHolder holder = (StaffViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onStaffItemClick(getItem(position));
            }
        });

        if (!StringUtil.isEmpty(item.getFirstName())) {
            holder.tvTitle.setText(item.getFirstName());
        } else {
            holder.tvTitle.setText("");
        }

        try {
            ImageUtil.displayStaffImageRound(mContext, holder.ivImage, item.getPhoto(), R.drawable.user_man_big);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public interface OnStaffAdapterClickListener {

        void onStaffItemClick(BeautyStaff beautyStaff);
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
}
