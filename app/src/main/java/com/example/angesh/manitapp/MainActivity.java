package com.example.angesh.manitapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener {

    EditText usernameField;
    EditText passwordField;
    TextView changeSignUpModeTextView;
    Boolean signUpModeActive;
    Button signUpButton;
    ImageView logo;
    RelativeLayout relativeLayout;

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {


        if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
            signUpOrLogin(v);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.changeSignUpMode){
            if(signUpModeActive==true){
                signUpModeActive=false;
                changeSignUpModeTextView.setText("Sign Up");
                signUpButton.setText("Log In");

            }
            else{
                signUpModeActive=true;
                changeSignUpModeTextView.setText("Log In");
                signUpButton.setText("Sign Up");
            }
        }
        else if(v.getId()==R.id.logo||v.getId()==R.id.relativeLayout){

            InputMethodManager imm=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }

    }


    public void signUpOrLogin(View view){
        if (signUpModeActive==true) {

            ParseUser user = new ParseUser();
            user.setUsername(String.valueOf(usernameField.getText()));
            user.setPassword(String.valueOf(passwordField.getText()));

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_LONG).show();
                        showUserList();


                    } else {
                        Toast.makeText(getApplicationContext(), e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            ParseUser.logInInBackground(String.valueOf(usernameField.getText()), String.valueOf(passwordField.getText()), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        Log.i("App Info", "Login Successful");
                        showUserList();
                        Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public  void showUserList(){
        Intent i=new Intent(getApplicationContext(),UserList.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        signUpModeActive=true;
        if (ParseUser.getCurrentUser()!=null){
            showUserList();
        }

        usernameField=(EditText)findViewById(R.id.username);
        passwordField=(EditText)findViewById(R.id.password);
        changeSignUpModeTextView=(TextView)findViewById(R.id.changeSignUpMode);
        signUpButton=(Button)findViewById(R.id.signUpButton);
        logo=(ImageView)findViewById(R.id.logo);
        relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout);


        changeSignUpModeTextView.setOnClickListener(this);
        logo.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);

        usernameField.setOnKeyListener(this);
        passwordField.setOnKeyListener(this);


        ParseAnalytics.trackAppOpenedInBackground(getIntent());




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
