package com.t2xm.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.t2xm.R;
import com.t2xm.utils.DatabaseOpenHelper;

public class StartActivity extends AppCompatActivity {

    //TODO change delay time
    private final long DELAY_TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //open database
        DatabaseOpenHelper.getDatabase(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        }, DELAY_TIME);
    }
}