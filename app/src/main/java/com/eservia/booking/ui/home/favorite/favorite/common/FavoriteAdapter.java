package com.eservia.booking.ui.home.favorite.favorite.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.ui.home.favorite.favorite.listeners.OnBusinessItemClickListener;
import com.eservia.booking.ui.home.favorite.favorite.listeners.OnBusinessPaginationListener;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.model.entity.Business;

public class FavoriteAdapter extends BaseRecyclerAdapter<Business> {

    private final Context mContext;

    private final OnBusinessItemClickListener mBusinessListener;

    private final OnBusinessPaginationListener mPaginationListener;

    public FavoriteAdapter(Context context,
                           OnBusinessItemClickListener businessClickListener,
                           OnBusinessPaginationListener paginationListener) {
        mContext = context;
        mBusinessListener = businessClickListener;
        mPaginationListener = paginationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_business, parent, false);
        return new BusinessViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position >= getItemCount() - THRESHHOLD) {
            if (mPaginationListener != null) {
                mPaginationListener.loadMoreFavorites();
            }
        }

        Business business = getListItems().get(position);

        BusinessViewHolder holder = (BusinessViewHolder) viewHolder;

        if (BusinessUtil.mayShowBookingButton(business.getSector().getSector())) {
            holder.view.setOnClickListener(view -> {
                if (mBusinessListener != null) {
                    mBusinessListener.onBusinessInfoClick(business, position);
                }
            });
            holder.btnBooking.setOnClickListener(view -> {
                if (mBusinessListener != null)
                    mBusinessListener.onBusinessReserveClick(business, position);
            });
            holder.ivIconNext.setOnClickListener(view -> {
            });
            showBookingButton(holder, BusinessUtil.shouldShowBookingButton(business));
        } else {
            holder.ivIconNext.setOnClickListener(view -> {
                if (mBusinessListener != null) {
                    mBusinessListener.onBusinessInfoClick(business, position);
                }
            });
            holder.view.setOnClickListener(view -> {
            });
            holder.btnBooking.setOnClickListener(view -> {
            });
            showBookingButton(holder, false);
        }

        holder.ivBusinessImage.setOnClickListener(view -> {
            if (mBusinessListener != null) {
                mBusinessListener.onBusinessInfoClick(business, position);
            }
        });
        holder.rlBusinessInfoContainer.setOnClickListener(view -> {
            if (mBusinessListener != null) {
                mBusinessListener.onBusinessInfoClick(business, position);
            }
        });

        try {
            ImageUtil.displayBusinessImageTransform(holder.ivBusinessImage.getContext(),
                    holder.ivBusinessImage, business.getLogo(),
                    R.drawable.icon_business_photo_placeholder_beauty, R.drawable.mask_business_image);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        holder.tvBusinessName.setText(business.getName());

        holder.rbRating.setRating(BusinessUtil.starsRating(business.getRating()));

        holder.tvRating.setText(BusinessUtil.formatRating(business.getRating()));

        holder.tvAddress.setText(BusinessUtil.businessAddressesTitle(mContext, business));

        String distance = BusinessUtil.formatBusinessDistanceTitle(mContext, business);
        if (distance.isEmpty()) {
            holder.tvDistance.setVisibility(View.GONE);
        } else {
            holder.tvDistance.setVisibility(View.VISIBLE);
            holder.tvDistance.setText(distance);
        }

        ViewUtil.setCardOutlineProvider(mContext, holder.rlCardHolder, holder.cvContainer);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSector(@NonNull String sector) {
        notifyDataSetChanged();
    }

    private void showBookingButton(BusinessViewHolder holder, boolean show) {
        if (show) {
            showBookingButton(holder);
        } else {
            hideBookingButton(holder);
        }
    }

    private void showBookingButton(BusinessViewHolder holder) {
        holder.ivBook.setVisibility(View.GONE);
        holder.ivIconNext.setVisibility(View.GONE);
        holder.btnBooking.setVisibility(View.VISIBLE);
    }

    private void hideBookingButton(BusinessViewHolder holder) {
        holder.ivBook.setVisibility(View.GONE);
        holder.ivIconNext.setVisibility(View.VISIBLE);
        holder.btnBooking.setVisibility(View.GONE);
    }
}
