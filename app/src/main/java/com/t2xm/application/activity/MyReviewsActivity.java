package com.t2xm.application.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
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
import com.t2xm.utils.values.RequestCode;

import java.util.ArrayList;
import java.util.List;

public class MyReviewsActivity extends AppCompatActivity {

    private Activity activity;

    private MyReviewsAdapter adapter;
    private RecyclerView rv_myReviews;
    private RelativeLayout rl_noReviews;

    private List<Review> reviewList;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);

        activity = this;

        rv_myReviews = findViewById(R.id.rv_my_reviews);
        rl_noReviews = findViewById(R.id.rl_no_reviews);
        updateRecyclerView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RequestCode.VIEW_REVIEWED_ITEM) {
            if(resultCode == RESULT_OK) {
                updateRecyclerView();
            }
        }
    }

    public void updateRecyclerView() {
        reviewList = ReviewDao.getReviewsByUsername(SharedPreferenceUtil.getUsername(activity));
        if (reviewList != null) {
            rv_myReviews.setVisibility(View.VISIBLE);
            rl_noReviews.setVisibility(View.GONE);
            itemList = new ArrayList<>();
            for (int i = 0; i < reviewList.size(); i++) {
                itemList.add(ItemDao.getItemByItemId(reviewList.get(i).itemId));
            }
            adapter = new MyReviewsAdapter(activity, reviewList, itemList);
            rv_myReviews.setAdapter(adapter);
            rv_myReviews.setLayoutManager(new LinearLayoutManager(activity));
        } else {
            rv_myReviews.setVisibility(View.GONE);
            rl_noReviews.setVisibility(View.VISIBLE);
        }
    }
}