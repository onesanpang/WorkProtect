package com.example.a23936.shoppingmall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login_but_getCode;
    private Button login_but_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //去除标题栏
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


        initView();

    }

    private void initView(){

        login_but_getCode = findViewById(R.id.login_but_getCode);
        login_but_login = findViewById(R.id.login_but_login);

        login_but_login.setOnClickListener(this);
        login_but_getCode.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_but_getCode:
                MyCountDownTime timer = new MyCountDownTime(60000,1000);
                timer.start();
                break;
            case R.id.login_but_login:
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    //button60s后点击的方法
    private class MyCountDownTime extends CountDownTimer{
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //改变button的颜色
            login_but_getCode.setTextColor(Color.parseColor("#CCCCCC"));
            //设置不让button再次点击
            login_but_getCode.setClickable(false);
            //在button上面显示时间
            login_but_getCode.setText(millisUntilFinished/1000+"s");
        }

        //60秒后可结束onTick可点击事件
        @Override
        public void onFinish() {
            login_but_getCode.setText("重新获取");
            //可再次点击
            login_but_getCode.setClickable(true);
            login_but_getCode.setTextColor(Color.parseColor("#0da0f2"));
        }
        //改变button的颜色

    }
}
