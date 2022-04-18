package com.eservia.booking.ui.business_page.beauty.departments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.model.entity.Address;

public class DepartmentsAdapter extends BaseRecyclerAdapter<Address> {

    private final Context mContext;

    private final OnDepartmentClickListener mClickListener;

    public DepartmentsAdapter(Context context, OnDepartmentClickListener clickListener) {
        mContext = context;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_business_page_beauty_item_address,
                parent, false);
        return new AddressViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Address address = getListItems().get(position);
        AddressViewHolder holder = (AddressViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mClickListener != null) {
                mClickListener.onDepartmentClick(address, position);
            }
        });

        holder.btnBooking.setOnClickListener(view -> {
            if (mClickListener != null) {
                mClickListener.onDepartmentBookingClick(address, position);
            }
        });

        holder.btnBooking.setVisibility(View.VISIBLE);
        holder.ivIconNext.setVisibility(View.GONE);

        String city = address.getCity();
        String street = address.getStreet();
        String number = address.getNumber();
        String addressName = BusinessUtil.getFullAddress(city, street, number);
        holder.tvAddress.setText(addressName);

        holder.tvPhone.setVisibility(View.GONE);
        if (address.getDistance() != null) {
            holder.llDistance.setVisibility(View.VISIBLE);
            holder.tvDistance.setText(BusinessUtil.formatAddressDistanceShort(mContext, address));
        } else {
            holder.llDistance.setVisibility(View.GONE);
        }

        int colorId = ContextCompat.getColor(mContext, R.color.colorPrimary);
        holder.left_separator.getBackground().mutate().setColorFilter(colorId, PorterDuff.Mode.SRC_IN);

        ViewUtil.setCardOutlineProvider(mContext, holder.rlCardHolder, holder.cvContainer);
    }

    public interface OnDepartmentClickListener {

        void onDepartmentClick(Address address, int position);

        void onDepartmentBookingClick(Address address, int position);
    }

    private static class AddressViewHolder extends RecyclerView.ViewHolder {

        View view;
        RelativeLayout rlCardHolder;
        CardView cvContainer;
        View left_separator;
        TextView tvAddress;
        TextView tvPhone;
        Button btnBooking;
        ImageView ivIconNext;
        LinearLayout llDistance;
        TextView tvFromYou;
        TextView tvDistance;

        AddressViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            rlCardHolder = itemView.findViewById(R.id.rlCardHolder);
            cvContainer = itemView.findViewById(R.id.cvContainer);
            left_separator = itemView.findViewById(R.id.left_separator);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            btnBooking = itemView.findViewById(R.id.btnBooking);
            ivIconNext = itemView.findViewById(R.id.ivIconNext);
            llDistance = itemView.findViewById(R.id.llDistance);
            tvFromYou = itemView.findViewById(R.id.tvFromYou);
            tvDistance = itemView.findViewById(R.id.tvDistance);
        }
    }
}
