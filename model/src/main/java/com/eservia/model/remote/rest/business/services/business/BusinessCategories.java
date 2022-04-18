package com.eservia.model.remote.rest.business.services.business;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class BusinessCategories {

    private final List<Integer> keys = new ArrayList<>();

    public List<Integer> getKeys() {
        return keys;
    }

    @NonNull
    @Override
    public String toString() {
        if (keys.isEmpty()) return "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            Integer key = keys.get(i);
            builder.append(key);
            if (i + 1 < keys.size()) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
