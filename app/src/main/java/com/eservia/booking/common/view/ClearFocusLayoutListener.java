package com.eservia.booking.common.view;

import android.view.View;
import android.view.ViewTreeObserver;

import com.eservia.utils.KeyboardUtil;

public class ClearFocusLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private final View view;

    private final View[] viewsToClearFocus;

    private int keyboardState;

    public ClearFocusLayoutListener(View view, View[] viewsToClearFocus) {
        this.view = view;
        this.viewsToClearFocus = viewsToClearFocus;
    }

    @Override
    public void onGlobalLayout() {
        if (view == null) return;

        if (KeyboardUtil.keyboardIsShown(view.getRootView())) {
            keyboardState = 1;
        } else {
            if (keyboardState != 0) {
                clearFocusForAll();
            }
            keyboardState = 0;
        }
    }

    private void clearFocusForAll() {
        for (View view : viewsToClearFocus) {
            view.clearFocus();
        }
    }
}
