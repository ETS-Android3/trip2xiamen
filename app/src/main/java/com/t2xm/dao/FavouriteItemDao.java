package com.t2xm.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import com.t2xm.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class FavouriteItemDao extends Dao {
    public static final String TABLE = "favouriteItems";
    public static final String FAVOURITEID = "fovouriteId";
    public static final String USERID = "userId";
    public static final String ITEMID = "itemId";

    public static boolean insertFavouriteItem(String username, Integer itemId) {
        Integer userId = UserDao.getUserIdByUsername(username);
        ContentValues cv = new ContentValues();
        cv.put(USERID, userId);
        cv.put(ITEMID, itemId);
        return database.insert(TABLE, null, cv) > 0;
    }

    public static boolean deleteFavouriteItem(String username, Integer itemId) {
        return database.delete(TABLE, "userId=? and itemId=?", new String[]{String.valueOf(UserDao.getUserIdByUsername(username)), String.valueOf(itemId)}) > 0;
    }

    @SuppressLint("Range")
    public static List<Item> getFavouriteItemListByUsername(String username) {
        Integer userId = UserDao.getUserIdByUsername(username);
        Cursor cursor = database.rawQuery("select t1.itemId, itemName, category, description, image, avgRating, longitude, latitude from favouriteItems t1 left join items t2 on t1.itemId=t2.itemId where userId=?", new String[]{String.valueOf(userId)});
        List<Item> itemList = null;
        if (cursor.getCount() > 0) {
            itemList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Item item = new Item();
                item.itemId = cursor.getInt(cursor.getColumnIndex("itemId"));
                item.itemName = cursor.getString(cursor.getColumnIndex("itemName"));
                item.category = cursor.getInt(cursor.getColumnIndex("category"));
                item.description = cursor.getString(cursor.getColumnIndex("description"));
                item.image = cursor.getString(cursor.getColumnIndex("image"));
                item.avgRating = cursor.getDouble(cursor.getColumnIndex("avgRating"));
                item.longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                item.latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                itemList.add(item);
            }
        }
        return itemList;
    }

    @SuppressLint("Range")
    public static List<Item> getItemListByUsernameAndCategory(String username, Integer category) {
        Integer userId = UserDao.getUserIdByUsername(username);
        Cursor cursor = database.rawQuery("select t1.itemId, itemName, category, description, image, avgRating, longitude, latitude from favouriteItems t1 left join items t2 on t1.itemId=t2.itemId where userId=? and t2.category=?", new String[]{String.valueOf(userId), String.valueOf(category)});
        List<Item> itemList = null;
        if (cursor.getCount() > 0) {
            itemList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Item item = new Item();
                item.itemId = cursor.getInt(cursor.getColumnIndex("itemId"));
                item.itemName = cursor.getString(cursor.getColumnIndex("itemName"));
                item.category = cursor.getInt(cursor.getColumnIndex("category"));
                item.description = cursor.getString(cursor.getColumnIndex("description"));
                item.image = cursor.getString(cursor.getColumnIndex("image"));
                item.avgRating = cursor.getDouble(cursor.getColumnIndex("avgRating"));
                item.longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                item.latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                itemList.add(item);
            }
        }
        return itemList;
    }

    public static boolean getIsInUserFavouriteItem(String username, Integer itemId) {
        Cursor cursor = database.rawQuery("select itemId favouriteId from favouriteItems t1 left join users t2 on t1.userId=t2.userId where t2.username=? and itemId=?", new String[]{username, String.valueOf(itemId)});
        return cursor.getCount() > 0;
    }

    public static boolean deleteFavouriteItemsByUserId(Integer userId) {
        long result = database.delete(TABLE, "userId=?", new String[]{String.valueOf(userId)});
        return result > 0;
    }
}
