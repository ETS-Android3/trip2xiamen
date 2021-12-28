package com.t2xm.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import com.t2xm.entity.User;

public class UserDao extends Dao {
    private static final String TABLE = "users";
    private static final String USERID = "userid";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PROFILE_IMG = "profileImg";

    public static Boolean checkUsernameExistence(String username) {
        return database.rawQuery("select userid from users where username=?", new String[]{username}).getCount() > 0;
    }

    public static Boolean checkEmailExistence(String email) {
        return database.rawQuery("select userid from users where email=?", new String[]{email}).getCount() > 0;
    }

    public static Integer getUserIdByUsername(String username) {
        Cursor cursor = database.rawQuery("select userId from users where username=?",
                new String[]{username});
        if (cursor.moveToNext()) {
            return cursor.getInt(0);
        }
        return null;
    }

    public static String getUsernameByUserId(Integer userId) {
        Cursor cursor = database.rawQuery("select username from users where userId=?", new String[]{String.valueOf(userId)});
        if (cursor.moveToNext()) {
            return cursor.getString(0);
        }
        return null;
    }

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

    public static User getUserInfoByUsername(String username) {
        return getUserInfoByUserId(getUserIdByUsername(username));
    }

    @SuppressLint("Range")
    public static User getUserInfoByUserId(Integer userId) {
        Cursor cursor = database.rawQuery("select username, email, profileImg from users where userId=?", new String[]{String.valueOf(userId)});
        User user = null;
        if (cursor.moveToNext()) {
            user = new User();
            user.username = cursor.getString(cursor.getColumnIndex("username"));
            user.email = cursor.getString(cursor.getColumnIndex("email"));
            user.profileImg = cursor.getBlob(cursor.getColumnIndex("profileImg"));
        }
        return user;
    }

    public static Boolean editUserProfileImage(User user) {
        ContentValues cv = new ContentValues();
        cv.put(PROFILE_IMG, user.profileImg);
        return database.update(TABLE, cv, "userId=?", new String[]{String.valueOf(user.userId)}) > 0;
    }

    public static Boolean validateUserAccount(String username, String password) {
        Cursor cursor = database.rawQuery("select userId from users where username=? and password=?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    public static Boolean editUserPasswordByUsername(String username, String password) {
        Integer userId = getUserIdByUsername(username);
        ContentValues cv = new ContentValues();
        cv.put(PASSWORD, password);
        return database.update(TABLE, cv, "userId=?", new String[]{String.valueOf(userId)}) > 0;
    }

    public static Boolean editUserEmailByUsername(String username, String email) {
        Integer userId = getUserIdByUsername(username);
        ContentValues cv = new ContentValues();
        cv.put(EMAIL, email);
        return database.update(TABLE, cv, "userId=?", new String[]{String.valueOf(userId)}) > 0;
    }

    public static Boolean deleteUserByUsername(String username) {
        Integer userId = getUserIdByUsername(username);
        return database.delete(TABLE, "userId=?", new String[]{String.valueOf(userId)}) > 0;
    }

    public static String getUserEmailByUsername(String username) {
        Integer userId = getUserIdByUsername(username);
        Cursor cursor = database.rawQuery("select email from users where userid=?", new String[]{String.valueOf(userId)});
        return cursor.moveToNext() ? cursor.getString(0) : "";
    }

    public static String getUserPasswordByUsername(String username) {
        Integer userId = getUserIdByUsername(username);
        Cursor cursor = database.rawQuery("select password from users where userid=?", new String[]{String.valueOf(userId)});
        return cursor.moveToNext() ? cursor.getString(0) : "";
    }
}
