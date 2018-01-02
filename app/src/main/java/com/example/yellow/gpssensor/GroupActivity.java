package com.example.yellow.gpssensor;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by asus2 on 2017/12/31.
 */

public class GroupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        init_group();
        init_adapter();
    }
    private void init_group()
    {
        final TextView dongtai_text = (TextView)findViewById(R.id.group_title_dongtai);
        final TextView withme_text = (TextView)findViewById(R.id.group_title_withme);
        final TextView sixin_text = (TextView)findViewById(R.id.group_title_sixin);
        final LinearLayout dongtai_view = (LinearLayout) findViewById(R.id.group_view_dongtai);
        final LinearLayout withme_view = (LinearLayout) findViewById(R.id.group_view_withme);
        final LinearLayout sixin_view = (LinearLayout) findViewById(R.id.group_view_sixin);
        View.OnClickListener dongtai_OnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dongtai_text.setTextColor((getBaseContext().getResources()).getColorStateList(R.color.listbgc));
                withme_text.setTextColor((getBaseContext().getResources()).getColorStateList(R.color.listbgc_half));
                sixin_text.setTextColor((getBaseContext().getResources()).getColorStateList(R.color.listbgc_half));
                dongtai_view.setVisibility(View.VISIBLE);
                withme_view.setVisibility(View.INVISIBLE);
                sixin_view.setVisibility(View.INVISIBLE);
            }
        };
        View.OnClickListener withme_OnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dongtai_text.setTextColor((getBaseContext().getResources()).getColorStateList(R.color.listbgc_half));
                withme_text.setTextColor((getBaseContext().getResources()).getColorStateList(R.color.listbgc));
                sixin_text.setTextColor((getBaseContext().getResources()).getColorStateList(R.color.listbgc_half));
                dongtai_view.setVisibility(View.INVISIBLE);
                withme_view.setVisibility(View.VISIBLE);
                sixin_view.setVisibility(View.INVISIBLE);
            }
        };
        View.OnClickListener sixin_OnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dongtai_text.setTextColor((getBaseContext().getResources()).getColorStateList(R.color.listbgc_half));
                withme_text.setTextColor((getBaseContext().getResources()).getColorStateList(R.color.listbgc_half));
                sixin_text.setTextColor(( getBaseContext().getResources()).getColorStateList(R.color.listbgc));
                dongtai_view.setVisibility(View.INVISIBLE);
                withme_view.setVisibility(View.INVISIBLE);
                sixin_view.setVisibility(View.VISIBLE);
            }
        };
        dongtai_text.setOnClickListener(dongtai_OnClick);
        withme_text.setOnClickListener(withme_OnClick);
        sixin_text.setOnClickListener(sixin_OnClick);
    }
    private void init_adapter()
    {

    }
    public void goToMap(View view){
        Intent intent=new Intent(this,MapActivity.class);
        startActivity(intent);
    }
    public void goToHome(View view){
        Intent intent=new Intent(this,home_page.class);
        startActivity(intent);
    }
    public void goToShare(View view){
        Intent intent=new Intent(GroupActivity.this,ShareActivity.class);
        intent.putExtra("type","none");
        intent.putExtra("last","Quanzi");
        startActivity(intent);
    }
}
