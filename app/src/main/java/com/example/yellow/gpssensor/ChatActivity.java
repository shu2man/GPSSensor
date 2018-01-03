package com.example.yellow.gpssensor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus2 on 2018/1/2.
 */

public class ChatActivity extends AppCompatActivity {
    private MYSQL sql=new MYSQL(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init_chat();
    }
    public void init_chat()
    {
        final Intent intent =getIntent();
        final ListView listView=(ListView)findViewById(R.id.list_view);
        ChatViewAdapter listView_adapter = new ChatViewAdapter(this,sql.get_speaks(intent.getStringExtra("user_id"),intent.getStringExtra("friend_id")));
        listView.setAdapter(listView_adapter);
        final EditText chatedit = (EditText)findViewById(R.id.edit_message);
        final Button send =(Button)findViewById(R.id.send_btn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql.new_chat(intent.getStringExtra("user_id"),intent.getStringExtra("friend_id"),chatedit.getText().toString());
                chatedit.setText("");
                ChatViewAdapter listView_adapter = new ChatViewAdapter(ChatActivity.this,sql.get_speaks(intent.getStringExtra("user_id"),intent.getStringExtra("friend_id")));
                listView.setAdapter(listView_adapter);
            }
        });
    }
    public class ChatViewAdapter extends BaseAdapter {

        //数据源
        private Cursor mList;

        //列数

        private Context mContext;

        public ChatViewAdapter(Context context, Cursor list) {
            super();
            this.mContext = context;
            this.mList = list;
        }

        /**
         * 这部很重要
         *(核心)
         * @return listview的行数
         */
        @Override
        public int getCount() {
            try{
                return mList.getCount();
            }catch (Exception e){
                return 0;
            }
        }
        /*
        @Override
        public int getCount() {
            int count = mList.size() / mColumn;
            if (mList.size() % mColumn > 0) {
                count++;
            }
            return count;
        }*/

        @Override
        public Cursor getItem(int position) {
            mList.move(position);
            return mList;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_chat, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            getItem(position);
            //更新数据源(核心)
            if(mList.getString(2).equals("I"))
            {
                holder.I_word.setText(sql.get_user_name(mList.getString(4)));
                holder.I_icon.setImageResource(Integer.getInteger(sql.get_user_icon(mList.getString(1))));
                holder.Other_icon.setVisibility(View.INVISIBLE);
                holder.Other_word.setVisibility(View.INVISIBLE);
                holder.I_icon.setVisibility(View.VISIBLE);
                holder.I_word.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.Other_icon.setImageResource(Integer.getInteger(sql.get_user_icon(mList.getString(3))));
                holder.I_word.setText(sql.get_user_name(mList.getString(4)));
                holder.Other_icon.setVisibility(View.VISIBLE);
                holder.Other_word.setVisibility(View.VISIBLE);
                holder.I_icon.setVisibility(View.INVISIBLE);
                holder.I_word.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }

        class ViewHolder {
            TextView I_word;
            TextView Other_word;
            ImageView I_icon;
            ImageView Other_icon;
            public ViewHolder(View view) {
                I_word = (TextView)findViewById(R.id.my_word);
                Other_word =(TextView)findViewById(R.id.others_word);
                I_icon =(ImageView) findViewById(R.id.avatar_my);
                Other_icon =(ImageView) findViewById(R.id.avatar_other);
                view.setTag(this);
            }
        }
    }
}
