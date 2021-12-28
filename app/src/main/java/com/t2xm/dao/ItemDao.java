package com.t2xm.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import com.t2xm.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDao extends Dao {
    public static final String TABLE = "items";
    public static final String ITEMID = "itemId";
    public static final String ITEMNAME = "itemName";
    public static final String CATEGORY = "category";
    public static final String DESCRIPTION = "description";
    public static final String PHONENUMBER = "phoneNumber";
    public static final String WEBSITE = "website";
    public static final String IMAGE = "image";
    public static final String AVGRATING = "avgRating";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";

    public static long insertItemAndGetItemId(Item item) {
        ContentValues cv = new ContentValues();
        cv.put(ITEMNAME, item.itemName);
        cv.put(CATEGORY, item.category);
        cv.put(DESCRIPTION, item.description);
        cv.put(PHONENUMBER, item.phoneNumber);
        cv.put(WEBSITE, item.website);
        cv.put(IMAGE, item.image);
        cv.put(LONGITUDE, item.longitude);
        cv.put(LATITUDE, item.latitude);
        return database.insert(TABLE, null, cv);
    }

    public static boolean insertItem(Item item) {
        return insertItemAndGetItemId(item) > 0;
    }

    public static void updateItemAvgRating(Integer itemId) {
        ContentValues cv = new ContentValues();
        cv.put(AVGRATING, ReviewDao.getAvgRatingByItemId(itemId));
        database.update(TABLE, cv, "itemId=?", new String[]{String.valueOf(itemId)});
    }

    @SuppressLint("Range")
    public static Item getItemByItemId(Integer itemId) {
        Cursor cursor = database.rawQuery("select itemId, itemName, category, description, phoneNumber, website, image, avgRating, longitude, latitude from items where itemId=?", new String[]{String.valueOf(itemId)});
        Item item = null;
        if (cursor.moveToNext()) {
            item = new Item();
            item.itemId = cursor.getInt(cursor.getColumnIndex(ITEMID));
            item.itemName = cursor.getString(cursor.getColumnIndex(ITEMNAME));
            item.category = cursor.getInt(cursor.getColumnIndex(CATEGORY));
            item.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
            item.phoneNumber = cursor.getString(cursor.getColumnIndex(PHONENUMBER));
            item.website = cursor.getString(cursor.getColumnIndex(WEBSITE)) ;
            item.image = cursor.getString(cursor.getColumnIndex(IMAGE));
            item.avgRating = cursor.getDouble(cursor.getColumnIndex(AVGRATING));
            item.longitude = cursor.getDouble(cursor.getColumnIndex(LONGITUDE));
            item.latitude = cursor.getDouble(cursor.getColumnIndex(LATITUDE));
        }
        return item;
    }

    @SuppressLint("Range")
    public static List<Item> getItemListByCategory(Integer category) {
        Cursor cursor = database.rawQuery("select itemId, itemName, category, description, image, avgRating from items where category=?", new String[]{String.valueOf(category)});
        List<Item> itemList = null;
        if (cursor.getCount() > 0) {
            itemList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Item item = new Item();
                item.itemId = cursor.getInt(cursor.getColumnIndex(ITEMID));
                item.itemName = cursor.getString(cursor.getColumnIndex(ITEMNAME));
                item.category = cursor.getInt(cursor.getColumnIndex(CATEGORY));
                item.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                item.image = cursor.getString(cursor.getColumnIndex(IMAGE));
                item.avgRating = cursor.getDouble(cursor.getColumnIndex(AVGRATING));
                itemList.add(item);
            }
        }
        return itemList;
    }

    @SuppressLint("Range")
    public static List<Item> getAllItemList() {
        Cursor cursor = database.rawQuery("select itemId, itemName, category, description, image, avgRating from items", null);
        List<Item> itemList = null;
        if (cursor.getCount() > 0) {
            itemList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Item item = new Item();
                item.itemId = cursor.getInt(cursor.getColumnIndex(ITEMID));
                item.itemName = cursor.getString(cursor.getColumnIndex(ITEMNAME));
                item.category = cursor.getInt(cursor.getColumnIndex(CATEGORY));
                item.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                item.image = cursor.getString(cursor.getColumnIndex(IMAGE));
                item.avgRating = cursor.getDouble(cursor.getColumnIndex(AVGRATING));
                itemList.add(item);
            }
        }
        return itemList;
    }
}
