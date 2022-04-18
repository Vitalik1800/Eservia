package com.eservia.booking.ui.event_details.beauty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.model.entity.BeautyService;
import com.eservia.model.entity.BeautyServiceGroup;
import com.eservia.utils.StringUtil;

public class ServiceAdapter extends BaseRecyclerAdapter<ServiceAdapterItem> {

    private final OnServiceClickListener mListener;

    private final Context mContext;

    public ServiceAdapter(Context context, OnServiceClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_beauty_service_and_group, parent, false);
        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ServiceAdapterItem item = getListItems().get(position);
        ServiceViewHolder holder = (ServiceViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onServiceItemClick(getItem(position));
            }
        });

        BeautyServiceGroup serviceGroup = item.getServiceGroup();
        holder.tvServiceGroup.setText(serviceGroup != null ? serviceGroup.getName() : "");

        BeautyService service = item.getService();
        holder.tvService.setText(service.getName());

        Float price = service.getPrice();
        if (!BookingUtil.servicePriceIsEmpty(price)) {
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvPrice.setText(BookingUtil.formatPrice(
                    BookingUtil.getDiscountPrice(item.getMarketing(), price)));

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

        String oldPrice = BookingUtil.oldPriceText(item.getMarketing(), price, service.getCurrency());
        holder.tvFrom.setVisibility(!StringUtil.isEmpty(oldPrice) ? View.VISIBLE : View.GONE);
        holder.tvFrom.setText(oldPrice);
        ViewUtil.applyCrossedTextStyle(holder.tvFrom);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(holder.clDetails);
        constraintSet.clear(R.id.tvFrom, ConstraintSet.LEFT);
        constraintSet.applyTo(holder.clDetails);

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

    public interface OnServiceClickListener {

        void onServiceItemClick(ServiceAdapterItem adapterItem);
    }

    private static class ServiceViewHolder extends RecyclerView.ViewHolder {

        View view;
        ConstraintLayout clDetails;
        TextView tvCurrency;
        TextView tvDuration;
        TextView tvPrice;
        TextView tvService;
        TextView tvFrom;
        LinearLayout llCheck;
        ImageView ivChecked;
        TextView tvServiceGroup;

        ServiceViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            clDetails = itemView.findViewById(R.id.clDetails);
            tvCurrency = itemView.findViewById(R.id.tvCurrency);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvService = itemView.findViewById(R.id.tvService);
            llCheck = itemView.findViewById(R.id.llCheck);
            ivChecked = itemView.findViewById(R.id.ivChecked);
            tvFrom = itemView.findViewById(R.id.tvFrom);
            tvServiceGroup = itemView.findViewById(R.id.tvServiceGroup);
        }
    }
}
