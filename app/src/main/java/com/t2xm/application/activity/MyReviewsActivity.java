package com.t2xm.application.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.t2xm.R;
import com.t2xm.dao.ItemDao;
import com.t2xm.dao.ReviewDao;
import com.t2xm.entity.Item;
import com.t2xm.entity.Review;
import com.t2xm.utils.SharedPreferenceUtil;
import com.t2xm.utils.adapter.MyReviewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyReviewsActivity extends AppCompatActivity {

    private MyReviewsAdapter adapter;
    private RecyclerView rv_myReviews;

    //TODO test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);

        rv_myReviews = findViewById(R.id.rv_my_reviews);

        List<Review> reviewList = ReviewDao.getReviewsByUsername(SharedPreferenceUtil.getUsername(getApplicationContext()));
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < reviewList.size(); i++) {
            itemList.add(ItemDao.getItemByItemId(reviewList.get(i).itemId));
        }
        adapter = new MyReviewsAdapter(getApplicationContext(), reviewList, itemList);
        rv_myReviews.setAdapter(adapter);
        rv_myReviews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}