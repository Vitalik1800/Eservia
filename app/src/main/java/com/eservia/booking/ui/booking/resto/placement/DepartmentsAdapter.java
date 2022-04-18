package com.eservia.booking.ui.booking.resto.placement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;

public class DepartmentsAdapter extends BaseRecyclerAdapter<DepartmentAdapterItem> {

    public interface OnDepartmentClickListener {

        void onDepartmentClicked(DepartmentAdapterItem item, int position);
    }

    private final Context mContext;

    private final OnDepartmentClickListener mListener;

    public DepartmentsAdapter(Context context, OnDepartmentClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_booking_resto_table_place,
                parent, false);
        return new DepartmentViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        DepartmentAdapterItem item = getItem(position);
        DepartmentViewHolder departmentViewHolder = (DepartmentViewHolder) viewHolder;
        departmentViewHolder.bindView(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelected(DepartmentAdapterItem selected) {
        for (DepartmentAdapterItem item : getListItems()) {
            if (item.getDepartment().getId().equals(selected.getDepartment().getId())) {
                item.setState(DepartmentAdapterItem.State.SELECTED);
            } else {
                if (item.getState().equals(DepartmentAdapterItem.State.SELECTED)) {
                    item.setState(DepartmentAdapterItem.State.UNSELECTED);
                }
            }
        }
        notifyDataSetChanged();
    }
}
