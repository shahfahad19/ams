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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class AttendanceList extends AppCompatActivity {

    AttendanceDB attendanceDB;
    String username, nameOfClass, students;
    List<String> dates = new ArrayList<String>(),
            totals = new ArrayList<String>(),
            presents = new ArrayList<String>(),
            absents = new ArrayList<String>(),
            leaves = new ArrayList<String>(),
            absentlist = new ArrayList<String>(),
            leavelist = new ArrayList<String>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.attendance_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.takeNewAttendance:
                Intent takeAttIntent = new Intent(getApplicationContext(), TakeAttendance.class);
                takeAttIntent.putExtra("class", nameOfClass);
                takeAttIntent.putExtra("students", students);
                takeAttIntent.putExtra("username", username);
                startActivity(takeAttIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        TextView className = findViewById(R.id.className);
        TextView classStd = findViewById(R.id.classStudents);
        TextView notAvail = findViewById(R.id.notAvailableAtt);
        TextView msgAtt = findViewById(R.id.msgAtt);



        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            username = (String) b.get("username");
            nameOfClass = (String) b.get("subject");
            students = (String) b.get("students");
        }
        nameOfClass = nameOfClass.substring(0, 1).toUpperCase() + nameOfClass.substring(1);

        setTitle(nameOfClass + ": Attendance");
        className.setText("Subject: "+ nameOfClass);
        classStd.setText("Students: "+students);


        attendanceDB = new AttendanceDB(this, username, nameOfClass);
        Cursor attendances = attendanceDB.getAttendances();

        if (attendances.getCount() == 0) {
            notAvail.setVisibility(View.VISIBLE);
            msgAtt.setVisibility(View.GONE);
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
                leavelist.add(attendances.getString(6));
            }
            RecyclerView rv = findViewById(R.id.recyclerView);
            rv.setLayoutManager(new LinearLayoutManager(this));

            String[] datesArr = dates.toArray(new String[0]);
            String[] totalArr = totals.toArray(new String[0]);
            String[] presentArr = presents.toArray(new String[0]);
            String[] absentsArr = absents.toArray(new String[0]);
            String[] leaveArr = leaves.toArray(new String[0]);
            String[] absentListArr = absentlist.toArray(new String[0]);
            String[] leaveListArr = leavelist.toArray(new String[0]);
            rv.setAdapter(new AttendanceAdapter(datesArr, totalArr, presentArr, absentsArr, leaveArr, absentListArr, leaveListArr));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    public void onClickCalled(String date, String total, String present, String absent, String leave, String absentlist, String leaveList) {
        Intent takeAttIntent = new Intent(getApplicationContext(), ViewAttendance.class);
        takeAttIntent.putExtra("class", nameOfClass);
        takeAttIntent.putExtra("students", students);
        takeAttIntent.putExtra("date", date);
        takeAttIntent.putExtra("total", total);
        takeAttIntent.putExtra("present", present);
        takeAttIntent.putExtra("absent", absent);
        takeAttIntent.putExtra("leave", leave);
        takeAttIntent.putExtra("absentlist", absentlist);
        takeAttIntent.putExtra("leavelist", leaveList);
        startActivity(takeAttIntent);
    }
}