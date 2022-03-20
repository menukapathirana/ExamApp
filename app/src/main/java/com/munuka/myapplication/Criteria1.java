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
 * The type Criteria 1 - show first 6 exam criteria
 */
public class Criteria1 extends Activity {

    /**
     * The Helper object database.
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
     * The Listview lv.
     */
    ListView lv;
    /**
     * The Adapter arrayadapter.
     */
    ArrayAdapter adapter;
    private Button btnNext;
    /**
     * The Dbcl variable.
     */
    int dbcl=1;
    /**
     * The Tv textview.
     */
    TextView[] tv;
    /**
     * The Result list arraylist.
     */
    ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String,String>>();
    /**
     * The Ans hashmap.
     */
    HashMap<String, String> ans = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_criteria1);
        //Initialize the button next
        btnNext = (Button) findViewById(R.id.btnNext);

        //Initialize database class ojbect
        helper = new myDbAdapter(this);

        //get student ID and Instructor ID from the Student class
        studentID = getIntent().getStringExtra("StudentID");;
        instID = getIntent().getStringExtra("insID");

        //Initialize the TextView
        tv = new TextView[]{(TextView) findViewById(R.id.title)};
        //Set text view text
        tv[0].setText("Exam Criteria 1-6");
        //Add first six criteria in Arraylist
        CriteriaList = new ArrayList<>();
        //Add all the criteria list
        CriteriaList.add("1. Aggressive handshake");
        CriteriaList.add("2. Inside wrist grip");
        CriteriaList.add("3. Outside wrist grip");
        CriteriaList.add("4. Both hands holding wrist");
        CriteriaList.add("5. Both wrists held");
        CriteriaList.add("6. Headlock side");

        //Initialize the listview
        lv = (ListView) findViewById(R.id.criteria_list);
        //set arraylist to the arrayadapter
        adapter = new ArrayAdapter<String>(this, R.layout.list_criteria1, CriteriaList);
        //Pass adapter to the listview
        lv.setAdapter(adapter);

        //set button next click function
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Launch next set of criteria layout in the system
                Intent i = new Intent(getApplicationContext(),
                        Criteria2.class);
                //Pass StudentID, Instructor ID and ans to the next page
                i.putExtra("StudentID",studentID);
                i.putExtra("insID",instID);
                i.putExtra("ans",resultList);
                startActivity(i);
                finish();
            }
        });



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

    }

    /**
     * Show input dialog.
     *
     * @param inputTitle the input title
     * @param pos        the pos
     */
//Input dialog function
    protected void showInputDialog(final String inputTitle, final int pos) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(Criteria1.this);
        View promptView = layoutInflater.inflate(R.layout.layout_criteria_input, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Criteria1.this);
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
                        //If score variable empty show error message
                        if(score.isEmpty())
                        {
                            editText.setError("Please enter score");
                        }
                        else {
                                //Check araylist value and input title value is equal or not
                                if(CriteriaList.get(pos).equals(inputTitle))
                                {
                                    //set database criteria column value with a starting value dbcl=1
                                    String crit = "q"+dbcl;
                                    //check variables are empty or not
                                    if(crit.equals("")&&score.equals("")&&studentID.equals("")&&instID.equals(""))
                                    {
                                        //IF empty show error message
                                        Toast.makeText(Criteria1.this, "Invalid. Empty values", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        {
                                            try
                                            {
                                                    //Pass db criteria column name and score value to a hash map
                                                    ans.put(crit,score);
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
                                                    resultList.add(ans);
                                                    //set textview text
                                                    tv[0].setText("Exam Criteria 1-6 Completed");
                                                    //set visible button next to the UI
                                                    btnNext.setVisibility(View.VISIBLE);
                                                }
                                            }
                                            catch (Exception e)
                                            {
                                                Toast.makeText(Criteria1.this,"Database Error",Toast.LENGTH_SHORT).show();
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
        //show alert dialog
        alert.show();
    }
}
