package com.eservia.booking.ui.booking.beauty.basket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.TimeUtil;
import com.eservia.model.entity.BeautyService;
import com.eservia.model.entity.BeautyStaff;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.Locale;

public class FilledPreparationsAdapter extends BaseRecyclerAdapter<FilledPreparationListItem> {

    private final OnFilledPreparationsClickListener mListener;

    private final Context mContext;

    public FilledPreparationsAdapter(Context context,
                                     OnFilledPreparationsClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == FilledPreparationListItem.TYPE_PREPARATION) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_booking_beauty_basket_preparation,
                    parent, false);
            return new FilledPreparationViewHolder(itemView);
        } else if (viewType == FilledPreparationListItem.TYPE_HEADER) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_booking_beauty_basket_header,
                    parent, false);
            return new HeaderViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof FilledPreparationViewHolder) {
            FilledPreparationItem item = (FilledPreparationItem) getListItems().get(position);
            FilledPreparationViewHolder holder = (FilledPreparationViewHolder) viewHolder;

            holder.view.setOnClickListener(view -> {
                if (mListener != null) {
                    mListener.onFilledPreparationClick(item);
                }
            });

            Preparation preparation = item.getPreparation();

            BeautyService service = preparation.getService();

            Float price = service.getPrice();
            if (!BookingUtil.servicePriceIsEmpty(price)) {
                holder.tvPrice.setVisibility(View.VISIBLE);
                holder.tvPrice.setText(BookingUtil.formatPrice(preparation.getDiscount() != null ?
                        preparation.getDiscount().getPriceValue() : price));

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
            }

            holder.tvService.setText(service.getName());

            @Nullable BeautyStaff staff = preparation.getStaff();
            String masterName = "";
            if (staff != null && staff.getFirstName() != null && !staff.getFirstName().isEmpty()) {
                masterName = staff.getFirstName();
            }

            @Nullable DateTime timeSlot = preparation.getTimeSlot().getTime();
            String time = "";
            if (timeSlot != null) {
                int hour = Integer.parseInt(timeSlot.toString("HH"));
                int min = Integer.parseInt(timeSlot.toString("mm"));
                time = String.format(Locale.getDefault(), "%02d:%02d", hour, min);
            }

            @Nullable DateTime serviceEndTime = item.getServiceEndTime();
            String endTime = "";
            if (serviceEndTime != null) {
                int hour = Integer.parseInt(serviceEndTime.toString("HH"));
                int min = Integer.parseInt(serviceEndTime.toString("mm"));
                endTime = String.format(Locale.getDefault(), "%02d:%02d", hour, min);
            }

            holder.tvTimeAndMaster.setText(String.format("%s-%s, %s", time, endTime, masterName));

            bindExpectationTime(holder, timeSlot, position);

            if (item.isEditMode()) {
                holder.clOptions.setVisibility(View.VISIBLE);
                holder.clDetails.setVisibility(View.GONE);

                holder.ivDelete.setOnClickListener(view -> {
                    if (mListener != null) {
                        mListener.onDeleteFilledPreparationClick(item);
                    }
                });
                holder.ivEdit.setOnClickListener(view -> {
                    if (mListener != null) {
                        mListener.onEditFilledPreparationClick(item);
                    }
                });
            } else {
                holder.clOptions.setVisibility(View.GONE);
                holder.clDetails.setVisibility(View.VISIBLE);
            }

        } else if (viewHolder instanceof HeaderViewHolder) {
            FilledPreparationHeaderItem item = (FilledPreparationHeaderItem) getListItems().get(position);
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            holder.tvTitle.setText(BookingUtil.dayToDescription(mContext, item.getDay()));
        }
    }

    private void bindExpectationTime(FilledPreparationViewHolder holder, DateTime startTime,
                                     int currentPosition) {
        if (currentPosition <= 0) {
            holder.rlExpectationTime.setVisibility(View.GONE);
            return;
        }

        FilledPreparationListItem prevItem = getItem(currentPosition - 1);
        if (!(prevItem instanceof FilledPreparationItem)) {
            holder.rlExpectationTime.setVisibility(View.GONE);
            return;
        }

        DateTime prevItemServiceEndTime = ((FilledPreparationItem) prevItem).getServiceEndTime();
        if (!TimeUtil.isSameDay(prevItemServiceEndTime, startTime)) {
            holder.rlExpectationTime.setVisibility(View.GONE);
            return;
        }

        int expectationTime = Minutes.minutesBetween(prevItemServiceEndTime, startTime).getMinutes();
        holder.rlExpectationTime.setVisibility(View.VISIBLE);
        if (expectationTime > 60) {
            int hours = expectationTime / 60;
            int minutes = expectationTime % 60;
            if (minutes != 0) {
                holder.tvExpectationTime.setText(mContext.getResources().getString(R.string.hour_min_of_waiting, hours, minutes));
            } else {
                holder.tvExpectationTime.setText(mContext.getResources().getString(R.string.hour_of_waiting, hours));
            }
        } else {
            holder.tvExpectationTime.setText(mContext.getResources().getString(R.string.min_of_waiting, expectationTime));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getListItems().get(position).getItemType();
    }

    public interface OnFilledPreparationsClickListener {

        void onFilledPreparationClick(FilledPreparationItem preparationItem);

        void onDeleteFilledPreparationClick(FilledPreparationItem preparationItem);

        void onEditFilledPreparationClick(FilledPreparationItem preparationItem);
    }

    private static class FilledPreparationViewHolder extends RecyclerView.ViewHolder {

        View view;
        ConstraintLayout clItemContainer;
        RelativeLayout rlExpectationTime;
        TextView tvExpectationTime;
        ConstraintLayout clDetails;
        ConstraintLayout clTitle;
        TextView tvCurrency;
        TextView tvPrice;
        TextView tvTimeLeft;
        ImageView ivTimeLeft;
        TextView tvService;
        TextView tvTimeAndMaster;
        ConstraintLayout clOptions;
        ImageView ivDelete;
        ImageView ivEdit;

        FilledPreparationViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            clItemContainer = itemView.findViewById(R.id.clItemContainer);
            rlExpectationTime = itemView.findViewById(R.id.rlExpectationTime);
            tvExpectationTime = itemView.findViewById(R.id.tvExpectationTime);
            clDetails = itemView.findViewById(R.id.clDetails);
            clTitle = itemView.findViewById(R.id.clTitle);
            tvCurrency = itemView.findViewById(R.id.tvCurrency);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTimeLeft = itemView.findViewById(R.id.tvTimeLeft);
            ivTimeLeft = itemView.findViewById(R.id.ivTimeLeft);
            tvService = itemView.findViewById(R.id.tvService);
            tvTimeAndMaster = itemView.findViewById(R.id.tvTimeAndMaster);
            clOptions = itemView.findViewById(R.id.clOptions);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvTitle;

        HeaderViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
