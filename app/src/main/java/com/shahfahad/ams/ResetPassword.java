package com.shahfahad.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        CheckBox togglePass = findViewById(R.id.passwordToggle);
        EditText pass = findViewById(R.id.password),
                verifyPass = findViewById(R.id.passwordVerify);
        togglePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    verifyPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else {
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    verifyPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
    }
}