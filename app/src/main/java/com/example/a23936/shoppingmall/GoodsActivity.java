package com.example.a23936.shoppingmall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;

    private ImageView image_love;

    private Button but_add;

    private FloatingActionButton floatingActionButton;
    private Banner banner;
    private ArrayList<String> images;

    private LinearLayout linear_collection;
    private LinearLayout linear_guige;


    private String[] data;

    private TextView text_name;
    private TextView text_text;
    private TextView text_canshu;
    private TextView text_price;


    private TextView text_color1;
    private TextView text_color2;
    private TextView text_color3;


    private Intent intent;


    private RewritePopwindow mPopwindow;

    private Handler mHandler = new Handler(Looper.myLooper());


//    private String image1Url = "https://i8.mifile.cn/a1/pms_1550646262.14555118.jpg";
//    private String image2Url = "https://i8.mifile.cn/a1/pms_1550646283.24414368.jpg";
//    private String image3Url = "https://i8.mifile.cn/a1/pms_1550646297.53454858.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initView();


        data = new String[]{"小米9 SE", "小米9 SE 6GB+128GB 深空灰", "¥1999"};
        text_name.setText(data[0]);
        text_canshu.setText(data[1]);
        text_price.setText(data[2]);

        getPhoneMessage();


        linear_guige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopwindow = new RewritePopwindow(GoodsActivity.this, itemsOnClick);
                mPopwindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

    }

    //获取homepage界面传过来的数据
    public void getPhoneMessage() {
        text_name.setText(intent.getStringExtra("name"));
        text_text.setText(intent.getStringExtra("text"));
        text_price.setText(intent.getStringExtra("price"));

    }

    public void initView() {
        intent = getIntent();
        imageView1 = findViewById(R.id.goods_image_introduce1);
        imageView2 = findViewById(R.id.goods_image_introduce2);
        imageView3 = findViewById(R.id.goods_image_introduce3);

        image_love = findViewById(R.id.goods_image_love);

        but_add = findViewById(R.id.goods_but_shopping);

        but_add.setOnClickListener(this);

        text_name = findViewById(R.id.goods_text_name);
        text_text = findViewById(R.id.goods_text_text);
        text_canshu = findViewById(R.id.goods_text_canshu);
        text_price = findViewById(R.id.goods_text_price);

        text_color1 = findViewById(R.id.popupwindow_text_color1);
        text_color2 = findViewById(R.id.popupwindow_text_color2);
        text_color3 = findViewById(R.id.popupwindow_text_color3);

        linear_collection = findViewById(R.id.goods_linear_love);
        linear_guige = findViewById(R.id.goods_linear_guige);

        linear_collection.setOnClickListener(this);

        image_love.setImageResource(R.mipmap.love);


        floatingActionButton = findViewById(R.id.goods_floatbut);
        banner = findViewById(R.id.goods_banner);

        floatingActionButton.setOnClickListener(this);

        images = new ArrayList<>();
        images.add(intent.getStringExtra("image"));
        images.add(intent.getStringExtra("image"));
        images.add(intent.getStringExtra("image"));

        banner.setImageLoader(new MyLoader());
        banner.setImages(images);
        banner.isAutoPlay(false);
        banner.start();

        getImage(intent.getStringExtra("image"), imageView1);
        getImage(intent.getStringExtra("image"), imageView2);
        getImage(intent.getStringExtra("image"), imageView3);


    }

    //获取网络图片
    public void getImage(String imageUrl, ImageView imageView) {
        Glide.with(this).load(imageUrl).into(imageView);
    }

    //转跳收藏活动
    public void startCollection(String[] data) {
        Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(GoodsActivity.this, CollectionActivity.class);
        intent.putExtra("name", data[0]);
        intent.putExtra("canshu", data[1]);
        intent.putExtra("price", data[2]);
        image_love.setImageResource(R.mipmap.shoucang);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_floatbut:
                finish();
                break;
            case R.id.goods_linear_love:
                startCollection(data);
                break;
            case R.id.goods_but_shopping:
                addGoodsMall(Const.URL+"/cart");
                Toast.makeText(this, "添加购物车成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void clearTextColor() {
        text_color1.setTextColor(Color.parseColor("#303030"));
        text_color2.setTextColor(Color.parseColor("#303030"));
        text_color3.setTextColor(Color.parseColor("#303030"));
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            mPopwindow.backgroundAlpha(GoodsActivity.this,1f);

            switch (v.getId()) {
                case R.id.popupWindow_linear_one:
                    Toast.makeText(GoodsActivity.this, "popupWindow_linear_one", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.popupwindow_linear_two:
                    Toast.makeText(GoodsActivity.this, "popupWindow_linear_two", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.popupwindow_text_color1:
                    clearTextColor();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            text_color1.setTextColor(Color.parseColor("#0da0f2"));
                            Toast.makeText(GoodsActivity.this, "深空灰", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                case R.id.popupwindow_text_color2:
                    clearTextColor();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            text_color2.setTextColor(Color.parseColor("#0da0f2"));
                            Toast.makeText(GoodsActivity.this, "全息幻彩紫", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                case R.id.popupwindow_text_color3:
                    clearTextColor();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            text_color3.setTextColor(Color.parseColor("#0da0f2"));
                            Toast.makeText(GoodsActivity.this, "全息幻彩蓝", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    //加入购物车
    private void addGoodsMall(final String path){


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("id",intent.getStringExtra("idGoods"))
                            .add("count","1")
                            .build();
                    Request request = new Request.Builder()
                            .addHeader("cookie",getSharedPreferences("user", Context.MODE_PRIVATE).getString("cookie",""))
                            .url(path)
                            .post(formBody)
                            .build();

                    Response response = client.newCall(request).execute();
                    Log.e("goods",response.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

}
