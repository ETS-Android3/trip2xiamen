package com.t2xm.application.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.t2xm.R;
import com.t2xm.utils.ValidationUtil;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText et_currentPassword;
    private EditText et_newPassword;
    private EditText et_confirmPassword;

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        et_currentPassword = findViewById(R.id.et_current_password);
        et_newPassword = findViewById(R.id.et_new_password);
        et_confirmPassword = findViewById(R.id.et_new_password);

        findViewById(R.id.btn_confirm_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO change password

                //TODO check whether current password match with database
            }
        });
    }

    private boolean validatePassword() {
        if(ValidationUtil.validatePassword(newPassword)) {
            return newPassword.equals(confirmPassword);
        }
        return false;
    }

    private void updateInputFields() {
        currentPassword = String.valueOf(et_currentPassword.getText());
        newPassword = String.valueOf(et_newPassword.getText());
        confirmPassword = String.valueOf(et_confirmPassword.getText());
    }
}