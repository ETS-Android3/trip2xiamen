package com.t2xm.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.t2xm.R;
import com.t2xm.dao.ItemDao;
import com.t2xm.dao.ReviewDao;
import com.t2xm.entity.Item;
import com.t2xm.entity.Review;
import com.t2xm.utils.valuesConverter.JsonUtil;
import com.t2xm.utils.adapter.ReviewAdapter;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private Integer itemId;

    private ImageView iv_itemImage;
    private TextView tv_itemName;
    private TextView tv_itemDescription;
    private ImageButton btn_location;
    private Button btn_writeReview;
    private ImageButton btn_addToFavourite;
    private RecyclerView rv_reviews;
    private LinearLayout ll_noReviewsContainer;
    private Button btn_writeReview2;

    private Item item;
    private List<String> item_imageList;
    private List<Review> item_reviewList;

    private View.OnClickListener viewMapLocationListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), LocationActivity.class);
            intent.putExtra("longitude", item.longitude);
            intent.putExtra("latitude", item.latitude);
            startActivity(intent);
        }
    };

    private View.OnClickListener writeReviewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
            intent.putExtra("itemId", itemId);
            startActivity(intent);
        }
    };

    private View.OnClickListener addToFavouriteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO set up add to favourite items
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        iv_itemImage = findViewById(R.id.iv_item_image);
        tv_itemName = findViewById(R.id.tv_item_name);
        tv_itemDescription = findViewById(R.id.tv_item_description);
        btn_location = findViewById(R.id.btn_location);
        btn_writeReview = findViewById(R.id.btn_write_review);
        btn_addToFavourite = findViewById(R.id.btn_add_to_favourite);
        rv_reviews = findViewById(R.id.rv_reviews);
        ll_noReviewsContainer = findViewById(R.id.ll_no_reviews_container);
        btn_writeReview2 = findViewById(R.id.btn_write_review_2);

        itemId = getIntent().getIntExtra("itemId", 0);
        if (itemId <= 0) {
            //TODO display error
            onBackPressed();
            return;
        }

        item = ItemDao.getItemByItemId(itemId);
        if (item == null) {
            //TODO display error if no item found
            onBackPressed();
            return;
        }
        try {
            item_imageList = JsonUtil.mapJsonToObject(item.image, List.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        item_reviewList = ReviewDao.getReviewListByItemId(itemId);

        updateItemInformation();
        setupOnClickListeners();
    }

    private void updateItemInformation() {
        int resource = getResources().getIdentifier(item_imageList.get(0), "drawable", getPackageName());
        iv_itemImage.setImageResource(resource);
        tv_itemName.setText(item.itemName);
        tv_itemDescription.setText(item.description);

        if (item_reviewList != null) {
            rv_reviews.setVisibility(View.VISIBLE);
            ll_noReviewsContainer.setVisibility(View.GONE);
            ReviewAdapter adapter = new ReviewAdapter(getApplicationContext(), item_reviewList);
            rv_reviews.setAdapter(adapter);
            rv_reviews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        } else {
            rv_reviews.setVisibility(View.GONE);
            ll_noReviewsContainer.setVisibility(View.VISIBLE);
        }
    }

    private void setupOnClickListeners() {
        btn_location.setOnClickListener(viewMapLocationListener);
        btn_writeReview.setOnClickListener(writeReviewListener);
        btn_addToFavourite.setOnClickListener(addToFavouriteListener);
        btn_writeReview2.setOnClickListener(writeReviewListener);
    }
}