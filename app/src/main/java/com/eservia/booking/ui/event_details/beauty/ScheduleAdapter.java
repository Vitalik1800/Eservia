package com.eservia.booking.ui.event_details.beauty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.adapter.BaseRecyclerAdapter;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.TimeUtil;
import com.eservia.model.entity.MarketingWorkSchedule;

import org.joda.time.DateTime;

public class ScheduleAdapter extends BaseRecyclerAdapter<MarketingWorkSchedule> {

    private final Context mContext;

    private final Listener mListener;

    public ScheduleAdapter(Context context, Listener listener) {
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_event_beauty_schedule_time, parent, false);
        return new TimeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        MarketingWorkSchedule schedule = getListItems().get(position);

        TimeViewHolder holder = (TimeViewHolder) viewHolder;

        holder.view.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onScheduleItemClick(schedule, position);
            }
        });

        DateTime start = TimeUtil.dateTimeFromMillisOfDay(schedule.getStartTime());
        DateTime end = TimeUtil.dateTimeFromMillisOfDay(schedule.getEndTime());

        holder.tvTime.setText(BusinessUtil.formatWeekDaysHoursPeriod(mContext,
                schedule.getDay(), start, end));
    }

    public interface Listener {

        void onScheduleItemClick(MarketingWorkSchedule schedule, int position);
    }

    private static class TimeViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvTime;

        TimeViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
