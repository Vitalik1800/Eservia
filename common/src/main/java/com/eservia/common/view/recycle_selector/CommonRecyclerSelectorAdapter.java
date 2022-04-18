package com.eservia.common.view.recycle_selector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.common.R;

import java.util.ArrayList;
import java.util.List;

public class CommonRecyclerSelectorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface Listener {
        void onAdapterItemSelected(CommonRecyclerSelectorAdapterItem item, int position);
    }

    private List<CommonRecyclerSelectorAdapterItem> items = new ArrayList<>();

    private final Context context;

    private final Listener listener;

    private boolean isItemsSizeIncreasing;

    CommonRecyclerSelectorAdapter(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_common_recycler_selector, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        CommonRecyclerSelectorAdapterItem item = items.get(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;

        itemViewHolder.view.setOnClickListener(v -> {
            setSelected(item);
            if (listener != null) {
                listener.onAdapterItemSelected(item, position);
            }
        });

        if (item.isSelected()) {
            itemViewHolder.image.setImageDrawable(item.getImageSelected());
            itemViewHolder.text.setTextColor(item.getTextColorSelected());
        } else {
            itemViewHolder.image.setImageDrawable(item.getImageNormal());
            itemViewHolder.text.setTextColor(item.getTextColorNormal());
        }
        itemViewHolder.text.setText(item.getText());

        if (isItemsSizeIncreasing) {
            int imagePaddingPx = calculateImagePadding(position);
            itemViewHolder.image.setPadding(imagePaddingPx, imagePaddingPx, imagePaddingPx, imagePaddingPx);
        } else {
            itemViewHolder.image.setPadding(0, 0, 0, 0);
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItems(List<CommonRecyclerSelectorAdapterItem> items) {
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelected(CommonRecyclerSelectorAdapterItem item) {
        for (CommonRecyclerSelectorAdapterItem itemTemp : items) {
            itemTemp.setSelected(false);
        }
        item.setSelected(true);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelected(int position) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setSelected(i == position);
        }
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItemsSizeIncreasing(boolean isItemsSizeIncreasing) {
        this.isItemsSizeIncreasing = isItemsSizeIncreasing;
        notifyDataSetChanged();
    }

    private int calculateImagePadding(int position) {
        position += 1;
        int coefficient = items.size() - position;
        if (coefficient > 5) {
            coefficient = 5;
        }
        return (int) (dpToPixel(coefficient) * 2);
    }

    private float dpToPixel(int dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView text;
        ImageView image;

        ItemViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.text = itemView.findViewById(R.id.text);
            this.image = itemView.findViewById(R.id.image);
        }
    }
}
