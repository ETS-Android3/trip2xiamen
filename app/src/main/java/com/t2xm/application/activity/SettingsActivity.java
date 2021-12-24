package com.t2xm.application.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.t2xm.R;
import com.t2xm.utils.SharedPreferenceUtil;

public class SettingsActivity extends AppCompatActivity {

    private DialogInterface.OnClickListener logoutOnClickListener;
    private DialogInterface.OnClickListener deleteAccountOnClickListener;
    private AlertDialog.Builder logoutBuilder;
    private AlertDialog.Builder deleteAccountBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        logoutOnClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO logout
            }
        };
        deleteAccountOnClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO delete account
            }
        };
        logoutBuilder = new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Confirm to logout your account?")
                .setPositiveButton("Yes, Logout", logoutOnClickListener)
                .setNegativeButton("No", null);
        deleteAccountBuilder = new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Confirm to delete your account?")
                .setPositiveButton("Yes, Delete", deleteAccountOnClickListener)
                .setNegativeButton("No", null);

        String username = SharedPreferenceUtil.getUsername(getApplicationContext());
        ((TextView) findViewById(R.id.tv_username)).setText(username);

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
                logoutBuilder.create().show();
            }
        });

        findViewById(R.id.btn_delete_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccountBuilder.create().show();
            }
        });
    }
}