package com.eservia.booking.ui.home.search.search;

import android.content.Context;
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
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessSector;
import com.eservia.simpleratingbar.SimpleRatingBar;

import java.util.List;

public class SearchAdapter extends BaseRecyclerAdapter<SearchListItem> {

    private final Context mContext;

    private final OnBusinessItemClickListener mBusinessListener;

    private final OnPaginationListener mPaginationListener;

    SearchAdapter(Context context,
                  OnBusinessItemClickListener businessClickListener,
                  OnPaginationListener paginationListener) {
        mContext = context;
        mBusinessListener = businessClickListener;
        mPaginationListener = paginationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case SearchListItem.ITEM_BUSINESS: {
                View itemView = inflater.inflate(R.layout.item_business,
                        parent, false);
                return new BusinessViewHolder(itemView);
            }
            case SearchListItem.ITEM_NOTHING_FOUND: {
                View itemView = inflater.inflate(R.layout.item_search_not_found_place_holder,
                        parent, false);
                return new NothingFoundViewHolder(itemView);
            }
            case SearchListItem.ITEM_LOAD_MORE_PROGRESS: {
                View itemView = inflater.inflate(R.layout.item_search_load_more_progress,
                        parent, false);
                return new LoadMoreProgressViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
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
            case SearchListItem.ITEM_BUSINESS: {
                BusinessAdapterItem adapterItem = (BusinessAdapterItem) getListItems().get(position);
                Business business = adapterItem.getBusiness();

                BusinessViewHolder holder = (BusinessViewHolder) viewHolder;

                holder.view.setOnClickListener(view -> {
                    if (mBusinessListener != null) {
                        mBusinessListener.onBusinessInfoClick(adapterItem, position);
                    }
                });

                BusinessSector sector = adapterItem.getSector();

                if (BusinessUtil.mayShowBookingButton(sector.getSector())) {
                    showBookingButton(holder, BusinessUtil.shouldShowBookingButton(business));
                    holder.btnBooking.setOnClickListener(view -> {
                        if (mBusinessListener != null)
                            mBusinessListener.onBusinessReserveClick(adapterItem, position);
                    });
                } else {
                    hideBookingButton(holder);
                }

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

                if (business.getAddresses() == null) {
                    holder.tvAddress.setText(R.string.has_not_any_departments);
                } else {
                    List<Address> addresses = business.getAddresses();
                    if (addresses.size() == 1) {
                        String street = addresses.get(0).getStreet();
                        String number = addresses.get(0).getNumber();
                        String addressName = BusinessUtil.getFullAddress(street, number);
                        holder.tvAddress.setText(addressName);
                    } else if (!addresses.isEmpty() && addresses.size() > 1) {
                        String departments = mContext.getString(R.string.departments_);
                        String text = String.format(departments, addresses.size());
                        holder.tvAddress.setText(text);
                    } else {
                        holder.tvAddress.setText(R.string.has_not_any_departments);
                    }
                }

                String distance = BusinessUtil.formatBusinessDistanceTitle(mContext, business);
                if (distance.isEmpty()) {
                    holder.tvDistance.setVisibility(View.GONE);
                } else {
                    holder.tvDistance.setVisibility(View.VISIBLE);
                    holder.tvDistance.setText(distance);
                }

                ViewUtil.setCardOutlineProvider(mContext, holder.rlCardHolder, holder.cvContainer);
                break;
            }
            case SearchListItem.ITEM_NOTHING_FOUND: {

                NothingFoundViewHolder holder = (NothingFoundViewHolder) viewHolder;

                holder.btnPlaceHolderButton.setOnClickListener(view -> {
                    if (mBusinessListener != null) {
                        mBusinessListener.onSuggestBusinessClick();
                    }
                });
                break;
            }
            case SearchListItem.ITEM_LOAD_MORE_PROGRESS: {
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

    public interface OnBusinessItemClickListener {

        void onBusinessReserveClick(BusinessAdapterItem item, int position);

        void onBusinessInfoClick(BusinessAdapterItem item, int position);

        void onSuggestBusinessClick();
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

    private static class BusinessViewHolder extends RecyclerView.ViewHolder {

        View view;
        RelativeLayout rlCardHolder;
        CardView cvContainer;
        ImageView ivBusinessImage;
        TextView tvBusinessName;
        SimpleRatingBar rbRating;
        TextView tvRating;
        TextView tvAddress;
        TextView tvDistance;
        RelativeLayout rlBusinessInfoContainer;
        RelativeLayout rlBook;
        ImageView ivBook;
        ImageView ivIconNext;
        Button btnBooking;

        BusinessViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            rlCardHolder = itemView.findViewById(R.id.rlCardHolder);
            cvContainer = itemView.findViewById(R.id.cvContainer);
            ivBusinessImage = itemView.findViewById(R.id.ivBusinessImage);
            tvBusinessName = itemView.findViewById(R.id.tvBusinessName);
            rbRating = itemView.findViewById(R.id.rbRating);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            rlBusinessInfoContainer = itemView.findViewById(R.id.rlBusinessInfoContainer);
            rlBook = itemView.findViewById(R.id.rlBook);
            ivBook = itemView.findViewById(R.id.ivBook);
            ivIconNext = itemView.findViewById(R.id.ivIconNext);
            btnBooking = itemView.findViewById(R.id.btnBooking);
        }
    }

    private static class NothingFoundViewHolder extends RecyclerView.ViewHolder {

        View view;
        Button btnPlaceHolderButton;

        NothingFoundViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            btnPlaceHolderButton = itemView.findViewById(R.id.btnPlaceHolderButton);
        }
    }

    private static class LoadMoreProgressViewHolder extends RecyclerView.ViewHolder {

        View view;

        LoadMoreProgressViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}
