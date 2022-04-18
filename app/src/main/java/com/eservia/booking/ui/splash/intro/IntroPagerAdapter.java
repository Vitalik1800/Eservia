package com.eservia.booking.ui.splash.intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.eservia.booking.R;

import java.util.ArrayList;
import java.util.List;

public class IntroPagerAdapter extends PagerAdapter {

    public interface OnCloseListener {
    }

    private List<IntroAdapterItem> mItems;

    private final LayoutInflater mInflater;

    public IntroPagerAdapter(Context context) {
        mItems = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        IntroAdapterItem item = mItems.get(position);
        View imageLayout = mInflater.inflate(R.layout.item_intro_slide, view, false);

        ImageView imageView = imageLayout.findViewById(R.id.ivImage);
        TextView message = imageLayout.findViewById(R.id.tvMessage);

        if (item.getImage() != null) {
            imageView.setImageDrawable(item.getImage());
        }
        if (item.getMessage() != null) {
            message.setText(item.getMessage());
        }

        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, @NonNull Object object) {
        return view.equals(object);
    }

    public void replaceItems(List<IntroAdapterItem> items) {
        if (items == null) return;
        mItems = items;
        notifyDataSetChanged();
    }

    public List<IntroAdapterItem> getItems() {
        return mItems;
    }
}
