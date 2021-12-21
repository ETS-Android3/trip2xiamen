package com.t2xm.database;

import android.database.sqlite.SQLiteDatabase;

public class ItemDao {
    private static SQLiteDatabase database = DatabaseOpenHelper.getDatabase(null);
}
