package com.eservia.booking.ui.home.bookings.delivery_info;

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
import com.eservia.model.entity.OrderRestoItem;

import java.util.Locale;

public class DeliveryOrderAdapter extends BaseRecyclerAdapter<OrderRestoItem> {

    private Context mContext;

    public DeliveryOrderAdapter(Context context) {
        mContext = context;
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
        OrderRestoItem item = getListItems().get(position);
        DeliveryOrderViewHolder holder = (DeliveryOrderViewHolder) viewHolder;

        String dishNameStr = item.getName() != null ? item.getName() : "";
        String dishWeightStr = item.getDimension() != null && item.getSize() != null
                ? DishUtil.INSTANCE.getFormattedWeight(item.getDimension(), item.getSize())
                : "";
        holder.tvItemName.setText(String.format(Locale.US, "%s, %s", dishNameStr, dishWeightStr));

        double totalPrice = item.getPrice() != null && item.getAmount() != null
                ? item.getPrice() * item.getAmount()
                : 0.0;
        holder.tvPrice.setText(DishUtil.INSTANCE.getFormattedPrice(mContext, totalPrice));

        holder.ivDelete.setVisibility(View.GONE);

        holder.portions_selector.setValue(item.getAmount() != null
                ? (int) item.getAmount().longValue()
                : 1);
        holder.portions_selector.setEditable(false);
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
