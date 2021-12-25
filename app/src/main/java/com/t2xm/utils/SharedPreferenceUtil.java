package com.t2xm.utils;

import android.content.Context;

public class SharedPreferenceUtil {
    private static final String FILE_NAME = "t2xm_sp";

    private static final String FIRST_STARTUP = "firstStartup";
   private static final String LOGGED_IN_USERNAME = "loggedInUsername";

    private SharedPreferenceUtil() {
    }

    public static boolean getIsFirstStartup(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(FIRST_STARTUP, true);
    }

    public static void setIsFirstStartup(Context context) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putBoolean(FIRST_STARTUP, false);
    }

    public static void setUsername(String username, Context context) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(LOGGED_IN_USERNAME, username);
    }

    public static String getUsername(Context context) {
        //TODO return username from sharedPreferences
//        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(LOGGED_IN_USERNAME, null);
        return "testuser";
    }

    public static void removeUsername(Context context) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().remove(LOGGED_IN_USERNAME);
    }
}
