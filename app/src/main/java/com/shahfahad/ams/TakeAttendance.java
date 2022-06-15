package com.shahfahad.ams;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TakeAttendance extends AppCompatActivity {

    Button presentBtn, absentBtn, leaveBtn;
    TextView rollNo, className, students, attResult;
    String nameOfClass, noOfStudents, absentRollNo = "", leaveRollNo = "", username;
    LinearLayout attLayout, attResultLayout;
    int totalStudents = 0, presentStudents = 0, absentStudents = 0, onLeaveStudents = 0, currentStudent = 1;
    AttendanceDB attendanceDB;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            nameOfClass = (String) b.get("class");
            noOfStudents = (String) b.get("students");
            username = (String) b.get("username");
        }
        nameOfClass = nameOfClass.substring(0, 1).toUpperCase() + nameOfClass.substring(1);

        presentBtn = findViewById(R.id.presentBtn);
        absentBtn = findViewById(R.id.absentBtn);
        leaveBtn = findViewById(R.id.leaveBtn);


        rollNo = findViewById(R.id.rollNo);
        className = findViewById(R.id.attClassName);
        students = findViewById(R.id.attStd);
        attResult = findViewById(R.id.attResult);

        attResultLayout = findViewById(R.id.attResultLayout);
        attLayout = findViewById(R.id.attLayout);


        className.setText("Class: " + nameOfClass);
        students.setText("Students: "+noOfStudents);


        rollNo.setText("Roll no. " + currentStudent);


        int studentsInt = 0;
        try {
            studentsInt = Integer.parseInt(noOfStudents);
        }
        catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        totalStudents = studentsInt;

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm aa");

        String formattedDate = myDateObj.format(myFormatObj);

        attendanceDB = new AttendanceDB(this, username, nameOfClass);

        presentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentStudent < totalStudents) {
                    presentStudents++;
                    currentStudent++;
                    rollNo.setText("Roll no. " + currentStudent);
                }
                else {
                    presentStudents++;

                    Boolean insert = attendanceDB.insertData(formattedDate, totalStudents, presentStudents, absentStudents, onLeaveStudents, absentRollNo, leaveRollNo);
                    if (insert == true)
                        Toast.makeText(TakeAttendance.this, "Attendance saved successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(TakeAttendance.this, "Failed to save attendance", Toast.LENGTH_SHORT).show();
                    attLayout.setVisibility(View.GONE);
                    attResultLayout.setVisibility(View.VISIBLE);
                    attResult.setText("Attendance Time: "+formattedDate+"\n\nTotal Students: "+noOfStudents+"\nPresent: "+presentStudents+"\nAbsent: "+absentStudents+"\nLeave: "+onLeaveStudents+"\n\nAbsent Students: \n"+absentRollNo+"\n\nOn Leave Students: \n"+leaveRollNo);
                }

            }
        });



        absentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentStudent < totalStudents) {
                    absentRollNo += currentStudent + ",";
                    absentStudents++;
                    currentStudent++;
                    rollNo.setText("Roll no. " + currentStudent);
                }
                else {
                    absentRollNo += currentStudent;
                    absentStudents++;
                    Boolean insert = attendanceDB.insertData(formattedDate, totalStudents, presentStudents, absentStudents, onLeaveStudents, absentRollNo, leaveRollNo);
                    if (insert == true)
                        Toast.makeText(TakeAttendance.this, "Attendance saved successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(TakeAttendance.this, "Failed to save attendance", Toast.LENGTH_SHORT).show();
                    attLayout.setVisibility(View.GONE);
                    attResultLayout.setVisibility(View.VISIBLE);
                    attResult.setText("Attendance Time: "+formattedDate+"\n\nTotal Students: "+noOfStudents+"\nPresent: "+presentStudents+"\nAbsent: "+absentStudents+"\nLeave: "+onLeaveStudents+"\n\nAbsent Students: \n"+absentRollNo+"\n\nOn Leave Students: \n"+leaveRollNo);
                }
            }
        });

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStudent < totalStudents) {
                    leaveRollNo += currentStudent + ",";
                    onLeaveStudents++;
                    currentStudent++;
                    rollNo.setText("Roll no. " + currentStudent);
                }
                else {
                    leaveRollNo += currentStudent;
                    onLeaveStudents++;
                    Boolean insert = attendanceDB.insertData(formattedDate, totalStudents, presentStudents, absentStudents, onLeaveStudents, absentRollNo, leaveRollNo);
                    if (insert == true)
                        Toast.makeText(TakeAttendance.this, "Attendance saved successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(TakeAttendance.this, "Failed to save attendance", Toast.LENGTH_SHORT).show();
                    attLayout.setVisibility(View.GONE);
                    attResultLayout.setVisibility(View.VISIBLE);
                    attResult.setText("Attendance Time: "+formattedDate+"\n\nTotal Students: "+noOfStudents+"\nPresent: "+presentStudents+"\nAbsent: "+absentStudents+"\nLeave: "+onLeaveStudents+"\n\nAbsent Students: \n"+absentRollNo+"\n\nOn Leave Students: \n"+leaveRollNo);

                }
            }
        });
    }
}