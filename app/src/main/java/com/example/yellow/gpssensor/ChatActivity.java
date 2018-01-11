package com.example.yellow.gpssensor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by asus2 on 2018/1/2.
 */

public class ChatActivity extends AppCompatActivity {
    private MYSQL sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sql=new MYSQL(this);
        init_chat();
    }
    class iitem{
        String I_word;
        String Other_word;
        String I_c;
        String Other_c;
        String I_icon;
        String Other_icon;
    }
    public void init_chat()
    {
        final Intent intent =getIntent();
        final RecyclerView listView=(RecyclerView)findViewById(R.id.list_view);
        Cursor c= sql.get_speaks(intent.getStringExtra("user_id"),intent.getStringExtra("friend_id"));
        ChatViewAdapter listView_adapter = new ChatViewAdapter(this,sql.get_speaks(intent.getStringExtra("user_id"),intent.getStringExtra("friend_id")));
        listView_adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(ChatActivity.this,"dddddd",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                Toast.makeText(ChatActivity.this,"dddddd",Toast.LENGTH_SHORT).show();
            }
        });
        listView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        listView.setAdapter(listView_adapter);
        final EditText chatedit = (EditText)findViewById(R.id.edit_message);
        final Button send =(Button)findViewById(R.id.send_btn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql.new_chat(intent.getStringExtra("user_id"),intent.getStringExtra("friend_id"),chatedit.getText().toString());
                addChatMsgToCloud(intent.getStringExtra("user_id"),intent.getStringExtra("friend_id"),chatedit.getText().toString());
                chatedit.setText("");
                ChatViewAdapter listView_adapter = new ChatViewAdapter(ChatActivity.this,sql.get_speaks(intent.getStringExtra("user_id"),intent.getStringExtra("friend_id")));
                listView.setAdapter(listView_adapter);
            }
        });
    }
    public class List_ViewHolder extends RecyclerView.ViewHolder {

        private TextView I_word;
        private TextView Other_word;
        private CardView I_c;
        private CardView Other_c;
        private ImageView I_icon;
        private ImageView Other_icon;
        private Cursor mList;
        public List_ViewHolder(View view,Cursor List) {
            super(view);
            mList=List;
            I_word = (TextView)view.findViewById(R.id.send_message);
            Other_word =(TextView)view.findViewById(R.id.get_message);
            I_c = (CardView)view.findViewById(R.id.my_word);
            Other_c =(CardView)view.findViewById(R.id.others_word);
            I_icon =(ImageView) view.findViewById(R.id.avatar_my);
            Other_icon =(ImageView) view.findViewById(R.id.avatar_other);
        }
        public Cursor getItem(int position) {
            mList.moveToFirst();
            mList.move(position);
            return mList;
        }
        public void bindData(int position) {
            getItem(position);
            if(mList.getString(2).equals("I"))
            {
                this.Other_icon.setVisibility(View.INVISIBLE);
                this.Other_c.setVisibility(View.INVISIBLE);
                this.I_icon.setVisibility(View.VISIBLE);
                this.I_c.setVisibility(View.VISIBLE);
                this.I_word.setText(mList.getString(4));
                Uri ui=Uri.parse(sql.get_user_icon(mList.getString(1)));
                this.I_icon.setImageURI(ui);
            }
            else
            {
                Uri ui=Uri.parse(sql.get_user_icon(mList.getString(3)));
                this.Other_icon.setVisibility(View.VISIBLE);
                this.Other_c.setVisibility(View.VISIBLE);
                this.I_icon.setVisibility(View.INVISIBLE);
                this.I_c.setVisibility(View.INVISIBLE);
                this.Other_icon.setImageURI(ui);
                this.Other_word.setText(mList.getString(4));
            }
        }
    }
    public interface OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position);
    }
    public class ChatViewAdapter extends RecyclerView.Adapter<List_ViewHolder>  {
        private Cursor mList;
        private Context mContext;
        private OnItemClickListener myonItemClickListener = null;
        public ChatViewAdapter(Context context, Cursor list) {
            this.mContext = context;
            this.mList = list;
        }
        @Override
        public List_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
            return new List_ViewHolder(itemView,mList);
        }
        @Override
        public void onBindViewHolder(final List_ViewHolder holder, int position) {
            holder.bindData(position);
            if (myonItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myonItemClickListener.onClick(holder.getAdapterPosition());
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        myonItemClickListener.onLongClick(holder.getAdapterPosition());
                        return false;
                    }
                });
            }
        }
        @Override
        public int getItemCount() {
            return mList.getCount();
        }
        public void setOnItemClickListener(OnItemClickListener OnItemClickListener) {
            this.myonItemClickListener = OnItemClickListener;
        }
    }


    public void addChatMsgToCloud(String sendId,String receiveId,String msg){
        ChatMsg chatMsg=new ChatMsg();
        chatMsg.setMsg(msg);
        chatMsg.setR_id(receiveId);
        chatMsg.setS_id(sendId);
        chatMsg.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null) Toast.makeText(ChatActivity.this,"成功发布到云端",Toast.LENGTH_SHORT).show();
                else Toast.makeText(ChatActivity.this,"同步到云端失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void queryFromCloud(){
        BmobQuery<ChatMsg> query=new BmobQuery<ChatMsg>();
        String bql="select * from ChatMsg where s_id = "+""+" and r_id = "+"";
        query.setSQL(bql);
        query.doSQLQuery(new SQLQueryListener<ChatMsg>() {
            @Override
            public void done(BmobQueryResult<ChatMsg> bmobQueryResult, BmobException e) {
                if(e==null){
                    List<ChatMsg> list=bmobQueryResult.getResults();
                    if(list!=null&&list.size()>0){
                        resultOut(list);
                    }
                }
                else Toast.makeText(ChatActivity.this,"云端查询失败"+e.getErrorCode(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<ChatMsg> resultOut(List<ChatMsg> l){
        return l;
    }


    public class ChatMsg extends BmobObject {
        private String s_id;//id
        private String r_id;//id
        private String msg;

        public String getMsg() {
            return msg;
        }

        public String getR_id() {
            return r_id;
        }

        public String getS_id() {
            return s_id;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setR_id(String receive) {
            this.r_id = receive;
        }
        public void setS_id(String send) {
            this.s_id=send;
        }
    }
}