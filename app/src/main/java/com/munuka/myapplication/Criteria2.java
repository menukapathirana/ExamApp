package com.munuka.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Criteria 2 - show rest of the exam criteria
 */
public class Criteria2 extends Activity {

    /**
     * The Helper db object.
     */
    myDbAdapter helper;
    /**
     * The Student id.
     */
    String studentID;
    /**
     * The Score.
     */
    String score;
    /**
     * The Criteria list arraylist.
     */
    ArrayList<String> CriteriaList;
    /**
     * The Inst id.
     */
    String instID;
    /**
     * The Lv listview.
     */
    ListView lv;
    /**
     * The Adapter.
     */
    ArrayAdapter adapter;
    private Button btnNext;
    /**
     * The Dbcl column variable.
     */
    int dbcl=7;
    /**
     * The Tv.
     */
    TextView[] tv;
    /**
     * The Result list arraylist.
     */
    ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String,String>>();
    /**
     * The Ans 1 hashmap.
     */
    HashMap<String, String> ans1 = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_criteria1);
        //Initialize button next
        btnNext = (Button) findViewById(R.id.btnNext);
        //Initialize database object
        helper = new myDbAdapter(this);
        //get Student id, instructor id, and result of the criteria in the resultList arraylist
        studentID = getIntent().getStringExtra("StudentID");;
        instID = getIntent().getStringExtra("insID");
        resultList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("ans");

        //Initilze TextView to the system
        tv = new TextView[]{(TextView) findViewById(R.id.title)};
        //Set text of the textview
        tv[0].setText("Exam Criteria 7-13");
        //Initialize Arraylist for the Criteria
        CriteriaList = new ArrayList<>();
        //Add all the criteria titiles
        CriteriaList.add("7. Strangles front");
        CriteriaList.add("8. Strangles side");
        CriteriaList.add("9. Strangles rear");
        CriteriaList.add("10. Bear hugs Behind (over arms)");
        CriteriaList.add("11. Bear hugs Behind (under arms)");
        CriteriaList.add("12. Bear hugs Front (over arms)");
        CriteriaList.add("13. Bear hugs Front (under arms)");

        //Initialize the listview
        lv = (ListView) findViewById(R.id.criteria_list);
        //set arraylist to the arrayadapter
        adapter = new ArrayAdapter<String>(this, R.layout.list_criteria1, CriteriaList);
        //Pass adapter to the listview
        lv.setAdapter(adapter);


        //set listview item click function
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Pass selected item and pass to the variable
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                //Initialize and assing value to the post variable
                int post = i;
                //pass both variables to the input dialog function
                showInputDialog(selectedItem,post);

            }
        });

        //set button next click function
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Launch next result layout in the system
                Intent i = new Intent(getApplicationContext(),
                        ViewResult.class);
                //Pass StudentID, Instructor ID and ans to the next page
                i.putExtra("StudentID",studentID);
                i.putExtra("insID",instID);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * Show input dialog.
     *
     * @param inputTitle the input title
     * @param pos        the pos
     */
//Input dialog function
    protected void showInputDialog(final String inputTitle,final int pos) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(Criteria2.this);
        View promptView = layoutInflater.inflate(R.layout.layout_criteria_input, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Criteria2.this);
        alertDialogBuilder.setView(promptView);

        //Initialize and assing all the UI componenets
        final TextView scoreTItle = (TextView)promptView.findViewById(R.id.scoreTItle);
        scoreTItle.setText(inputTitle);
        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);

        //set Input min and max values
        editText.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "5")});
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Assing the input dialog score value to the variable score
                        score = editText.getText().toString();
                        if(score.isEmpty())
                        {
                            //If score variable empty show error message
                            editText.setError("Please enter score");
                        }
                        else {
                                if(CriteriaList.get(pos).equals(inputTitle))
                                {
                                    //Check araylist value and input title value is equal or not
                                    String crit = "q"+dbcl;
                                    //set database criteria column value with a starting value dbcl=7
                                    if(crit.equals("")&&score.equals("")&&studentID.equals("")&&instID.equals(""))
                                    {
                                        //IF empty show error message
                                        Toast.makeText(Criteria2.this, "Invalid. Empty values", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        try
                                        {

                                            //Pass db criteria column name and score value to a hash map
                                            ans1.put(crit,score);
                                            //Remove selected item from the adapter
                                            adapter.remove(CriteriaList.get(pos));
                                            //Relaod the adapter
                                            adapter.notifyDataSetChanged();
                                            //increase dbcl varaible with one
                                            dbcl++;

                                            //get the count of the adapter
                                            int adapterCount=adapter.getCount();

                                            //if adapter count is o
                                            if(adapterCount==0)
                                            {
                                                //assing hashmap values to the arraylist resultList
                                                resultList.add(ans1);
                                                //Add exam grading to the the database
                                                helper.UpdateScore(studentID, instID,resultList);
                                                //set textview text
                                                tv[0].setText("Exam Completed");
                                                btnNext.setText("View Result");
                                                //set visible button next to the UI
                                                btnNext.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            Toast.makeText(Criteria2.this,"Database Error",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                            }

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
