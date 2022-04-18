package com.eservia.model.remote.rest.request;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class StringKeyList {

    private final List<String> keys = new ArrayList<>();

    public List<String> getKeys() {
        return keys;
    }

    public StringKeyList add(String key) {
        keys.add(key);
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        if (keys.isEmpty()) return "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            builder.append(keys.get(i));
            if (i + 1 < keys.size()) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
