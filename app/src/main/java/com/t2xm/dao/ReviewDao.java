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
    private static final String RECOMMEND = "recommend";
    private static final String TIME = "time";

    public static long insertReviewAndGetReviewId(Review review) {
        ContentValues cv = new ContentValues();
        cv.put(USERID, review.userId);
        cv.put(ITEMID, review.itemId);
        cv.put(RATING, review.rating);
        cv.put(REVIEWTEXT, review.reviewText);
        cv.put(RECOMMEND, review.recommend == true ? 1 : 0);
        cv.put(TIME, review.time);
        long result = database.insert(TABLE, null, cv);
        ItemDao.updateItemAvgRating(review.itemId);
        return result;
    }

    public static boolean insertReview(Review review) {
        return insertReviewAndGetReviewId(review) > 0;
    }

    @SuppressLint("Range")
    public static List<Review> getReviewListByItemId(Integer itemId) {
        Cursor cursor = database.rawQuery("select reviewId, userId, reviewtext, rating from reviews where " +
                "itemId=?", new String[]{String.valueOf(itemId)});
        List<Review> reviewList = null;
        if (cursor.getCount() > 0) {
            reviewList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Review review = new Review();
                review.reviewId = cursor.getInt(cursor.getColumnIndex(REVIEWID));
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
        Cursor cursor = database.rawQuery("select userId, itemId, reviewtext, rating from reviews order by time desc limit 5", null);
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
        Integer itemId = getItemIdByReviewId(reviewId);
        boolean result = database.delete(TABLE, "reviewId=?", new String[]{String.valueOf(reviewId)}) > 0;
        ItemDao.updateItemAvgRating(itemId);
        return result;
    }

    public static Integer getItemIdByReviewId(Integer reviewId) {
        Cursor cursor = database.rawQuery("select itemId from reviews where reviewId=?", new String[]{String.valueOf(reviewId)});
        return cursor.moveToNext() ? cursor.getInt(0) : 0;
    }

    @SuppressLint("Range")
    public static List<Review> getReviewsByUsername(String username) {
        Cursor cursor = database.rawQuery("select reviewId, itemId, reviewtext, rating from reviews where userId=? order by time", new String[]{String.valueOf(UserDao.getUserIdByUsername(username))});
        List<Review> reviewList = null;
        if (cursor.getCount() > 0) {
            reviewList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Review review = new Review();
                review.reviewId = cursor.getInt(cursor.getColumnIndex(REVIEWID));
                review.itemId = cursor.getInt(cursor.getColumnIndex(ITEMID));
                review.reviewText = cursor.getString(cursor.getColumnIndex(REVIEWTEXT));
                review.rating = cursor.getInt(cursor.getColumnIndex(RATING));
                reviewList.add(review);
            }
        }
        return reviewList;
    }

    public static Double getAvgRatingByItemId(Integer itemId) {
        Cursor cursor = database.rawQuery("select avg(rating) from reviews where itemId=?",
                new String[]{String.valueOf(itemId)});
        return cursor.moveToNext() ? cursor.getDouble(0) : 0d;
    }
}
