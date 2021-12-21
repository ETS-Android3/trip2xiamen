package com.t2xm.database;

import android.database.sqlite.SQLiteDatabase;

public class UserDao {
    private static SQLiteDatabase database = DatabaseOpenHelper.getDatabase(null);
}
