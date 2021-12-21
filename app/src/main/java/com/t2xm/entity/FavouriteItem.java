package com.t2xm.entity;

import androidx.annotation.Nullable;

public class FavouriteItem {
    public Integer favouriteId;
    public Integer userId;
    public Integer itemId;

    public FavouriteItem() {
    }

    public FavouriteItem(@Nullable Integer favouriteId, Integer userId, Integer itemId) {
        this.favouriteId = favouriteId;
        this.userId = userId;
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "FavouriteItem{" +
                "favouriteId=" + favouriteId +
                ", userId=" + userId +
                ", itemId=" + itemId +
                '}';
    }
}
