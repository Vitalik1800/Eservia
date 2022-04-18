package com.eservia.booking.util;


import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.eservia.booking.R;

public class ValidatorUtil {

    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 40;

    public static final int MIN_EMAIL_LENGTH = 5;
    public static final int MAX_EMAIL_LENGTH = 255;

    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 30;

    public static final int MIN_PHONE_LENGTH = 9;
    public static final int MAX_PHONE_LENGTH = 12;

    public static final int CONFIRM_CODE_LENGTH = 6;

    public static final int MIN_SHORT_MESS_LENGTH = 3;
    public static final int MAX_SHORT_MESS_LENGTH = 120;

    public static final int MIN_ESERVIA_FEEDBACK_LENGTH = 3;
    public static final int MAX_ESERVIA_FEEDBACK_LENGTH = 700;

    public static final int MAX_BOOKING_COMMENT_LENGTH = 255;

    public static final int MAX_TEXT_LENGTH = 500;

    public static final int MIN_PROFILE_YEARS = 12;

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    @Nullable
    public static String isConfirmCodeValid(Context context, String code) {
        if (isEmpty(code)) {
            return getString(context, R.string.empty_field_error);
        }
        if (code.length() < CONFIRM_CODE_LENGTH) {
            return context.getString(R.string.error_confirm_code_length);
        }
        if (code.length() > CONFIRM_CODE_LENGTH) {
            return context.getString(R.string.error_confirm_code_length);
        }
        if (!PatternsUtil.CONFIRM_CODE.matcher(code).matches()) {
            return context.getString(R.string.error_confirm_code_matcher);
        }
        return null;
    }

    @Nullable
    public static String isSecureCodeValid(Context context, String value) {
        if (isEmpty(value)) {
            return getString(context, R.string.empty_field_error);
        }
        if (!PatternsUtil.SECURE_CODE.matcher(value).matches()) {
            return getString(context, R.string.secure_code_is_not_valid);
        }
        return null;
    }

    @Nullable
    public static String isPasswordValid(Context context, String password) {
        if (isEmpty(password)) {
            return getString(context, R.string.empty_field_error);
        }
        if (password.contains(" ")) {
            return getString(context, R.string.contains_spaces_error);
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            return String.format(context.getString(R.string.error_password_short),
                    MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
        }
        if (password.length() > MAX_PASSWORD_LENGTH) {
            return String.format(context.getString(R.string.error_password_long),
                    MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
        }
        if (!PatternsUtil.PASSWORD.matcher(password).matches()) {
            return getString(context, R.string.error_password_matcher);
        }
        return null;
    }

    @Nullable
    public static String isConfirmPasswordValid(Context context, String password, String confirmPassword) {
        if (isEmpty(confirmPassword)) {
            return getString(context, R.string.empty_field_error);
        }
        if (confirmPassword.contains(" ")) {
            return getString(context, R.string.contains_spaces_error);
        }
        if (confirmPassword.length() < MIN_PASSWORD_LENGTH) {
            return String.format(context.getString(R.string.error_password_short),
                    MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
        }
        if (confirmPassword.length() > MAX_PASSWORD_LENGTH) {
            return String.format(context.getString(R.string.error_password_long),
                    MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
        }
        if (!PatternsUtil.PASSWORD.matcher(confirmPassword).matches()) {
            return getString(context, R.string.error_password_matcher);
        }
        if (!confirmPassword.equals(password)) {
            return getString(context, R.string.error_password_not_confirmed);
        }
        return null;
    }

    @Nullable
    public static String isNameValid(Context context, String name) {
        if (isEmpty(name.trim())) {
            return getString(context, R.string.empty_field_error);
        }
        if (name.length() < MIN_NAME_LENGTH) {
            return getString(context, R.string.error_field_short);
        }
        if (name.length() > MAX_NAME_LENGTH) {
            return getString(context, R.string.error_field_long);
        }
        if (name.contains("\n")) {
            return getString(context, R.string.error_contains_enter);
        }
        if (containsDigits(name)) {
            return context.getString(R.string.error_contains_numbers);
        }
        if (!PatternsUtil.NAME_PATTERN.matcher(name).matches()) {
            return context.getString(R.string.error_contains_symbols);
        }
        return null;
    }

    @Nullable
    public static String isEmailValid(Context context, String value) {
        if (isEmpty(value)) {
            return getString(context, R.string.empty_field_error);
        }
        if (value.contains(" ")) {
            return getString(context, R.string.contains_spaces_error);
        }
        if (value.length() < MIN_EMAIL_LENGTH) {
            return getString(context, R.string.error_email_short);
        }
        if (value.length() > MAX_EMAIL_LENGTH) {
            return getString(context, R.string.error_email_long);
        }
        if (!PatternsUtil.EMAIL.matcher(value).matches()) {
            return getString(context, R.string.invalid_mail_error);
        }
        return null;
    }

    @Nullable
    public static String isPhoneValid(Context context, String value) {
        if (value.startsWith("+")) {
            value = value.substring(1);
        }
        if (isEmpty(value)) {
            return getString(context, R.string.empty_field_error);
        }
        if (!PatternsUtil.PHONE.matcher(value).matches()) {
            return getString(context, R.string.error_phone_matcher);
        }
        if (value.length() < MIN_PHONE_LENGTH) {
            return String.format(
                    context.getString(R.string.error_phone_min_length), MIN_PHONE_LENGTH);
        }
        if (value.length() > MAX_PHONE_LENGTH) {
            return String.format(
                    context.getString(R.string.error_phone_max_length), MAX_PHONE_LENGTH);
        }
        return null;
    }

    @Nullable
    public static String isTextValid(Context context, String value) {
        if (isEmpty(value)) {
            return getString(context, R.string.empty_field_error);
        }
        if (value.length() > MAX_TEXT_LENGTH) {
            return getString(context, R.string.error_exceeding_symbols_amount);
        }
        return null;
    }

    @Nullable
    public static String isThemeValid(Context context, String value) {
        if (isEmpty(value)) {
            return getString(context, R.string.empty_field_error);
        }
        if (value.length() < MIN_SHORT_MESS_LENGTH) {
            return context.getString(R.string.error_theme_short);
        }
        if (value.length() > MAX_SHORT_MESS_LENGTH) {
            return getString(context, R.string.error_theme_long);
        }
        return null;
    }

    @Nullable
    public static String isLongMessageValid(Context context, String value) {
        if (isEmpty(value)) {
            return getString(context, R.string.empty_field_error);
        }
        if (value.length() < MIN_ESERVIA_FEEDBACK_LENGTH) {
            return context.getString(R.string.error_feedback_short);
        }
        if (value.length() > MAX_ESERVIA_FEEDBACK_LENGTH) {
            return getString(context, R.string.error_exceeding_symbols_amount);
        }
        return null;
    }

    public static boolean containsDigits(final String s) {
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private static String getString(Context context, @StringRes int id) {
        return context.getString(id);
    }
}
