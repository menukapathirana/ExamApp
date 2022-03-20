package com.munuka.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Student.
 */
public class student extends Activity {
    /**
     * The Helper.
     */
    myDbAdapter helper;
    /**
     * The Selected item.
     */
    String selectedItem;
    /**
     * The Ins id.
     */
    String insID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_student);

        //Get username from the main activity
        insID = getIntent().getStringExtra("username");

        //Initialize database object
        helper = new myDbAdapter(this);
        //get student who ready for the exam from db and pass to the arraylist
        ArrayList<HashMap<String, String>> userList = helper.getStudentData();
        //initialize listview from the UI
        ListView lv = (ListView) findViewById(R.id.student_list);
        //set listadapter and pass values to the adapter
        ListAdapter adapter = new SimpleAdapter(student.this, userList, R.layout.list_student, new String[]{"sid", "sname"}, new int[]{R.id.studentID, R.id.studentName});
        LayoutInflater inflater = getLayoutInflater();
        //Set header for the listview
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.list_student_header,lv,false);
        //set adapter to the listview
        lv.setAdapter(adapter);
        //add header to the listview
        lv.addHeaderView(header);

        //If listview empty, this viewtext value will show in the UI
        lv.setEmptyView(findViewById(R.id.EmptyView));

        //Listview click on item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> member = (HashMap<String, String>) adapterView.getItemAtPosition(i);
                //get the listvie selected item value (Student ID)
                selectedItem=member.get("sid");
                //pass selected item value to the dialog form
                showExamDialog(selectedItem);
            }
        });
    }

    /**
     * Dialog form fucntion
     */
    private void showExamDialog(final String selectItem){
        final AlertDialog.Builder Dialog = new AlertDialog.Builder(this);
        //set the title of the form
        Dialog.setTitle("The Jiu-Jitsu Exam");
        //set the message of the dialog
        Dialog.setMessage("Do you want to start the exam?")
                .setCancelable(false)
                //set Yes button
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        //Update sudent exam status in the database (Atempt = 1) by find Student ID (selectItem)
                        helper.updateStudentExamStatus(selectItem);
                        //Launch exam first six criteria in the application
                        Intent i = new Intent(getApplicationContext(),
                                Criteria1.class);
                        //Pass student ID and Instructor ID
                        i.putExtra("StudentID",selectItem);
                        i.putExtra("insID",insID);
                        startActivity(i);
                        finish();


                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        Dialog.show();

    }
}
