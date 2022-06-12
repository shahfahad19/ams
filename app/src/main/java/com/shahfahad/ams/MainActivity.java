package com.shahfahad.ams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                String loggedin = sharedPreferences.getString("Loggedin", "false");
                if (loggedin.equals("false")) {
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent intent  = new Intent(getApplicationContext(), ClassesList.class);
                    intent.putExtra("username", loggedin);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
    }
}