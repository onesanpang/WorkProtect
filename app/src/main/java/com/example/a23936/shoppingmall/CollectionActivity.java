package com.example.a23936.shoppingmall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CollectionActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;
    private List<Phone> phoneList = new ArrayList<>();

    private RecyclerView recyclerView;
    private PhoneAdapterC adapterC;


    private String[] data = new String[3];
    private Bitmap bitmaps;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        //去除标题栏
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initView();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterC = new PhoneAdapterC(phoneList);
        adapterC.setOnItemClickListener(new PhoneAdapterC.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(CollectionActivity.this, "你点击了"+position, Toast.LENGTH_SHORT).show();
            }
        });
        adapterC.setOnitemLongClickListener(new PhoneAdapterC.OnItemLongClickListener() {
            @Override
            public void onLongClick(int position) {
                Toast.makeText(CollectionActivity.this, "你长按了"+position, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapterC);

        //Phone phone = new Phone(bitmaps,data[0],data[1],data[2]);
        //phoneList.add(phone);
        adapterC.notifyItemInserted(phoneList.size());
        adapterC.notifyItemChanged(phoneList.size());

    }

    public void initView(){
        imageView = findViewById(R.id.collection_image_back);
        recyclerView = findViewById(R.id.collection_recyclerview);

        imageView.setOnClickListener(this);

        Intent intent = getIntent();

        data[0] = intent.getStringExtra("name");
        data[1] = intent.getStringExtra("canshu");
        data[2] = intent.getStringExtra("price");
        bitmaps = getBitmapImage("https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/d729b57d7eadb4d672cfdc7e7b05648e.jpg?w=1212&h=716");




    }




    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.collection_image_back:
                finish();
                break;
            default:
                break;
        }
    }

    //Glide加载网络图片(Bitmap)
    private Bitmap getBitmapImage(final String path) {
        final Bitmap[] bitmap = {null};
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    //获取网络连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //使用GET方法访问网络
                    connection.setRequestMethod("GET");
                    //超时时间为0.5秒
                    connection.setConnectTimeout(500);
                    //获取返回码
                    if (connection.getResponseCode() == 200){
                        InputStream inputStream = connection.getInputStream();
                        //使用工厂把网络的输入流生产Bitmap
                        bitmap[0] = BitmapFactory.decodeStream(inputStream);
                        countDownLatch.countDown();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap[0];
    }
}
