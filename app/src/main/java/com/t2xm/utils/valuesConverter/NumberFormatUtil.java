package com.t2xm.utils.valuesConverter;

import java.text.DecimalFormat;

public class NumberFormatUtil {
    public static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public static Double get2dpDouble(Double d) {
        return Double.valueOf(decimalFormat.format(d));
    }
}
