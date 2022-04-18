package com.eservia.simplecalendar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.simplecalendar.R;
import com.eservia.simplecalendar.utils.TimeUtil;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_DAY_EMPTY = 1;
    private static final int ITEM_TYPE_DAY = 2;

    private final OnDayItemClickListener mListener;

    private final Context mContext;

    private final List<DayAdapterItem> mItems = new ArrayList<>();

    @ColorInt
    private int mItemColorId;

    public DayAdapter(Context context, OnDayItemClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_DAY_EMPTY) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_calendar_day_empty, parent, false);
            return new EmptyDayViewHolder(itemView);
        } else if (viewType == ITEM_TYPE_DAY) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_calendar_day, parent, false);
            return new DayViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == ITEM_TYPE_DAY) {

            DayAdapterItem item = mItems.get(position);
            DayViewHolder holder = (DayViewHolder) viewHolder;

            holder.view.setOnClickListener(view -> {
                if (mListener != null && !item.isHoliday()) {
                    mListener.onDayItemClick(item);
                }
            });

            holder.tvDayName.setText(String.valueOf(item.getDay()));

            if (item.isToday() && !item.isSelected()) {
                holder.ivDayToday.setVisibility(View.VISIBLE);
            } else {
                holder.ivDayToday.setVisibility(View.INVISIBLE);
            }

            if (item.isSelected()) {
                holder.tvDayName.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.ivDaySelected.setColorFilter(mItemColorId, PorterDuff.Mode.SRC_IN);
                holder.ivDaySelected.setVisibility(View.VISIBLE);
            } else {
                holder.ivDaySelected.setVisibility(View.INVISIBLE);
                if (item.isHoliday()) {
                    holder.tvDayName.setTextColor(ContextCompat.getColor(mContext, R.color.grey));
                } else {
                    holder.tvDayName.setTextColor(ContextCompat.getColor(mContext, R.color.dark));
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isEmpty() ? ITEM_TYPE_DAY_EMPTY : ITEM_TYPE_DAY;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelected(DayAdapterItem selected) {
        for (DayAdapterItem item : mItems) {
            item.setSelected(item.equals(selected));
        }
        notifyDataSetChanged();
    }

    @Nullable
    public DayAdapterItem getItemByDate(DateTime date) {

        for (DayAdapterItem item : mItems) {

            if (TimeUtil.isSameDay(item.getDateTime(), date)) {

                return item;
            }
        }
        return null;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItemColorId(@ColorInt int colorId) {
        mItemColorId = colorId;
        notifyDataSetChanged();
    }

    public DayAdapterItem getItem(int position) {
        return mItems.get(position);
    }

    public List<DayAdapterItem> getItems() {
        return new ArrayList<>(mItems);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addAll(List<DayAdapterItem> items) {
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(DayAdapterItem item) {
        this.mItems.add(item);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void remove(DayAdapterItem item) {
        this.mItems.remove(item);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void remove(int id) {
        this.mItems.remove(id);
        notifyDataSetChanged();
    }

    public void replaceAll(List<DayAdapterItem> items) {
        this.mItems.clear();
        addAll(items);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearAll() {
        this.mItems.clear();
        notifyDataSetChanged();
    }
}
