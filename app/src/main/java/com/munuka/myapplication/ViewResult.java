package com.munuka.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type View result.
 */
public class ViewResult extends Activity {

    /**
     * The Helper.
     */
    myDbAdapter helper;
    /**
     * The Inst id.
     */
    String instID;
    /**
     * The Student id.
     */
    String studentID;
    /**
     * The Result criteria list.
     */
    ArrayList<String> ResultCriteriaList;
    /**
     * The Adapter.
     */
    ArrayAdapter adapter;
    /**
     * The Lv.
     */
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);

        //Initialize db oject
        helper = new myDbAdapter(this);

        //Initilize and assing UI components
        TextView txtStudent = (TextView)findViewById(R.id.studentID);
        TextView txtInstructor = (TextView)findViewById(R.id.insttName);
        TextView txtdate = (TextView)findViewById(R.id.date);
        TextView txtDegree = (TextView)findViewById(R.id.degree);
        TextView txtTotal = (TextView)findViewById(R.id.total);
        lv = (ListView) findViewById(R.id.Resultcriteria_list);

        //set list view empty show text view text
        lv.setEmptyView(findViewById(R.id.EmptyView));

        //get student id and instructor id from the result page
        studentID = getIntent().getStringExtra("StudentID");;
        instID = getIntent().getStringExtra("insID");

        try
        {
            //get all the exaam result value and pass to the arraylist
            ArrayList<HashMap<String, String>> ResultList = helper.getExamData(studentID);

            for (HashMap<String, String> hashMap : ResultList) {
                    //assing values to the textview
                   txtdate.setText("Date : " + hashMap.get(("date")));
                   txtInstructor.setText("Instructor ID : " + hashMap.get("Name"));
                   txtStudent.setText("Student ID : " + hashMap.get("sid"));

                   //calculate all the score values
                   int score = Integer.parseInt(hashMap.get("q1"))+Integer.parseInt(hashMap.get("q2"))+Integer.parseInt(hashMap.get("q3"))
                           +Integer.parseInt(hashMap.get("q4"))+Integer.parseInt(hashMap.get("q5"))+Integer.parseInt(hashMap.get("q6"))+Integer.parseInt(hashMap.get("q7"))
                           +Integer.parseInt(hashMap.get("q8"))+Integer.parseInt(hashMap.get("q9"))+Integer.parseInt(hashMap.get("q10"))+Integer.parseInt(hashMap.get("q11"))
                           +Integer.parseInt(hashMap.get("q12"))+Integer.parseInt(hashMap.get("q13"));

                   //set all the criteria value and result in arraylist
                    ResultCriteriaList = new ArrayList<>();
                    ResultCriteriaList.add("Aggressive handshake : " + hashMap.get("q1"));
                    ResultCriteriaList.add("Inside wrist grip : " + hashMap.get("q2"));
                    ResultCriteriaList.add("Outside wrist grip : " + hashMap.get("q3"));
                    ResultCriteriaList.add("Both hands holding wrist : " + hashMap.get("q4"));
                    ResultCriteriaList.add("Both wrists held : " + hashMap.get("q5"));
                    ResultCriteriaList.add("Headlock side : " + hashMap.get("q6"));
                    ResultCriteriaList.add("Strangles front : " + hashMap.get("q7"));
                    ResultCriteriaList.add("Strangles side : " + hashMap.get("q8"));
                    ResultCriteriaList.add("Strangles rear : " + hashMap.get("q9"));
                    ResultCriteriaList.add("Bear hugs Behind (over arms) : " + hashMap.get("q10"));
                    ResultCriteriaList.add("Bear hugs Behind (under arms) : " + hashMap.get("q11"));
                    ResultCriteriaList.add("Bear hugs Front (over arms) : " + hashMap.get("q12"));
                    ResultCriteriaList.add("Bear hugs Front (under arms) : " + hashMap.get("q13"));

                    //Pass criteria arraylist to the arrayadapter
                    adapter = new ArrayAdapter< String>(this, R.layout.list_criteria1, ResultCriteriaList);
                    //set adapter to the listview
                    lv.setAdapter(adapter);

                    //set total score values to textview
                    txtTotal.setText("Score : " + score+"/65");

                    //if score less than 26
                    if(score<26)
                    {
                        //show first degree
                        txtDegree.setText("First Degree");
                    }
                    // if score between 26 and 39
                    else if (score >= 26 && score <=39)
                    {
                        //show second degree
                        txtDegree.setText("Second Degree");
                    }
                    // if score between 40 and 59
                    else if (score >= 40 && score <=59)
                    {
                        //show third degree
                        txtDegree.setText("Third Degree");
                    }
                    //if score greater than 60
                    else if(score>60)
                    {
                        //show fourth degree
                        txtDegree.setText("Fourth Degree");
                    }
            }

        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
            Log.v("Error",e.getMessage());
        }
    }
}
