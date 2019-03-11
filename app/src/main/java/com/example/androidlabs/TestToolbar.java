package com.example.androidlabs;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class TestToolbar extends AppCompatActivity {

    String message = "This is the initial message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar bar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(bar);

        Toast.makeText(this, "Welcome to lab6", Toast.LENGTH_LONG).show();
    }

@Override
    public boolean onCreateOptionsMenu(Menu menu){
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_lab6,menu);
        return true;
}

@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.choice1:
            Toast.makeText(this, message,
                    Toast.LENGTH_LONG).show();
            return true;

        case R.id.choice2:
            alertExample();
            return true;

        case R.id.choice3:
            //Toast.makeText(this, "Go back?", Toast.LENGTH_LONG).show();

        Snackbar snackbar = Snackbar.make(findViewById(R.id.choice3), "Go Back?", Snackbar.LENGTH_LONG)
                .setAction("GoBack", e -> Log.e("Menu Example", "Clicked Undo"));
            snackbar.setAction("GoBack",e -> finish());

            snackbar.show();
            return true;



        case R.id.choice4:
            Toast.makeText(this, "You clicked on the overflow menu",
                    Toast.LENGTH_LONG).show();
            return true;

        default:return super.onOptionsItemSelected(item);
    }

}
    public void alertExample()
    {
        View middle = getLayoutInflater().inflate(R.layout.dialog, null);

        EditText et = (EditText)middle.findViewById(R.id.view_edit_text);

        //btn.setOnClickListener( clk -> et.setText("You clicked my button!"));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Customer Message")
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Accept
                        message = et.getText().toString();
                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                        //message = "This is the initial message";
                    }
                }).setView(middle);

        builder.create().show();
    }

}
