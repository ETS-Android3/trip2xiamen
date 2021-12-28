package com.t2xm.application.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.t2xm.R;
import com.t2xm.dao.UserDao;
import com.t2xm.entity.User;
import com.t2xm.utils.ToastUtil;
import com.t2xm.utils.ValidationUtil;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout lay_username;
    private TextInputLayout lay_email;
    private TextInputLayout lay_password;
    private TextInputLayout lay_confirmPassword;

    private TextInputEditText et_username;
    private TextInputEditText et_email;
    private TextInputEditText et_password;
    private TextInputEditText et_confirmPassword;

    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setupActivityComponents();
        setupActivityListeners();
    }

    private boolean validateSignUpInput() {
        boolean valid = true;

        // validate username
        if (username.equals("")) {
            lay_username.setError(getString(R.string.error_username_empty));
            valid = valid && false;
        }
        else if (ValidationUtil.validateUsername(username) == false) {
            lay_username.setError(getString(R.string.username_format));
            valid = valid && false;
        }
        else if (UserDao.checkUsernameExistence(username) == true) {
            lay_username.setError(getString(R.string.error_username_exist));
            valid = valid && false;
        }
        else {
            lay_username.setError(getString(R.string.no_error));
        }

        //validate email
        if (email.equals("")) {
            lay_email.setError(getString(R.string.error_email_empty));
            valid = valid && false;
        }
        else if (ValidationUtil.validateEmail(email) == false) {
            lay_email.setError(getString(R.string.email_format));
            valid = valid && false;
        }
        else if (UserDao.checkEmailExistence(email) == true) {
            lay_email.setError(getString(R.string.error_email_exist));
            valid = valid && false;
        }
        else {
            lay_email.setError(getString(R.string.no_error));
        }

        //validate password
        if (password.equals("")) {
            lay_password.setError(getString(R.string.error_password_empty));
            valid = valid && false;
        }
        else if (ValidationUtil.validatePassword(password) == false) {
            lay_password.setError(getString(R.string.password_format));
            valid = valid && false;
        }
        else {
            lay_password.setError(getString(R.string.no_error));
        }

        //validate confirm password
        if (confirmPassword.equals("")) {
            lay_confirmPassword.setError(getString(R.string.error_confirm_password_empty));
            valid = valid && false;
        }
        else if(!confirmPassword.equals(password)) {
            lay_confirmPassword.setError(getString(R.string.error_password_not_match));
            valid = valid && false;
        }
        else {
            lay_confirmPassword.setError(getString(R.string.no_error));
        }

        return valid;
    }

    private void updateEditTextValues() {
        username = String.valueOf(et_username.getText());
        email = String.valueOf(et_email.getText());
        password = String.valueOf(et_password.getText());
        confirmPassword = String.valueOf(et_confirmPassword.getText());
    }

    private void setupActivityComponents() {
        lay_username = findViewById(R.id.lay_username);
        lay_email = findViewById(R.id.lay_email);
        lay_password = findViewById(R.id.lay_password);
        lay_confirmPassword = findViewById(R.id.lay_confirm_password);

        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirmPassword = findViewById(R.id.et_confirm_password);
    }

    private void setupActivityListeners() {
        findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEditTextValues();
                if (validateSignUpInput() == true) {
                    if (password.equals(confirmPassword) == false) {
                        ToastUtil.createAndShowToast(getApplicationContext(), "The provided password and confirm password do not match");
                    } else {
                        User user = new User(null, username, email, password, null);
                        boolean result = UserDao.insertUser(user);
                        if (result == true) {
                            ToastUtil.createAndShowToast(getApplicationContext(), "Sign up successful");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    onBackPressed(); //go back to login page
                                    finish();
                                }
                            }, 1000);
                        } else {
                            ToastUtil.createAndShowToast(getApplicationContext(), "Sign up failed");
                        }
                    }
                }
            }
        });
    }
}