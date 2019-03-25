package com.example.androidlabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MessageActivity extends AppCompatActivity {

        protected long id;
        protected String message;
        //protected int type;
        boolean isSent;

        public MessageActivity(){
        }
        public MessageActivity(String message,boolean isSent,long id){
            this.id = id;
            this.message = message;
            //this.type = type;
            this.isSent = isSent;
        }

        public void update(String message, boolean isSent){
            this.message = message;
            this.isSent = isSent;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public String getMessage(){
            return this.message;
        }
        /*        public void setType(int type) {
                    this.type = type;
                }
                public int getType(){
                    return this.type;
                }*/
        public void setId(long id){
            this.id = id;
        }
        public long getId(){
            return this.id;
        }

        public void setIsSent(boolean isSent) {
            this.isSent = isSent;
        }
        public boolean getIsSent(){
            return this.isSent;
        }
    }
