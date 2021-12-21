package com.t2xm.utils;

import androidx.annotation.Nullable;

public class LoggingUtil {
    public static void printMessage(@Nullable String t, Object msg) {
        String tag = (t == null) ? "NOTAG" : t;
        String message = (msg != null) ? msg.toString() : "null";
        System.out.println(tag + ": " + message);
    }
}
