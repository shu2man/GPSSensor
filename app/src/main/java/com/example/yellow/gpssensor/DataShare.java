package com.example.yellow.gpssensor;

import android.app.Application;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Yellow on 2018-1-3.
 */

public class DataShare extends Application {
    private String username;
    private String userid;

    private Bitmap snapShot;
    private String path;

    private List<BmobTable.friends> Friends;
    private List<String> Name;
    private List<String> Word;
    private List<String> Time;
    private List<Bitmap> Cimg;
    private List<BmobTable.user> FriUser;

    public DataShare(){
        //initFriends();
    }
    public void setUsername(String name){
        username=name;
    }
    public String getUserid(){
        return userid;
    }
    public void setUserid(String id){
        userid=id;
    }
    public String getUsername(){
        return username;
    }
    public void setSnapShot(Bitmap ss){
        snapShot=ss;
    }
    public Bitmap getSnapShot(){
        return snapShot;
    }
    public void setPath(String p){
        path=p;
    }
    public String getPath(){
        return path;
    }

    public List<String> getFriendsNameList(){
        return Name;
    }
    public List<String> getFriendsWordList(){
        return Word;
    }
    public List<String> getFriendsTimeList(){
        return Time;
    }
    public List<Bitmap> getFriendsCimgList(){
        return Cimg;
    }
    public List<BmobTable.user> getFriUser(){
        return FriUser;
    }


    public void initFriends(){
        if(Friends!=null) Friends.clear();
        if(Name!=null) Name.clear();
        if(Cimg!=null) Cimg.clear();
        if(Word!=null) Word.clear();
        if(Time!=null) Time.clear();
        if(FriUser!=null) FriUser.clear();

        Friends=new ArrayList<>();
        Name=new ArrayList<>();
        Cimg=new ArrayList<>();
        Word=new ArrayList<>();
        Time=new ArrayList<>();
        FriUser=new ArrayList<>();

        findFriendsFromCloud();
    }
    public void initFriendsData(){

    }
    public void findFriendsFromCloud(){
        //查找朋友关系
        BmobQuery<BmobTable.friends> cond1=new BmobQuery<BmobTable.friends>();
        BmobQuery<BmobTable.friends> cond2=new BmobQuery<>();
        cond1.addWhereEqualTo("id1",userid);
        cond2.addWhereEqualTo("id2",userid);
        List<BmobQuery<BmobTable.friends>> orQuery=new ArrayList<BmobQuery<BmobTable.friends>>();
        orQuery.add(cond1);
        //orQuery.add(cond2);
        BmobQuery<BmobTable.friends> mainQuery=new BmobQuery<>();
        mainQuery.or(orQuery);
        mainQuery.findObjects(new FindListener<BmobTable.friends>() {
            @Override
            public void done(List<BmobTable.friends> list, BmobException e) {
            if(e==null&&list!=null){
                Friends=list;
                Toast.makeText(DataShare.this,list.size()+" 条好友记录",Toast.LENGTH_SHORT).show();
            }
            }
        });

        //通过好友关系，查找好友信息
        List<BmobQuery<BmobTable.user>> orList=new ArrayList<>();
        for(int i=0;i<Friends.size();i++) {
            BmobQuery<BmobTable.user> eq=new BmobQuery<>();
            eq.addWhereEqualTo("objectId",Friends.get(i).getId2());
            orList.add(eq);
        }
        BmobQuery<BmobTable.user> mainQuery2=new BmobQuery<>();
        mainQuery2.or(orList);
        mainQuery2.findObjects(new FindListener<BmobTable.user>() {
            @Override
            public void done(List<BmobTable.user> list, BmobException e) {
            if(e==null&&list!=null){
                FriUser=list;
                Toast.makeText(DataShare.this,list.size()+" 个朋友",Toast.LENGTH_SHORT).show();
            }
            else if(list==null) Toast.makeText(DataShare.this,"list为空",Toast.LENGTH_SHORT).show();
            //Toast.makeText(DataShare.this,list.size()+"-2",Toast.LENGTH_SHORT).show();//donote incase of null list
            }
        });

        //接下来通过好友关系查找最近聊天记录
        for(int i=0;i<Friends.size();i++){
            Word.add(i+" 雷猴旁友！");
        }

    }
    public void findUserByObjectId(String id){
        BmobQuery<BmobTable.user> cond1=new BmobQuery<>();
        cond1.addWhereEqualTo("objectId",id);
        cond1.findObjects(new FindListener<BmobTable.user>() {
            @Override
            public void done(List<BmobTable.user> list, BmobException e) {
                if(e==null&&list!=null){;

                }
            }
        });
    }



}