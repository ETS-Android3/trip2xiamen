package com.t2xm.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.t2xm.entity.ReviewImage;

import java.util.ArrayList;
import java.util.List;

public class ReviewImageDao extends Dao {
    private static final String TABLE = "reviewImages";
    private static final String IMAGEID = "imageId";
    private static final String REVIEWID = "reviewId";
    private static final String REVIEWIMAGE = "reviewImage";

    public static boolean insertReviewImage(ReviewImage reviewImage) {
        return insertReviewImageAndGetImageId(reviewImage) > 0;
    }

    public static long insertReviewImageAndGetImageId(ReviewImage reviewImage) {
        ContentValues cv = new ContentValues();
        cv.put(REVIEWID, reviewImage.imageId);
        cv.put(REVIEWIMAGE, reviewImage.reviewImage);
        return database.insert(TABLE, null, cv);
    }

    public static byte[] getReviewImageByteArrayByImageId(Integer imageId) {
        Cursor cursor = database.rawQuery("select reviewImage from reviewImages where imageId=?", new String[]{String.valueOf(imageId)});
        return cursor.moveToNext() ? cursor.getBlob(0) : null;
    }

    public static List<byte[]> getReviewImageByteListByReviewId(Integer reviewId) {
        Cursor cursor = database.rawQuery("select reviewImage from reviewImages where reviewId=?", new String[]{String.valueOf(reviewId)});
        List<byte[]> imageList = null;
        if (cursor.getCount() > 0) {
            imageList = new ArrayList<>();
            while (cursor.moveToNext()) {
                imageList.add(cursor.getBlob(0));
            }
        }
        return imageList;
    }
}
