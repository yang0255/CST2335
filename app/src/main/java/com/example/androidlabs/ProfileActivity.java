package com.example.androidlabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton mImageButton = null;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.e(ACTIVITY_NAME, "In function: onCreate");
        setContentView(R.layout.activity_profile);

        final Intent intent = getIntent();
        String nameText = intent.getStringExtra("name");
        String emailText = intent.getStringExtra("EMAIL");

        EditText enterName = (EditText)findViewById(R.id.editText5);
        enterName.setText(nameText);

        EditText enterEmail = (EditText)findViewById(R.id.textView5);
        enterName.setText(emailText);

        mImageButton = findViewById(R.id.button2);
        mImageButton.setOnClickListener(a ->dispatchTakePictureIntent());

       // button = findViewById(R.id.button3);
        //button.setOnClickListener(a ->gotoChatRoomActivity());

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function: onDestroy");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        //Log.e(ACTIVITY_NAME, "In function: onActivityResult");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //private void gotoChatRoomActivity() {

       // Log.i(ACTIVITY_NAME, "Go to chat");
        //Intent newIntent = new Intent(ProfileActivity.this, ChatWinow.class);
        //startActivityForResult(newIntent,60);

       // }
    }



