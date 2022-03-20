package com.munuka.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The type Signup activity.
 */
public class SignupActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    /**
     * The Name.
     */
    String name, /**
     * The Id.
     */
    ID, /**
     * The Password.
     */
    password;

    /**
     * The Helper.
     */
    myDbAdapter helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);

        //Initialize all UI component to the actvity
        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        //Initialize db object
        helper = new myDbAdapter(this);

        //set button tegister click function
        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //pass input text values to the variables
                name = inputFullName.getText().toString().trim();
                ID = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();

                //check username and password empty or not
                if (!name.isEmpty() && !password.isEmpty()) {
                    //if not empty insert user details to the db
                    helper.insertData(name,password);
                    //show message that user registered successfully
                    Toast.makeText(getApplicationContext(),
                            "Register Success", Toast.LENGTH_SHORT)
                            .show();
                    //Lauch login activity
                    Intent i = new Intent(getApplicationContext(),
                            LoginActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    //if not show error message
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        /**
         * if user wants to go the login activity click login button by this function
         */
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}

