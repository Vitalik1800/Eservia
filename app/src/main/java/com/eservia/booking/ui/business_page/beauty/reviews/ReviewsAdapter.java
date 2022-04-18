package com.eservia.booking.ui.business_page.beauty.reviews;

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
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.expantabletextview.views.ExpandableTextView;
import com.eservia.glide.Glide;
import com.eservia.glide.load.engine.DiskCacheStrategy;
import com.eservia.glide.load.resource.drawable.DrawableTransitionOptions;
import com.eservia.glide.request.RequestOptions;
import com.eservia.model.entity.BusinessComment;
import com.eservia.model.entity.PhotoSize;
import com.eservia.model.entity.UserInfoShort;
import com.eservia.simpleratingbar.SimpleRatingBar;

import org.joda.time.DateTime;

public class ReviewsAdapter extends BaseRecyclerAdapter<ReviewsAdapterItem> {

    private static final int THRESHHOLD = 25;

    private final CommentClickListener mCommentClickListener;

    private final CommentPaginationListener mPaginationListener;

    private final Context mContext;

    public ReviewsAdapter(Context context, CommentClickListener listener,
                          CommentPaginationListener paginationListener) {
        mContext = context;
        mCommentClickListener = listener;
        mPaginationListener = paginationListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case ReviewsAdapterItem.ITEM_REVIEW: {
                View itemView = inflater.inflate(R.layout.item_business_page_beauty_review,
                        parent, false);
                return new CommentViewHolder(itemView);
            }
            case ReviewsAdapterItem.ITEM_CREATE_REVIEW: {
                View itemView = inflater.inflate(R.layout.item_business_page_beauty_create_review,
                        parent, false);
                return new CreateCommentViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position >= getItemCount() - THRESHHOLD) {
            if (mPaginationListener != null) {
                mPaginationListener.loadMoreComments();
            }
        }

        int viewType = getItemViewType(position);
        switch (viewType) {
            case ReviewsAdapterItem.ITEM_REVIEW: {

                ReviewsAdapterItem adapterItem = getListItems().get(position);

                BusinessComment item = ((ItemReview) adapterItem).getComment();
                CommentViewHolder holder = (CommentViewHolder) viewHolder;

                holder.view.setOnClickListener(view -> {
                    if (mCommentClickListener != null) {
                        mCommentClickListener.onCommentClick(item, position);
                    }
                });

                holder.tvComment.setOnClickListener(view -> holder.tvComment.toggle());

                if (item.getUserInfo() != null) {

                    UserInfoShort userInfo = item.getUserInfo();

                    try {
                        Glide.with(mContext)
                                .load(ImageUtil.getUserPhotoPath(PhotoSize.MIDDLE, userInfo.getPhotoPath()))
                                .apply(RequestOptions.circleCropTransform())
                                .apply(RequestOptions.placeholderOf(R.drawable.user_man_big))
                                .apply(RequestOptions.errorOf(R.drawable.user_man_big))
                                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(holder.ivUserPic);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (userInfo.getFirstName() != null) {
                        holder.tvUserName.setText(userInfo.getFirstName());
                    } else {
                        holder.tvUserName.setText("");
                    }
                } else {
                    holder.tvUserName.setText("");
                }

                if (item.getComment() != null) {
                    holder.tvComment.setVisibility(View.VISIBLE);
                    holder.tvComment.setText(item.getComment());
                } else {
                    holder.tvComment.setVisibility(View.GONE);
                    holder.tvComment.setText("");
                }

                if (item.getCreatedAt() != null) {
                    DateTime time = DateTime.parse(item.getCreatedAt());
                    holder.tvDate.setText(time.toString(BusinessUtil.COMMENT_DATE_FORMAT));
                } else {
                    holder.tvDate.setText("");
                }

                if (item.getRating() != null) {
                    holder.rbRating.setRating(BusinessUtil.starsRating(item.getRating()));

                    holder.tvRating.setText(BusinessUtil.formatRating(item.getRating()));
                } else {
                    holder.tvRating.setText("");
                }

                ViewUtil.setCardOutlineProvider(mContext, holder.rlCardHolderReview, holder.cvContainerReview);

                break;
            }
            case ReviewsAdapterItem.ITEM_CREATE_REVIEW: {
                ItemCreateReview item = (ItemCreateReview) getListItems().get(position);
                CreateCommentViewHolder holder = (CreateCommentViewHolder) viewHolder;

                holder.view.setOnClickListener(view -> {
                    if (mCommentClickListener != null) {
                        mCommentClickListener.onWriteCommentClick();
                    }
                });

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

    public interface CommentPaginationListener {

        void loadMoreComments();
    }

    public interface CommentClickListener {

        void onCommentClick(BusinessComment comment, int position);

        void onWriteCommentClick();
    }

    private static class CommentViewHolder extends RecyclerView.ViewHolder {

        View view;
        RelativeLayout rlCardHolderReview;
        CardView cvContainerReview;
        ImageView ivUserPic;
        TextView tvDate;
        ExpandableTextView tvComment;
        TextView tvUserName;
        TextView tvRating;
        SimpleRatingBar rbRating;

        CommentViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            rlCardHolderReview = itemView.findViewById(R.id.rlCardHolderReview);
            cvContainerReview = itemView.findViewById(R.id.cvContainerReview);
            ivUserPic = itemView.findViewById(R.id.ivUserPic);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvRating = itemView.findViewById(R.id.tvRating);
            rbRating = itemView.findViewById(R.id.rbRating);
        }
    }

    private static class CreateCommentViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvLeaveFeedback;

        CreateCommentViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvLeaveFeedback = itemView.findViewById(R.id.tvLeaveFeedback);
        }
    }
}
