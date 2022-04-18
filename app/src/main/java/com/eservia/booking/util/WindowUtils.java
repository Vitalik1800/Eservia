package com.eservia.booking.util;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.eservia.booking.R;

public class WindowUtils {

    @SuppressLint("ObsoleteSdkInt")
    public static void setFullScreenLayout(@NonNull Activity activity, @IdRes int contentView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
            View container = activity.getWindow().getDecorView().findViewById(contentView);
            container.setPadding(0, container.getPaddingTop() + getStatusBarHeight(activity), 0, 0);
        }
    }

    public static void setFullScreenWithStatusBar(@NonNull Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public static int getStatusBarHeight(@NonNull Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static void setStatusBarColor(@NonNull Activity activity, @ColorRes int colorRes) {
        WindowUtils.setStatusBarColor(activity.getWindow(), ActivityCompat.getColor(activity, colorRes));
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void setStatusBarColor(Window window, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void setNavigationBarColor(Window window, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(color);
        }
    }

    public static void hideSoftKeyboard(Context context, @NonNull View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void setWhiteStatusBar(Activity activity) {
        if (activity == null) return;
        setLightStatusBar(activity, R.color.white);
    }

    public static void setLightStatusBar(Activity activity) {
        if (activity == null) return;
        setLightStatusBar(activity, R.color.windowBackgroundDefault);
    }

    public static void setBlackStatusBar(Activity activity) {
        if (activity == null) return;
        setDarkStatusBar(activity, R.color.black);
    }

    public static void setLightStatusBar(Activity activity, int colorId) {
        if (activity == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().setBackgroundDrawable(new ColorDrawable(
                    ContextCompat.getColor(activity, colorId)));
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            activity.getWindow().setBackgroundDrawable(new ColorDrawable(
                    ContextCompat.getColor(activity, R.color.statusBarColorGray)));
        }
    }

    public static void setDarkStatusBar(Activity activity, int colorId) {
        if (activity == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().setBackgroundDrawable(new ColorDrawable(
                    ContextCompat.getColor(activity, colorId)));
            activity.getWindow().getDecorView().setSystemUiVisibility(0);
        } else {
            activity.getWindow().setBackgroundDrawable(new ColorDrawable(
                    ContextCompat.getColor(activity, R.color.statusBarColorGray)));
        }
    }

    public static void setNormalStatusBar(Activity activity) {
        if (activity == null) return;
        activity.getWindow().setBackgroundDrawableResource(R.drawable.background_toolbar_gradient_green);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(0);
        }
    }

    public static void setTouchable(Activity activity, boolean touchable) {
        if (touchable) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}
