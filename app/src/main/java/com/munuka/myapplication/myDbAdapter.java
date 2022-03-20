package com.munuka.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * The type My db adapter.
 */
public class myDbAdapter {
    /**
     * The Myhelper object.
     */
    myDbHelper myhelper;

    /**
     * Instantiates a new My db adapter.
     * Initialize dataabase object after create db and the version
     * @param context the context
     */

    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    /**
     * Insert data long.
     * Insert user details to the db
     * @param name the name
     * @param pass the pass
     * @return the long
     */

    public long insertData(String name, String pass)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.MyPASSWORD, pass);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    /**
     * Check user exist boolean.
     * Check user exit or not in the db when login to the system
     * @param name     the name
     * @param Password the password
     * @return the boolean
     */

    public boolean checkUserExist(String name, String Password){
        String[] columns = {myDbHelper.UID};
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String selection = myDbHelper.NAME+"=? and "+myDbHelper.MyPASSWORD + " = ?";
        String[] selectionArgs = {name, Password};

        Cursor cursor = db.query(myhelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();
        if(count > 0){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Get student data array list.
     * Get Student Details who ready for the exam
     * @return the array list
     */

    public ArrayList<HashMap<String, String>> getStudentData(){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = " SELECT " + myDbHelper.StudentID + " , " + myDbHelper.StudentName + " FROM " + myDbHelper.TABLE_STUDENT + " WHERE " + myDbHelper.ExamAttemtp + " = " + " 0 ";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> s_user = new HashMap<>();
            s_user.put(myDbHelper.StudentID,cursor.getString(cursor.getColumnIndex(myDbHelper.StudentID)));
            s_user.put(myDbHelper.StudentName,cursor.getString(cursor.getColumnIndex(myDbHelper.StudentName)));
            userList.add(s_user);
        }
        return  userList;
    }

    /**
     * Get all student data array list.
     *
     * @return the array list
     */

    public ArrayList<HashMap<String, String>> getAllStudentData(){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = " SELECT * FROM " + myDbHelper.TABLE_STUDENT ;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> s_user = new HashMap<>();
            s_user.put(myDbHelper.StudentID,cursor.getString(cursor.getColumnIndex(myDbHelper.StudentID)));
            s_user.put(myDbHelper.StudentName,cursor.getString(cursor.getColumnIndex(myDbHelper.StudentName)));

           String ex = cursor.getString(cursor.getColumnIndex(myDbHelper.ExamAttemtp));

           //if i means user attempt to the exam
           if(ex.equals("1"))
           {
               s_user.put(myDbHelper.ExamAttemtp,"Attempted");
           }
           //if 0 means user not attempt to the exam
           else if(ex.equals("0"))
           {
               s_user.put(myDbHelper.ExamAttemtp,"Not Attempt");
           }
           // add to the arraylist
            userList.add(s_user);
        }
        return  userList;
    }


    /**
     * Get exam data array list.
     * Get Exam Details by student id
     * @param studentID the student id
     * @return the array list
     */
    public ArrayList<HashMap<String, String>> getExamData(String studentID){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        //String query = " SELECT * FROM " + myDbHelper.TABLE_EXAM + " WHERE " + myDbHelper.StudentID + " = " + studentID + " AND " + myDbHelper.NAME + " = " + InsID;
        String query = " SELECT * FROM " + myDbHelper.TABLE_EXAM + " WHERE " + myDbHelper.StudentID + " = " + studentID;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> s_user = new HashMap<>();
            s_user.put(myDbHelper.StudentID,cursor.getString(cursor.getColumnIndex(myDbHelper.StudentID)));
            s_user.put(myDbHelper.NAME,cursor.getString(cursor.getColumnIndex(myDbHelper.NAME)));
            s_user.put(myDbHelper.date,cursor.getString(cursor.getColumnIndex(myDbHelper.date)));
            s_user.put(myDbHelper.q1,cursor.getString(cursor.getColumnIndex(myDbHelper.q1)));
            s_user.put(myDbHelper.q2,cursor.getString(cursor.getColumnIndex(myDbHelper.q2)));
            s_user.put(myDbHelper.q3,cursor.getString(cursor.getColumnIndex(myDbHelper.q3)));
            s_user.put(myDbHelper.q4,cursor.getString(cursor.getColumnIndex(myDbHelper.q4)));
            s_user.put(myDbHelper.q5,cursor.getString(cursor.getColumnIndex(myDbHelper.q5)));
            s_user.put(myDbHelper.q6,cursor.getString(cursor.getColumnIndex(myDbHelper.q6)));
            s_user.put(myDbHelper.q7,cursor.getString(cursor.getColumnIndex(myDbHelper.q7)));
            s_user.put(myDbHelper.q8,cursor.getString(cursor.getColumnIndex(myDbHelper.q8)));
            s_user.put(myDbHelper.q9,cursor.getString(cursor.getColumnIndex(myDbHelper.q9)));
            s_user.put(myDbHelper.q10,cursor.getString(cursor.getColumnIndex(myDbHelper.q10)));
            s_user.put(myDbHelper.q11,cursor.getString(cursor.getColumnIndex(myDbHelper.q11)));
            s_user.put(myDbHelper.q12,cursor.getString(cursor.getColumnIndex(myDbHelper.q12)));
            s_user.put(myDbHelper.q13,cursor.getString(cursor.getColumnIndex(myDbHelper.q13)));
            userList.add(s_user);
        }
        return  userList;
    }

    /**
     * Update student exam status.
     *
     * @param StudentID the student id
     */
    public void updateStudentExamStatus(String StudentID)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.ExamAttemtp,"1");
        String[] whereArgs = {StudentID};
        db.update(myDbHelper.TABLE_STUDENT,contentValues, myDbHelper.StudentID+" = ?",whereArgs );
    }

    /**
     * Update student.
     *
     * @param OldStudentID the old student id
     * @param NewStudentID the new student id
     * @param StudentName  the student name
     */

    public void updateStudent(String OldStudentID,String NewStudentID,String StudentName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.StudentID,NewStudentID);
        contentValues.put(myDbHelper.StudentName,StudentName);
        String[] whereArgs = {OldStudentID};
        db.update(myDbHelper.TABLE_STUDENT,contentValues, myDbHelper.StudentID+" = ?",whereArgs );
    }

    /**
     * Update score.
     * End of the exam student exam results will add to the db
     * @param StudentID    the student id
     * @param InstructorID the instructor id
     * @param val          the val
     */

    public void UpdateScore(String StudentID,String InstructorID,ArrayList<HashMap<String, String>> val)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(myDbHelper.StudentID,StudentID);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        contentValues.put(myDbHelper.date,currentDateTimeString);
        contentValues.put(myDbHelper.NAME,InstructorID);


        for (HashMap<String, String> hashMap : val) {

            for (String key : hashMap.keySet()) {
                contentValues.put(key,hashMap.get((key)));
            }
        }
        db.insert(myDbHelper.TABLE_EXAM, null , contentValues);
    }


    /**
     * The type My db helper.
     * Create a class with inherit sqliteopenhelper
     */
    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name of the instructor
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="id";     // Column I (mytable)
        private static final String NAME = "Name";    //Column II (mytable)
        private static final String MyPASSWORD= "Password";    // Column III (mytable)
        //Create table query of the mytable
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+ MyPASSWORD+" VARCHAR(225));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;

        //Insert user details to the database
        private static final String INSERT_USER = " INSERT INTO " + TABLE_NAME + " VALUES ('1','1','1') ";


        //Create table Student and insert 10 student details to the database
        private static final String TABLE_STUDENT = "Student";
        private static final String StudentID ="sid";
        private static final String StudentName="sname";
        private static final String ExamAttemtp="examattemp";

        //Create student table query
        private static final String CREATE_TABLE_STUDENT = "CREATE TABLE "+TABLE_STUDENT+
                " ("+StudentID+" VARCHAR(100), "+StudentName+" VARCHAR(255), "+ExamAttemtp+" VARCHAR(255));";
        private static final String DROP_TABLE1 ="DROP TABLE IF EXISTS "+TABLE_STUDENT;


        //insert student details query
        private static final String INSERT_STUDENT1 = " INSERT INTO " + TABLE_STUDENT + " VALUES ('453625','kevin','0') ";
        private static final String INSERT_STUDENT2 = " INSERT INTO " + TABLE_STUDENT + " VALUES ('342728','Shane','0') ";
        private static final String INSERT_STUDENT3 = " INSERT INTO " + TABLE_STUDENT + " VALUES ('345454','Jeorge','0') ";
        private static final String INSERT_STUDENT4 = " INSERT INTO " + TABLE_STUDENT + " VALUES ('546423','Dave','0') ";
        private static final String INSERT_STUDENT5 = " INSERT INTO " + TABLE_STUDENT + " VALUES ('234223','Henry','0') ";
        private static final String INSERT_STUDENT6 = " INSERT INTO " + TABLE_STUDENT + " VALUES ('345234','Sherry','0') ";
        private static final String INSERT_STUDENT7 = " INSERT INTO " + TABLE_STUDENT + " VALUES ('234232','Swan','0') ";
        private static final String INSERT_STUDENT8 = " INSERT INTO " + TABLE_STUDENT + " VALUES ('234234','Peter','0') ";
        private static final String INSERT_STUDENT9 = " INSERT INTO " + TABLE_STUDENT + " VALUES ('464453','Noel','0') ";
        private static final String INSERT_STUDENT10 = " INSERT INTO " + TABLE_STUDENT + " VALUES ('564374','Jessica','0') ";



        //Create Exam table for the 10 Criterias
        private static final String TABLE_EXAM = "Exam";
        private static final String date="date";
        private static final String q1="q1";
        private static final String q2="q2";
        private static final String q3="q3";
        private static final String q4="q4";
        private static final String q5="q5";
        private static final String q6="q6";
        private static final String q7="q7";
        private static final String q8="q8";
        private static final String q9="q9";
        private static final String q10="q10";
        private static final String q11="q11";
        private static final String q12="q12";
        private static final String q13="q13";

        //Create exam table query
        private static final String CREATE_TABLE_EXAM = "CREATE TABLE "+TABLE_EXAM+
                " ("+StudentID+" VARCHAR(100), "+date+" VARCHAR(255), "+NAME+" VARCHAR(255), "+q1+" VARCHAR(10), "+q2+" VARCHAR(10), "+q3+" VARCHAR(10), "+q4+" VARCHAR(10), "+q5+" VARCHAR(10), "+q6+" VARCHAR(10), "+q7+" VARCHAR(10), "+q8+" VARCHAR(10), "+q9+" VARCHAR(10), "+q10+" VARCHAR(10), "+q11+" VARCHAR(10), "+q12+" VARCHAR(10), "+q13+" VARCHAR(10));";
        private static final String DROP_TABLE_EXAM ="DROP TABLE IF EXISTS "+TABLE_EXAM;





        private Context context;

        /**
         * Instantiates a new My db helper.
         *
         * @param context the context
         */
        public myDbHelper(Context context) {
            //set db name and version
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                //execute all the query in the db
                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_TABLE_STUDENT);
                db.execSQL(CREATE_TABLE_EXAM);

                db.execSQL(INSERT_USER);
                db.execSQL(INSERT_STUDENT1);
                db.execSQL(INSERT_STUDENT2);
                db.execSQL(INSERT_STUDENT3);
                db.execSQL(INSERT_STUDENT4);
                db.execSQL(INSERT_STUDENT5);
                db.execSQL(INSERT_STUDENT6);
                db.execSQL(INSERT_STUDENT7);
                db.execSQL(INSERT_STUDENT8);
                db.execSQL(INSERT_STUDENT9);
                db.execSQL(INSERT_STUDENT10);


            } catch (Exception e) {
                Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Toast.makeText(context,"OnUpgrade",Toast.LENGTH_SHORT).show();
                //drop tables when upgrade
                db.execSQL(DROP_TABLE);
                db.execSQL(DROP_TABLE1);
                db.execSQL(DROP_TABLE_EXAM);
                onCreate(db);
            }catch (Exception e) {
                Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
