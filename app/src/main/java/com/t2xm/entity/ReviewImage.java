package com.t2xm.entity;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class ReviewImage {
    public Integer imageId;
    public Integer reviewId;
    public byte[] reviewImage;

    public ReviewImage() {
    }

    public ReviewImage(@Nullable Integer imageId, Integer reviewId, byte[] reviewImage) {
        this.imageId = imageId;
        this.reviewId = reviewId;
        this.reviewImage = reviewImage;
    }

    @Override
    public String toString() {
        return "ReviewImage{" +
                "imageId=" + imageId +
                ", reviewId=" + reviewId +
                ", reviewImage=" + Arrays.toString(reviewImage) +
                '}';
    }
}
