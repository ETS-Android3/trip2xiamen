package com.t2xm.dao;

import android.database.sqlite.SQLiteDatabase;

import com.t2xm.utils.DatabaseOpenHelper;

public class ReviewDao {
    private static SQLiteDatabase database = DatabaseOpenHelper.getDatabase(null);
}