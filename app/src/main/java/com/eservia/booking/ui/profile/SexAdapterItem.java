package com.eservia.booking.ui.profile;

import androidx.annotation.StringRes;

public class SexAdapterItem {

    private final long sex;

    @StringRes
    private int titleStringId;

    private boolean isChecked;

    public SexAdapterItem(long sex, int titleStringId, boolean isChecked) {
        this.sex = sex;
        this.titleStringId = titleStringId;
        this.isChecked = isChecked;
    }

    public long getSex() {
        return sex;
    }

    public void setSex(long gender) {
    }

    public int getTitleStringId() {
        return titleStringId;
    }

    public void setTitleStringId(int titleStringId) {
        this.titleStringId = titleStringId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
