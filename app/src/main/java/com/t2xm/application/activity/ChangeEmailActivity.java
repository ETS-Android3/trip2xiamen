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

public class ChangeEmailActivity extends AppCompatActivity {

    private Activity activity;

    private String username;

    private TextInputLayout lay_currentEmail;
    private TextInputLayout lay_newEmail;
    private TextInputLayout lay_confirmEmail;

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

        activity = this;

        username = SharedPreferenceUtil.getUsername(activity);

        setupActivityComponents();
        setupActivityListeners();
    }

    private boolean validateEmail() {
        boolean valid = true;

        //validate current email
        if (currentEmail.equals("")) {
            lay_currentEmail.setError(getString(R.string.error_email_empty));
            valid = valid && false;
        } else if (!currentEmail.equals(UserDao.getUserEmailByUsername(username))) {
            lay_currentEmail.setError(getString(R.string.error_wrong_email));
            valid = valid && false;
        } else {
            lay_currentEmail.setError(getString(R.string.no_error));
        }

        //validate new email
        if (newEmail.equals("")) {
            lay_newEmail.setError(getString(R.string.error_email_empty));
            valid = valid && false;
        } else if (currentEmail.equals(newEmail)) {
            lay_newEmail.setError(getString(R.string.error_same_email));
            valid = valid && false;
        } else if (!ValidationUtil.validateEmail(newEmail)) {
            lay_newEmail.setError(getString(R.string.email_format));
            valid = valid && false;
        } else if (UserDao.checkEmailExistence(newEmail)) {
            lay_newEmail.setError(getString(R.string.error_email_exist));
            valid = valid && false;
        } else {
            lay_newEmail.setError(getString(R.string.no_error));
        }

        //validate confirm email
        if (confirmEmail.equals("")) {
            lay_confirmEmail.setError(getString(R.string.error_email_empty));
            valid = valid && false;
        } else if (!newEmail.equals(confirmEmail)) {
            lay_confirmEmail.setError(getString(R.string.error_email_not_match));
            valid = valid && false;
        } else {
            lay_confirmEmail.setError(getString(R.string.no_error));
        }

        return valid;
    }

    private void updateInputFields() {
        currentEmail = String.valueOf(et_currentEmail.getText());
        newEmail = String.valueOf(et_newEmail.getText());
        confirmEmail = String.valueOf(et_confirmEmail.getText());
    }

    private void setupActivityComponents() {
        lay_currentEmail = findViewById(R.id.lay_current_email);
        lay_newEmail = findViewById(R.id.lay_new_email);
        lay_confirmEmail = findViewById(R.id.lay_confirm_email);

        et_currentEmail = findViewById(R.id.et_current_email);
        et_newEmail = findViewById(R.id.et_new_email);
        et_confirmEmail = findViewById(R.id.et_confirm_email);
    }

    private void setupActivityListeners() {
        findViewById(R.id.btn_confirm_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInputFields();
                if (validateEmail()) {
                    if (UserDao.editUserEmailByUsername(username, newEmail)) {
                        ToastUtil.createAndShowToast(activity, "Your email has been updated");
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