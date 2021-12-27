package com.t2xm.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    private static final String FILE_NAME = "t2xm_sp";

    private static final String FIRST_STARTUP = "firstStartup";
    private static final String LOGGED_IN_USERNAME = "loggedInUsername";

    private SharedPreferenceUtil() {
    }

    public static boolean getIsFirstStartup(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(FIRST_STARTUP, true);
    }

    public static void setIsFirstStartup(boolean isFirstStartup, Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(FIRST_STARTUP, isFirstStartup);
        editor.commit();
    }

    public static void setUsername(String username, Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(LOGGED_IN_USERNAME, username);
        editor.commit();
    }

    public static String getUsername(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(LOGGED_IN_USERNAME, null);
    }

    public static void removeUsername(Context context) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().remove(LOGGED_IN_USERNAME);
    }


    private static android.content.SharedPreferences.Editor getEditor(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
    }
}
