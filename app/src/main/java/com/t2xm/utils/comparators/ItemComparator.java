package com.t2xm.utils.comparators;

import com.t2xm.entity.Item;

import java.util.Comparator;

public class ItemComparator {
    public static Comparator alphabetAsc = new sortItemAlphabeticalAsc();
    public static Comparator ratingAsc = new sortItemRatingAsc();
}

class sortItemAlphabeticalAsc implements Comparator<Item> {
    @Override
    public int compare(Item item1, Item item2) {
        return item1.itemName.compareToIgnoreCase(item2.itemName);
    }
}

class sortItemRatingAsc implements Comparator<Item> {
    @Override
    public int compare(Item item1, Item item2) {
        return 0;
    }
}