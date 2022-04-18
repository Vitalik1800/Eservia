package com.eservia.utils;

import java.math.BigDecimal;

public class NumberUtil {

    public static boolean isEqual(double a, double b) {
        return BigDecimal.valueOf(a).compareTo(BigDecimal.valueOf(b)) == 0;
    }
}
