package com.example.yellow.gpssensor;

import android.app.Application;

/**
 * Created by Yellow on 2018-1-3.
 */

public class DataShare extends Application {
    private String username;

    public DataShare(){

    }
    public void setUsername(String name){
        username=name;
    }
    public String getUsername(){
        return username;
    }
}
