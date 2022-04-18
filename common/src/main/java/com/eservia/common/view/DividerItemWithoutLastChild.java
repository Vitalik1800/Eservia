package com.eservia.common.view;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemWithoutLastChild extends DividerItemDecoration {

    public DividerItemWithoutLastChild(Context context, int orientation) {
        super(context, orientation);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        // hide the divider for the last child
        // this work when your item view in RecyclerView has a NOT transparent background
        // for example android:background="#ffffff"
        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.setEmpty();
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }
}
