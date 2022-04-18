package com.eservia.booking.util;


import java.util.regex.Pattern;

public class PatternsUtil {

    public static final Pattern EMAIL = Pattern.compile(
            "[a-zA-Z0-9+._%\\-]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static final Pattern PHONE = Pattern.compile(        // sdd = space, dot, or dash
            "(\\+?[0-9]+[\\- .]*)?"                           // +<digits><sdd>*
                    + "(\\([0-9]+\\)[\\- .]*)?"               // (<digits>)<sdd>*
                    + "([0-9][0-9\\- .][0-9\\- .]+[0-9])"); // <digit><digit|sdd>+<digit>

    public static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} .'-]+$");
    public static final Pattern PASSWORD = Pattern.compile("^[\\d\\p{L}A-z!@#$%^&*()_+=-]+$");
    public static final Pattern SECURE_CODE = Pattern.compile("^[\\d,A-z]{6}$");
    public static final Pattern CONFIRM_CODE = Pattern.compile("^[\\d,A-z]{6}$");
    public static final Pattern CONFIRM_CODE_MATCHER = Pattern.compile("\\d{6}$");
    public static final Pattern CONFIRM_SMS_CODE_MATCHER = Pattern.compile("ES-\\d{6}");
}
