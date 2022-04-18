package com.eservia.booking.ui.booking.beauty.booking;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.model.entity.BeautyService;
import com.eservia.model.entity.BeautyStaff;

public class AutoSelectedStaffAdapter extends BaseRecyclerAdapter<AutoSelectedStaffAdapterItem> {

    private final AutoSelectedStaffClickListener mClickListener;

    private final Context mContext;

    public AutoSelectedStaffAdapter(Context context,
                                    AutoSelectedStaffClickListener clickListener) {
        mContext = context;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_booking_beauty_staff_auto_selected,
                parent, false);
        return new AutoSelectedStaffViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        AutoSelectedStaffAdapterItem item = getListItems().get(position);
        Preparation preparation = item.getPreparation();
        AutoSelectedStaffViewHolder holder = (AutoSelectedStaffViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mClickListener != null) {
                mClickListener.onAutoSelectedStaffClick(item, position);
            }
        });

        holder.rlPriceContainer.setOnClickListener(view -> {
            if (mClickListener != null) {
                mClickListener.onAutoSelectedStaffEditClick(item, position);
            }
        });

        BeautyStaff staff = preparation.getStaff();
        holder.tvStaffName.setText(BusinessUtil.getStaffFullName(
                staff.getFirstName(), staff.getLastName()));

        try {
            ImageUtil.displayStaffImageRound(mContext, holder.ivImage,
                    staff.getPhoto(), R.drawable.user_man_big);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        BeautyService service = preparation.getService();

        if (service.getName() != null) {
            holder.tvService.setText(service.getName());
        } else {
            holder.tvService.setText("");
        }

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

        holder.tvService.setTextColor(item.getColorId());

        ((GradientDrawable) holder.rlImageContainer.getBackground()).setStroke(
                Math.round(ViewUtil.dpToPixel(mContext, 4)), item.getColorId());
    }

    public interface AutoSelectedStaffClickListener {

        void onAutoSelectedStaffClick(AutoSelectedStaffAdapterItem item, int position);

        void onAutoSelectedStaffEditClick(AutoSelectedStaffAdapterItem item, int position);
    }

    private static class AutoSelectedStaffViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView ivImage;
        RelativeLayout rlImageContainer;
        RelativeLayout rlPriceContainer;
        TextView tvCurrency;
        TextView tvPrice;
        TextView tvService;
        TextView tvStaffName;
        RelativeLayout rlStaffAndServiceName;

        AutoSelectedStaffViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivImage = itemView.findViewById(R.id.ivImage);
            rlImageContainer = itemView.findViewById(R.id.rlImageContainer);
            rlPriceContainer = itemView.findViewById(R.id.rlPriceContainer);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCurrency = itemView.findViewById(R.id.tvCurrency);
            tvStaffName = itemView.findViewById(R.id.tvStaffName);
            tvService = itemView.findViewById(R.id.tvService);
            rlStaffAndServiceName = itemView.findViewById(R.id.rlStaffAndServiceName);
        }
    }
}
