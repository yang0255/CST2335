package com.example.androidlabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Lab8_activity_message extends Fragment {
    private boolean isTablet;
    private Bundle dataFromActivity;
    private String message;
    private long id;
    private long position;
    private boolean isSent;

    public void setTablet(boolean tablet) { isTablet = tablet; }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivitylab5_lab8.ITEM_ID );
        position = dataFromActivity.getLong(ChatRoomActivitylab5_lab8.ITEM_POSITION );
        isSent = dataFromActivity.getInt(ChatRoomActivitylab5_lab8.ITEM_ISSEND ) ==1 ? true : false;

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.lab8_2, container, false);

        //show the message
        TextView message = (TextView)result.findViewById(R.id.message);
        message.setText(dataFromActivity.getString(ChatRoomActivitylab5_lab8.ITEM_MESSAGE));

        //show the id:
        TextView idView = (TextView)result.findViewById(R.id.idText);
        idView.setText("DatabaseId=" + id + "       Position=" + position + "      isSent=" + isSent);

        // get the delete button, and add a click listener:
        Button deleteButton = (Button)result.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener( clk -> {

            if(isTablet) { //both the list and details are on the screen:
                ChatRoomActivitylab5_lab8 parent = (ChatRoomActivitylab5_lab8) getActivity();
                parent.deleteMessageId((int)id, (int)position); //this deletes the item and updates the list


                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            //for Phone:
            else //You are only looking at the details, you need to go back to the previous list page
            {
                EmptyActivity parent = (EmptyActivity) getActivity();
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra(ChatRoomActivitylab5_lab8.ITEM_ID, dataFromActivity.getLong(ChatRoomActivitylab5_lab8.ITEM_ID ));
                backToFragmentExample.putExtra(ChatRoomActivitylab5_lab8.ITEM_POSITION, dataFromActivity.getLong(ChatRoomActivitylab5_lab8.ITEM_POSITION ));
                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
            }
        });
        return result;
    }
}
