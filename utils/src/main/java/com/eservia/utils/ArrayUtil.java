package com.eservia.utils;

import java.util.Set;

public class ArrayUtil {

    public static long[] toPrimitives(Long... objects) {
        long[] primitives = new long[objects.length];
        for (int i = 0; i < objects.length; i++)
            primitives[i] = objects[i];
        return primitives;
    }

    public static Long[] toArray(Set<Long> set) {
        return set.toArray(new Long[0]);
    }

    public static long[] toPrimitivesArray(Set<Long> set) {
        return toPrimitives(toArray(set));
    }
}
