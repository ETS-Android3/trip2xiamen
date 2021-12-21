package com.t2xm.entity;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class Item {
    public Integer itemId;
    public Integer category;
    public String description;
    public byte[] image;
    public double avgRating;
    public double longitude;
    public double latitude;

    public Item() {
    }

    public Item(@Nullable Integer itemId, Integer category, String description, byte[] image, double avgRating, double longitude, double latitude) {
        this.itemId = itemId;
        this.category = category;
        this.description = description;
        this.image = image;
        this.avgRating = avgRating;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", image=" + Arrays.toString(image) +
                ", avgRating=" + avgRating +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
