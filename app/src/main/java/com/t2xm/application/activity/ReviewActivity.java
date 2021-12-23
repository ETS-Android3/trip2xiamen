package com.t2xm.application.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.t2xm.R;
import com.t2xm.dao.ItemDao;
import com.t2xm.entity.Item;
import com.t2xm.utils.JsonUtil;

import org.w3c.dom.Text;

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

        itemId = getIntent().getIntExtra("itemId", 0);
        if(itemId <= 0) {
            //TODO error
        }

        item = ItemDao.getItemByItemId(itemId) ;
        if(item == null) {
            //TODO error
        }
        try {
            iv_itemImage.setImageResource(getResources().getIdentifier(String.valueOf(JsonUtil.mapJsonToObject(item.image, List.class).get(0)), "drawable", getPackageName()));
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