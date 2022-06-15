package com.shahfahad.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    LoginDB myDb;
    SharedPreferences sharedPreferences;
    CheckBox togglePass;
    EditText pass, username;
    TextView signupText, resetPassText;
    Button loginBtn;

    public static Activity LoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginActivity = this;

        setTitle("AMS: Login");
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        myDb = new LoginDB(this);

        togglePass = findViewById(R.id.passwordToggle);
        pass = findViewById(R.id.password);
        username = findViewById(R.id.username);
        signupText = findViewById(R.id.signupBtn);
        resetPassText = findViewById(R.id.forgotPassword);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String password = pass.getText().toString();

                if(user.equals("")||password.equals(""))
                    Toast.makeText(Login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = myDb.checkusernamepassword(user, password);
                    Boolean checkuser = myDb.checkusername(user);
                    if (checkuser == true) {
                        if(checkuserpass==true){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Loggedin", user);
                            editor.commit();
                            Intent intent  = new Intent(getApplicationContext(), ClassesList.class);
                            intent.putExtra("username", user);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(Login.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Login.this, "User Does Not Exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });

        resetPassText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ResetPassword.class);
                startActivity(intent);
            }
        });

        togglePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}