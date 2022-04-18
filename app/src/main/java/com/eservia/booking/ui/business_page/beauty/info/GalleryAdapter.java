package com.eservia.booking.ui.business_page.beauty.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
import com.eservia.model.entity.BusinessPhoto;

public class GalleryAdapter extends BaseRecyclerAdapter<BusinessPhoto> {

    private static final int THRESHHOLD = 25;

    private final GalleryPhotoClickListener mPhotoClickListener;

    private final GalleryPhotoPaginationListener mPaginationListener;

    private final Context mContext;

    public GalleryAdapter(Context context, GalleryPhotoClickListener listener,
                          GalleryPhotoPaginationListener paginationListener) {
        mContext = context;
        mPhotoClickListener = listener;
        mPaginationListener = paginationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_business_page_beauty_gallery_photo, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position >= getItemCount() - THRESHHOLD) {
            if (mPaginationListener != null) {
                mPaginationListener.loadMoreGalleryPhoto();
            }
        }

        BusinessPhoto item = getListItems().get(position);
        PhotoViewHolder holder = (PhotoViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mPhotoClickListener != null) {
                mPhotoClickListener.onGalleryPhotoClick(item, position);
            }
        });

        try {
            Glide.with(mContext)
                    .load(item.getPath())
                    .apply(RequestOptions.placeholderOf(R.drawable.icon_business_photo_placeholder_beauty))
                    .apply(RequestOptions.errorOf(R.drawable.icon_business_photo_placeholder_beauty))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.ivPhoto);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ViewUtil.setCardOutlineProvider(mContext, holder.rlCardHolder, holder.cvContainer);
    }

    public interface GalleryPhotoPaginationListener {

        void loadMoreGalleryPhoto();
    }

    public interface GalleryPhotoClickListener {

        void onGalleryPhotoClick(BusinessPhoto photo, int position);
    }

    private static class PhotoViewHolder extends RecyclerView.ViewHolder {

        View view;
        RelativeLayout rlCardHolder;
        CardView cvContainer;
        ImageView ivPhoto;


        PhotoViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            rlCardHolder = itemView.findViewById(R.id.rlCardHolder);
            cvContainer = itemView.findViewById(R.id.cvContainer);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
        }
    }
}
