package com.shahfahad.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPassword extends AppCompatActivity {

    LoginDB DBHelper;

    EditText username, emailAddress, pass, verifyPass;

    CheckBox togglePass;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        setTitle("Reset Password");

        username = findViewById(R.id.username);
        emailAddress = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        verifyPass = findViewById(R.id.passwordVerify);
        togglePass = findViewById(R.id.passwordToggle);
        reset = findViewById(R.id.resetBtn);

        DBHelper = new LoginDB(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String email = emailAddress.getText().toString();
                String password = pass.getText().toString();
                String repass = verifyPass.getText().toString();

                //Email Validation
                Boolean validEmail;
                String regex = "^(.+)@(.+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);
                validEmail = matcher.matches();



                if (user.equals("") || password.equals("") || repass.equals("") || email.equals("")) {
                    Toast.makeText(ResetPassword.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    if (user.length() < 3) {
                        Toast.makeText(ResetPassword.this, "Username should be at least 3 charcters", Toast.LENGTH_SHORT).show();
                    }

                    else if (!validEmail) {
                        Toast.makeText(ResetPassword.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                    }

                    else if (password.length() < 6) {
                        Toast.makeText(ResetPassword.this, "Password should be at least 6 digit", Toast.LENGTH_SHORT).show();
                    }
                    else if (!password.equals(repass)) {
                        Toast.makeText(ResetPassword.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Boolean checkuser = DBHelper.checkusername(user);
                        if(checkuser==true){
                            Boolean insert = DBHelper.updatePassword(user, email, password);
                            if(insert==true){
                                Toast.makeText(ResetPassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Login.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(ResetPassword.this, "Password change failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(ResetPassword.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });



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