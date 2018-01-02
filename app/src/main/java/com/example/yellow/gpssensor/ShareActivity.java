package com.example.yellow.gpssensor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Yellow on 2018-1-2.
 */

public class ShareActivity extends Activity {
    private String type;
    private String lastActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        type=getIntent().getStringExtra("type");
        lastActivity=getIntent().getStringExtra("last");

        shareContentInit();
    }
    public void shareContentInit(){

    }

    public void share(View view){
        //save and update listviews and gridviews
    }
    public void backToLast(){
        switch (lastActivity){
            case "Map":
                Intent intent1=new Intent(this,MapActivity.class);
                startActivity(intent1);
                break;
            case "Home":
                Intent intent2=new Intent(this,home_page.class);
                startActivity(intent2);
                break;
            case "Quanzi":
                Intent intent3=new Intent(this,GroupActivity.class);
                startActivity(intent3);
        }
        this.finish();
    }
}
