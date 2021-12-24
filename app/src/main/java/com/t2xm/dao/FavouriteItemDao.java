package com.t2xm.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import com.t2xm.entity.FavouriteItem;
import com.t2xm.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class FavouriteItemDao extends Dao {
    public static final String TABLE = "favouriteItems";
    public static final String FAVOURITEID = "fovouriteId";
    public static final String USERID = "userId";
    public static final String ITEMID = "itemId";

    public static boolean insertFavouriteItem(FavouriteItem item) {
        ContentValues cv = new ContentValues();
        cv.put(USERID, item.userId);
        cv.put(ITEMID, item.itemId);
        return database.insert(TABLE, null, cv) > 0;
    }

    public static boolean deleteFavouriteItem(Integer itemId) {
        return database.delete(TABLE, "itemId=?", new String[]{String.valueOf(itemId)}) > 0;
    }

    @SuppressLint("Range")
    public static List<Item> getFavouriteItemListByUserId(Integer userId) {
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
}
