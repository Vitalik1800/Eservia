package com.eservia.booking.ui.business_page.beauty.reviews;

import com.eservia.model.entity.BusinessComment;

public class ItemReview extends ReviewsAdapterItem {

    private BusinessComment comment;

    public ItemReview(BusinessComment comment) {
        this.comment = comment;
    }

    public BusinessComment getComment() {
        return comment;
    }

    public void setComment(BusinessComment comment) {
        this.comment = comment;
    }

    @Override
    public int getType() {
        return ITEM_REVIEW;
    }
}
