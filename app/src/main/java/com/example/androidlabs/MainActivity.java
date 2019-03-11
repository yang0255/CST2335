package com.example.androidlabs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


/*        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, TestToolbar.class);
                   // intent.setClass(MainActivity.this, TestToolbar.class);
                    startActivity(intent);*//*


                }
            });*/
        Button button = (Button) findViewById(R.id.button);
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        button.setOnClickListener(d -> {
            startActivityForResult(intent,202);
        });

        }
    }
