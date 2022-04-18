package com.eservia.booking.ui.business_page.restaurant.menu.view_holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.ui.business_page.restaurant.menu.MenuAdapter;
import com.eservia.booking.ui.business_page.restaurant.menu.adapter_items.DishItem;
import com.eservia.booking.util.DishUtil;
import com.eservia.booking.util.ImageUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.butterknife.ButterKnife;
import com.eservia.glide.Glide;
import com.eservia.glide.MaskTransformation;
import com.eservia.glide.load.MultiTransformation;
import com.eservia.glide.load.engine.DiskCacheStrategy;
import com.eservia.glide.load.resource.bitmap.CenterCrop;
import com.eservia.glide.load.resource.drawable.DrawableTransitionOptions;
import com.eservia.glide.request.RequestOptions;
import com.eservia.model.entity.OrderRestoNomenclature;
import com.eservia.model.entity.PhotoSize;
import com.eservia.utils.StringUtil;

public class DishViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout rlCardHolder;
    CardView cvContainer;
    ImageView ivPicture;
    TextView tvWeight;
    TextView tvTime;
    TextView tvPrice;
    TextView tvTitle;
    TextView tvSubtitle;

    private final MenuAdapter.OnMenuClickListener mClickListener;

    public DishViewHolder(View itemView, MenuAdapter.OnMenuClickListener clickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mClickListener = clickListener;
    }

    public void bindView(DishItem item) throws ClassNotFoundException {
        rlCardHolder = itemView.findViewById(R.id.rlCardHolder);
        cvContainer = itemView.findViewById(R.id.cvContainer);
        ivPicture = itemView.findViewById(R.id.ivPicture);
        tvWeight = itemView.findViewById(R.id.tvWeight);
        tvTime = itemView.findViewById(R.id.tvTime);
        tvPrice = itemView.findViewById(R.id.tvPrice);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
        rlCardHolder.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.onDishClicked(item);
            }
        });

        OrderRestoNomenclature dish = item.getNomenclature();

        Glide.with(ivPicture.getContext())
                .load(ImageUtil.getUserPhotoPath(PhotoSize.MIDDLE, dish.getPhotoPath()))
                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
                        new MaskTransformation(R.drawable.mask_business_image))))
                .apply(RequestOptions.placeholderOf(R.drawable.icon_business_photo_placeholder_beauty))
                .apply(RequestOptions.errorOf(R.drawable.icon_business_photo_placeholder_beauty))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivPicture);

        tvWeight.setText(DishUtil.INSTANCE.getFormattedMinWeight(dish));

        tvTime.setText(DishUtil.INSTANCE.getFormattedCookingTime(tvTime.getContext(), dish));

        tvPrice.setText(DishUtil.INSTANCE.getFormattedMinPrice(tvPrice.getContext(), dish));

        tvTitle.setText(dish.getName());

        String description = dish.getDescription();
        tvSubtitle.setText(!StringUtil.isEmpty(description) ? description : "");
        tvSubtitle.setVisibility(!StringUtil.isEmpty(description) ? View.VISIBLE : View.GONE);

        ViewUtil.setCardOutlineProvider(rlCardHolder.getContext(), rlCardHolder, cvContainer);
    }
}
