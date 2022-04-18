package com.eservia.booking.ui.business_page.staff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.utils.StringUtil;

public class BusinessStaffAdapter extends BaseRecyclerAdapter<BeautyStaff> {

    private final OnStaffAdapterClickListener mListener;

    private final OnStaffPaginationListener mPaginationListener;

    private final Context mContext;

    public BusinessStaffAdapter(Context context,
                                OnStaffAdapterClickListener listener,
                                OnStaffPaginationListener paginationListener) {
        mContext = context;
        mListener = listener;
        mPaginationListener = paginationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_business_page_staff, parent, false);
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
                mListener.onStaffItemClick(item, position);
            }
        });

        holder.tvName.setText(BusinessUtil.getStaffFullName(item.getFirstName(),
                item.getLastName()));

        if (!StringUtil.isEmpty(item.getPosition())) {
            holder.tvPosition.setVisibility(View.VISIBLE);
            holder.tvPosition.setText(item.getPosition());
        } else {
            holder.tvPosition.setVisibility(View.GONE);
            holder.tvPosition.setText("");
        }

        try {
            ImageUtil.displayStaffImageRound(mContext, holder.ivImage, item.getPhoto(),
                    R.drawable.user_man_big);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ViewUtil.setCardOutlineProvider(mContext, holder.rlCardHolder, holder.cvContainer);
    }

    public interface OnStaffAdapterClickListener {

        void onStaffItemClick(BeautyStaff beautyStaff, int position);
    }

    public interface OnStaffPaginationListener {

        void loadMoreStaff();
    }

    private static class StaffViewHolder extends RecyclerView.ViewHolder {

        View view;
        RelativeLayout rlCardHolder;
        CardView cvContainer;
        ImageView ivImage;
        TextView tvName;
        TextView tvPosition;

        StaffViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvName = itemView.findViewById(R.id.tvName);
            tvPosition = itemView.findViewById(R.id.tvPosition);
            ivImage = itemView.findViewById(R.id.ivImage);
            cvContainer = itemView.findViewById(R.id.cvContainer);
            rlCardHolder = itemView.findViewById(R.id.rlCardHolder);
        }
    }
}
