package com.eservia.booking.ui.business_page.restaurant.menu.view_holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.ui.business_page.restaurant.menu.MenuAdapter;
import com.eservia.booking.ui.business_page.restaurant.menu.adapter_items.CategoryItem;
import com.eservia.booking.util.ViewUtil;
import com.eservia.butterknife.ButterKnife;
import com.eservia.utils.StringUtil;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout rlCardHolder;
    CardView cvContainer;
    TextView tvGroup;
    ImageView ivIconNext;

    private final MenuAdapter.OnMenuClickListener mClickListener;

    public CategoryViewHolder(View itemView, MenuAdapter.OnMenuClickListener clickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mClickListener = clickListener;
    }

    public void bindView(CategoryItem item) {
        rlCardHolder = itemView.findViewById(R.id.rlCardHolder);
        cvContainer = itemView.findViewById(R.id.cvContainer);
        tvGroup = itemView.findViewById(R.id.tvGroup);
        ivIconNext = itemView.findViewById(R.id.ivIconNext);
        rlCardHolder.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.onCategoryClicked(item);
            }
        });
        String title = item.getCategory().getName();
        tvGroup.setText(!StringUtil.isEmpty(title) ? title : "");
        ViewUtil.setCardOutlineProvider(ivIconNext.getContext(), rlCardHolder, cvContainer);
    }
}
