package com.t2xm.application.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.t2xm.R;
import com.t2xm.dao.UserDao;
import com.t2xm.utils.SharedPreferenceUtil;
import com.t2xm.utils.ToastUtil;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText et_username;
    private TextInputEditText et_password;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupActivityComponents();
        setupActivityListeners();
    }

    private boolean validateLoginInput() {
        //TODO prompt error
        if (username.trim().equals("")) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Please enter your username");
            return false;
        }
        if (password.trim().equals("")) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Please enter your password");
            return false;
        }
        return true;
    }

    private void updateUsernameAndPassword() {
        username = String.valueOf(et_username.getText());
        password = String.valueOf(et_password.getText());
    }

    private void setupActivityComponents() {
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
    }

    private void setupActivityListeners() {
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUsernameAndPassword();
                if (validateLoginInput() == true) {
                    if (UserDao.checkUsernameExistence(username) == false) {
                        ToastUtil.createAndShowToast(getApplicationContext(), "Account with the provided username does not exist");
                    } else {
                        if (UserDao.validateUserAccount(username, password) == true) {
                            ToastUtil.createAndShowToast(getApplicationContext(), "Login successful");
                            SharedPreferenceUtil.setUsername(username, getApplicationContext());
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                            }, 1000);

                        } else {
                            ToastUtil.createAndShowToast(getApplicationContext(), "Wrong username or password");
                        }
                    }
                }
            }
        });

        findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        AppCompatActivity activity = this;
        new AlertDialog.Builder(activity)
                .setTitle("Exit the application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //close application
                        activity.finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .create().show();
    }
}