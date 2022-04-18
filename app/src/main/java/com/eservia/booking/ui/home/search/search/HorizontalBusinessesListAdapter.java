package com.eservia.booking.ui.home.search.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.util.ViewUtil;
import com.eservia.glide.Glide;
import com.eservia.glide.load.engine.DiskCacheStrategy;
import com.eservia.glide.load.resource.drawable.DrawableTransitionOptions;
import com.eservia.glide.request.RequestOptions;
import com.eservia.model.entity.Business;

public class HorizontalBusinessesListAdapter extends BaseRecyclerAdapter<Business> {

    private final OnBusinessClickListener mPhotoClickListener;

    private final PaginationListener mPaginationListener;

    private final Context mContext;

    public HorizontalBusinessesListAdapter(Context context, OnBusinessClickListener listener,
                                           PaginationListener paginationListener) {
        mContext = context;
        mPhotoClickListener = listener;
        mPaginationListener = paginationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_business_horizontal, parent, false);
        return new BusinessViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position >= getItemCount() - THRESHHOLD) {
            if (mPaginationListener != null) {
                mPaginationListener.loadMoreMostPopularBusinesses();
            }
        }

        Business business = getListItems().get(position);
        BusinessViewHolder holder = (BusinessViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mPhotoClickListener != null) {
                mPhotoClickListener.onHorizontalBusinessClick(business);
            }
        });

        try {
            Glide.with(mContext)
                    .load(business.getLogo())
                    .apply(RequestOptions.placeholderOf(R.drawable.icon_business_photo_placeholder_beauty))
                    .apply(RequestOptions.errorOf(R.drawable.icon_business_photo_placeholder_beauty))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.ivPhoto);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        holder.tvText.setText(business.getName());

        ViewUtil.setCardOutlineProvider(mContext, holder.rlCardHolder, holder.cvContainer);
    }

    public interface PaginationListener {

        void loadMoreMostPopularBusinesses();
    }

    public interface OnBusinessClickListener {

        void onHorizontalBusinessClick(Business business);
    }

    private static class BusinessViewHolder extends RecyclerView.ViewHolder {

        View view;
        RelativeLayout rlCardHolder;
        CardView cvContainer;
        ImageView ivPhoto;
        TextView tvText;

        BusinessViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            rlCardHolder = itemView.findViewById(R.id.rlCardHolder);
            cvContainer = itemView.findViewById(R.id.cvContainer);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvText = itemView.findViewById(R.id.tvText);
        }
    }
}
