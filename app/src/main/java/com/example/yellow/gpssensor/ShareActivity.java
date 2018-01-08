package com.example.yellow.gpssensor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.*;

/**
 * Created by Yellow on 2018-1-2.
 */

//AppSecret: 71d51701664b52196a58c0d3a3ce37c9

public class ShareActivity extends Activity {
    private String type;
    private String lastActivity;
    private EditText content;

    //登录微信的应用ID
    private static final String APP_ID="wxd8e1494ccbebbd1f";
    //和微信通信的openapi接口
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        type=getIntent().getStringExtra("type");
        lastActivity=getIntent().getStringExtra("last");
        if(lastActivity==null) lastActivity="";

        content=(EditText)findViewById(R.id.share_edit_text);

        shareContentInit();
    }
    public void shareContentInit(){

    }

    public void share(View view){
        //save and update listviews and gridviews
        switch (view.getId()){
            case R.id.share_share_btn:

                break;

            case R.id.share_others_pengyouquang:
                String contentText=content.getText().toString();
                api= WXAPIFactory.createWXAPI(this,APP_ID,true);
                //api.registerApp(APP_ID);//注册到微信
                if(!api.isWXAppInstalled()){
                    Toast.makeText(this,"未检测到微信客户端",Toast.LENGTH_SHORT).show();
                    return;
                }

                WXTextObject textObject=new WXTextObject();
                textObject.text=contentText;

                //要发送的图片
                Bitmap bmp= BitmapFactory.decodeResource(getResources(),R.drawable.jiqu_hd);
                //初始化WXImageObject对象
                WXImageObject imageObject=new WXImageObject(bmp);
                //初始化WXMediaMessage对象
                WXMediaMessage msg=new WXMediaMessage();
                msg.mediaObject=imageObject;
                msg.description=contentText;
                //msg.setThumbImage(bmp);

                //构造Req,微信处理完返回应用
                SendMessageToWX.Req req=new SendMessageToWX.Req();
                //设置发送场景，WXSceneSession聊天界面，WXSceneTimeline朋友圈，WXSceneFavorite收藏
                req.scene=SendMessageToWX.Req.WXSceneTimeline;
                //transaction用于唯一标识一个字段
                req.transaction=String.valueOf(System.currentTimeMillis());
                req.message=msg;

                //调用api发送消息到微信
                api.sendReq(req);

                break;
        }
    }
    public void backToLast(View view){
        switch (lastActivity){
            case "Map":
                Intent intent1=new Intent(this,MapActivity.class);
                startActivity(intent1);
                this.finish();
                break;
            case "Home":
                Intent intent2=new Intent(this,home_page.class);
                startActivity(intent2);
                this.finish();
                break;
            case "Quanzi":
                Intent intent3=new Intent(this,GroupActivity.class);
                startActivity(intent3);
                this.finish();
                break;
            default:
                Intent intent4=new Intent(this,home_page.class);
                startActivity(intent4);
                this.finish();
                break;
        }
    }
}
