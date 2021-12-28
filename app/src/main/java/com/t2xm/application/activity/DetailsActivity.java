package com.t2xm.application.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.t2xm.R;
import com.t2xm.dao.FavouriteItemDao;
import com.t2xm.dao.ItemDao;
import com.t2xm.dao.ReviewDao;
import com.t2xm.entity.Item;
import com.t2xm.entity.Review;
import com.t2xm.utils.PermissionUtil;
import com.t2xm.utils.SharedPreferenceUtil;
import com.t2xm.utils.ToastUtil;
import com.t2xm.utils.adapter.ReviewAdapter;
import com.t2xm.utils.values.PermissionString;
import com.t2xm.utils.values.RequestCode;
import com.t2xm.utils.valuesConverter.JsonUtil;
import com.t2xm.utils.valuesConverter.NumberFormatUtil;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private Activity activity;

    private Integer itemId;
    private String username;

    private ImageView iv_itemImage;
    private TextView tv_itemName;
    private TextView tv_itemDescription;
    private ImageButton btn_location;
    private ImageButton btn_phone;
    private List<ImageView> iv_starList;
    private TextView tv_itemRating;
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
            intent.putExtra("locationName", item.itemName);
            try {
                intent.putExtra("image", (String) JsonUtil.mapJsonToObject(item.image, List.class).get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }
    };

    private View.OnClickListener phoneCallListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(PermissionUtil.phoneCallPermissionGranted(activity)) {
                startCallPhoneActivity();
            }
            else {
                PermissionUtil.grantCallPhonePermission(activity);
            }
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
        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View view) {
            if (FavouriteItemDao.getIsInUserFavouriteItem(username, itemId)) {
                if (FavouriteItemDao.deleteFavouriteItem(username, itemId)) {
                    btn_addToFavourite.setColorFilter(getColor(R.color.gray));
                    ToastUtil.createAndShowToast(getApplicationContext(), item.itemName + " has been removed from your favourite list");
                }
            } else {
                if (FavouriteItemDao.insertFavouriteItem(username, itemId)) {
                    btn_addToFavourite.setColorFilter(getColor(R.color.red));
                    ToastUtil.createAndShowToast(getApplicationContext(), item.itemName + " has been added to your favourite list");
                }
            }
        }
    };

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        activity = this;

        setupActivityComponents();
        setupOnClickListeners();

        username = SharedPreferenceUtil.getUsername(getApplicationContext());

        //get itemId from intent
        itemId = getIntent().getIntExtra("itemId", 0);
        if (itemId <= 0) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Error: Please try again");
            onBackPressed();
            return;
        }

        //get item
        item = ItemDao.getItemByItemId(itemId);
        if (item == null) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Error: Please try again");
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

        //update favourite button
        if (FavouriteItemDao.getIsInUserFavouriteItem(SharedPreferenceUtil.getUsername(getApplicationContext()), itemId)) {
            btn_addToFavourite.setColorFilter(getColor(R.color.red));
        } else {
            btn_addToFavourite.setColorFilter(getColor(R.color.gray));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == RequestCode.CALL_PHONE_PERMISSION) {
            startCallPhoneActivity();
        }
    }

    private void startCallPhoneActivity() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "123"));
        startActivity(intent);
    }

    private void updateItemInformation() {
        int resource = getResources().getIdentifier(item_imageList.get(0), "drawable", getPackageName());
        iv_itemImage.setImageResource(resource);
        tv_itemName.setText(item.itemName);
        tv_itemRating.setText(String.valueOf(NumberFormatUtil.get2dpDouble(item.avgRating)));
        updateRatingStars(NumberFormatUtil.get2dpDouble(item.avgRating));
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
        btn_phone.setOnClickListener(phoneCallListener);
        btn_writeReview.setOnClickListener(writeReviewListener);
        btn_addToFavourite.setOnClickListener(addToFavouriteListener);
        btn_writeReview2.setOnClickListener(writeReviewListener);
    }

    private void setupActivityComponents() {
        iv_itemImage = findViewById(R.id.iv_item_image);
        tv_itemName = findViewById(R.id.tv_item_name);
        tv_itemDescription = findViewById(R.id.tv_item_description);
        btn_location = findViewById(R.id.btn_location);
        btn_phone = findViewById(R.id.btn_phone);
        tv_itemRating = findViewById(R.id.tv_item_rating);
        btn_writeReview = findViewById(R.id.btn_write_review);
        btn_addToFavourite = findViewById(R.id.btn_add_to_favourite);
        rv_reviews = findViewById(R.id.rv_reviews);
        ll_noReviewsContainer = findViewById(R.id.ll_no_reviews_container);
        btn_writeReview2 = findViewById(R.id.btn_write_review_2);

        iv_starList = new ArrayList<>();
        iv_starList.add(findViewById(R.id.iv_star_1));
        iv_starList.add(findViewById(R.id.iv_star_2));
        iv_starList.add(findViewById(R.id.iv_star_3));
        iv_starList.add(findViewById(R.id.iv_star_4));
        iv_starList.add(findViewById(R.id.iv_star_5));
    }

    private void updateRatingStars(Double rating) {
        int index = 0;
        while (index < 5) {
            if (rating >= 1) {
                iv_starList.get(index).setImageResource(R.drawable.ic_baseline_star_24);
                rating -= 1;
            } else if (rating >= 0.5) {
                iv_starList.get(index).setImageResource(R.drawable.ic_baseline_star_half_24);
                rating = Math.floor(rating);
            } else {
                iv_starList.get(index).setImageResource(R.drawable.ic_baseline_star_border_24);
                rating = Math.floor(rating);
            }
            index++;
        }
    }
}