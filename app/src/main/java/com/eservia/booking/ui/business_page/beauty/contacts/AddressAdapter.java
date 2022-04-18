package com.eservia.booking.ui.business_page.beauty.contacts;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.model.entity.Address;
import com.eservia.utils.StringUtil;

public class AddressAdapter extends BaseRecyclerAdapter<AddressAdapterItem> {

    private final Context mContext;

    private final OnAddressClickListener mClickListener;

    private final OnAddressPaginationListener mPaginationListener;

    public AddressAdapter(Context context,
                          OnAddressClickListener clickListener,
                          OnAddressPaginationListener paginationListener) {
        mContext = context;
        mClickListener = clickListener;
        mPaginationListener = paginationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case AddressAdapterItem.ITEM_ADDRESS: {
                View itemView = inflater.inflate(R.layout.item_business_page_beauty_item_address,
                        parent, false);
                return new AddressViewHolder(itemView);
            }
            case AddressAdapterItem.ITEM_HEADER: {
                View itemView = inflater.inflate(R.layout.item_business_page_beauty_item_header,
                        parent, false);
                return new HeaderViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position >= getItemCount() - THRESHHOLD) {
            if (mPaginationListener != null) {
                mPaginationListener.loadMoreAddresses();
            }
        }
        int viewType = getItemViewType(position);
        switch (viewType) {
            case AddressAdapterItem.ITEM_ADDRESS: {
                ItemAddress item = (ItemAddress) getListItems().get(position);
                Address address = item.getAddress();
                AddressViewHolder holder = (AddressViewHolder) viewHolder;

                holder.view.setOnClickListener(view -> {
                    if (mClickListener != null) {
                        mClickListener.onAddressClick(item, position);
                    }
                });

                holder.tvPhone.setOnClickListener(view -> {
                    if (mClickListener != null) {
                        mClickListener.onAddressPhoneClick(item, position);
                    }
                });

                String city = address.getCity();
                String street = address.getStreet();
                String number = address.getNumber();
                String addressName = BusinessUtil.getFullAddress(city, street, number);
                holder.tvAddress.setText(addressName);

                String phone = address.getPhone();
                if (!StringUtil.isEmpty(phone)) {
                    holder.tvPhone.setVisibility(View.VISIBLE);
                    holder.tvPhone.setText(phone);
                } else {
                    holder.tvPhone.setVisibility(View.GONE);
                }

                String mark = address.getMark();
                if (!StringUtil.isEmpty(mark)) {
                    holder.tvMark.setVisibility(View.VISIBLE);
                    holder.tvMark.setText(mark);
                } else {
                    holder.tvMark.setVisibility(View.GONE);
                }

                int colorId = ColorUtil.randomColor(mContext);
                holder.left_separator.getBackground().mutate().setColorFilter(
                        colorId, PorterDuff.Mode.SRC_IN);

                ViewUtil.setCardOutlineProvider(mContext, holder.rlCardHolder, holder.cvContainer);
                break;
            }
            case AddressAdapterItem.ITEM_HEADER: {
                ItemHeader item = (ItemHeader) getListItems().get(position);
                HeaderViewHolder holder = (HeaderViewHolder) viewHolder;

                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getListItems().get(position).getType();
    }

    public interface OnAddressClickListener {

        void onAddressClick(ItemAddress itemAddress, int position);

        void onAddressPhoneClick(ItemAddress itemAddress, int position);
    }

    public interface OnAddressPaginationListener {

        void loadMoreAddresses();
    }

    private static class AddressViewHolder extends RecyclerView.ViewHolder {

        View view;
        RelativeLayout rlCardHolder;
        CardView cvContainer;
        View left_separator;
        TextView tvAddress;
        TextView tvPhone;
        TextView tvMark;

        AddressViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            rlCardHolder = itemView.findViewById(R.id.rlCardHolder);
            cvContainer = itemView.findViewById(R.id.cvContainer);
            left_separator = itemView.findViewById(R.id.left_separator);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvMark = itemView.findViewById(R.id.tvMark);
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvHeader;

        HeaderViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvHeader = itemView.findViewById(R.id.tvHeader);
        }
    }
}
