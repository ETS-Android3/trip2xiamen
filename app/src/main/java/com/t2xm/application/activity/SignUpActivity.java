package com.t2xm.application.activity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.t2xm.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_email;
    private EditText et_password;
    private EditText et_confirmPassword;

    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirmPassword = findViewById(R.id.et_confirmPassword);

        findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO sign up
            }
        });
    }

    private boolean validateSignUpInput() {
        //TODo validate input
        return false;
    }

    private void updateEditTextValues() {
        username = String.valueOf(et_username.getText());
        email = String.valueOf(et_email.getText());
        password = String.valueOf(et_password.getText());
        confirmPassword = String.valueOf(et_confirmPassword.getText());
    }
}