package com.eservia.booking.ui.booking.beauty.basket_sort;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.util.BookingUtil;
import com.eservia.common.view.item_touch_helper.ItemTouchHelperAdapter;
import com.eservia.common.view.item_touch_helper.ItemTouchHelperViewHolder;
import com.eservia.common.view.item_touch_helper.OnStartDragListener;
import com.eservia.model.entity.BeautyService;

import java.util.Collections;

public class BasketSortAdapter extends BaseRecyclerAdapter<BasketSortAdapterItem> implements
        ItemTouchHelperAdapter {

    private final OnPreparationsClickListener mListener;

    private final OnStartDragListener mDragStartListener;

    public BasketSortAdapter(Context context, OnPreparationsClickListener listener,
                             OnStartDragListener dragStartListener) {
        mListener = listener;
        mDragStartListener = dragStartListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_booking_beauty_basket_sort_preparation,
                parent, false);
        return new PreparationViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        BasketSortAdapterItem item = getListItems().get(position);
        PreparationViewHolder holder = (PreparationViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onPreparationClick(item);
            }
        });

        // Start a drag whenever the handle view it touched
        holder.ivDrag.setOnTouchListener((v, event) -> {
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onStartDrag(holder);
            }
            return false;
        });

        holder.ivDrag.setVisibility(View.VISIBLE);

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
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(getListItems(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        getListItems().remove(position);
        notifyItemRemoved(position);
    }

    public interface OnPreparationsClickListener {

        void onPreparationClick(BasketSortAdapterItem preparationItem);

        void onDeletePreparationClick(BasketSortAdapterItem preparationItem);
    }

    private static class PreparationViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

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
        ImageView ivDrag;
        View bottomDividerSeparator;

        PreparationViewHolder(View itemView) {
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
            ivDrag = itemView.findViewById(R.id.ivDrag);
            bottomDividerSeparator = itemView.findViewById(R.id.bottomDividerSeparator);
        }

        @Override
        public void onItemSelected() {
        }

        @Override
        public void onItemClear() {
        }
    }
}
