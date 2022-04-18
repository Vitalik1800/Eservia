package com.eservia.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class BeautyDiscount {

    public static final String TYPE_PERCENT = "percent";
    public static final String TYPE_FIXED_PRICE = "fixed_price";

    @SerializedName("promotion_id")
    private Long promotionId;

    @SerializedName("discount_value")
    private Float discountValue;

    @SerializedName("discount_type")
    private String discountType;

    @SerializedName("price_value")
    private Float priceValue;

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Float getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Float discountValue) {
        this.discountValue = discountValue;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Float getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(Float priceValue) {
        this.priceValue = priceValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeautyDiscount that = (BeautyDiscount) o;

        if (!Objects.equals(promotionId, that.promotionId))
            return false;
        if (!Objects.equals(discountValue, that.discountValue))
            return false;
        if (!Objects.equals(discountType, that.discountType))
            return false;
        return Objects.equals(priceValue, that.priceValue);
    }

    @Override
    public int hashCode() {
        int result = promotionId != null ? promotionId.hashCode() : 0;
        result = 31 * result + (discountValue != null ? discountValue.hashCode() : 0);
        result = 31 * result + (discountType != null ? discountType.hashCode() : 0);
        result = 31 * result + (priceValue != null ? priceValue.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "BeautyDiscount{" +
                "promotionId=" + promotionId +
                ", discountValue=" + discountValue +
                ", discountType='" + discountType + '\'' +
                ", priceValue=" + priceValue +
                '}';
    }
}
