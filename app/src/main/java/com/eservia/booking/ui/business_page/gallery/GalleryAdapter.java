package com.eservia.booking.ui.business_page.gallery;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.eservia.biv.loader.ImageLoader;
import com.eservia.biv.view.BigImageView;
import com.eservia.booking.R;
import com.eservia.model.entity.BusinessPhoto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends PagerAdapter {

    private static final int THRESHHOLD = 25;

    public interface OnGalleryItemClickListener {

        void onGalleryItemClick(int position);
    }

    public interface OnGalleryPaginationListener {

        void loadMorePhotos();
    }

    private final OnGalleryItemClickListener mItemClickListener;

    private final OnGalleryPaginationListener mPaginationListener;

    private List<BusinessPhoto> mPhotos;

    private final LayoutInflater mInflater;

    public GalleryAdapter(Context context,
                          OnGalleryItemClickListener clickListener,
                          OnGalleryPaginationListener paginationListener) {
        mPhotos = new ArrayList<>();
        mItemClickListener = clickListener;
        mPaginationListener = paginationListener;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        if (position >= getCount() - THRESHHOLD) {
            if (mPaginationListener != null) {
                mPaginationListener.loadMorePhotos();
            }
        }

        BusinessPhoto photo = mPhotos.get(position);

        View imageLayout = mInflater.inflate(R.layout.item_image_slide, view, false);

        BigImageView bigImageView = imageLayout.findViewById(R.id.ivImage);

        bigImageView.showImage(Uri.parse(photo.getPath()));

        bigImageView.setOnClickListener(v -> {
            if (mItemClickListener != null) {
                mItemClickListener.onGalleryItemClick(position);
            }
        });

        ProgressBar progress = imageLayout.findViewById(R.id.progressBar);

        bigImageView.setImageLoaderCallback(new ImageLoaderListener(progress));

        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    public void addPhotos(List<BusinessPhoto> photos) {
        if (photos == null) return;
        mPhotos.addAll(photos);
        notifyDataSetChanged();
    }

    public void replacePhotos(List<BusinessPhoto> photos) {
        if (photos == null) return;
        mPhotos = photos;
        notifyDataSetChanged();
    }

    private static class ImageLoaderListener implements ImageLoader.Callback {

        ProgressBar progress;

        public ImageLoaderListener(ProgressBar progress) {
            this.progress = progress;
        }


        @Override
        public void onCacheHit(int imageType, File image) {

        }

        @Override
        public void onCacheMiss(int imageType, File image) {

        }

        @Override
        public void onStart() {
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        public void onProgress(int progress) {
        }

        @Override
        public void onFinish() {
        }

        @Override
        public void onSuccess(File image) {
            progress.setVisibility(View.GONE);
        }

        @Override
        public void onFail(Exception error) {
            progress.setVisibility(View.GONE);
        }
    }
}
