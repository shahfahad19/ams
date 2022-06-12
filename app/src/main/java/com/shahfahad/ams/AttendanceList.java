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

public class AttendanceList extends AppCompatActivity {

    AttendanceDB attendanceDB;
    SharedPreferences sharedPreferences;
    String username, subjectName, students;
    List<String> dates = new ArrayList<String>(),
            totals = new ArrayList<String>(),
            presents = new ArrayList<String>(),
            absents = new ArrayList<String>(),
            leaves = new ArrayList<String>(),
            absentlist = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        TextView welcome = findViewById(R.id.welcomeText);
        TextView msg = findViewById(R.id.notAvailable);
        TextView cmsg = findViewById(R.id.msgClasses);


        setTitle("Dashboard");

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            username = (String) b.get("username");
            subjectName = (String) b.get("subject");
            students = (String) b.get("students");

        }
        attendanceDB = new AttendanceDB(this, username, subjectName);
        Cursor attendances = attendanceDB.getAttendances();
        if (attendances.getCount() == 0) {
            msg.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No Classes Available", Toast.LENGTH_SHORT).show();
            cmsg.setVisibility(View.GONE);
            return;
        }

        else {
            while (attendances.moveToNext()) {
                dates.add(attendances.getString(0));
                totals.add(attendances.getString(1));
                presents.add(attendances.getString(2));
                absents.add(attendances.getString(3));
                leaves.add(attendances.getString(4));
                absentlist.add(attendances.getString(5));
            }
            RecyclerView rv = findViewById(R.id.recyclerView);
            rv.setLayoutManager(new LinearLayoutManager(this));




            String[] datesArr = dates.toArray(new String[0]);
            String[] totalArr = totals.toArray(new String[0]);
            String[] presentArr = presents.toArray(new String[0]);
            String[] absentsArr = absents.toArray(new String[0]);
            String[] leaveArr = leaves.toArray(new String[0]);
            String[] absentListArr = absentlist.toArray(new String[0]);

            rv.setAdapter(new AttendanceAdapter(datesArr, totalArr, presentArr, absentsArr, leaveArr, absentListArr));

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    public void onClickCalled(String anyValue) {
        // Call another acitivty here and pass some arguments to it.
        Toast.makeText(this, anyValue, Toast.LENGTH_SHORT).show();
    }
}