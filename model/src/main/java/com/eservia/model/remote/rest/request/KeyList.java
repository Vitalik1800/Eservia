package com.eservia.model.remote.rest.request;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class KeyList {

    private final List<Integer> keys = new ArrayList<>();

    public List<Integer> getKeys() {
        return keys;
    }

    public KeyList add(Integer key) {
        keys.add(key);
        return this;
    }

    public KeyList addAll(List<Integer> ids) {
        keys.addAll(ids);
        return this;
    }

    public KeyList addAll(HashSet<Integer> ids) {
        keys.addAll(ids);
        return this;
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
