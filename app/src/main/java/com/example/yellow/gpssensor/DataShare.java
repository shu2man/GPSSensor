package com.example.yellow.gpssensor;

import android.app.Application;
import android.graphics.Bitmap;
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

    private List<home_page.friends> Friends;
    private List<String> Name;
    private List<String> Word;
    private List<String> Time;
    private List<Bitmap> Cimg;

    public DataShare(){

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

    public void initFriends(){
        Friends=new ArrayList<>();
    }
    public void initFriendsData(){

    }
    public List<home_page.friends> findFriendsFromCloud(){

        BmobQuery<home_page.friends> cond1=new BmobQuery<>();
        BmobQuery<home_page.friends> cond2=new BmobQuery<>();
        cond1.addWhereEqualTo("id1",userid);
        cond2.addWhereEqualTo("id2",userid);
        List<BmobQuery<home_page.friends>> orQuery=new ArrayList<BmobQuery<home_page.friends>>();
        orQuery.add(cond1);
        orQuery.add(cond2);
        BmobQuery<home_page.friends> mainQuery=new BmobQuery<>();
        mainQuery.or(orQuery);
        mainQuery.findObjects(new FindListener<home_page.friends>() {
            @Override
            public void done(List<home_page.friends> list, BmobException e) {
                if(e==null&&list!=null){

                }
            }
        });
    }



}