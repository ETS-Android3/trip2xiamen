package com.t2xm.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.t2xm.R;
import com.t2xm.dao.ItemDao;
import com.t2xm.entity.Item;
import com.t2xm.utils.DatabaseOpenHelper;
import com.t2xm.utils.ItemXmlParser;
import com.t2xm.utils.SharedPreferenceUtil;

public class StartActivity extends AppCompatActivity {

    //TODO change delay time
    private final long DELAY_TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //open database
        DatabaseOpenHelper.getDatabase(getApplicationContext());

        if (SharedPreferenceUtil.getIsFirstStartup(this)) {
            boolean result = false;
            try {
                for (Item item : ItemXmlParser.parseItems(getResources().getXml(R.xml.items))) {
                    result = ItemDao.insertItem(item);
                }
                SharedPreferenceUtil.setIsFirstStartup(false, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPreferenceUtil.hasLoggedIn(getApplicationContext()) == true) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        }, DELAY_TIME);
    }
}