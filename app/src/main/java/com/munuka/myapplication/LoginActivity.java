package com.munuka.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The type Login activity.
 */
public class LoginActivity extends Activity {
    private EditText inputID;
    private EditText inputPassword;
    /**
     * The Name.
     */
    public String Name, /**
     * The Password.
     */
    Password;
    private Button btnLogin,btnLinkToRegisterScreen;
    /**
     * The Helper.
     */
    myDbAdapter helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        /*This is main class - Initialize UI components in the activity*/

        inputID = (EditText) findViewById(R.id.name);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        helper = new myDbAdapter(this);

        btnLinkToRegisterScreen  = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        //If instructor not registered, he/she can register througn this button
        btnLinkToRegisterScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        SignupActivity.class);
                startActivity(i);
                finish();
            }
        });

        //Once instructor registerd to the system can login to the application
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass username and password values to the varaibles
                Name = inputID.getText().toString().trim();
                Password = inputPassword.getText().toString().trim();

                //Check the user is exit or not in the database
                boolean isExist = helper.checkUserExist(Name,Password);

                //If Username or Password empty will show a error message
                if(Name.isEmpty() || Password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
                //If user exists user can login to the system and go to the main page in the applicaton
                else if(isExist){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //Pass username to the next activity
                    intent.putExtra("username", Name);
                    startActivity(intent);
                    finish();
                } else {
                    //remove the password content and show the error message
                    inputPassword.setText(null);
                    Toast.makeText(LoginActivity.this, "Login failed. Invalid username or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
