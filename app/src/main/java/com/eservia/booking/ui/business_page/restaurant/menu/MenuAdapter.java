package com.eservia.booking.ui.business_page.restaurant.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.ui.business_page.restaurant.menu.adapter_items.BaseMenuItem;
import com.eservia.booking.ui.business_page.restaurant.menu.adapter_items.CategoryItem;
import com.eservia.booking.ui.business_page.restaurant.menu.adapter_items.DishItem;
import com.eservia.booking.ui.business_page.restaurant.menu.view_holders.CategoryViewHolder;
import com.eservia.booking.ui.business_page.restaurant.menu.view_holders.DishViewHolder;

public class MenuAdapter extends BaseRecyclerAdapter<BaseMenuItem> {

    public interface OnMenuClickListener {

        void onCategoryClicked(CategoryItem categoryItem);

        void onDishClicked(DishItem dishItem);
    }

    private final Context mContext;

    private final OnMenuClickListener mClickListener;

    public MenuAdapter(Context context, OnMenuClickListener clickListener) {
        mContext = context;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case BaseMenuItem.ITEM_CATEGORY: {
                View itemView = inflater.inflate(R.layout.item_booking_restaurant_dish_group,
                        parent, false);
                return new CategoryViewHolder(itemView, mClickListener);
            }
            case BaseMenuItem.ITEM_DISH: {
                View itemView = inflater.inflate(R.layout.item_booking_restaurant_dish,
                        parent, false);
                return new DishViewHolder(itemView, mClickListener);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        BaseMenuItem item = getItem(position);

        switch (item.getType()) {
            case BaseMenuItem.ITEM_CATEGORY: {
                CategoryViewHolder categoryViewHolder = (CategoryViewHolder) viewHolder;
                categoryViewHolder.bindView((CategoryItem) item);
                break;
            }
            case BaseMenuItem.ITEM_DISH: {
                DishViewHolder dishViewHolder = (DishViewHolder) viewHolder;
                try {
                    dishViewHolder.bindView((DishItem) item);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
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
}
