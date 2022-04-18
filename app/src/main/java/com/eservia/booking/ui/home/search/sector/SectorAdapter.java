package com.eservia.booking.ui.home.search.sector;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.common.view.OnPaginationListener;
import com.eservia.booking.util.SectorUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.model.entity.BusinessSector;

public class SectorAdapter extends BaseRecyclerAdapter<ListItem> {

    private final Context mContext;

    private final OnSectorClickListener mListener;

    private final OnPaginationListener mPaginationListener;

    SectorAdapter(Context context,
                  OnSectorClickListener listener,
                  OnPaginationListener paginationListener) {
        mContext = context;
        mListener = listener;
        mPaginationListener = paginationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ListItem.TYPE_SECTOR) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_sector, parent, false);
            return new SectorViewHolder(itemView);
        } else if (viewType == ListItem.TYPE_CHOOSE_SECTOR) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_choose_sector, parent, false);
            return new ChooseSectorViewHolder(itemView);
        } else if (viewType == ListItem.TYPE_NOT_FOUND) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_search_not_found_place_holder, parent, false);
            return new NothingFoundViewHolder(itemView);
        }
        throw new IllegalStateException("unsupported item type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position >= getItemCount() - THRESHHOLD) {
            if (mPaginationListener != null) {
                mPaginationListener.loadMore();
            }
        }
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ListItem.TYPE_SECTOR: {
                SectorItem sectorItem = (SectorItem) getListItems().get(position);
                SectorViewHolder holder = (SectorViewHolder) viewHolder;

                holder.view.setOnClickListener(view -> {
                    if (mListener != null) {
                        mListener.onSectorClick(sectorItem.getSector());
                    }
                });

                holder.tvSector.setText(SectorUtil.sectorName(mContext, sectorItem.getSector()));
                holder.tvSectorShadow.setText(SectorUtil.sectorName(mContext, sectorItem.getSector()));

                Drawable drawable = SectorUtil.businessSectorDrawable(mContext,
                        sectorItem.getSector().getSector());
                if (drawable != null) {
                    holder.ivLeft.setVisibility(View.VISIBLE);
                    holder.ivLeft.setImageDrawable(drawable);
                } else {
                    holder.ivLeft.setVisibility(View.INVISIBLE);
                }

                ViewUtil.setCardOutlineProvider(mContext, holder.rlCardHolder, holder.cvContainer);
                break;
            }
            case ListItem.TYPE_CHOOSE_SECTOR: {
                ChooseSectorItem chooseSectorItem = (ChooseSectorItem) getListItems().get(position);
                ChooseSectorViewHolder holder = (ChooseSectorViewHolder) viewHolder;
                break;
            }
            case ListItem.TYPE_NOT_FOUND: {
                NotFoundSectorsItem notFoundSectorsItem = (NotFoundSectorsItem) getListItems().get(position);
                NothingFoundViewHolder holder = (NothingFoundViewHolder) viewHolder;

                ViewUtil.setMargins(holder.view, 0, (int) ViewUtil.dpToPixel(mContext, 120), 0, 0);
                holder.btnPlaceHolderButton.setVisibility(View.INVISIBLE);
                holder.tvPlaceHolderLabelSearch.setText(mContext.getResources().getString(R.string.placeholder_message_not_found));
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

    public interface OnSectorClickListener {

        void onSectorClick(BusinessSector sector);
    }

    private static class SectorViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvSector;
        TextView tvSectorShadow;
        ImageView ivLeft;
        ImageView ivRight;
        RelativeLayout rlCardHolder;
        CardView cvContainer;

        SectorViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvSector = itemView.findViewById(R.id.tvSector);
            tvSectorShadow = itemView.findViewById(R.id.tvSectorShadow);
            ivLeft = itemView.findViewById(R.id.ivLeft);
            ivRight = itemView.findViewById(R.id.ivRight);
            rlCardHolder = itemView.findViewById(R.id.rlCardHolder);
            cvContainer = itemView.findViewById(R.id.cvContainer);
        }
    }

    private static class ChooseSectorViewHolder extends RecyclerView.ViewHolder {

        View view;

        ChooseSectorViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }

    private static class NothingFoundViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvPlaceHolderLabelSearch;
        Button btnPlaceHolderButton;

        NothingFoundViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvPlaceHolderLabelSearch = itemView.findViewById(R.id.tvPlaceHolderLabelSearch);
            btnPlaceHolderButton = itemView.findViewById(R.id.btnPlaceHolderButton);
        }
    }
}
