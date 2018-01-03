package com.example.yellow.gpssensor;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.StackView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus2 on 2017/12/29.
 */

public class home_page extends AppCompatActivity {
    private MYSQL sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        sql=new MYSQL(this);
        init_homepage();
        StackView stackView =(StackView)findViewById(R.id.homepage_stackview);
        List<Map<String,Object>> listitems = new ArrayList<>();
        Map<String,Object> item1 = new HashMap<>();
        item1.put("img",R.mipmap.qq);
        listitems.add(item1);
        listitems.add(item1);
        listitems.add(item1);
        Map<String,Object> item2 = new HashMap<>();
        item2.put("img",R.mipmap.weibo);
        listitems.add(item2);
        listitems.add(item2);
        listitems.add(item2);
        Map<String,Object> item3 = new HashMap<>();
        item3.put("img",R.mipmap.weixin);
        listitems.add(item3);
        listitems.add(item3);
        listitems.add(item3);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listitems,R.layout.stackview_layout,new String[]{"img"},new int[]{R.id.stackview_image});
        stackView.setAdapter(simpleAdapter);
    }
    private void init_homepage()
    {
        final TextView remen= (TextView)findViewById(R.id.homepage_remen_text);
        final TextView guanzhu= (TextView)findViewById(R.id.homepage_guanzhu_text);
        final GridView view_remen=(GridView)findViewById(R.id.homepage_rimen_grid);
        final ListView list_guanzhu=(ListView)findViewById(R.id.homepage_guanzhu_list);
        remen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remen.setTextColor((ColorStateList) ((Resources) getBaseContext().getResources()).getColorStateList(R.color.colorAccent));
                guanzhu.setTextColor((ColorStateList) ((Resources) getBaseContext().getResources()).getColorStateList(R.color.colorPrimary));
                view_remen.setVisibility(View.VISIBLE);
                list_guanzhu.setVisibility(View.INVISIBLE);
            }
        });
        guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guanzhu.setTextColor((ColorStateList) ((Resources) getBaseContext().getResources()).getColorStateList(R.color.colorAccent));
                remen.setTextColor((ColorStateList) ((Resources) getBaseContext().getResources()).getColorStateList(R.color.colorPrimary));
                list_guanzhu.setVisibility(View.VISIBLE);
                view_remen.setVisibility(View.INVISIBLE);
            }
        });
        FatherViewAdapter guanzhu_adapter = new FatherViewAdapter(this,sql.select_guanzhu_all());
        remenViewAdapter remen_adapter = new remenViewAdapter(this,sql.select_guanzhu_all());
        view_remen.setAdapter(remen_adapter);
        list_guanzhu.setAdapter(guanzhu_adapter);
    }

    public class FatherViewAdapter extends BaseAdapter {

        //数据源
        private Cursor mList;

        //列数

        private Context mContext;

        public FatherViewAdapter(Context context,Cursor item) {
            super();
            this.mContext = context;
            this.mList = item;
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
            return  mList;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.care_item, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //更新数据源(核心)
            try{
                holder.img.setImageURI(Uri.parse(getItem(position).getString(2)));
                holder.name.setText(sql.get_user_name(getItem(position).getString(1)));
                holder.time.setText(getItem(position).getString(3));
                holder.data.setText(getItem(position).getString(4));
                holder.zan.setText(getItem(position).getString(5));
                holder.tiaozhuan.setText(getItem(position).getString(6));
                holder.gadapter.setmList(sql.select_pic(getItem(position).getString(7)));
                holder.ladapter.setmList(sql.select_pinglun(getItem(position).getString(8)));
            }catch (Exception e){}
            holder.gadapter.notifyDataSetChanged();
            holder.ladapter.notifyDataSetChanged();
            return convertView;
        }

        class ViewHolder {
            ImageView img;
            TextView  name;
            TextView  time;
            TextView  data;
            TextView  zan;
            TextView  tiaozhuan;
            GridView gridView;
            ListView listView;
            ListViewAdapter ladapter;
            GridViewAdapter gadapter;

            public ViewHolder(View view) {
                img=(ImageView) findViewById(R.id.care_profile_photo);
                name=(TextView) findViewById(R.id.care_nick_name) ;
                time=(TextView) findViewById(R.id.care_share_time) ;
                data=(TextView) findViewById(R.id.care_share_content) ;
                zan=(TextView) findViewById(R.id.care_zan_num) ;
                tiaozhuan=(TextView) findViewById(R.id.care_do_num) ;
                listView = (ListView) view.findViewById(R.id.care_pinglun_list);
                ladapter = new ListViewAdapter(mContext);
                listView.setAdapter(ladapter);
                gridView = (GridView) view.findViewById(R.id.care_gridview_picture);
                gadapter = new GridViewAdapter(mContext);
                gridView.setAdapter(gadapter);
                view.setTag(this);
            }
        }
    }

    public class ListViewAdapter extends BaseAdapter {

        //数据源
        private Cursor mList;

        private Context mContext;

        public ListViewAdapter(Context context) {
            super();
            this.mContext = context;
        }

        public Cursor getmList() {
            return mList;
        }

        public void setmList(Cursor mList) {
            this.mList = mList;
        }

        @Override
        public int getCount() {
            try{
                return mList.getCount();
            }catch (Exception e){
                return 0;
            }
        }

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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            try{
                holder.comment.setText(getItem(position).getString(3));
                holder.name.setText(getItem(position).getString(2));
            }catch (Exception e){}
            return convertView;
        }

        class ViewHolder {

            TextView name;
            TextView comment;

            public ViewHolder(View view) {
                name = (TextView) view.findViewById(R.id.comment_nick_name);
                comment = (TextView) view.findViewById(R.id.comment_content);
                view.setTag(this);
            }
        }
    }
    public class GridViewAdapter extends BaseAdapter {

        //数据源
        private Cursor mList ;

        private Context mContext;

        public GridViewAdapter(Context context) {
            super();
            this.mContext = context;
        }

        public Cursor getmList() {
            return mList;
        }

        public void setmList(Cursor mList) {
            this.mList = mList;
        }

        @Override
        public int getCount() {
            try{
                return mList.getCount();
            }catch (Exception e){
                return 0;
            }
        }

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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_img, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            try{
                holder.iv.setImageURI(Uri.parse(getItem(position).getString(2)));
            }catch (Exception e){}
            return convertView;
        }

        class ViewHolder {

            ImageView iv;

            public ViewHolder(View view) {
                iv = (ImageView) view.findViewById(R.id.item_img_image);
                view.setTag(this);
            }
        }
    }

    public void goToMap(View view){
        Intent intent=new Intent(this,MapActivity.class);
        Intent this_intent = getIntent();
        intent.putExtra("user",this_intent.getStringExtra("user"));
        startActivity(intent);
    }
    public void goToGroup(View view){
        Intent intent=new Intent(this,GroupActivity.class);
        Intent this_intent = getIntent();
        intent.putExtra("user",this_intent.getStringExtra("user"));
        startActivity(intent);
    }

    public class remenViewAdapter extends BaseAdapter {

        //数据源
        private Cursor mList;

        //列数

        private Context mContext;

        public remenViewAdapter(Context context,Cursor item) {
            super();
            this.mContext = context;
            this.mList = item;
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
            return  mList;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_hot, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //更新数据源(核心)

            getItem(position);
            String ss;
            try {
                ss=mList.getString(7);
            }catch (Exception e){ss="0";}
            Cursor c=sql.select_pic(ss);
            c.moveToNext();
            try{
                holder.img.setImageURI(Uri.parse(c.getString(2)));
                holder.icon.setImageURI(Uri.parse(mList.getString(2)));
                holder.word.setText(mList.getString(4));
                holder.zan.setText(mList.getString(5));
            }catch (Exception e){}
            return convertView;
        }

        class ViewHolder {
            ImageView img;
            ImageView icon;
            TextView  word;
            TextView  zan;

            public ViewHolder(View view) {
                img=(ImageView) findViewById(R.id.hot_item_picture);
                icon=(ImageView) findViewById(R.id.hot_item_profile_photo);
                word=(TextView) findViewById(R.id.hot_item_content_shortcut) ;
                zan=(TextView) findViewById(R.id.hot_zan_num) ;
                view.setTag(this);
            }
        }
    }
}

