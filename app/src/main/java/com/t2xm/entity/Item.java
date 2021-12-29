package com.t2xm.entity;

import androidx.annotation.Nullable;

public class Item {
    public Integer itemId;
    public String itemName;
    public Integer category;
    public String description;
    public String phoneNumber;
    public String officialWebsite;
    public String travelInformationWebsite;
    public String image;
    public Double avgRating;
    public Double longitude;
    public Double latitude;

    public Item() {
    }

    public Item(@Nullable Integer itemId, String itemName, Integer category, String description, String phoneNumber, String officialWebsite, String travelInformationWebsite, String image, Double avgRating, Double longitude, Double latitude) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.officialWebsite = officialWebsite;
        this.travelInformationWebsite = travelInformationWebsite;
        this.image = image;
        this.avgRating = avgRating;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", officialWebsite='" + officialWebsite + '\'' +
                ", travelInformationWebsite='" + travelInformationWebsite + '\'' +
                ", image='" + image + '\'' +
                ", avgRating=" + avgRating +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
