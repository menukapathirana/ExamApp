package com.munuka.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The type Main activity - main page of the application.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The Ins id.
     */
    String insID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get username from the login page to the system
        insID = getIntent().getStringExtra("username");

        /**
         * user can click to show the students who ready for the exam
         */
        Button btn_Exam = (Button)findViewById(R.id.btn_Exam);
        btn_Exam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, student.class);
                //Pass username to the exam page
                intent.putExtra("username", insID);
                startActivity(intent);
            }
        });

        /**
         * User can show all the students in the system
         */
        Button btn_Student = (Button)findViewById(R.id.btn_Student);
        btn_Student.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewAllStudent.class);
                //Pass username to the exam page
                intent.putExtra("username", insID);
                startActivity(intent);
            }
        });

    }
}
