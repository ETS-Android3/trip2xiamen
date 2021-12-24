package com.t2xm.utils;

import android.content.Context;

public class SharedPreferenceUtil {
    private static final String FILE_NAME = "t2xm_sp";
    private SharedPreferenceUtil(){}

    public static void setUsername(String username, Context context) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString("username", username);
    }

    public static String getUsername(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString("username", null);
    }

    public static void removeUsername(Context context) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().remove("username");
    }
}
