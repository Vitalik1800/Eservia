package com.eservia.booking.ui.business_page.marketing;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.glide.Glide;
import com.eservia.glide.MaskTransformation;
import com.eservia.glide.load.MultiTransformation;
import com.eservia.glide.load.engine.DiskCacheStrategy;
import com.eservia.glide.load.resource.bitmap.CenterCrop;
import com.eservia.glide.load.resource.drawable.DrawableTransitionOptions;
import com.eservia.glide.request.RequestOptions;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.Marketing;
import com.eservia.model.entity.PhotoSize;

public class BusinessMarketingsAdapter extends BaseRecyclerAdapter<Marketing> {

    private final Context mContext;

    private final NewsClickListener mClickListener;

    private final OnNewsPaginationListener mPaginationListener;

    public BusinessMarketingsAdapter(Context context,
                                     NewsClickListener clickListener,
                                     OnNewsPaginationListener paginationListener) {
        mContext = context;
        mClickListener = clickListener;
        mPaginationListener = paginationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_home_news, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position >= getItemCount() - THRESHHOLD) {
            if (mPaginationListener != null) {
                mPaginationListener.loadMoreNews();
            }
        }

        Marketing marketing = getListItems().get(position);

        NewsViewHolder holder = (NewsViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mClickListener != null) {
                mClickListener.onNewsClick(marketing, position);
            }
        });

        try {
            Glide.with(mContext)
                    .load(ImageUtil.getUserPhotoPath(PhotoSize.MIDDLE, marketing.getPathToPhoto()))
                    .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
                            new MaskTransformation(R.drawable.mask_business_image))))
                    .apply(RequestOptions.placeholderOf(R.drawable.icon_business_photo_placeholder_beauty))
                    .apply(RequestOptions.errorOf(R.drawable.icon_business_photo_placeholder_beauty))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.ivBusinessImage);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Business business = marketing.getBusiness();

        if (business != null) {
            holder.tvBusinessName.setText(business.getName());
        } else {
            holder.tvBusinessName.setText("");
        }

        if (marketing.getTitle() != null) {
            holder.tvTitle.setText(marketing.getTitle());
        } else {
            holder.tvTitle.setText("");
        }

        if (marketing.getDescription() != null) {
            holder.tvDescription.setText(marketing.getDescription());
        } else {
            holder.tvDescription.setText("");
        }

        Integer type = marketing.getMarketingTypeId();
        if (type != null) {

            if (type.equals(Marketing.TYPE_NEWS)) {

                holder.rlMark.setVisibility(View.GONE);

            } else if (type.equals(Marketing.TYPE_EVENT)) {

                holder.rlMark.setVisibility(View.VISIBLE);
                holder.ivMark.setBackground(ContextCompat.getDrawable(mContext,
                        R.drawable.background_attention));
                holder.tvMark.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources()
                        .getDimension(R.dimen.mark_event_text_size));
                holder.tvMark.setText(mContext.getResources().getString(R.string.mark_event_sign));

            } else if (type.equals(Marketing.TYPE_PROMOTION)) {
                Integer discountType = marketing.getDiscountTypeId();
                Float discount = marketing.getDiscount();
                if (discount != null && discountType != null && discountType.equals(Marketing.DISCOUNT_PERCENT)) {
                    holder.rlMark.setVisibility(View.VISIBLE);
                    holder.ivMark.setBackground(ContextCompat.getDrawable(mContext,
                            R.drawable.background_sale));
                    holder.tvMark.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources()
                            .getDimension(R.dimen.mark_promo_text_size));
                    holder.tvMark.setText(mContext.getResources().getString(R.string.sale_mark,
                            marketing.getDiscount().intValue()));
                } else {
                    holder.rlMark.setVisibility(View.GONE);
                }

            } else {
                holder.rlMark.setVisibility(View.GONE);
            }

        } else {
            holder.rlMark.setVisibility(View.GONE);
        }

        ViewUtil.setCardOutlineProvider(mContext, holder.rlCardHolder, holder.cvContainer);
    }

    public interface NewsClickListener {

        void onNewsClick(Marketing item, int position);
    }

    public interface OnNewsPaginationListener {

        void loadMoreNews();
    }

    private static class NewsViewHolder extends RecyclerView.ViewHolder {

        View view;
        RelativeLayout rlCardHolder;
        CardView cvContainer;
        RelativeLayout rlMark;
        ImageView ivMark;
        TextView tvMark;
        TextView tvBusinessName;
        TextView tvTitle;
        TextView tvDescription;
        ImageView ivBusinessImage;

        NewsViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            rlCardHolder = itemView.findViewById(R.id.rlCardHolder);
            cvContainer = itemView.findViewById(R.id.cvContainer);
            rlMark = itemView.findViewById(R.id.rlMark);
            ivMark = itemView.findViewById(R.id.ivMark);
            tvMark = itemView.findViewById(R.id.tvMark);
            tvBusinessName = itemView.findViewById(R.id.tvBusinessName);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivBusinessImage = itemView.findViewById(R.id.ivBusinessImage);
        }
    }
}
