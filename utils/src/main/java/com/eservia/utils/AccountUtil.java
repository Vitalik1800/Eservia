package com.eservia.utils;


public class AccountUtil {

    public static String getFullName(String firstName, String lastName) {
        StringBuilder builder = new StringBuilder();
        if (!StringUtil.isEmpty(firstName)) {
            builder.append(firstName);
        }
        if (!StringUtil.isEmpty(lastName)) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(lastName);
        }
        return builder.toString();
    }
}
