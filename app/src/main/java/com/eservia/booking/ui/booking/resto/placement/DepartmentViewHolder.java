package com.eservia.booking.ui.booking.resto.placement;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.butterknife.ButterKnife;

public class DepartmentViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout rlHolder;
    TextView tvTitle;

    private final DepartmentsAdapter.OnDepartmentClickListener mClickListener;

    public DepartmentViewHolder(View itemView, DepartmentsAdapter.OnDepartmentClickListener clickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mClickListener = clickListener;
    }

    public void bindView(DepartmentAdapterItem item) {
        rlHolder = itemView.findViewById(R.id.rlHolder);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        rlHolder.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.onDepartmentClicked(item, getAdapterPosition());
            }
        });

        tvTitle.setText(item.getDepartment().getName());

        switch (item.getState()) {
            case SELECTED: {
                rlHolder.setBackground(ContextCompat.getDrawable(rlHolder.getContext(),
                        R.drawable.background_common_button_red_light));
                tvTitle.setTextColor(ContextCompat.getColor(tvTitle.getContext(), R.color.white));
                rlHolder.setAlpha(1.0f);
                break;
            }
            case DISABLED: {
                rlHolder.setBackground(ContextCompat.getDrawable(rlHolder.getContext(),
                        R.drawable.background_common_button_resto_white_red_border));
                tvTitle.setTextColor(ContextCompat.getColor(tvTitle.getContext(), R.color.resto));
                rlHolder.setAlpha(0.5f);
                break;
            }
            default: {
                rlHolder.setBackground(ContextCompat.getDrawable(rlHolder.getContext(),
                        R.drawable.background_common_button_resto_white_red_border));
                tvTitle.setTextColor(ContextCompat.getColor(tvTitle.getContext(), R.color.resto));
                rlHolder.setAlpha(1.0f);
                break;
            }
        }
    }
}
