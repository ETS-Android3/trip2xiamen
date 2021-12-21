package com.t2xm.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "t2xm.db";
    private static DatabaseOpenHelper connection_instance;
    private static SQLiteDatabase database;

    private DatabaseOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DatabaseOpenHelper getConnection(Context context) {
        if (connection_instance == null) {
            connection_instance = new DatabaseOpenHelper(context.getApplicationContext(), DATABASE_NAME, null, 1);
        }
        return connection_instance;
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = getConnection(context).getWritableDatabase();
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users (userid integer primary key autoincrement, username text not null, email text not null, password text not null, profileImg longblob)");
        sqLiteDatabase.execSQL("create table reviews (reviewid integer primary key autoincrement, itemId integer not null, userid integer not null, reviewText text not null, rating integer not null, time datetime default (datetime('now''localtime')))");
        sqLiteDatabase.execSQL("create table reviewImages (imageid integer primary key autoincrement, reviewid integer not null, reviewImage longblob not null)");
        sqLiteDatabase.execSQL("create table items (itemid integer primary key autoincrement, itemName text not null, category integer not null, description text, image longblob, avgRating double, longitude double, latitude double)");
        sqLiteDatabase.execSQL("create table favouriteItems (favouriteid integer primary key autoincrement, userid integer not null, itemid integer not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        List<String> databases_name = new ArrayList<>();
        databases_name.add("users");
        databases_name.add("reviews");
        databases_name.add("reviewImages");
        databases_name.add("items");
        databases_name.add("ratings");
        databases_name.add("favouriteItems");
        for (int index = 0; index < databases_name.size(); index++) {
            sqLiteDatabase.execSQL("drop table if exists " + databases_name.get(index));
        }
        onCreate(sqLiteDatabase);
    }
}
