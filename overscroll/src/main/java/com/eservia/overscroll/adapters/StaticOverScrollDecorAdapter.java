package com.eservia.overscroll.adapters;

import android.view.View;

import com.eservia.overscroll.HorizontalOverScrollBounceEffectDecorator;
import com.eservia.overscroll.VerticalOverScrollBounceEffectDecorator;

/**
 * A static adapter for views that are ALWAYS over-scroll-able (e.g. image view).
 *
 * @see HorizontalOverScrollBounceEffectDecorator
 * @see VerticalOverScrollBounceEffectDecorator
 */
public class StaticOverScrollDecorAdapter implements IOverScrollDecoratorAdapter {

    protected final View mView;

    public StaticOverScrollDecorAdapter(View view) {
        mView = view;
    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public boolean isInAbsoluteStart() {
        return true;
    }

    @Override
    public boolean isInAbsoluteEnd() {
        return true;
    }
}
