package com.eservia.model.remote.rest.business.services.business;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class BusinessTags {

    private final List<Integer> tagsKeys = new ArrayList<>();

    public List<Integer> getTagsKeys() {
        return tagsKeys;
    }

    @NonNull
    @Override
    public String toString() {
        if (tagsKeys.isEmpty()) return "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tagsKeys.size(); i++) {
            Integer key = tagsKeys.get(i);
            builder.append(key);
            if (i + 1 < tagsKeys.size()) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
