package com.eservia.booking.ui.booking.beauty.booking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.model.entity.BeautyService;

import org.joda.time.DateTime;

import java.util.Locale;

public class PreparedServicesAdapter extends BaseRecyclerAdapter<PreparedServicesAdapterItem> {

    private final OnItemClickListener mListener;

    private final Context mContext;

    public PreparedServicesAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_booking_beauty_service_prepared, parent, false);
        return new PreparedServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        PreparedServicesAdapterItem item = getListItems().get(position);
        PreparedServiceViewHolder holder = (PreparedServiceViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mListener != null && !getItem(position).isSelected()) {
                mListener.onPreparedServiceItemClick(getItem(position));
            }
        });

        BeautyService service = item.getPreparation().getService();
        holder.tvTitle.setText(service.getName());

        if (item.isSelected()) {
            holder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.tvTime.setTextColor(ContextCompat.getColor(mContext, R.color.white));

            holder.tvBottomTriangle.setVisibility(View.VISIBLE);
            holder.tvBottomTriangle.setTextColor(item.getPreparation().getColorId());

            holder.clDetails.setBackground(ContextCompat.getDrawable(
                    mContext, R.drawable.background_service_prepared_checked));
            holder.clDetails.getBackground().mutate().setColorFilter(
                    item.getPreparation().getColorId(), PorterDuff.Mode.SRC_IN);

        } else {
            holder.tvTitle.setTextColor(item.getPreparation().getColorId());
            holder.tvTime.setTextColor(item.getPreparation().getColorId());

            holder.tvBottomTriangle.setVisibility(View.INVISIBLE);

            holder.clDetails.setBackground(ContextCompat.getDrawable(
                    mContext, R.drawable.background_service_prepared_un_checked));
            ((GradientDrawable) holder.clDetails.getBackground()).setStroke(
                    Math.round(ViewUtil.dpToPixel(mContext, 1)), item.getPreparation().getColorId());
        }

        if (item.isFinished()) {
            holder.ivFinished.setVisibility(View.VISIBLE);
            holder.tvTime.setVisibility(View.VISIBLE);
            String timeFormat = "%02d:%02d";
            String serviceDurationFormat = "%s-%s";
            DateTime startDate = item.getPreparation().getTimeSlot().getTime();
            int hour = Integer.parseInt(startDate.toString("HH"));
            int min = Integer.parseInt(startDate.toString("mm"));
            String time = String.format(Locale.getDefault(), timeFormat, hour, min);
            DateTime endDate = BookingUtil.serviceEndTime(startDate, service.getDuration());
            int endHour = Integer.parseInt(endDate.toString("HH"));
            int endMin = Integer.parseInt(endDate.toString("mm"));
            String endTime = String.format(Locale.getDefault(), timeFormat, endHour, endMin);
            holder.tvTime.setText(String.format(serviceDurationFormat, time, endTime));
        } else {
            holder.ivFinished.setVisibility(View.INVISIBLE);
            holder.tvTime.setVisibility(View.GONE);
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelected(Integer serviceId) {
        for (PreparedServicesAdapterItem item : getListItems()) {
            item.setSelected(item.getPreparation().getService().getId().equals(serviceId));
        }
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAllSelected() {
        for (PreparedServicesAdapterItem item : getListItems()) {
            item.setSelected(true);
        }
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void revalidateFinishedStates() {
        for (PreparedServicesAdapterItem item : getListItems()) {
            item.setFinished(item.getPreparation().isFull());
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {

        void onPreparedServiceItemClick(PreparedServicesAdapterItem adapterItem);
    }

    private static class PreparedServiceViewHolder extends RecyclerView.ViewHolder {

        View view;
        ConstraintLayout clDetails;
        TextView tvTitle;
        TextView tvTime;
        TextView tvBottomTriangle;
        ImageView ivFinished;

        PreparedServiceViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            clDetails = itemView.findViewById(R.id.clDetails);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvBottomTriangle = itemView.findViewById(R.id.tvBottomTriangle);
            ivFinished = itemView.findViewById(R.id.ivFinished);
        }
    }
}
