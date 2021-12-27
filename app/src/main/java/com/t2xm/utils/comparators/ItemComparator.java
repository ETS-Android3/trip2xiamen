package com.t2xm.utils.comparators;

import com.t2xm.entity.Item;

import java.util.Comparator;

public class ItemComparator {
    public static Comparator alphabetAsc = new SortItemAlphabeticalAsc();
    public static Comparator ratingAsc = new SortItemRatingAsc();
}

class SortItemAlphabeticalAsc implements Comparator<Item> {
    @Override
    public int compare(Item item1, Item item2) {
        return item1.itemName.compareToIgnoreCase(item2.itemName);
    }
}

class SortItemRatingAsc implements Comparator<Item> {
    @Override
    public int compare(Item item1, Item item2) {
        return item1.avgRating.compareTo(item2.avgRating);
    }
}