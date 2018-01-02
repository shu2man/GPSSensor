package com.example.yellow.gpssensor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class welcome extends AppCompatActivity {
    final private Handler welcome_bar_handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what==233)
            {
                final ProgressBar progressBar = (ProgressBar)findViewById(R.id.welcome_bar_progress);
                progressBar.setProgress(progressBar.getProgress()+1);
                if (progressBar.getProgress()==progressBar.getMax()-1)
                {
                    progressBar.setProgress(progressBar.getProgress()+1);
                    try {
                        Thread.sleep(200);
                    }catch (InterruptedException e){}
                    Intent intent =new Intent(welcome.this,login.class);
                    startActivity(intent);
                    welcome.this.finish();
                }
            }
        }
    };
    public void welcome_bar_animation()
    {
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.welcome_bar_progress);
        Thread thread = new Thread(){
            @Override
            public void run(){
                while (true){
                    try {
                        Thread.sleep(5);
                    }catch (InterruptedException e){}
                    Message mas = welcome_bar_handler.obtainMessage(233);
                    mas.sendToTarget();
                }
            }
        };
        thread.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcome_bar_animation();
    }


}
