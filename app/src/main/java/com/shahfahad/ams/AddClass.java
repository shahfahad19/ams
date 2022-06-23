package com.shahfahad.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddClass extends AppCompatActivity {


    EditText className, description, students;
    Button addBtn;
    ClassesDB ClassDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        setTitle("Add Class");

        className = findViewById(R.id.classname);
        description = findViewById(R.id.description);
        students = findViewById(R.id.students);

        addBtn = findViewById(R.id.addBtn);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String username = "";
        if(b!=null) username =(String) b.get("username");


        ClassDB = new ClassesDB(this, username);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOfClass = className.getText().toString();
                String desc = description.getText().toString();
                String std = students.getText().toString();
                if (std.equals("")) std = "0";
                int noOfStudents = Integer.parseInt(std);

                if (nameOfClass.equals("") || std.equals("") ) {
                    Toast.makeText(AddClass.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (nameOfClass.length() < 4) {
                        Toast.makeText(AddClass.this, "Class Name should be at least 4 characters", Toast.LENGTH_SHORT).show();

                    }
                    else if (noOfStudents < 1) {
                        Toast.makeText(AddClass.this, "Please provide valid number of students", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Boolean checkuser = ClassDB.checkClass(nameOfClass);
                        //Boolean checkuser = false;
                        if(checkuser==false){
                            Boolean insert = ClassDB.insertData(nameOfClass, desc, noOfStudents);
                            //Boolean insert = true;
                            if(insert==true){
                                Toast.makeText(AddClass.this, "Class Added Successfully", Toast.LENGTH_SHORT).show();
                                className.setText("");
                                description.setText("");
                                students.setText("");

                            }else{
                                Toast.makeText(AddClass.this, "Class could not be added!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(AddClass.this, "Class already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });

    }
}