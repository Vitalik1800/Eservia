package com.eservia.utils;


import android.widget.EditText;

public class PhoneUtil {

    public static final String PHONE_PREFIX = "+38";

    public static String phoneWithPlus(String phone) {
        if (!phone.startsWith("+")) {
            phone = "+" + phone;
        }
        return phone;
    }

    public static void addPrefix(EditText etPhone) {
        if (etPhone.getText().toString().isEmpty()) {
            etPhone.append(PHONE_PREFIX);
        }
    }
}
