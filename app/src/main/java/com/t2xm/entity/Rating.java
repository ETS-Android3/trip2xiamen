package com.t2xm.entity;

import androidx.annotation.Nullable;

public class Rating {
    public Integer ratingId;
    public Integer userId;
    public Integer itemId;
    public Integer rate;

    public Rating() {
    }

    public Rating(@Nullable Integer ratingId, Integer userId, Integer itemId, Integer rate) {
        this.ratingId = ratingId;
        this.userId = userId;
        this.itemId = itemId;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingId=" + ratingId +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", rate=" + rate +
                '}';
    }
}
