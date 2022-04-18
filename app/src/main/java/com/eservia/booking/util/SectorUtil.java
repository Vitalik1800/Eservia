package com.eservia.booking.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.eservia.booking.R;
import com.eservia.model.entity.BusinessCategory;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.remote.rest.request.StringKeyList;
import com.eservia.utils.LocaleUtil;

public class SectorUtil {

    public static StringKeyList getSectorsKeys() {
        StringKeyList sectors = new StringKeyList();
        sectors.add(BusinessSector.BEAUTY_CLUB);
        sectors.add(BusinessSector.RESTAURANTS);
        sectors.add(BusinessSector.HEALTH);
        sectors.add(BusinessSector.OTHER);
        return sectors;
    }

    @Nullable
    public static Drawable businessSectorDrawable(Context context, String sector) {
        switch (sector) {
            case BusinessSector.BEAUTY_CLUB:
                return getDrawable(context, R.drawable.ic_sector_beauty);
            case BusinessSector.RESTAURANTS:
                return getDrawable(context, R.drawable.ic_sector_resto);
            case BusinessSector.HEALTH:
                return getDrawable(context, R.drawable.ic_sector_health);
            case BusinessSector.OTHER:
                return getDrawable(context, R.drawable.ic_sector_other);
            default:
                return null;
        }
    }

    public static String sectorName(Context context, BusinessSector sector) {
        String lang = LocaleUtil.getLanguage(context);
        switch (lang) {
            case LocaleUtil.LANG_RU:
                return sector.getNameRu();
            case LocaleUtil.LANG_UK:
                return sector.getNameUk();
            default:
                return sector.getNameEn();
        }
    }

    public static String businessCategoryName(Context context, BusinessCategory category) {
        String lang = LocaleUtil.getLanguage(context);
        switch (lang) {
            case LocaleUtil.LANG_RU:
                return category.getNameRu();
            case LocaleUtil.LANG_UK:
                return category.getNameUk();
            default:
                return category.getNameEn();
        }
    }

    public static boolean isBeauty(String sector) {
        return sector.equals(BusinessSector.BEAUTY_CLUB);
    }

    public static boolean isRestaurant(String sector) {
        return sector.equals(BusinessSector.RESTAURANTS);
    }

    public static boolean isHealth(String sector) {
        return sector.equals(BusinessSector.HEALTH);
    }

    public static boolean isOther(String sector) {
        return sector.equals(BusinessSector.OTHER);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private static Drawable getDrawable(Context context, @DrawableRes int id) {
        return context.getResources().getDrawable(id);
    }

}
