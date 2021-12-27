package com.t2xm.application.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.t2xm.R;
import com.t2xm.dao.UserDao;
import com.t2xm.entity.User;
import com.t2xm.utils.ToastUtil;
import com.t2xm.utils.ValidationUtil;

import org.w3c.dom.Text;

public class SignUpActivity extends AppCompatActivity {

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
        //TODO provide errors
        if(username.equals("")) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Please provide your username");
            return false;
        }
        if(email.equals("")) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Please provide your email");
            return false;
        }
        if(password.equals("")) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Please provide your password");
            return false;
        }
        if(confirmPassword.equals("")) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Please confirm your password");
            return false;
        }
        if (ValidationUtil.validateUsername(username) == false) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Please provide valid username");
            return false;
        }
        if(ValidationUtil.validateEmail(email) == false) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Please provide valid email");
            return false;
        }
        if(ValidationUtil.validatePassword(password) == false) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Please provide valid password");
            return false;
        }
        if(UserDao.checkUsernameExistence(username) == true) {
            ToastUtil.createAndShowToast(getApplicationContext(), "An account with the provided username already exist, please try other username");
            return false;
        }
        if(UserDao.checkEmailExistence(email) == true) {
            ToastUtil.createAndShowToast(getApplicationContext(), "An account with the provided email already exist, please try other email");
            return false;
        }
        return true;
    }

    private void updateEditTextValues() {
        username = String.valueOf(et_username.getText());
        email = String.valueOf(et_email.getText());
        password = String.valueOf(et_password.getText());
        confirmPassword = String.valueOf(et_confirmPassword.getText());
    }

    private void setupActivityComponents() {
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
                if(validateSignUpInput() == true) {
                    if(password.equals(confirmPassword) == false) {
                        ToastUtil.createAndShowToast(getApplicationContext(), "The provided password and confirm password do not match");
                    }
                    else {
                        User user = new User(null, username, email, password, null);
                        boolean result = UserDao.insertUser(user) ;
                        if(result == true) {
                            ToastUtil.createAndShowToast(getApplicationContext(), "Sign up successful");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    onBackPressed(); //go back to login page
                                    finish();
                                }
                            }, 1000);
                        }
                        else {
                            ToastUtil.createAndShowToast(getApplicationContext(), "Sign up failed");
                        }
                    }
                }
            }
        });
    }
}