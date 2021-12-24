package com.t2xm.application.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.t2xm.R;
import com.t2xm.dao.ItemDao;
import com.t2xm.entity.Item;
import com.t2xm.utils.JsonUtil;
import com.t2xm.utils.ToastUtil;

import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private Integer itemId;

    private ImageView iv_itemImage;
    private TextView tv_itemName;
    private TextView tv_reviewCharacterCount;
    private EditText et_reviewText;
    private RecyclerView rv_uploadImage;
    private CheckBox cb_recommend;
    private Button btn_submitReview;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        iv_itemImage = findViewById(R.id.iv_item_image);
        tv_itemName = findViewById(R.id.tv_item_name);
        tv_reviewCharacterCount = findViewById(R.id.tv_review_character_count);
        et_reviewText = findViewById(R.id.et_review_text);
        rv_uploadImage = findViewById(R.id.rv_reviews);
        cb_recommend = findViewById(R.id.cb_recommend);
        btn_submitReview = findViewById(R.id.btn_submit_review);

        itemId = getIntent().getIntExtra("itemId", 0);
        if (itemId <= 0) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Error");
            onBackPressed();
            return;
        }

        item = ItemDao.getItemByItemId(itemId);
        if (item == null) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Error");
            onBackPressed();
            return;
        }
        try {
            String img = String.valueOf(JsonUtil.mapJsonToObject(item.image, List.class).get(0));
            int resource = getResources().getIdentifier(img, "drawable", getPackageName());
            iv_itemImage.setImageResource(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_itemName.setText(item.itemName);

        btn_submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO submit review
            }
        });
    }
}