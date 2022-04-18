package com.eservia.booking.ui.delivery.resto.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;

public class DeliveryAddressAdapter extends BaseRecyclerAdapter<DeliveryAddressAdapterItem> {

    public interface OnAddressClickListener {

        void onAddressItemClicked(DeliveryAddressAdapterItem item, int position);
    }

    private final Context mContext;

    private final OnAddressClickListener mListener;

    public DeliveryAddressAdapter(Context context, OnAddressClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_delivery_address, parent, false);
        return new DeliveryAddressViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        DeliveryAddressAdapterItem item = getItem(position);
        DeliveryAddressViewHolder deliveryAddressViewHolder = (DeliveryAddressViewHolder) viewHolder;
        deliveryAddressViewHolder.bindView(item);
    }
}
