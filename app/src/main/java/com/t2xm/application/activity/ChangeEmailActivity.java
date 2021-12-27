package com.t2xm.application.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.t2xm.R;
import com.t2xm.dao.UserDao;
import com.t2xm.utils.SharedPreferenceUtil;
import com.t2xm.utils.ToastUtil;
import com.t2xm.utils.ValidationUtil;

public class ChangeEmailActivity extends AppCompatActivity {

    private String username;

    private TextInputEditText et_currentEmail;
    private TextInputEditText et_newEmail;
    private TextInputEditText et_confirmEmail;

    private String currentEmail;
    private String newEmail;
    private String confirmEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        username = SharedPreferenceUtil.getUsername(getApplicationContext());

        setupActivityComponents();
        setupActivityListeners();
    }

    private boolean validateEmail() {
        if (ValidationUtil.validateEmail(newEmail)) {
            if (newEmail.equals(confirmEmail)) {
                return true;
            }
            ToastUtil.createToast(this, "New email and confirm email do not match");
            return false;
        }
        ToastUtil.createToast(this, "The email does not fulfil email format");
        return false;
    }

    private void updateInputFields() {
        currentEmail = String.valueOf(et_currentEmail.getText());
        newEmail = String.valueOf(et_newEmail.getText());
        confirmEmail = String.valueOf(et_confirmEmail.getText());
    }

    private void setupActivityComponents() {
        et_currentEmail = findViewById(R.id.et_current_email);
        et_newEmail = findViewById(R.id.et_new_email);
        et_confirmEmail = findViewById(R.id.et_confirm_email);
    }

    private void setupActivityListeners() {
        findViewById(R.id.btn_confirm_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO test
                updateInputFields();
                if (currentEmail.equals(UserDao.getUserEmailByUsername(username))) {
                    if (UserDao.checkEmailExistence(newEmail)) {
                        ToastUtil.createAndShowToast(getApplicationContext(), "New email already exist, please try other email");
                    } else {
                        boolean result = false;
                        if (validateEmail()) {
                            result = UserDao.editUserEmailByUsername(username, newEmail);
                        }
                        if (result == true) {
                            ToastUtil.createAndShowToast(getApplicationContext(), "Your email has been changed");
                            onBackPressed();
                            finish();
                        }
                    }
                }
            }
        });
    }
}