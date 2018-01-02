package com.example.yellow.gpssensor;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
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
import android.widget.StackView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus2 on 2017/12/29.
 */

public class home_page extends AppCompatActivity {
    List<Item> li=new ArrayList<>();
    List<Itemg> lig=new ArrayList<>();
    FatherViewAdapter listAdapter = new FatherViewAdapter(this,li);
    public class Item{
        int img;
        String  name;
        String  time;
        String  data;
        String  zan;
        String  tiaozhuan;
        List<Iteml> ladapter;
        List<Itemg> gadapter;
    }
    public class Iteml{
        String name;
        String comment;
    }
    public class Itemg{
        int img;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Thread thread1 = new Thread(){
            @Override
            public void run(){
                init_girdview();
            }
        };
        thread1.start();
        Thread thread2 = new Thread(){
            @Override
            public void run(){
                init_homepage();
            }
        };
        thread2.start();
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
    private void init_girdview()
    {
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
        final GridView view_remen=(GridView)findViewById(R.id.homepage_rimen_grid);
        SimpleAdapter gridAdapter = new SimpleAdapter(this,listitems,R.layout.item_with_me,
                new String[]{"imn"},
                new int[]{R.id.care_profile_photo});
        view_remen.setAdapter(gridAdapter);
        final ListView list_guanzhu=(ListView)findViewById(R.id.homepage_guanzhu_list);
        Itemg ijij = new Itemg();
        ijij.img=R.mipmap.weixin;
        lig.add(ijij);
        lig.add(ijij);
        lig.add(ijij);
        lig.add(ijij);
        lig.add(ijij);
        lig.add(ijij);
        list_guanzhu.setAdapter(listAdapter);
        Thread thread = new Thread(){
            @Override
            public void run(){
                for(int i = 0 ; i < 5 ; i++){
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){}
                        mh.obtainMessage(233).sendToTarget();
                }
            }
        };
        thread.start();
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
    }

    public class FatherViewAdapter extends BaseAdapter {

        //数据源
        private List<Item> mList = new ArrayList<>();

        //列数

        private Context mContext;

        public FatherViewAdapter(Context context, List<Item> list) {
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
                return mList.size();
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
        public Item getItem(int position) {
            return mList.get(position);
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
                holder.img.setBackgroundResource(getItem(position).img);
            }catch (Exception e){}
            try{
                holder.name.setText(getItem(position).name);
            }catch (Exception e){}
            try{
                holder.time.setText(getItem(position).time);
            }catch (Exception e){}
            try{
                holder.data.setText(getItem(position).data);
            }catch (Exception e){}
            try{
                holder.zan.setText(getItem(position).zan);
            }catch (Exception e){}
            try{
                holder.tiaozhuan.setText(getItem(position).tiaozhuan);
            }catch (Exception e){}
            try{
                holder.ladapter.setmList(getItem(position).ladapter);
            }catch (Exception e){}
            try{
                holder.gadapter.setmList(getItem(position).gadapter);
            }catch (Exception e){}
            try{
                holder.gadapter.notifyDataSetChanged();
                holder.ladapter.notifyDataSetChanged();
            }catch (Exception e){}
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
        private List<Iteml> mList = new ArrayList<>();

        private Context mContext;

        public ListViewAdapter(Context context) {
            super();
            this.mContext = context;
        }

        public List<Iteml> getmList() {
            return mList;
        }

        public void setmList(List<Iteml> mList) {
            this.mList = mList;
        }

        @Override
        public int getCount() {
            try{
                return mList.size();
            }catch (Exception e){
                return 0;
            }
        }

        @Override
        public Iteml getItem(int position) {
            return mList.get(position);
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
                holder.comment.setText(getItem(position).comment);
            }catch (Exception e){}
            try{
                holder.name.setText(getItem(position).name);
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
        private List<Itemg> mList = new ArrayList<>();

        private Context mContext;

        public GridViewAdapter(Context context) {
            super();
            this.mContext = context;
        }

        public List<Itemg> getmList() {
            return mList;
        }

        public void setmList(List<Itemg> mList) {
            this.mList = mList;
        }

        @Override
        public int getCount() {
            try{
                return mList.size();
            }catch (Exception e){
                return 0;
            }
        }

        @Override
        public Itemg getItem(int position) {
            return mList.get(position);
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
                holder.iv.setImageResource(getItem(position).img);
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
    final Handler mh = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what==233)
            {
                try{
                    {
                        List<Iteml> lil=new ArrayList<>();
                        for(int k = 0 ; k < 15 ; k++)
                        {
                            Iteml iiii = new Iteml();
                            iiii.name=Integer.toString(k);
                            iiii.comment="sgdg";
                            lil.add(iiii);
                        }
                        Item ii = new Item();
                        ii.img=R.mipmap.weixin;
                        ii.name="asasda";
                        ii.ladapter=lil;
                        ii.gadapter=lig;
                        li.add(ii);
                        li.add(ii);
                        li.add(ii);
                        li.add(ii);
                        li.add(ii);
                    }
                    Item ii = new Item();
                    ii.img=R.mipmap.qq;
                    li.add(ii);
                    listAdapter.notifyDataSetChanged();
                }
                catch (Exception e){}
            }
        }
    };

    public void goToMap(View view){
        Intent intent=new Intent(this,MapActivity.class);
        startActivity(intent);
    }
    public void goToGroup(View view){
        Intent intent=new Intent(this,GroupActivity.class);
        startActivity(intent);
    }
}

