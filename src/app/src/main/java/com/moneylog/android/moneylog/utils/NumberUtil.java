package com.moneylog.android.moneylog.utils;

import java.util.Locale;

/**
 * Utility class to handle numbers
 */
public class NumberUtil {

    /**
     * Transforms a double into a String.
     *
     * @param val number
     * @return String
     */
    public static String stringify(final Double val) {
        if (val != null) {
            return String.format(Locale.ROOT, "%.2f", val);
        }
        return null;
    }

}
