package com.eservia.common.view.recycle_selector;

import android.graphics.drawable.Drawable;

public class CommonRecyclerSelectorAdapterItem {

    private Drawable imageSelected;

    private Drawable imageNormal;

    private String text;

    private int textColorSelected;

    private int textColorNormal;

    private boolean isSelected;

    public CommonRecyclerSelectorAdapterItem(Drawable imageSelected, Drawable imageNormal,
                                             String text, int textColorSelected, int textColorNormal,
                                             boolean isSelected) {
        this.imageSelected = imageSelected;
        this.imageNormal = imageNormal;
        this.text = text;
        this.textColorSelected = textColorSelected;
        this.textColorNormal = textColorNormal;
        this.isSelected = isSelected;
    }

    public Drawable getImageSelected() {
        return imageSelected;
    }

    public void setImageSelected(Drawable imageSelected) {
        this.imageSelected = imageSelected;
    }

    public Drawable getImageNormal() {
        return imageNormal;
    }

    public void setImageNormal(Drawable imageNormal) {
        this.imageNormal = imageNormal;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextColorSelected() {
        return textColorSelected;
    }

    public void setTextColorSelected(int textColorSelected) {
        this.textColorSelected = textColorSelected;
    }

    public int getTextColorNormal() {
        return textColorNormal;
    }

    public void setTextColorNormal(int textColorNormal) {
        this.textColorNormal = textColorNormal;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
