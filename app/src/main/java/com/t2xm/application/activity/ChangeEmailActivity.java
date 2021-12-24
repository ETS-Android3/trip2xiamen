package com.t2xm.application.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.t2xm.R;
import com.t2xm.utils.ValidationUtil;

public class ChangeEmailActivity extends AppCompatActivity {

    private EditText et_currentEmail;
    private EditText et_newEmail;
    private EditText et_confirmEmail;

    private String currentEmail;
    private String newEmail;
    private String confirmEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        et_currentEmail = findViewById(R.id.et_current_email);
        et_newEmail = findViewById(R.id.et_new_email);
        et_confirmEmail = findViewById(R.id.et_confirm_email);

        findViewById(R.id.btn_confirm_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO change email

                //TODO check whether email match with the database
            }
        });
    }

    private boolean validateEmail() {
        if(ValidationUtil.validateEmail(newEmail)) {
            return newEmail.equals(confirmEmail);
        }
        return false;
    }

    private void updateInputFields() {
        currentEmail = String.valueOf(et_currentEmail.getText());
        newEmail = String.valueOf(et_newEmail.getText());
        confirmEmail = String.valueOf(et_confirmEmail.getText());
    }
}