package com.t2xm.application.activity;

import android.app.Activity;
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

    private Activity activity;

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

        activity = this;

        username = SharedPreferenceUtil.getUsername(activity);

        setupActivityComponents();
        setupActivityListeners();
    }

    private boolean validateEmail() {
        return ValidationUtil.validateEmail(newEmail) ;
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
                updateInputFields();
                if (currentEmail.equals("") || newEmail.equals("") || confirmEmail.equals("")) {
                    ToastUtil.createAndShowToast(activity, "Please fill up all fields");
                    return;
                }

                if (currentEmail.equals(newEmail)) {
                    ToastUtil.createAndShowToast(activity, "Current email and new email cannot be the same");
                } else if(UserDao.checkEmailExistence(newEmail)) {
                    ToastUtil.createAndShowToast(activity, "An account with the provided new email already exist");
                }
                else if (validateEmail() == true) {
                    if(newEmail.equals(confirmEmail)) {
                        if (UserDao.editUserEmailByUsername(username, newEmail)) {
                            ToastUtil.createAndShowToast(activity, "Your email has been updated");
                            onBackPressed();
                            finish();
                        } else {
                            ToastUtil.createAndShowToast(activity, "Error: Please try again");
                        }
                    }
                    else {
                        ToastUtil.createAndShowToast(activity, "New Email and Confirm Email do not match");
                    }
                } else {
                    ToastUtil.createAndShowToast(activity, "Please provide valid email");
                }
            }
        });
    }
}