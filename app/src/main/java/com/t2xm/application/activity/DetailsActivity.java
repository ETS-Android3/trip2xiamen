package com.t2xm.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.t2xm.R;
import com.t2xm.dao.ItemDao;
import com.t2xm.dao.ReviewDao;
import com.t2xm.entity.Item;
import com.t2xm.entity.Review;
import com.t2xm.utils.JsonUtil;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private Integer itemId;

    private ImageView iv_itemImage;
    private TextView tv_itemName;
    private TextView tv_itemDescription;
    private ImageButton btn_location;
    private Button btn_writeReview;
    private ImageButton btn_addToFavourite;

    private Item item;
    private List<String> item_imageList;
    private List<Review> item_reviewList;

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

        //TODO setup reviews
    }

    private void setupOnClickListeners() {
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO set up location
                Intent intent = new Intent(getApplicationContext(), LocationActivity.class);
                intent.putExtra("longitude", item.longitude);
                intent.putExtra("latitude", item.latitude);
                startActivity(intent);
            }
        });

        //TODO set up direct to review page
        btn_writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                intent.putExtra("itemId", itemId);
                startActivity(intent);
            }
        });

        btn_addToFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO set up add to favourite items
            }
        });
    }
}