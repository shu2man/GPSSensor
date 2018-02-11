package com.example.yellow.gpssensor;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Yellow on 2018-2-11.
 */

public class ChatMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

    }
    public void initChatMainData(){
        DataShare ds=((DataShare)getApplicationContext());
        //获取好友信息
        ds.initFriends();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.chat_main_recyclerview);
        MyRecyclerViewAdapter adapter=new MyRecyclerViewAdapter(ds);

        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                goToChat(position);
            }
        });

    }
    public void goToChat(int position){
        DataShare ds=((DataShare)getApplicationContext());
        Intent in=new Intent(ChatMainActivity.this,ChatActivity.class);
        in.putExtra("user_id",ds.getUserid());
        in.putExtra("friend_id",ds.getFriUser().get(position));
        startActivity(in);
    }

}
