package com.shahfahad.ams;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ClassesList extends AppCompatActivity {

    LoginDB db;
    ClassesDB classesDB;
    SharedPreferences sharedPreferences;
    String username, fullname;
    List<String> classNames = new ArrayList<String>(),
            classDescriptions = new ArrayList<String>(),
            classStudents = new ArrayList<String>();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setCancelable(true);
                alertDialog.setTitle("Logout");
                alertDialog.setIcon(R.drawable.logo);
                alertDialog.setMessage("Are you sure?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Loggedin", "false");
                        editor.commit();
                        Intent intent  = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(),"Logged out successfully",Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.setNegativeButton("No", null);
                alertDialog.show();
                return true;
            case R.id.addClass:
                Intent addClassIntent  = new Intent(getApplicationContext(), AddClass.class);
                addClassIntent.putExtra("username", username);
                startActivity(addClassIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_list);

        TextView welcome = findViewById(R.id.welcomeText);
        TextView msg = findViewById(R.id.notAvailable);
        TextView cmsg = findViewById(R.id.msgClasses);
        db = new LoginDB(this);


        setTitle("Dashboard");

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            username = (String) b.get("username");
            fullname = db.getName(username);

        }
        classesDB = new ClassesDB(this, username);
        welcome.setText("Welcome " + fullname);
        Cursor myClasses = classesDB.getClasses();
        if (myClasses.getCount() == 0) {
            msg.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No Classes Available", Toast.LENGTH_SHORT).show();
            cmsg.setVisibility(View.GONE);
            return;
        }

        else {
            while (myClasses.moveToNext()) {
                classNames.add(myClasses.getString(0));
                classDescriptions.add(myClasses.getString(1));
                classStudents.add(myClasses.getString(2));
            }
            RecyclerView rv = findViewById(R.id.recyclerView);
            rv.setLayoutManager(new LinearLayoutManager(this));


            String[] namesArr = classNames.toArray(new String[0]);
            String[] descArr = classDescriptions.toArray(new String[0]);
            String[] stdArr = classStudents.toArray(new String[0]);
            rv.setAdapter(new ClassAdapter(namesArr, descArr, stdArr));

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    public void onClickCalled(String subjectname, String students) {
        Intent i = new Intent(getApplicationContext(), AttendanceList.class);
        i.putExtra("username", username);
        i.putExtra("subject", subjectname);
        i.putExtra("students", students);
        startActivity(i);

    }
}