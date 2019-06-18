package com.example.a23936.shoppingmall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FirstActivity extends AppCompatActivity {

    private ImageView imageView;
    private SQLiteDatabase db;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //去除标题栏
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        imageView = findViewById(R.id.first_image_goods);

        sharedPreferences = getSharedPreferences("pic",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getImage();
        intoDb();
        getBannerImages(Const.URL + "/content/89");
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
                //Log.e("FitstActivity",getSharedPreferences("user",Context.MODE_PRIVATE).getString("username","")+"123");

                    startActivity(new Intent(FirstActivity.this,LoginActivity.class));
                    finish();

                return false;
            }
        }).sendEmptyMessageDelayed(0,5000);
    }

    private void intoDb(){


        db = openOrCreateDatabase("data.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists phoneq(_id integer primary key autoincrement," +
                "name text not null,introduce text not null,price text not null)");


        String sql1 = "insert into phoneq(name,introduce,price) values('Redmi 7','4000Ah超长续航','¥699起')";
        String sql2 = "insert into phoneq(name,introduce,price) values('Redmi Note 7','4800万拍照千元机','¥999起')";
        String sql3 = "insert into phoneq(name,introduce,price) values('Redmi Note 7 Pro','索尼4800万超清拍照','¥1599')";
        String sql4 = "insert into phoneq(name,introduce,price) values('小米 9SE','索尼4800万三摄骁龙712','¥1999起')";

        for(int i=0;i<5;i++) {

            db.execSQL(sql1);
            db.execSQL(sql2);
            db.execSQL(sql3);
            db.execSQL(sql4);
        }

    }

    //获取首页轮播图的图片
    private void getBannerImages(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String[] picUrl = new String[3];
                    String[] msgUrl = new String[3];
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(path)
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("FirstActivity", responseData);


                    JSONArray data = new JSONObject(responseData).getJSONArray("data");

                    for(int i=0;i<data.length();i++){
                        JSONObject object = (JSONObject) data.get(i);

                        String url = object.getString("url");
                        String pic = object.getString("pic");

                        Log.e("FirstActivity",url+"_______"+pic+"______"+i);
                        picUrl[i] = pic;
                        msgUrl[i] = url;
                    }

                    Log.e("FirstActivity",picUrl.length+"");
                    editor.putString("banner1",picUrl[0]);
                    editor.putString("banner2",picUrl[1]);
                    editor.putString("banner3",picUrl[2]);
                    editor.commit();


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
