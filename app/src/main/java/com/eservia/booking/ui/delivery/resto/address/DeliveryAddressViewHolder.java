package com.eservia.booking.ui.delivery.resto.address;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.butterknife.ButterKnife;

public class DeliveryAddressViewHolder extends RecyclerView.ViewHolder {

    View view;
    TextView tvTitle;

    private final DeliveryAddressAdapter.OnAddressClickListener mClickListener;

    DeliveryAddressViewHolder(View itemView,
                                     DeliveryAddressAdapter.OnAddressClickListener clickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        view = itemView;
        mClickListener = clickListener;
    }

    void bindView(DeliveryAddressAdapterItem item) {
        tvTitle = view.findViewById(R.id.tvTitle);
        view.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.onAddressItemClicked(item, getAdapterPosition());
            }
        });
        tvTitle.setText(item.getTitle());
    }
}
