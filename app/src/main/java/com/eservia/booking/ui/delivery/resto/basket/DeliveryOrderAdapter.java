package com.eservia.booking.ui.delivery.resto.basket;

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
import com.eservia.booking.util.DishUtil;
import com.eservia.common.view.CommonCounter;

import java.util.Locale;

public class DeliveryOrderAdapter extends BaseRecyclerAdapter<DeliveryOrderAdapterItem> {

    private final DeliveryOrderAdapterListener mListener;

    private final Context mContext;

    public DeliveryOrderAdapter(Context context,
                                DeliveryOrderAdapterListener listener) {
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_delivery_basket, parent, false);
        return new DeliveryOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        DeliveryOrderAdapterItem item = getListItems().get(position);
        DeliveryOrderViewHolder holder = (DeliveryOrderViewHolder) viewHolder;

        String dishNameStr = item.getDish().getName();
        String dishWeightStr = DishUtil.INSTANCE.getFormattedWeight(item.getDish(),
                item.getOrderRestoItem().getSize());
        holder.tvItemName.setText(String.format(Locale.US, "%s, %s", dishNameStr, dishWeightStr));

        double totalPrice = item.getOrderRestoItem().getPriceByPortion()
                * item.getOrderRestoItem().getAmount();
        holder.tvPrice.setText(DishUtil.INSTANCE.getFormattedPrice(mContext, totalPrice));

        if (item.isEditMode()) {
            holder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            holder.ivDelete.setVisibility(View.GONE);
        }

        holder.ivDelete.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onDeleteDeliveryOrderClick(item, position);
            }
        });

        holder.portions_selector.setMaxValue(item.getMaxPortionsCount());

        holder.portions_selector.setValue((int) item.getOrderRestoItem().getAmount().longValue());

        holder.portions_selector.setListener(new CommonCounter.CommonCounterListener() {
            @Override
            public void onCounterValueIncreased(int count) {
                if (mListener != null) {
                    mListener.onDeliveryOrderPortionsChanged(item,
                            viewHolder.getAdapterPosition(), count);
                }
            }

            @Override
            public void onCounterValueDecreased(int count) {
                if (mListener != null) {
                    mListener.onDeliveryOrderPortionsChanged(item,
                            viewHolder.getAdapterPosition(), count);
                }
            }

            @Override
            public void onUnableToIncrease() {
            }

            @Override
            public void onUnableToDecrease() {
            }
        });
    }

    public interface DeliveryOrderAdapterListener {

        void onDeleteDeliveryOrderClick(DeliveryOrderAdapterItem item, int position);

        void onDeliveryOrderPortionsChanged(DeliveryOrderAdapterItem item, int position,
                                            int portions);
    }

    private static class DeliveryOrderViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvPrice;
        TextView tvItemName;
        ImageView ivDelete;
        CommonCounter portions_selector;

        DeliveryOrderViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            portions_selector = itemView.findViewById(R.id.portions_selector);
        }
    }
}
