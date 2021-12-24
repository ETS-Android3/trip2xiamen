package com.t2xm.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.t2xm.R;
import com.t2xm.utils.ToastUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO login function
                //validate input
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO direct to signup page
            }
        });
    }

    private boolean validateLoginInput() {
        if(username.trim().equals("")) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Please enter your username");
            return false;
        }
        if(password.trim().equals("")) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Please enter your password");
        }
        return true;
    }

    private void updateUsernameAndPassword() {
        username = String.valueOf(et_username.getText());
        password = String.valueOf(et_password.getText());
    }
}