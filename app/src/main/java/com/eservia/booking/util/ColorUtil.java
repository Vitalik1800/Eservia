package com.eservia.booking.util;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.ColorRes;

import com.eservia.booking.R;

import java.util.Random;

public class ColorUtil {

    public static void setProgressColor(ProgressBar bar, @ColorRes int color) {
        bar.getIndeterminateDrawable().setColorFilter(
                bar.getContext().getResources().getColor(color),
                android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public static int randomColor(Context context) {
        int[] colors = context.getResources().getIntArray(R.array.prepared_booking_services_colors);
        return colors[new Random().nextInt(colors.length)];
    }

    public static int[] swipeRefreshColors(Context context) {
        return context.getResources().getIntArray(R.array.prepared_booking_services_colors);
    }
}
