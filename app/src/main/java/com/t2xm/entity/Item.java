package com.t2xm.entity;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class Item {
    public Integer itemId;
    public String itemName;
    public Integer category;
    public String description;
    public byte[] image;
    public Double avgRating;
    public Double longitude;
    public Double latitude;

    public Item() {
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", image=" + Arrays.toString(image) +
                ", avgRating=" + avgRating +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public Item(Integer itemId, String itemName, Integer category, String description, byte[] image, Double avgRating, Double longitude, Double latitude) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.image = image;
        this.avgRating = avgRating;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
