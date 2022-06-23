package com.shahfahad.ams;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewAttendance extends AppCompatActivity {

    TextView classname, students, summary;
    Button delete;
    AttendanceDB attendanceDB;
    String nameOfClass, noOfStudents, attendance, date, present, absent, leave, absentlist, leavelist, username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        classname = findViewById(R.id.vaClassname);
        students = findViewById(R.id.vaStudents);
        summary = findViewById(R.id.vaSummary);
        delete = findViewById(R.id.deletebtn);

        setTitle("Attendance Summary");

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            nameOfClass = (String) b.get("class");
            noOfStudents = (String) b.get("students");
            attendance = (String) b.get("summary");
            date = (String) b.get("date");
            present = (String) b.get("present");
            absent = (String) b.get("absent");
            leave = (String) b.get("leave");
            absentlist = (String) b.get("absentlist");
            leavelist = (String) b.get("leavelist");
            username = (String) b.get("username");



            classname.setText("Class: " + nameOfClass);
            students.setText("Students: "+ noOfStudents);
            summary.setText("Attendance Time: "+date+"\n\nTotal Students: "+noOfStudents+"\nPresent: "+present+"\nAbsent: "+absent+"\nLeave: "+leave+"\n\nAbsent Students:\n"+absentlist+"\n\nOn Leave Students:\n"+leavelist);
        }
        attendanceDB = new AttendanceDB(this, username, nameOfClass);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewAttendance.this);
                alertDialog.setCancelable(true);
                alertDialog.setTitle("Delete Attendance?");
                alertDialog.setIcon(R.drawable.logo);
                alertDialog.setMessage("Are you sure?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int deleteRows = attendanceDB.deleteAtt(date);
                        if (deleteRows > 0) {
                            Toast.makeText(ViewAttendance.this, "Attendance deleted!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(ViewAttendance.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", null);
                alertDialog.show();

            }
        });
    }
}