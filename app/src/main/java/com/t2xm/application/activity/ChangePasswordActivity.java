package com.t2xm.application.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.t2xm.R;
import com.t2xm.dao.UserDao;
import com.t2xm.utils.SharedPreferenceUtil;
import com.t2xm.utils.ToastUtil;
import com.t2xm.utils.ValidationUtil;

public class ChangePasswordActivity extends AppCompatActivity {

    private Activity activity;

    private String username;

    private TextInputEditText et_currentPassword;
    private TextInputEditText et_newPassword;
    private TextInputEditText et_confirmPassword;

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        activity = this;

        username = SharedPreferenceUtil.getUsername(activity);

        setupActivityComponents();
        setupActivityListeners();
    }

    private boolean validatePassword() {
        return ValidationUtil.validatePassword(newPassword);
    }

    private void updateInputFields() {
        currentPassword = String.valueOf(et_currentPassword.getText()).trim();
        newPassword = String.valueOf(et_newPassword.getText()).trim();
        confirmPassword = String.valueOf(et_confirmPassword.getText()).trim();
    }

    private void setupActivityComponents() {
        et_currentPassword = findViewById(R.id.et_current_password);
        et_newPassword = findViewById(R.id.et_new_password);
        et_confirmPassword = findViewById(R.id.et_confirm_password);
    }

    private void setupActivityListeners() {
        findViewById(R.id.btn_confirm_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInputFields();
                if (currentPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")) {
                    ToastUtil.createAndShowToast(getApplicationContext(), "Please fill up all fields");
                    return;
                }

                if (currentPassword.equals(newPassword)) {
                    ToastUtil.createAndShowToast(getApplicationContext(), "Current password and new password cannot be " +
                            "the same");
                } else if (validatePassword() == true) {
                    if(newPassword.equals(confirmPassword)) {
                        if (UserDao.editUserPasswordByUsername(username, newPassword)) {
                            ToastUtil.createAndShowToast(getApplicationContext(), "Your password has been updated");
                            onBackPressed();
                            finish();
                        } else {
                            ToastUtil.createAndShowToast(getApplicationContext(), "Error: Please try again");
                        }
                    }
                    else {
                        ToastUtil.createAndShowToast(activity, "New Password and Confirm Password do not match");
                    }
                } else {
                    ToastUtil.createAndShowToast(activity, "Please provide valid password");
                }
            }
        });
    }
}