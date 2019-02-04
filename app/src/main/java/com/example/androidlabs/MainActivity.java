package com.example.androidlabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "EMAIL";
    EditText emailTextView;
    EditText passwordTextView;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear);

        // setContentView(R.layout.activity_main);
        //setContentView(R.layout.actvity_main_linear);
        //setContentView(R.layout.activity_main_grid);
        //  setContentView(R.layout.activity_main_relative);
        sp = getSharedPreferences("Lab3", MODE_PRIVATE);
        emailTextView = (EditText) findViewById(R.id.editText1);
        //passwordTextView = (EditText)findViewById(R.id.editText2);

        // String name = sp.getString(USER_NAME, "Default value");
        String email = sp.getString(USER_EMAIL, "");

        // emailTextView.setText(name);
        emailTextView.setText(email);

        Button btnLogin = findViewById(R.id.button5);

        btnLogin.setOnClickListener( v -> {
            Intent profilePage = new Intent(MainActivity.this, ProfileActivity.class);


            // get email user typed
            String emailTyped = emailTextView.getText().toString();
            profilePage.putExtra(USER_EMAIL, emailTyped);

            startActivity(profilePage);
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        sp = getSharedPreferences("Lab3", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String emailText = emailTextView.getText().toString();
        //String passwordText = passwordTextView.getText().toString();


        //editor.putString(USER_NAME, emailText);
        editor.putString(USER_EMAIL, emailText);

        editor.commit();


    }
}


