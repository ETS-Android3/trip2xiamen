package com.t2xm.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.t2xm.entity.User;
import com.t2xm.utils.DatabaseOpenHelper;

public class UserDao {
    private static SQLiteDatabase database = DatabaseOpenHelper.getDatabase(null);

    private static final String TABLE = "users";
    private static final String USERID = "userid";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PROFILE_IMG = "profileImg";

    public static Boolean insertUser(User user) {
        return insertUserAndGetUserId(user) > 0;
    }

    public static long insertUserAndGetUserId(User user) {
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, user.username);
        cv.put(EMAIL, user.email);
        cv.put(PASSWORD, user.password);
        long result = database.insert(TABLE, null, cv);
        return result;
    }
}
