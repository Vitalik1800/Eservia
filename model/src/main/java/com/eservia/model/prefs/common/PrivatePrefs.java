package com.eservia.model.prefs.common;


public interface PrivatePrefs {

    String getString(String key);

    int getInt(String key);

    long getLong(String key);

    float getFloat(String key);

    boolean getBoolean(String key);

    void putString(String key, String value);

    void putInt(String key, int value);

    void putLong(String key, long value);

    void putFloat(String key, float value);

    void putBoolean(String key, boolean value);

    void remove(String key);

    void clear();
}
