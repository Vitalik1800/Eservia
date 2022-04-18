package com.eservia.booking.ui.booking.beauty.basket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.util.BookingUtil;
import com.eservia.model.entity.BeautyService;

public class UnFilledPreparationsAdapter extends BaseRecyclerAdapter<UnFilledPreparationItem> {

    private final OnUnFilledPreparationsClickListener mListener;

    public UnFilledPreparationsAdapter(Context context,
                                       OnUnFilledPreparationsClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_booking_beauty_basket_preparation,
                parent, false);
        return new UnFilledPreparationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        UnFilledPreparationItem item = getListItems().get(position);
        UnFilledPreparationViewHolder holder = (UnFilledPreparationViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onUnFilledPreparationClick(item);
            }
        });

        Preparation preparation = item.getPreparation();

        BeautyService service = preparation.getService();

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
        }

        holder.tvService.setText(service.getName());

        holder.tvTimeAndMaster.setVisibility(View.GONE);

        holder.tvTimeLeft.setVisibility(View.INVISIBLE);
        holder.ivTimeLeft.setVisibility(View.INVISIBLE);

        holder.rlExpectationTime.setVisibility(View.GONE);

        holder.view.setAlpha(0.5f);
    }

    public interface OnUnFilledPreparationsClickListener {

        void onUnFilledPreparationClick(UnFilledPreparationItem preparationItem);
    }

    private static class UnFilledPreparationViewHolder extends RecyclerView.ViewHolder {

        View view;
        RelativeLayout rlExpectationTime;
        TextView tvExpectationTime;
        ConstraintLayout clDetails;
        TextView tvCurrency;
        TextView tvPrice;
        TextView tvTimeLeft;
        ImageView ivTimeLeft;
        TextView tvService;
        TextView tvTimeAndMaster;
        ConstraintLayout clOptions;
        ImageView ivDelete;
        ImageView ivEdit;

        UnFilledPreparationViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            rlExpectationTime = itemView.findViewById(R.id.rlExpectationTime);
            tvExpectationTime = itemView.findViewById(R.id.tvExpectationTime);
            clDetails = itemView.findViewById(R.id.clDetails);
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
}
