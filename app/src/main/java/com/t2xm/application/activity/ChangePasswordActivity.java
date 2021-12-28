package com.t2xm.application.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.t2xm.R;
import com.t2xm.dao.UserDao;
import com.t2xm.utils.SharedPreferenceUtil;
import com.t2xm.utils.ToastUtil;
import com.t2xm.utils.ValidationUtil;

public class ChangePasswordActivity extends AppCompatActivity {

    private Activity activity;

    private String username;

    private TextInputLayout lay_currentPassword;
    private TextInputLayout lay_newPassword;
    private TextInputLayout lay_confirmPassword;


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
        boolean valid = true;

        //validate current password
        if (currentPassword.equals("")) {
            lay_currentPassword.setError(getString(R.string.error_password_empty));
            valid = valid && false;
        } else if (!UserDao.validateUserAccount(SharedPreferenceUtil.getUsername(activity), currentPassword)) {
            lay_currentPassword.setError(getString(R.string.error_wrong_password));
            valid = valid && false;
        } else {
            lay_currentPassword.setError(getString(R.string.no_error));
        }

        //validate new password
        if (newPassword.equals("")) {
            lay_newPassword.setError(getString(R.string.error_password_empty));
            valid = valid && false;
        } else if (currentPassword.equals(newPassword)) {
            lay_newPassword.setError(getString(R.string.error_same_password));
            valid = valid && false;
        } else if (!ValidationUtil.validatePassword(newPassword)) {
            lay_newPassword.setError(getString(R.string.password_format));
            valid = valid && false;
        } else {
            lay_newPassword.setError(getString(R.string.no_error));
        }

        //validate confirm password
        if (confirmPassword.equals("")) {
            lay_confirmPassword.setError(getString(R.string.error_password_empty));
            valid = valid && false;
        } else if (!newPassword.equals(confirmPassword)) {
            lay_confirmPassword.setError(getString(R.string.error_password_not_match));
            valid = valid && false;
        } else {
            lay_confirmPassword.setError(getString(R.string.no_error));
        }

        return valid;
    }

    private void updateInputFields() {
        currentPassword = String.valueOf(et_currentPassword.getText()).trim();
        newPassword = String.valueOf(et_newPassword.getText()).trim();
        confirmPassword = String.valueOf(et_confirmPassword.getText()).trim();
    }

    private void setupActivityComponents() {
        lay_currentPassword = findViewById(R.id.lay_current_password);
        lay_newPassword = findViewById(R.id.lay_new_password);
        lay_confirmPassword = findViewById(R.id.lay_confirm_password);

        et_currentPassword = findViewById(R.id.et_current_password);
        et_newPassword = findViewById(R.id.et_new_password);
        et_confirmPassword = findViewById(R.id.et_confirm_password);
    }

    private void setupActivityListeners() {
        findViewById(R.id.btn_confirm_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInputFields();

                if (validatePassword()) {
                    if (UserDao.editUserPasswordByUsername(username, newPassword)) {
                        ToastUtil.createAndShowToast(activity, "Your password has been updated");
                        onBackPressed();
                        finish();
                    } else {
                        ToastUtil.createAndShowToast(activity, "Error: Please try again");
                    }
                }
            }
        });
    }
}