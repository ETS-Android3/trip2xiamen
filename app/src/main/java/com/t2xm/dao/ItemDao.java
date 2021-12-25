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
    public static final String IMAGE = "image";
    public static final String AVGRATING = "avgRating";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";

    public static long insertItemAndGetItemId(Item item) {
        ContentValues cv = new ContentValues();
        cv.put(ITEMNAME, item.itemName);
        cv.put(CATEGORY, item.category);
        cv.put(DESCRIPTION, item.description);
        cv.put(IMAGE, item.image);
        cv.put(LONGITUDE, item.longitude);
        cv.put(LATITUDE, item.latitude);
        return database.insert(TABLE, null, cv);
    }

    public static boolean insertItem(Item item) {
        return insertItemAndGetItemId(item) > 0;
    }

    public static double getItemAvgRating(Integer itemId) {
        Cursor cursor = database.rawQuery("select avg(rating) from items where itemId=?", new String[]{String.valueOf(itemId)});
        return cursor.moveToNext() ? cursor.getDouble(0) : 0d;
    }

    @SuppressLint("Range")
    public static Item getItemByItemId(Integer itemId) {
        Cursor cursor = database.rawQuery("select itemId, itemName, category, description, image, avgRating, longitude, latitude from items where itemId=?", new String[]{String.valueOf(itemId)});
        Item item = null;
        if (cursor.moveToNext()) {
            item = new Item() ;
            item.itemId = cursor.getInt(cursor.getColumnIndex("itemId"));
            item.itemName = cursor.getString(cursor.getColumnIndex("itemName"));
            item.category = cursor.getInt(cursor.getColumnIndex("category"));
            item.description = cursor.getString(cursor.getColumnIndex("description"));
            item.image = cursor.getString(cursor.getColumnIndex("image"));
            item.avgRating = cursor.getDouble(cursor.getColumnIndex("avgRating"));
            item.longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            item.latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
        }
        return item;
    }

    @SuppressLint("Range")
    public static List<Item> getItemListByCategory(Integer category) {
        Cursor cursor = database.rawQuery("select itemId, itemName, category, description, image, avgRating, longitude, latitude from items where category=?", new String[]{String.valueOf(category)});
        List<Item> itemList = null;
        if(cursor.getCount() > 0) {
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
    public static List<Item> getAllItemList() {
        Cursor cursor = database.rawQuery("select itemId, itemName, category, description, image, avgRating, longitude, latitude from items", null);
        List<Item> itemList = null;
        if(cursor.getCount() > 0) {
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
