package com.eservia.model.prefs.common;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;


public class UserPrefs implements PrivatePrefs {

    private final Context context;
    private final String prefsName;

    UserPrefs(@NonNull Context context, @NonNull String prefsName) {
        this.context = context;
        this.prefsName = prefsName;
    }

    private SharedPreferences getSharedPrefs() {
        return context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
    }

    @Override
    public String getString(String key) {
        return getSharedPrefs().getString(key, null);
    }

    @Override
    public int getInt(String key) {
        return getSharedPrefs().getInt(key, 0);
    }

    @Override
    public long getLong(String key) {
        return getSharedPrefs().getLong(key, 0L);
    }

    @Override
    public float getFloat(String key) {
        return getSharedPrefs().getFloat(key, 0.0f);
    }

    @Override
    public boolean getBoolean(String key) {
        return getSharedPrefs().getBoolean(key, false);
    }

    @Override
    public void putString(String key, String value) {
        getSharedPrefs().edit()
                .putString(key, value)
                .apply();
    }

    @Override
    public void putInt(String key, int value) {
        getSharedPrefs().edit()
                .putInt(key, value)
                .apply();
    }

    @Override
    public void putLong(String key, long value) {
        getSharedPrefs().edit()
                .putLong(key, value)
                .apply();
    }

    @Override
    public void putFloat(String key, float value) {
        getSharedPrefs().edit()
                .putFloat(key, value)
                .apply();
    }

    @Override
    public void putBoolean(String key, boolean value) {
        getSharedPrefs().edit()
                .putBoolean(key, value)
                .apply();
    }

    @Override
    public void remove(String key) {
        getSharedPrefs().edit()
                .remove(key)
                .apply();
    }

    @Override
    public void clear() {
        getSharedPrefs().edit().clear().apply();
    }
}
