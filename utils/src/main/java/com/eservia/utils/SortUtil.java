package com.eservia.utils;

public class SortUtil {

    private static final String ASC = "";
    private static final String DESC = "-";

    private static final String RATING = "rating";
    private static final String NAME = "name";
    private static final String COMMENTS = "comments";
    private static final String DATE = "date";
    private static final String CREATED_AT = "created_at";
    private static final String DISTANCE = "distance";

    public static String nameAsc() {
        return getFormatted(ASC, NAME);
    }

    public static String ratingDesc() {
        return getFormatted(DESC, RATING);
    }

    public static String commentsDesc() {
        return getFormatted(DESC, COMMENTS);
    }

    public static String dateDesc() {
        return getFormatted(DESC, DATE);
    }

    public static String createdAtDesc() {
        return getFormatted(DESC, CREATED_AT);
    }

    public static String distanceAsc() {
        return getFormatted(ASC, DISTANCE);
    }

    private static String getFormatted(String prefix, String sortType) {
        return prefix + sortType;
    }
}
