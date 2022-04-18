package com.eservia.booking.ui.booking.beauty.service_group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.common.view.OnPaginationListener;
import com.eservia.booking.util.ViewUtil;
import com.eservia.model.entity.BeautyServiceGroup;
import com.eservia.utils.StringUtil;

public class ServiceGroupAdapter extends BaseRecyclerAdapter<BeautyServiceGroup> {

    private static final int THRESHHOLD = 25;

    private final Context mContext;

    private final OnItemClickListener mListener;

    private final OnPaginationListener mPaginationListener;

    public ServiceGroupAdapter(Context context, OnItemClickListener listener,
                               OnPaginationListener paginationListener) {
        mContext = context;
        mListener = listener;
        mPaginationListener = paginationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_booking_beauty_service_group, parent, false);
        return new ServiceGroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position >= getItemCount() - THRESHHOLD) {
            if (mPaginationListener != null) {
                mPaginationListener.loadMore();
            }
        }

        BeautyServiceGroup serviceGroup = getListItems().get(position);
        ServiceGroupViewHolder holder = (ServiceGroupViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onServiceGroupClick(getItem(position));
            }
        });

        String serviceGroupName = serviceGroup.getName();
        if (!StringUtil.isEmpty(serviceGroupName)) {
            holder.tvServiceGroup.setText(serviceGroupName);
        } else {
            holder.tvServiceGroup.setText("");
        }

        ViewUtil.setCardOutlineProvider(mContext, holder.rlCardHolder, holder.cvContainer);
    }

    public interface OnItemClickListener {

        void onServiceGroupClick(BeautyServiceGroup serviceGroup);
    }


    private static class ServiceGroupViewHolder extends RecyclerView.ViewHolder {

        View view;
        RelativeLayout rlCardHolder;
        CardView cvContainer;
        TextView tvServiceGroup;

        ServiceGroupViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            rlCardHolder = itemView.findViewById(R.id.rlCardHolder);
            cvContainer = itemView.findViewById(R.id.cvContainer);
            tvServiceGroup = itemView.findViewById(R.id.tvServiceGroup);
        }
    }
}
