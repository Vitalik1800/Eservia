package com.eservia.booking.ui.home.search.search;

import com.eservia.model.entity.BusinessCategory;

public class CategoryAdapterItem {

    private boolean isSelected;
    private BusinessCategory category;

    public CategoryAdapterItem(BusinessCategory category) {
        this.category = category;
        this.isSelected = false;
    }

    public CategoryAdapterItem(BusinessCategory category, boolean isSelected) {
        this.isSelected = isSelected;
        this.category = category;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public BusinessCategory getCategory() {
        return category;
    }

    public void setCategory(BusinessCategory category) {
        this.category = category;
    }
}
