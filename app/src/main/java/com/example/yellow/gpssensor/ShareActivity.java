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
        lastActivity=getIntent().getStringExtra("last")

        shareContentInit();
    }
    public void shareContentInit(){

    }

    public void share(View view){

    }
    public void backToHome(){
        if(lastActivity.equals("Map")){
            Intent intent=new Intent(this,MapActivity.class);
            startActivity(intent);
            this.finish();
        }
        else if(lastActivity.equals("Home")){
            Intent intent=new Intent(this,home_page.class);
            startActivity(intent);
            this.finish();
        }
        else if(lastActivity.equals("Quanzi")){
            Intent intent=new Intent(this,Quanzi.class);
            startActivity(intent);
            this.finish();
        }
    }
}
