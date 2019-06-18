package com.example.a23936.shoppingmall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONObject;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //private Button login_but_getCode;
    private Button login_but_login;
    private TextView login_text_regriester;
    private EditText login_edit_userName;
    //private EditText login_edit_code;
    private EditText login_edit_password;
    private String code;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Handler mHandler = new Handler(Looper.getMainLooper());


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

        //login_but_getCode = findViewById(R.id.login_but_getCode);
        login_but_login = findViewById(R.id.login_but_login);

        login_text_regriester = findViewById(R.id.login_text_regriester);

        login_edit_userName = findViewById(R.id.login_edit_number);
        //login_edit_code = findViewById(R.id.login_edit_code);
        login_edit_password = findViewById(R.id.login_edit_password);

        login_but_login.setOnClickListener(this);
        //login_but_getCode.setOnClickListener(this);

        login_text_regriester.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        if (!getSharedPreferences("user",Context.MODE_PRIVATE).getString("cookie","").equals("null")){
            login_edit_userName.setText(getSharedPreferences("user",Context.MODE_PRIVATE).getString("username",""));
            login_edit_password.setText(getSharedPreferences("user",Context.MODE_PRIVATE).getString("password",""));
        }

    }




    @Override
    public void onClick(View v) {
        switch(v.getId()){
//            case R.id.login_but_getCode:
//                MyCountDownTime timer = new MyCountDownTime(60000,1000);
//                timer.start();

            case R.id.login_but_login:
//                if (login_edit_userName.getText().toString().equals("")||login_edit_code.getText().toString().equals("")){
//                    Toast.makeText(this, "请输入正确的电话和验证码", Toast.LENGTH_SHORT).show();
//                }else{
//                    editor.putString("number",login_edit_userName.getText().toString());
//                    editor.commit();
//                    Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                    finish();
//                }

                sendMessage(Const.URL+"/user/login");
                break;
            case R.id.login_text_regriester:
                startActivity(new Intent(this,RegriesterActivity.class));
                break;
            default:
                break;
        }
    }


    private void sendMessage(final String path){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    FormBody requestBody = new FormBody.Builder()
                            .add("username",login_edit_userName.getText().toString())
                            .add("password",login_edit_password.getText().toString())
                            .build();
                    //Log.e("login",login_edit_userName.getText().toString());
                    Request request = new Request.Builder()
                            .url(path)
                            .post(requestBody)
                            .build();

                    Response response = client.newCall(request).execute();
                    List<String> cookies = response.headers().values("Set-Cookie");
                    String sessoin = cookies.get(0);
                    String cookie = sessoin.substring(0,sessoin.indexOf(";"));
                    Log.e("login",cookie);

                    String responseData = response.body().string();
                    Log.e("response",responseData);
                    JSONObject jsonObject = new JSONObject(responseData);

                    String data = jsonObject.getString("data");
                    JSONObject dataJson = new JSONObject(data);
                    String username = dataJson.getString("username");
                    String email = dataJson.getString("email");
                    String phone = dataJson.getString("phone");


                    String header = jsonObject.getString("header");
                    JSONObject jsonObject1 = new JSONObject(header);
                    String msg = jsonObject1.getString("msg");

                    if (msg.equals("Success")){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        editor.putString("username",username);
                        editor.putString("email",email);
                        editor.putString("phone",phone);
                        editor.putString("cookie",cookie);
                        editor.putString("password",login_edit_password.getText().toString());
                        editor.commit();
                        finish();
                    }else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //button60s后点击的方法
//    private class MyCountDownTime extends CountDownTimer{
//        /**
//         * @param millisInFuture    The number of millis in the future from the call
//         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
//         *                          is called.
//         * @param countDownInterval The interval along the way to receive
//         *                          {@link #onTick(long)} callbacks.
//         */
//        public MyCountDownTime(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            //改变button的颜色
//            login_but_getCode.setTextColor(Color.parseColor("#CCCCCC"));
//            //设置不让button再次点击
//            login_but_getCode.setClickable(false);
//            //在button上面显示时间
//            login_but_getCode.setText(millisUntilFinished/1000+"s");
//            if (millisUntilFinished/1000 == 52){
//                login_edit_code.setText(madeCode());
//            }
//        }
//
//        //60秒后可结束onTick可点击事件
//        @Override
//        public void onFinish() {
//            login_but_getCode.setText("重新获取");
//            //可再次点击
//            login_but_getCode.setClickable(true);
//            login_but_getCode.setTextColor(Color.parseColor("#0da0f2"));
//        }
//        //改变button的颜色
//
//    }
//
//    private String madeCode(){
//        for(int i=0;i<=100;i++){
//            code = String.valueOf((Math.random()*9+1)*100000);
//        }
//        return code;
//    }
}
