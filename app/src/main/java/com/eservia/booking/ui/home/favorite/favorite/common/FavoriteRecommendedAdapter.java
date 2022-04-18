package com.eservia.booking.ui.home.favorite.favorite.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.ui.home.favorite.favorite.listeners.OnRecommendedListener;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.model.entity.Business;

public class FavoriteRecommendedAdapter extends BaseRecyclerAdapter<Business> {

    private final Context mContext;

    private final OnRecommendedListener mListener;

    public FavoriteRecommendedAdapter(Context context, OnRecommendedListener listener) {
        mContext = context;
        mListener = listener;
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

        Business business = getListItems().get(position);

        BusinessViewHolder holder = (BusinessViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onRecommendedBusinessClick(business, position);
            }
        });

        holder.btnFavoriteOn.setOnClickListener(view -> {
            if (mListener != null)
                mListener.onRecommendedBusinessLikeClick(business, position);
        });

        holder.btnFavoriteOff.setOnClickListener(view -> {
            if (mListener != null)
                mListener.onRecommendedBusinessLikeClick(business, position);
        });

        showFavoritedIcon(holder, business);

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

    private void showFavoritedIcon(BusinessViewHolder holder, Business business) {
        holder.ivBook.setVisibility(View.GONE);
        holder.ivIconNext.setVisibility(View.GONE);
        holder.btnBooking.setVisibility(View.GONE);

        if (business.getIs().getFavorited()) {
            holder.btnFavoriteOn.setVisibility(View.VISIBLE);
            holder.btnFavoriteOff.setVisibility(View.GONE);
        } else {
            holder.btnFavoriteOn.setVisibility(View.GONE);
            holder.btnFavoriteOff.setVisibility(View.VISIBLE);
        }
    }
}
