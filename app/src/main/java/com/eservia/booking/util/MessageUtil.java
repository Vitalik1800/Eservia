package com.eservia.booking.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;

public class MessageUtil {

    public static void showToast(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void showToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    public static void showToast(Context context, @StringRes int messageId) {
        showToast(context, context.getString(messageId));
    }

    public static void showToast(Context context, Throwable throwable) {
        showToast(context, getResErrorMessage(context, throwable));
    }

    public static void showSnackbar(View view, String message) {
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        }
    }

    public static void showSnackbar(View view, @StringRes int messageId) {
        showSnackbar(view, view.getContext().getString(messageId));
    }

    public static void showSnackbar(View view, Throwable throwable) {
        String message = getResErrorMessage(view.getContext(), throwable);
        showSnackbar(view, message);
    }

    public static String getResErrorMessage(Context context, Throwable throwable) {
        @StringRes int messageId = ServerErrorUtil.getErrorStringRes(throwable);
        return context.getString(messageId);
    }

    public static String getResErrorOrServerErrorMessage(Context context, Throwable throwable) {
        @StringRes int messageId = ServerErrorUtil.getErrorStringRes(throwable);
        String serverMessage = ServerErrorUtil.getServerFirstErrorString();
        if (!serverMessage.isEmpty()) {
            return serverMessage;
        } else {
            return context.getString(messageId);
        }
    }

}
