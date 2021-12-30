package com.t2xm.application.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.t2xm.R;
import com.t2xm.dao.UserDao;
import com.t2xm.utils.SharedPreferenceUtil;
import com.t2xm.utils.ToastUtil;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout lay_username;
    private TextInputLayout lay_password;
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
        boolean valid = true;
        if (username.trim().equals("")) {
            lay_username.setError(getString(R.string.error_username_empty));
            valid = valid && false;
        } else {
            lay_username.setError(getString(R.string.no_error));
        }

        if (password.trim().equals("")) {
            lay_password.setError(getString(R.string.error_password_empty));
            valid = valid && false;
        } else {
            lay_password.setError(getString(R.string.no_error));
        }
        return valid;
    }

    private void updateUsernameAndPassword() {
        username = String.valueOf(et_username.getText());
        password = String.valueOf(et_password.getText());
    }

    private void setupActivityComponents() {
        lay_username = findViewById(R.id.lay_username);
        lay_password = findViewById(R.id.lay_password);
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
                        lay_username.setError(getString(R.string.error_username_not_exist));
                    } else {
                        lay_username.setError(getString(R.string.no_error));
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
                            lay_username.setError(getString(R.string.error_wrong_username));
                            lay_password.setError(getString(R.string.error_wrong_password));
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