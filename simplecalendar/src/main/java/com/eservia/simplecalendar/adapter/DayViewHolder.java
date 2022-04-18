package com.eservia.simplecalendar.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eservia.simplecalendar.R;

public class DayViewHolder extends RecyclerView.ViewHolder {

    View view;
    ImageView ivDaySelected;
    ImageView ivDayToday;
    TextView tvDayName;

    DayViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        ivDaySelected = itemView.findViewById(R.id.ivDaySelected);
        ivDayToday = itemView.findViewById(R.id.ivDayToday);
        tvDayName = itemView.findViewById(R.id.tvDayName);
    }
}
