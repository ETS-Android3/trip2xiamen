package com.t2xm.utils.valuesConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/YY HH:mm:ss");

    public static String getCurrentDateTime() {
        return dateTimeFormat.format(new Date().getTime());
    }

    public static String getDateTimeByTimestamp(Long timestamp) {
        return dateTimeFormat.format(new Date(timestamp).getTime());
    }

    public static Long getCurrentTimestamp() {
        return new Date().getTime();
    }
}