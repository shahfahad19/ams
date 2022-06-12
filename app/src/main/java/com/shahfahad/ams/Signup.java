package com.shahfahad.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Signup extends AppCompatActivity {

    LoginDB DBHelper;
    SharedPreferences sharedPreferences;

    EditText fullName, userName, email, pass, verifyPass;
    CheckBox togglePass;
    Button signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Sign Up");

        fullName = findViewById(R.id.fullname);
        userName = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        verifyPass = findViewById(R.id.passwordVerify);

        togglePass = findViewById(R.id.passwordToggle);
        signup = findViewById(R.id.signupBtn);

        DBHelper = new LoginDB(this);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = fullName.getText().toString();
                String uname = userName.getText().toString();
                String password = pass.getText().toString();
                String repass = verifyPass.getText().toString();
                String emailAddr = email.getText().toString();

                //Email Validation
                Boolean validEmail;
                String regex = "^(.+)@(.+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(emailAddr);
                validEmail = matcher.matches();



                if (fname.equals("") || uname.equals("") || password.equals("") || repass.equals("") || emailAddr.equals("")) {
                    Toast.makeText(Signup.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    if (fname.length() < 3) {
                        Toast.makeText(Signup.this, "Full Name should be at least 3 characters", Toast.LENGTH_SHORT).show();
                    }
                    else if (uname.length() < 3) {
                        Toast.makeText(Signup.this, "Username should be at least 3 characters", Toast.LENGTH_SHORT).show();
                    }

                    else if (!validEmail) {
                        Toast.makeText(Signup.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                    }

                    else if (password.length() < 6) {
                        Toast.makeText(Signup.this, "Password should be at least 6 digit", Toast.LENGTH_SHORT).show();
                    }
                    else if (!password.equals(repass)) {
                        Toast.makeText(Signup.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Boolean checkuser = DBHelper.checkusername(uname);
                        if(checkuser==false){
                            Boolean insert = DBHelper.insertData(fname, uname, emailAddr, password);
                            if(insert==true){
                                Toast.makeText(Signup.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Loggedin", uname);
                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(), ClassesList.class);
                                intent.putExtra("username", uname);
                                startActivity(intent);
                                Login.LoginActivity.finish();
                                finish();

                            }else{
                                Toast.makeText(Signup.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(Signup.this, "User already exists!", Toast.LENGTH_SHORT).show();
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