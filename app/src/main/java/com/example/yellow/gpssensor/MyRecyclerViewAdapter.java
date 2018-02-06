package com.example.yellow.gpssensor;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Time;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;


/*
*2017.10.20 yellow
* 参考自 http://www.jianshu.com/p/f2e0463e5aef
 */


/**
 * Created by Yellow on 2017-10-20.
 */

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{
    private List<String> Name;
    private List<String> Word;
    private List<String> Time;
    private List<Bitmap> Cimg;
    private DataShare ds;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }


    public MyRecyclerViewAdapter(DataShare context){
        ds=context;
        initData();
    }
    public void initData() {
        Name=new ArrayList<>();
        Word=new ArrayList<>();
        Time=new ArrayList<>();
        Cimg=new ArrayList<>();

        Name=ds.getFriendsNameList();
        Word=ds.getFriendsWordList();
        Time=ds.getFriendsTimeList();
        Cimg=ds.getFriendsCimgList();
    }
    //创建ChildView
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_in_homepage, parent, false);
        return new MyViewHolder(layout);//from(this)
    }

    //将数据绑定到每一个childView中
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.nametv.setText(Name.get(position));
        holder.timetv.setText(Time.get(position));
        holder.wordtv.setText(Word.get(position));
        holder.cimgv.setImageBitmap(Cimg.get(position));
/*        holder.title.setText(Name.get(position));
        holder.iconletter.setText(Name.get(position).substring(0,1));*/
        //点击事件
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                //为ItemView设置监听器
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
        //长按事件
        if(mOnItemLongClickListener!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    int position=holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView,position);
                    return true;//返回true 表示消耗了事件，若为false则还将执行短按click事件
                }
            });
        }
    }

    //得到child的数量
    @Override
    public int getItemCount() {
        return Name.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nametv;
        TextView timetv;
        TextView wordtv;
        de.hdodenhof.circleimageview.CircleImageView cimgv;
        private MyViewHolder(View view){
            super(view);
            nametv=(TextView)view.findViewById(R.id.name);
            timetv=(TextView)view.findViewById(R.id.last_send_time);
            wordtv=(TextView)view.findViewById(R.id.send_message);
            cimgv=(CircleImageView)view.findViewById(R.id.avatar2);
        }

/*        TextView title;
        TextView iconletter;
        private MyViewHolder(View view) {
            super(view);
            title= (TextView) view.findViewById(R.id.goods_name);
            iconletter=(TextView) view.findViewById(R.id.goods_icon_letter);
        }*/
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener){
        this.mOnItemLongClickListener=mOnItemLongClickListener;
    }

}
