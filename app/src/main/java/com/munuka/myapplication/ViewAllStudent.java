package com.munuka.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type View all student.
 */
public class ViewAllStudent extends Activity {

    /**
     * The Helper.
     */
    myDbAdapter helper;
    /**
     * The Selected item.
     */
    String selectedItem;
    /**
     * The Selectsname.
     */
    String selectsname;
    /**
     * The Attempt.
     */
    String Attempt;
    /**
     * The Ins id.
     */
    String insID;
    /**
     * The Adapter.
     */
    ListAdapter adapter;
    /**
     * The User list.
     */
    ArrayList<HashMap<String, String>> userList;
    /**
     * The Lv.
     */
    ListView lv;
    /**
     * The Inflater.
     */
    LayoutInflater inflater;
    /**
     * The Header.
     */
    ViewGroup header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_student);

        //get instructor id from the previous intent
        insID = getIntent().getStringExtra("username");

        //initialize and assing db object
        helper = new myDbAdapter(this);

        //get all student from the db and pass to the arraylist
        userList = helper.getAllStudentData();
        //initialize listview
        lv = (ListView) findViewById(R.id.student_list);
        //set adapter with arraylist and the headers
        adapter = new SimpleAdapter(ViewAllStudent.this, userList, R.layout.list_all_student, new String[]{"sid", "sname", "examattemp"}, new int[]{R.id.studentID, R.id.studentName, R.id.examattemp});
        inflater = getLayoutInflater();
        //set header with layout
        header = (ViewGroup) inflater.inflate(R.layout.list_all_student_header, lv, false);
        //set adapter to the listview
        lv.setAdapter(adapter);
        //set header to the listview
        lv.addHeaderView(header);

        //if listvie empty show this text view text
        lv.setEmptyView(findViewById(R.id.EmptyView));

        //set listview item click function
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> member = (HashMap<String, String>) adapterView.getItemAtPosition(i);
                //pass selected item values to the varaibles
                selectedItem = member.get("sid");
                selectsname = member.get("sname");
                Attempt = member.get("examattemp");

                //pass values to the input dialog function
                showInputDialog(selectedItem,selectsname,Attempt);


            }
        });
    }


    /**
     * Show input dialog.
     *
     * @param studentDetials     the student detials
     * @param Studentselectsname the studentselectsname
     * @param examattemp         the examattemp
     */
        protected void showInputDialog(final String studentDetials, String Studentselectsname, final String examattemp) {

            // get prompts.xml view
            LayoutInflater layoutInflater = LayoutInflater.from(ViewAllStudent.this);
            View promptView = layoutInflater.inflate(R.layout.layout_all_student_input, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewAllStudent.this);
            alertDialogBuilder.setView(promptView);

            //intialiaze UI componets in input dialog
            final TextView StudentTItle = (TextView)promptView.findViewById(R.id.StudentTItle);
            StudentTItle.setText(studentDetials);

            final Button btn_ViewResult = (Button)promptView.findViewById(R.id.btn_ViewResult);

            //if student is not attemp to the exam
            if(examattemp.equals("Not Attempt"))
            {
                //Button View Reulst is invisible
                btn_ViewResult.setVisibility(View.INVISIBLE);
                btn_ViewResult.setEnabled(false);
                btn_ViewResult.setBackgroundColor(Color.DKGRAY);
            }
            //if student alrady attemp to the exam
            else if(examattemp.equals("Attempted"))
            {
                //user can view the result and lauch the result page
                btn_ViewResult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(getApplicationContext(),
                                ViewResult.class);
                        //pass StudentID and Instructor ID
                        in.putExtra("StudentID", studentDetials);
                        in.putExtra("insID", insID);
                        startActivity(in);
                    }
                });
            }

            //pass Student ID and Student Name to the EditTExt from the input dialog
            final EditText edittextsid = (EditText) promptView.findViewById(R.id.edittextsid);
            edittextsid.setText(studentDetials);

            final EditText edittextsname = (EditText) promptView.findViewById(R.id.edittextsname);
            edittextsname.setText(Studentselectsname);



            // setup a dialog window
            alertDialogBuilder.setCancelable(false)
                   .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //If student not attempt to the exam, user can update student details
                            if(examattemp.equals("Not Attempt"))
                            {
                                //get Edit text student Id and student name to the varaibles
                                String Sid = edittextsid.getText().toString().trim();
                                String Sname = edittextsname.getText().toString().trim();

                                //Check edittext values are empty or not
                                if(Sid.isEmpty() || Sname.isEmpty())
                                {
                                    //if empty show error message
                                    Toast.makeText(ViewAllStudent.this,"Empty values",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    edittextsid.setFocusable(true);
                                    //if not update student id and name in the database
                                    helper.updateStudent(studentDetials,edittextsid.getText().toString(),edittextsname.getText().toString());
                                    //ageing pass values to the arraylist from the db
                                    userList = helper.getAllStudentData();
                                    adapter = new SimpleAdapter(ViewAllStudent.this, userList, R.layout.list_all_student, new String[]{"sid", "sname", "examattemp"}, new int[]{R.id.studentID, R.id.studentName, R.id.examattemp});
                                    inflater = getLayoutInflater();
                                    lv.setAdapter(adapter);

                                }
                            }
                            else if(examattemp.equals("Attempted"))
                            {
                                //If student attempt to the exam show cannot update the student
                                Toast.makeText(ViewAllStudent.this,"Cannot update. Please contact System Administrator!",Toast.LENGTH_SHORT).show();
                            }



                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create an alert dialog
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }










}
