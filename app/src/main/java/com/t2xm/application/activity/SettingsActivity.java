package com.t2xm.application.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.t2xm.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.rl_user_profile_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO upload image from gallery or camera
            }
        });

        findViewById(R.id.btn_change_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO change password activity
            }
        });

        findViewById(R.id.btn_change_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO change email activity
            }
        });

        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO logout
            }
        });

        findViewById(R.id.btn_delete_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO delete account
            }
        });
    }
}