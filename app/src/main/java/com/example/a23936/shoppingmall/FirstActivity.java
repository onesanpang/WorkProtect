package com.example.a23936.shoppingmall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class FirstActivity extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //去除标题栏
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        imageView = findViewById(R.id.first_image_goods);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getImage();
        startNextActivity();
    }
    public void getImage(){
        Glide.with(getApplicationContext()).load("https://i1.mifile.cn/a4/xmad_15021710762698_QXnBu.jpg").into(imageView);

    }
    //自动延迟2秒转跳活动
    public void startNextActivity(){
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                startActivity(new Intent(FirstActivity.this,LoginActivity.class));
                finish();
                return false;
            }
        }).sendEmptyMessageDelayed(0,4000);
    }

}
