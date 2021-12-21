package com.t2xm.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.t2xm.R;

public class StartActivity extends AppCompatActivity {

    //TODO change delay time
    private final long DELAY_TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO change activity to start to Login Activity
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }, DELAY_TIME);
    }
}