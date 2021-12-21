package com.t2xm.entity;

import androidx.annotation.Nullable;

public class Review {
    public Integer reviewId;
    public Integer itemId;
    public Integer userId;
    public String reviewText;
    public Integer rating;
    public Long time;

    public Review() {
    }

    public Review(@Nullable Integer reviewId, Integer itemId, Integer userId, String reviewText, Integer rating,
                  Long time) {
        this.reviewId = reviewId;
        this.itemId = itemId;
        this.userId = userId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", itemId=" + itemId +
                ", userId=" + userId +
                ", reviewText='" + reviewText + '\'' +
                ", rating=" + rating +
                ", time=" + time +
                '}';
    }
}
