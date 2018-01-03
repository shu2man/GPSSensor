package com.example.yellow.gpssensor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private boolean isFirstLaunch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkShouldRegister();
        init_login();
    }
    private void init_login()
    {
        final Button unlogin = (Button)findViewById(R.id.login_no_register_button);
        unlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });
    }
    public void checkShouldRegister(){
        sharedPreferences=getApplicationContext().getSharedPreferences("MyPreference",MODE_PRIVATE);
        isFirstLaunch=sharedPreferences.getBoolean("isFirstLaunch",true);
        if(!isFirstLaunch){
            goToHome();
        }
        else{
            //Guide The User To Register In
        }
    }
    public void goToHome(){
        Intent intent = new Intent(login.this,home_page.class);
        startActivity(intent);
        login.this.finish();
    }

    public void registerVisibility(int mode){//1-exist user login,  2-new user register
        ConstraintLayout CL1=(ConstraintLayout)findViewById(R.id.login_alternative_container);
        TextView tv=(TextView)findViewById(R.id.confirm_password_text);
        EditText et=(EditText)findViewById(R.id.confirm_password_edit);
        Button btn=(Button)findViewById(R.id.login_btn);
        if(mode==1){
            CL1.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
            et.setVisibility(View.GONE);
            btn.setText("登录");
        }
        else if(mode==2){
            CL1.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
            et.setVisibility(View.VISIBLE);
            btn.setText("完成");
        }
    }
    public void goToRegister(View view){
        registerVisibility(2);
    }
    public void RegisterIn(View view){
        EditText et_confirm=(EditText)findViewById(R.id.confirm_password_edit);
        EditText et_new=(EditText)findViewById(R.id.login_password_edit);
        EditText et_name=(EditText)findViewById(R.id.login_name_edit);
        if(et_confirm.getText().toString().equals("") || et_new.getText().toString().equals("")){
            Toast.makeText(this,"Password Incomplete",Toast.LENGTH_SHORT).show();
        }
        //保存用户名和密码
        else if(et_confirm.getText().toString().equals(et_new.getText().toString())){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("isFirstLaunch",false);
            editor.putString("Password",et_confirm.getText().toString());
            editor.putString("Username",et_name.getText().toString());
            editor.apply();

            Intent intent=new Intent(this,home_page.class);
            startActivity(intent);
            DataShare ds=((DataShare)getApplicationContext());
            ds.setUsername(sharedPreferences.getString("Username","游客"));
            Toast.makeText(this,sharedPreferences.getString("Username","游客"),Toast.LENGTH_SHORT).show();
            this.finish();
        }
        else{
            Toast.makeText(this,"Inconsistent Password!",Toast.LENGTH_SHORT).show();
        }
/*        if(isFirstLaunch){
            EditText et_confirm=(EditText)findViewById(R.id.confirm_password_edit);
            EditText et_new=(EditText)findViewById(R.id.login_password_edit);
            if(et_confirm.getText().toString().equals("") || et_new.getText().toString().equals("")){
                Toast.makeText(this,"Password Incomplete",Toast.LENGTH_SHORT).show();
            }
            else if(et_confirm.getText().toString().equals(et_new.getText().toString())){
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("isFirstLaunch",false);
                editor.putString("Password",et_confirm.getText().toString());
                editor.apply();

                Intent intent=new Intent(this,home_page.class);
                startActivity(intent);
                this.finish();
            }
            else{
                Toast.makeText(this,"Inconsistent Password!",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            String psd=sharedPreferences.getString("Password","null");
            EditText et=(EditText)findViewById(R.id.login_password_edit);
            if(et.getText().toString().equals("")){
                Toast.makeText(this,"Empty Password",Toast.LENGTH_SHORT).show();
            }
            else if(et.getText().toString().equals(psd)){
                Intent intent=new Intent(this,home_page.class);
                startActivity(intent);
                this.finish();
            }
            else{
                Toast.makeText(this,"Wrong Password",Toast.LENGTH_SHORT).show();
            }
        }*/

    }


}