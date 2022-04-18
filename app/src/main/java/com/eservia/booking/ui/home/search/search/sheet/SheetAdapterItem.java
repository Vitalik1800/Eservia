package com.eservia.booking.ui.home.search.search.sheet;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public class SheetAdapterItem<T> {

    private String key;

    @Nullable
    @StringRes
    private Integer titleStringId;

    @Nullable
    private String title;

    private boolean isChecked;

    private T model;

    public SheetAdapterItem(String key, @Nullable Integer titleStringId, @Nullable String title,
                            boolean isChecked) {
        this.key = key;
        this.titleStringId = titleStringId;
        this.title = title;
        this.isChecked = isChecked;
    }

    public SheetAdapterItem(String key, @Nullable Integer titleStringId, @Nullable String title,
                            boolean isChecked, T model) {
        this.key = key;
        this.titleStringId = titleStringId;
        this.title = title;
        this.isChecked = isChecked;
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Nullable
    public Integer getTitleStringId() {
        return titleStringId;
    }

    public void setTitleStringId(@Nullable Integer titleStringId) {
        this.titleStringId = titleStringId;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
