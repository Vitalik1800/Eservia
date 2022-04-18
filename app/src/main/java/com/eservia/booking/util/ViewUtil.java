package com.eservia.booking.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.CardOutlineProvider;
import com.eservia.booking.common.view.CountDrawable;
import com.google.android.material.appbar.AppBarLayout;


public class ViewUtil {

    public static float dpToPixel(Context context, int dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static void applyCrossedTextStyle(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public static void setCounterDrawableCount(Context context, MenuItem menuItem, String count) {
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();
        CountDrawable badge;
        Drawable reuse = icon.findDrawableByLayerId(R.id.item_count);
        if (reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }
        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.item_count, badge);
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void setCardOutlineProvider(Context context, View cardContainer, CardView cardView) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            setCardOutlineProvider(context, cardContainer, cardView,
                    new CardOutlineProvider(context));
        } else {
            cardView.setCardElevation(ViewUtil.dpToPixel(context, 2));
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void setCardOutlineProviderStraightCorners(Context context, View cardContainer, CardView cardView) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            CardOutlineProvider provider = new CardOutlineProvider(context,
                    CardOutlineProvider.DEFAULT_PADDING_X,
                    CardOutlineProvider.DEFAULT_PADDING_Y,
                    CardOutlineProvider.DEFAULT_SHIFT, 0);
            ViewUtil.setCardOutlineProvider(context, cardContainer, cardView, provider);
        } else {
            ViewUtil.setCardOutlineProvider(context, cardContainer, cardView);
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void setCardOutlineProvider(Context context, View cardContainer,
                                              CardView cardView, ViewOutlineProvider provider) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cardContainer.setOutlineProvider(provider);
            cardContainer.invalidateOutline();
            cardView.setCardElevation(ViewUtil.dpToPixel(context, 0));
        } else {
            cardView.setCardElevation(ViewUtil.dpToPixel(context, 2));
        }
    }

    public static void setMargins(View view, int leftPx, int topPx, int rightPx, int bottomPx) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(leftPx, topPx, rightPx, bottomPx);
            view.requestLayout();
        }
    }

    public static void setWidthHeight(View view, int widthPx, int heightPx) {
        view.getLayoutParams().width = widthPx;
        view.getLayoutParams().height = heightPx;
        view.requestLayout();
    }

    public static void moveCursorToEnd(EditText editText) {
        editText.post(() -> editText.setSelection(editText.getText().toString().length()));
    }

    public static void scrollTop(NestedScrollView nestedScrollView) {
        if (nestedScrollView != null) {
            nestedScrollView.post(() -> {
                nestedScrollView.fullScroll(View.FOCUS_UP);
                nestedScrollView.scrollTo(0, 0);
            });
        }
    }

    public static void scrollTop(RecyclerView recyclerView) {
        if (recyclerView != null) {
            recyclerView.post(() -> recyclerView.smoothScrollToPosition(0));
        }
    }

    public static void scrollTop(AppBarLayout appBarLayout) {
        if (appBarLayout != null) {
            appBarLayout.post(() -> appBarLayout.setExpanded(true));
        }
    }

    public static void scrollTop(AppBarLayout appBarLayout, RecyclerView recyclerView) {
        scrollTop(recyclerView);
        scrollTop(appBarLayout);
    }

    public static void showPassword(EditText editText) {
        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    public static void hidePassword(EditText editText) {
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void setOnTouchListenerForVerticalScroll(EditText editText) {
        editText.setOnTouchListener((v, event) -> {
            if (v.equals(editText)) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
            }
            return false;
        });
    }
}
