package com.t2xm.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUtil {
    private static Context context = ContextUtil.getContext();
    private static SQLiteDatabase database;

    public static SQLiteDatabase getTestDatabase() {
        if (database == null) {
            synchronized (DatabaseUtil.class) {
                database = DatabaseOpenHelper.getDatabase(context);
            }
        }
        return database;
    }
}
