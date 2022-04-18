package com.eservia.common.view.item_touch_helper;


public interface ItemTouchHelperAdapter {


    void onItemMove(int fromPosition, int toPosition);


    void onItemDismiss(int position);
}
