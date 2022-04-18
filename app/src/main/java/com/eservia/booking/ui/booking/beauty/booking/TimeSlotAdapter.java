package com.eservia.booking.ui.booking.beauty.booking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;

import org.joda.time.DateTime;

public class TimeSlotAdapter extends BaseRecyclerAdapter<TimeSlotAdapterItem> {

    private final OnTimeSlotClickListener mListener;

    private final Context mContext;

    @ColorInt
    private int mItemColorId;

    public TimeSlotAdapter(Context context, OnTimeSlotClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_booking_beauty_time_slot, parent, false);
        return new TimeSlotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        TimeSlotAdapterItem item = getListItems().get(position);
        TimeSlotViewHolder holder = (TimeSlotViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mListener != null && !getItem(position).isSelected() && !getItem(position).isDisabled()) {
                mListener.onTimeSlotItemClick(getItem(position));
            }
        });

        if (item.isDisabled()) {
            holder.tvTimeSlotTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorInactive));
        } else {
            holder.tvTimeSlotTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black_light_secondary));
        }

        if (item.isSelected()) {
            holder.tvTimeSlotTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.ivTimeSlotSelected.setColorFilter(mItemColorId, PorterDuff.Mode.SRC_IN);
            holder.ivTimeSlotSelected.setVisibility(View.VISIBLE);
        } else {
            holder.ivTimeSlotSelected.setVisibility(View.INVISIBLE);
        }

        String hour = item.getDateTime().toString("HH");
        String min = item.getDateTime().toString("mm");
        String hoursText = hour.length() > 1 ? hour : "0" + hour;
        String minutesText = min.length() > 1 ? min : "0" + min;
        holder.tvTimeSlotTitle.setText(String.format("%s:%s", hoursText, minutesText));

        if (item.getDiscount() != null) {
            holder.ivDiscount.setVisibility(View.VISIBLE);
        } else {
            holder.ivDiscount.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItemColorId(@ColorInt int colorId) {
        mItemColorId = colorId;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelected(DateTime dateTime) {
        for (TimeSlotAdapterItem item : getListItems()) {
            item.setSelected(item.getDateTime().equals(dateTime));
        }
        notifyDataSetChanged();
    }

    public interface OnTimeSlotClickListener {

        void onTimeSlotItemClick(TimeSlotAdapterItem adapterItem);
    }

    private static class TimeSlotViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvTimeSlotTitle;
        ImageView ivTimeSlotSelected;
        ImageView ivDiscount;

        TimeSlotViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvTimeSlotTitle = itemView.findViewById(R.id.tvTimeSlotTitle);
            ivTimeSlotSelected = itemView.findViewById(R.id.ivTimeSlotSelected);
            ivDiscount = itemView.findViewById(R.id.ivDiscount);
        }
    }
}
