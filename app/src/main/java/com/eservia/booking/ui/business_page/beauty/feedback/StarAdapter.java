package com.eservia.booking.ui.business_page.beauty.feedback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.util.ViewUtil;
import com.eservia.simpleratingbar.SimpleRatingBar;

public class StarAdapter extends BaseRecyclerAdapter<StarItem> {

    private final StarClickListener mClickListener;

    private final Context mContext;

    public StarAdapter(Context context, StarClickListener clickListener) {
        mContext = context;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_business_feedback_beauty_rating_selector_star,
                parent, false);
        return new StarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        StarItem item = getListItems().get(position);
        StarViewHolder holder = (StarViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mClickListener != null) {
                mClickListener.onStarClick(item, position);
            }
        });

        switch (item.getState()) {
            case FILLED: {
                holder.rbRating.setRating(1.0f);
                holder.rbRating.setStarSize(ViewUtil.dpToPixel(mContext, 12));
                break;
            }
            case FILLED_EXPANDED: {
                holder.rbRating.setRating(1.0f);
                holder.rbRating.setStarSize(ViewUtil.dpToPixel(mContext, 27));
                break;
            }
            case NORMAL: {
                holder.rbRating.setRating(0.0f);
                holder.rbRating.setStarSize(ViewUtil.dpToPixel(mContext, 12));
                break;
            }
            default: {
                break;
            }
        }
    }

    public interface StarClickListener {

        void onStarClick(StarItem star, int position);
    }

    private static class StarViewHolder extends RecyclerView.ViewHolder {

        View view;
        SimpleRatingBar rbRating;

        StarViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            rbRating = itemView.findViewById(R.id.rbRating);
        }
    }
}
