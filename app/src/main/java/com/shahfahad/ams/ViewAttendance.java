package com.shahfahad.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewAttendance extends AppCompatActivity {

    TextView classname, students, summary;
    String nameOfClass, noOfStudents, attendance, date, present, absent, leave, absentlist, leavelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        classname = findViewById(R.id.vaClassname);
        students = findViewById(R.id.vaStudents);
        summary = findViewById(R.id.vaSummary);

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


            classname.setText("Class: " + nameOfClass);
            students.setText("Students: "+ noOfStudents);
            summary.setText("Attendance Time: "+date+"\n\nTotal Students: "+noOfStudents+"\nPresent: "+present+"\nAbsent: "+absent+"\nLeave: "+leave+"\n\nAbsent Students:\n"+absentlist+"\n\nOn Leave Students:\n"+leavelist);
        }
    }
}