package com.eservia.common.view.recycle_selector;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommonRecyclerSelector extends RecyclerView implements
        CommonRecyclerSelectorAdapter.Listener {

    public interface CommonRecyclerSelectorListener {

        void onCommonRecyclerItemSelected(CommonRecyclerSelectorAdapterItem item, int position);
    }

    private CommonRecyclerSelectorAdapter adapter;

    private CommonRecyclerSelectorListener listener;

    public CommonRecyclerSelector(Context context) {
        super(context);
        init(context, null);
    }

    public CommonRecyclerSelector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        adapter = new CommonRecyclerSelectorAdapter(context, this);
        setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        setAdapter(adapter);
    }

    @Override
    public void onAdapterItemSelected(CommonRecyclerSelectorAdapterItem item, int position) {
        if (listener != null) {
            listener.onCommonRecyclerItemSelected(item, position);
        }
    }

    public void setListener(CommonRecyclerSelectorListener listener) {
        this.listener = listener;
    }

    public void setItems(List<CommonRecyclerSelectorAdapterItem> items) {
        adapter.setItems(items);
    }

    public void setItemsSizeIncreasing(boolean isItemsSizeIncreasing) {
        adapter.setItemsSizeIncreasing(isItemsSizeIncreasing);
    }

    public void setSelectedItemAtPosition(int position) {
        adapter.setSelected(position);
    }
}
