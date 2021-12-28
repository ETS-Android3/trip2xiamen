package com.t2xm.application.activity;

import android.app.Activity;
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

    private Activity activity;

    private MyReviewsAdapter adapter;
    private RecyclerView rv_myReviews;

    private List<Review> reviewList;
    private List<Item> itemList;

    //TODO test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);

        activity = this;

        rv_myReviews = findViewById(R.id.rv_my_reviews);
        updateRecyclerView();
    }

    public void updateRecyclerView() {
        reviewList = ReviewDao.getReviewsByUsername(SharedPreferenceUtil.getUsername(activity));
        if (reviewList != null) {
            itemList = new ArrayList<>();
            for (int i = 0; i < reviewList.size(); i++) {
                itemList.add(ItemDao.getItemByItemId(reviewList.get(i).itemId));
            }
            adapter = new MyReviewsAdapter(activity, reviewList, itemList);
            rv_myReviews.setAdapter(adapter);
            rv_myReviews.setLayoutManager(new LinearLayoutManager(activity));
        }
        else {
            //TODO display no items screen
        }
    }
}