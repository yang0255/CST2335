package com.example.androidlabs;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


public class ChatRoomActivity extends AppCompatActivity {

    int numObjects = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab4);

        //ListAdapter adt = new MyArrayAdapter(new String[] {"A", "B", "C"});
        //ListAdapter adt = new MyOwnAdapter();
        ChatAdapter chat = new ChatAdapter();

        ListView theList = (ListView)findViewById(R.id.listView);

       /* SwipeRefreshLayout refresher = (SwipeRefreshLayout)findViewById(R.id.refresher) ;
        refresher.setOnRefreshListener(()-> {
            numObjects *= 2;
            chat.notifyDataSetChanged();
            refresher.setRefreshing( false );
        });*/

        //This listens for items being clicked in the list view
        theList.setOnItemClickListener(( parent,  view,  position,  id) -> {
            Log.e("you clicked on :" , "item "+ position);

            numObjects = 20;
            chat.notifyDataSetChanged();
        });

        Button sendButton = (Button)findViewById(R.id.SEND);
        sendButton.setOnClickListener( c -> {
            EditText userTyped = (EditText)findViewById(R.id.editText1);
            String userType = userTyped.getText().toString();
            userTyped.setText("");
            chat.messages.add(new Message(userType,1));
            chat.notifyDataSetChanged();
        });

        Button receiveButton = (Button)findViewById(R.id.receive);
        receiveButton.setOnClickListener( c -> {
            EditText userTyped = (EditText)findViewById(R.id.editText1);
            String userType = userTyped.getText().toString();
            userTyped.setText("");
            chat.messages.add(new Message(userType,2));
            chat.notifyDataSetChanged();
        });

        theList.setAdapter(chat);
    }

    //This class needs 4 functions to work properly:
    protected class ChatAdapter extends BaseAdapter
    {
        List<Message> messages = new ArrayList<>();
        @Override
        public int getCount() {
            return messages.size();
        }

        public Object getItem(int position){
            return messages.get(position).getMessage();//"\nItem "+ (position+1) + "\nSub Item "+ (position+1) +"\n";
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            int type = messages.get(position).getType();
            View newView;
            if(type ==1){
                newView = inflater.inflate(R.layout.listview_item_receive, parent, false );
            }else{
                newView = inflater.inflate(R.layout.listview_item_send, parent, false );
            }
            TextView rowText = (TextView)newView.findViewById(R.id.edit);
            String stringToShow = getItem(position).toString();
            rowText.setText( stringToShow );
            return newView;
        }

        public long getItemId(int position)
        {
            return position;
        }
    }

    /*/This class needs 4 functions to work properly:
    protected class MyOwnAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return numObjects;
        }

        public Object getItem(int position){
            return "\nItem "+ (position+1) + "\nSub Item "+ (position+1) +"\n";
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(R.layout.single_row, parent, false );

            TextView rowText = (TextView)newView.findViewById(R.id.textOnRow);
            String stringToShow = getItem(position).toString();
            rowText.setText( stringToShow );
            //return the row:
            return newView;
        }

        public long getItemId(int position)
        {
            return position;
        }
    }*/
    //A copy of ArrayAdapter. You just give it an array and it will do the rest of the work.
    protected class MyArrayAdapter<E> extends BaseAdapter
    {
        private List<E> dataCopy = null;

        //Keep a reference to the data:
        public MyArrayAdapter(List<E> originalData)
        {
            dataCopy = originalData;
        }

        //You can give it an array
        public MyArrayAdapter(E [] array)
        {
            dataCopy = Arrays.asList(array);
        }


        //Tells the list how many elements to display:
        public int getCount()
        {
            return dataCopy.size();
        }


        public E getItem(int position){
            return dataCopy.get(position);
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            //get an object to load a layout:
            LayoutInflater inflater = getLayoutInflater();

            //Recycle views if possible:
            TextView root = (TextView)old;
            //If there are no spare layouts, load a new one:
            if(old == null)
                root = (TextView)inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            //Get the string to go in row: position
            String toDisplay = getItem(position).toString();

            //Set the text of the text view
            root.setText(toDisplay);

            //Return the text view:
            return root;
        }


        //Return 0 for now. We will change this when using databases
        public long getItemId(int position)
        {
            return 0;
        }
    }

    //Message class
    protected class Message {
        String message;
        int type;

        public Message(){
        }
        private Message(String message,int type){
            this.message = message;
            this.type = type;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        private String getMessage(){
            return this.message;
        }
        public void setType(int type) {
            this.type = type;
        }
        private int getType(){
            return this.type;
        }
    }
}


