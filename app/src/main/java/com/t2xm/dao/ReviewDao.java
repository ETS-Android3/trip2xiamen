package com.t2xm.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import com.t2xm.entity.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewDao extends Dao {
    private static final String TABLE = "reviews";
    private static final String REVIEWID = "reviewId";
    private static final String ITEMID = "itemId";
    private static final String USERID = "userId";
    private static final String REVIEWTEXT = "reviewText";
    private static final String RATING = "rating";
    private static final String TIME = "time";

    public static long insertReviewAndGetReviewId(Review review) {
        ContentValues cv = new ContentValues();
        cv.put(USERID, review.userId);
        cv.put(ITEMID, review.itemId);
        cv.put(REVIEWTEXT, review.reviewText);
        return database.insert(TABLE, null, cv);
    }

    public static boolean insertReview(Review review) {
        return insertReviewAndGetReviewId(review) > 0;
    }

    @SuppressLint("Range")
    public static List<Review> getReviewListByItemId(Integer itemId) {
        Cursor cursor = database.rawQuery("select userId, reviewtext, rating from reviews where itemId=?",
                new String[]{String.valueOf(itemId)});
        List<Review> reviewList = null;
        if (cursor.getCount() > 0) {
            reviewList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Review review = new Review();
                review.userId = cursor.getInt(cursor.getColumnIndex(USERID));
                review.reviewText = cursor.getString(cursor.getColumnIndex(REVIEWTEXT));
                review.rating = cursor.getInt(cursor.getColumnIndex(RATING));
                reviewList.add(review);
            }
        }
        return reviewList;
    }

    @SuppressLint("Range")
    public static List<Review> get10LatestReview() {
        Cursor cursor = database.rawQuery("select userId, itemId, reviewtext, rating from reviews order by time desc limit 10", null);
        List<Review> reviewList = null;
        if (cursor.getCount() > 0) {
            reviewList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Review review = new Review();
                review.userId = cursor.getInt(cursor.getColumnIndex(USERID));
                review.itemId = cursor.getInt(cursor.getColumnIndex(ITEMID));
                review.reviewText = cursor.getString(cursor.getColumnIndex(REVIEWTEXT));
                review.rating = cursor.getInt(cursor.getColumnIndex(RATING));
                reviewList.add(review);
            }
        }
        return reviewList;
    }

    public static boolean deleteReviewByReviewId(Integer reviewId) {
        return database.delete(TABLE, "reviewId=?", new String[]{String.valueOf(reviewId)}) > 0;
    }

    @SuppressLint("Range")
    public static List<Review> getReviewsByUsername(String username) {
        Cursor cursor = database.rawQuery("select itemId, reviewtext, rating from reviews where userId=? order by time", new String[]{String.valueOf(UserDao.getUserIdByUsername(username))});
        List<Review> reviewList = null;
        if (cursor.getCount() > 0) {
            reviewList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Review review = new Review();
                review.itemId = cursor.getInt(cursor.getColumnIndex(ITEMID));
                review.reviewText = cursor.getString(cursor.getColumnIndex(REVIEWTEXT));
                review.rating = cursor.getInt(cursor.getColumnIndex(RATING));
                reviewList.add(review);
            }
        }
        return reviewList;
    }
}
