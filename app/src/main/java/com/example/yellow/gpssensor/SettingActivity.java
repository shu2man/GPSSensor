package com.example.yellow.gpssensor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Yellow on 2018-1-3.
 */

public class SettingActivity extends Activity {
    private String name;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUserInfo();
    }
    public void initUserInfo(){
        //获取用户头像、昵称和签名
        DataShare ds=((DataShare)getApplicationContext());
        name=ds.getUsername();
    }


    public void setProfilePhoto(View view){
        LayoutInflater factor=LayoutInflater.from(SettingActivity.this);
        View dialog=factor.inflate(R.layout.dialog_set_photo,null);
        final AlertDialog.Builder alertdialog=new AlertDialog.Builder(SettingActivity.this);
        alertdialog.setView(dialog);
        alertdialog.setTitle("修改头像");


        alertdialog.show();
    }
    public void setNameAndSig(View view){
        LayoutInflater factor=LayoutInflater.from(SettingActivity.this);
        View dialog=factor.inflate(R.layout.dialog_set_name,null);
        final AlertDialog.Builder alertdialog=new AlertDialog.Builder(SettingActivity.this);
        alertdialog.setView(dialog);
        alertdialog.setTitle("修改个人信息");

        final EditText etn=(EditText)findViewById(R.id.dialog_name_edit);
        final EditText etc=(EditText)findViewById(R.id.characteristis_edit);

        alertdialog.setPositiveButton("确认修改",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateUserInfo(etn.getText().toString(),etc.getText().toString());
            }
        });
        alertdialog.setNegativeButton("放弃修改",null);

        alertdialog.show();
    }
    public void aboutUsDialog(View view){
        LayoutInflater factor=LayoutInflater.from(SettingActivity.this);
        View dialog=factor.inflate(R.layout.dialog_about_us,null);
        final AlertDialog.Builder alertdialog=new AlertDialog.Builder(SettingActivity.this);
        alertdialog.setView(dialog);
        alertdialog.show();
    }

    public void updateUserInfo(String name,String character){

    }

    public void logOut(View view){
        sharedPreferences=getApplicationContext().getSharedPreferences("MyPreference",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent=new Intent(this,login.class);
        startActivity(intent);
        this.finish();
    }
    public void backToMap(View view){
        Intent intent=new Intent(this,MapActivity.class);
        startActivity(intent);
        this.finish();
    }

}
