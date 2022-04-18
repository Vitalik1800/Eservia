package com.eservia.booking.ui.booking.beauty.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.common.view.OnPaginationListener;
import com.eservia.booking.util.BookingUtil;
import com.eservia.model.entity.BeautyService;

public class ServiceAdapter extends BaseRecyclerAdapter<ServiceAdapterItem> {

    private final OnItemClickListener mListener;

    private final OnPaginationListener mPaginationListener;

    private final Context mContext;

    public ServiceAdapter(Context context, OnItemClickListener listener,
                          OnPaginationListener paginationListener) {
        mContext = context;
        mListener = listener;
        mPaginationListener = paginationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_booking_beauty_service, parent, false);
        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position >= getItemCount() - THRESHHOLD) {
            if (mPaginationListener != null) {
                mPaginationListener.loadMore();
            }
        }

        ServiceAdapterItem item = getListItems().get(position);
        ServiceViewHolder holder = (ServiceViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onServiceItemClick(getItem(position));
            }
        });

        BeautyService service = item.getService();
        holder.tvService.setText(service.getName());

        holder.tvFrom.setVisibility(item.getService().isFixedPrice() != null
                && item.getService().isFixedPrice() ? View.VISIBLE : View.GONE);

        Float price = service.getPrice();
        if (!BookingUtil.servicePriceIsEmpty(price)) {
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvPrice.setText(BookingUtil.formatPrice(price));

            String currency = service.getCurrency();
            if (currency != null && !currency.isEmpty()) {
                holder.tvCurrency.setVisibility(View.VISIBLE);
                holder.tvCurrency.setText(currency.toUpperCase());
            } else {
                holder.tvCurrency.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.tvPrice.setVisibility(View.INVISIBLE);
            holder.tvCurrency.setVisibility(View.INVISIBLE);
            holder.tvFrom.setVisibility(View.GONE);
        }

        Integer duration = service.getDuration();
        if (BookingUtil.serviceDurationIsEmpty(duration)) {
            holder.tvDuration.setVisibility(View.VISIBLE);
            holder.tvDuration.setText(String.valueOf(duration));
        } else {
            holder.tvDuration.setVisibility(View.INVISIBLE);
        }

        if (item.isSelected()) {
            holder.llCheck.setBackground(ContextCompat.getDrawable(
                    mContext, R.drawable.background_round_red));
            holder.ivChecked.setVisibility(View.VISIBLE);
        } else {
            holder.llCheck.setBackground(ContextCompat.getDrawable(
                    mContext, R.drawable.background_round_grey_borders));
            holder.ivChecked.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelected(boolean selected, Integer serviceId) {
        for (ServiceAdapterItem item : getListItems()) {
            if (item.getService().getId().equals(serviceId)) {
                item.setSelected(selected);
            }
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {

        void onServiceItemClick(ServiceAdapterItem adapterItem);
    }

    private static class ServiceViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvCurrency;
        TextView tvDuration;
        TextView tvPrice;
        TextView tvService;
        TextView tvFrom;
        LinearLayout llCheck;
        ImageView ivChecked;

        ServiceViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvCurrency = itemView.findViewById(R.id.tvCurrency);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvService = itemView.findViewById(R.id.tvService);
            llCheck = itemView.findViewById(R.id.llCheck);
            ivChecked = itemView.findViewById(R.id.ivChecked);
            tvFrom = itemView.findViewById(R.id.tvFrom);
        }
    }
}
